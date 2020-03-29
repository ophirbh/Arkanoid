package sprites;

import java.awt.Color;
import java.util.Random;
import biuoop.DrawSurface;
import geometryprimitives.Line;
import geometryprimitives.Point;
import management.GameEnvironment;
import management.CollisionInfo;
import abstracts.Border;
import driver.GameLevel;

/**
 * Represents a ball.
 */
public class Ball implements Sprite {

    public static final int DEF_SPEED = 100;
    public static final int DEF_LOW_SPEED = 1;

    private Point center;
    private int r;
    private Color color;
    private Velocity v;
    private Border border;
    private Line rTraj;
    private Line lTraj;
    private Line uTraj;
    private Line dTraj;
    private GameEnvironment gameEnvi;

    /**
     * Creates a new ball in the given location, and with the given radius
     * and color.
     * @param center The location point of the center of the ball.
     * @param r The radius of the ball.
     * @param color The color of the ball.
     * @param game The game environment of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment game) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.border = null;
        this.updateAllTraj();
        this.gameEnvi = game;
    }


    /**
     * Creates a new ball.
     * @param x The x coordinate of the ball's location.
     * @param y The y coordinate of the ball's location.
     * @param r The radius of the ball.
     * @param color The color of the ball.
     * @param game The game environment of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment game) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
        this.border = null;
        this.updateAllTraj();
        this.gameEnvi = game;
    }

    /**
     * Get the ball's X coordinate.
     * @return The ball's X coordinate.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Get the ball's Y coordinate.
     * @return The ball's Y coordinate.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Sets a legal default X coordinate (rad, y) for the ball using its
     * radius in case its initial value is illegal.
     * @param radius The ball's radius.
     */
    public void setDefX(int radius) {
        this.center.setX(radius);
    }

    /**
     * Sets a legal default Y coordinate (x, rad) for the ball using its
     * radius in case its initial value is illegal.
     * @param radius The ball's radius.
     */
    public void setDefY(int radius) {
        this.center.setY(radius);
    }

    /**
     * Get the ball's size. (it's radius).
     * @return The ball's size. (radius).
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Get the ball's color.
     * @return The ball's color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Draws the ball on the given surface.
     * @param surface The surface upon the ball will be drawn.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.RED);
        surface.fillCircle(this.getX(), this.getY(), 2);
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * Sets the ball's velocity to the given velocity.
     * @param velocity The ball's new velocity.
     */
    public void setVelocity(Velocity velocity) {
        this.v = velocity;
        this.updateAllTraj();
    }

    /**
     * Sets the ball's velocity to the given velocity, given by the changes
     * in position in both X and Y axis.
     * @param dx The change of position in the X axis.
     * @param dy The change of position in the Y axis.
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
        this.updateAllTraj();
    }

    /**
     * Gets the ball's velocity.
     * @return The ball's velocity.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * Move the ball one step.
     * @param dt the amount of seconds passed since the last call.
     */
    public void moveOneStep(double dt) {

        if (this.getBorder() != null) {
            if (this.getBorder().getLeftBorder().isIntersecting(this.lTraj)
                    || this.getBorder().getRightBorder().
                    isIntersecting(this.rTraj)) {
                this.setVelocity(-this.getVelocity().getDX(),
                        this.getVelocity().getDY());
            }
            if (this.getBorder().getUpperBorder().isIntersecting(this.uTraj)
                    || this.getBorder().getLowerBorder().
                    isIntersecting(this.dTraj)) {
                this.setVelocity(this.getVelocity().getDX(),
                        -this.getVelocity().getDY());
            }
        }

        Line trajectory = this.computeTrajectory(dt);
        CollisionInfo possibleCol = this.gameEnvi.getClosestCollision(trajectory);
        if (possibleCol == null) {
            Velocity decoupledVel = this.getVelocity().decoupleSpeed(dt);
            this.center = decoupledVel.applyToPoint(this.center);
        } else {
            this.v = possibleCol.collisionObject().hit(this, possibleCol.
                    collisionPoint(), this.v);
        }

        this.updateAllTraj();
    }

    /**
     * Updates the X axis left trajectory. A trajectory is a moving line
     * segment that simulates the ball's next left X axis step.
     */
    public void updateLTraj() {
        this.lTraj = new Line(this.getX(), this.getY(), this.getX()
                - this.getSize() + this.getVelocity().getDX(), this.getY());

    }

    /**
     * Updates the X axis right trajectory. A trajectory is a moving line
     * segment that simulates the ball's next right X axis step.
     */
    public void updateRTraj() {
        this.rTraj = new Line(this.getX(), this.getY(), this.getX()
                + this.getSize() + this.getVelocity().getDX(), this.getY());

    }

    /**
     * Updates the Y axis up trajectory. A trajectory is a moving line segment
     * that simulates the ball's next up Y axis step.
     */
    public void updateUTraj() {
        this.uTraj = new Line(this.getX(), this.getY(), this.getX(),
                this.getY() - this.getSize() + this.getVelocity().getDY());
    }

    /**
     * Updates the Y axis down trajectory. A trajectory is a moving line
     * segment that simulates the ball's next Y down axis step.
     */
    public void updateDTraj() {
        this.dTraj = new Line(this.getX(), this.getY(), this.getX(),
                this.getY() + this.getSize() + this.getVelocity().getDY());
    }

    /**
     * Get the ball's borders.
     * @return The ball's borders.
     */
    public Border getBorder() {
        return this.border;
    }

    /**
     * Set borders for the ball's movement.
     * @param bord The border.
     */
    public void setBorder(Border bord) {
        this.border = bord;
    }

    /**
     * Sets a ball velocity with accordance to its size. The bigger the ball -
     * the slower its speed. The velocity angle is random.
     */
    public void setVelocityBySize() {
        Random angle = new Random();

        Velocity vel = new Velocity(0, 0);
        if (this.getSize() > 50) {
            this.setVelocity(vel.fromAngleAndSpeed(angle.nextInt(360),
                    this.DEF_LOW_SPEED)); ; // limits are: 0-360
        } else {
            this.setVelocity(vel.fromAngleAndSpeed(angle.nextInt(360),
                    this.DEF_SPEED / this.getSize()));
        }
    }

    /**
     * Update all of ball's trajectories.
     */
    public void updateAllTraj() {
        this.updateDTraj();
        this.updateLTraj();
        this.updateRTraj();
        this.updateUTraj();
    }

    /**
     * Returns the ball's trajectory (A line from its current location to
     * where its speed will take him in the next move if no collisions will
     * occur).
     * @param dt specifies the amount of seconds passed since the last call.
     * @return The ball's trajectory.
     */
    public Line computeTrajectory(double dt) {
        double startX = this.getCenter().getX();
        double startY = this.getCenter().getY();
        Velocity decoupledVel = this.getVelocity().decoupleSpeed(dt);
        double endX = this.getCenter().getX() + decoupledVel.getDX();
        double endY = this.getCenter().getY() + decoupledVel.getDY();
        return new Line(startX, startY, endX, endY);
    }

    /**
     * Return the center point of the ball.
     * @return The center point of the ball.
     */
    public Point getCenter() {
        return new Point(this.center);
    }

    /**
     * Notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * Add sprite to game.
     * @param g The game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Removes the ball from the argument game.
     * @param game The game.
     * @return True if removed, false otherwise.
     */
    public boolean removeFromGame(GameLevel game) {
        return game.removeSprite(this);
    }

}

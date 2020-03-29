package sprites;
import geometryprimitives.Point;

/**
 * sprites.Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {

    private double dx;
    private double dy;

    /**
     * Creates a new velocity object. with the given changes in both X and Y
     * axis.
     * @param dx The change of position in the X axis.
     * @param dy The change of position in the Y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Get the change of position in the X axis.
     * @return The change of position in the X axis.
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * Get the change of position in the Y axis.
     * @return The change of position in the Y axis.
     */
    public double getDY() {
        return this.dy;
    }

    /**
     * Take a point with position: (x,y), and return a new point with position:
     * (x+dx, y+dy).
     * @param p The point with the initial position.
     * @return The point with the updated position.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.getDX(), p.getY()
                + this.getDY());
    }

    /**
     * Creates a new instance of velocity according to given angle and speed.
     * @param angle The angle of the velocity.
     * @param speed The speed of the velocity.
     * @return New velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = angle % 360;
        double angleInRad = Math.toRadians(angle);

        double dx = Math.sin(angleInRad) * speed;
        double dy = -Math.cos(angleInRad) * speed;

        return new Velocity(dx, dy);
    }

    /**
     * Get the speed value according to dx, dy.
     * @return The speed value.
     */
    public double getSpeed() {
        return Math.sqrt((this.getDX() * this.getDX()) + (this.getDY()
                * this.getDY()));
    }

    /**
     * Decouple speed from frame-rate.
     * @param dt the amount of seconds passed since the last call.
     * @return Decoupled velocity.
     */
    public Velocity decoupleSpeed(double dt) {
        return new Velocity(dx * dt, dy * dt);
    }
}

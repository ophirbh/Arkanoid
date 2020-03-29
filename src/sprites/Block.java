package sprites;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import driver.GameLevel;
import management.HitListener;
import management.HitNotifier;

/**
 * Represnts a block in the plain.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle geoRep;
    private sprites.Fill[] fills;
    private Color stroke;
    private int hitPoints;
    private ArrayList<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Constructor.
     * @param rectangle The geometric representation of the block.
     * @param fills The block fills.
     * @param stroke The block stroke.
     * @param hits The block hits until it disappears.
     */
    public Block(Rectangle rectangle, sprites.Fill[] fills, Color stroke, int hits) {
        this.geoRep = rectangle;
        this.fills = fills;
        this.stroke = stroke;
        this.hitPoints = hits;
    }

    /**
     * Constructor.
     * @param rectangle The geometric representation of the block.
     * @param fill The block fill.
     * @param stroke The block stroke.
     * @param hits The block hits until it disappears.
     */
    public Block(Rectangle rectangle, sprites.Fill fill, Color stroke, int hits) {
        this(rectangle, createSingleFillArray(fill, hits), stroke, hits);
    }

    /**
     * Creates a single fill array.
     * @param fill The fill.
     * @param copies Number of copies.
     * @return Fill array.
     */
    private static Fill[] createSingleFillArray(Fill fill, int copies) {
        if (copies <= 0) {
            copies = 1;
        }

        Fill[] fills = new Fill[copies];

        for (int index = 0; index < copies; index++) {
            fills[index] = fill;
        }

        return fills;
    }

    /**
     * Return the geometric representation of this block.
     * @return The geometric representation of this block.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.geoRep;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity. The return is the new velocity expected after the
     * hit (based on the force the object inflicted on us).
     * @param hitter The hitter object.
     * @param collisionPoint The collision point.
     * @param currentVelocity The current velocity.
     * @return The velocity expected after the hit.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.decreaseHitPoint();
        this.notifyHit(hitter);
        if (this.geoRep.isPointOnCorner(collisionPoint)) {
            return new Velocity(-currentVelocity.getDX(),
                    -currentVelocity.getDY());
        }
        if (collisionPoint.isOnHorizontalLine(this.geoRep.getUpperEdge())
                || collisionPoint.isOnHorizontalLine(
                        this.geoRep.getLowerEdge())) {
            return new Velocity(currentVelocity.getDX(),
                    -currentVelocity.getDY());
        }
        if (collisionPoint.isOnVerticalLine(this.geoRep.getLeftEdge())
                || collisionPoint.isOnVerticalLine(
                        this.geoRep.getRightEdge())) {
            return new Velocity(-currentVelocity.getDX(),
                    currentVelocity.getDY());
        }
        return currentVelocity;
    }

    /**
     * Draw block on the given draw surface.
     * @param d The given draw surface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        Point upperLeft = geoRep.getUpperLeft();

        drawFill(d, upperLeft);
        if (this.stroke != null) {
            drawStroke(d, upperLeft);
        }
    }

    /**
     * Draw stroke.
     * @param d Draw surface.
     * @param upperLeft Upper left point.
     */
    private void drawStroke(DrawSurface d, Point upperLeft) {
        d.setColor(this.stroke);
        d.drawRectangle(
                (int) upperLeft.getX(),
                (int) upperLeft.getY(),
                (int) this.geoRep.getWidth(),
                (int) this.geoRep.getHeight());
    }

    /**
     * Draw fill.
     * @param d Draw surface.
     * @param upperLeft Upper left point.
     */
    private void drawFill(DrawSurface d, Point upperLeft) {
        int fillIndex = this.hitPoints - 1;

        if (fillIndex < 0) {
            fillIndex = 0;
        }

        Fill fill = this.fills[fillIndex];

        if (fill.isImage()) {
            // draw the image at location 10, 20.
            d.drawImage((int) upperLeft.getX(), (int) upperLeft.getY(), fill.getImage());
        } else {
            d.setColor(fill.getColor());
            d.fillRectangle(
                    (int) upperLeft.getX(),
                    (int) upperLeft.getY(),
                    (int) this.geoRep.getWidth(),
                    (int) this.geoRep.getHeight());
        }
    }

    /**
     * Notify the sprite that time has passed.
     * @param dt  the amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
        return;
    }

    /**
     * Add block to a given game.
     * @param g The given game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Decrease one hit point if there are any.
     */
    public void decreaseHitPoint() {
        if (this.hitPoints > 0) {
            this.hitPoints--;
        }
    }

    /**
     * Get the number of hit points left.
     * @return The number of hit points left.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Return the upper left point of the block.
     * @return The upper left point of the block.
     */
    public Point getUpperLeft() {
        return this.geoRep.getUpperLeft();
    }

    /**
     * Return the width of the block.
     * @return The width of the block.
     */
    public double getWidth() {
        return this.geoRep.getWidth();
    }

    /**
     * Return the height of the block.
     * @return The height of the block.
     */
    public double getHeight() {
        return this.geoRep.getHeight();
    }

    /**
     * Return the middle point of the block.
     * @return The middle point of the block.
     */
    public Point getMiddlePoint() {
        return new Point(this.getUpperLeft().getX() + (this.getWidth() / 2),
                this.getUpperLeft().getY() + (this.getHeight() / 2));
    }

    /**
     * Draw the hits indicator on the block.
     * @param d The given drawsu
     */
    public void drawHitsIndicator(DrawSurface d) {
        d.setColor(Color.WHITE);
        if (this.hitPoints == 0) {
            d.drawText((int) this.getMiddlePoint().getX(),
                    (int) this.getMiddlePoint().getY(), "X", 8);
        } else {
            d.drawText((int) this.getMiddlePoint().getX(),
                    (int) this.getMiddlePoint().getY(),
                    String.valueOf(this.getHitPoints()), 8);
        }
    }

    /**
     * Set hit points to desired value.
     * @param hits The new hit points.
     */
    public void setHitPoints(int hits) {
        if (hits <= 0) {
            this.hitPoints = 0;
        } else {
            this.hitPoints = hits;
        }
    }

    /**
     * Removes the block from the argument game.
     * @param game The game.
     * @return True if removed, false otherwise.
     */
    public boolean removeFromGame(GameLevel game) {
        boolean collidableRemoved;
        boolean spriteRemoved;
        collidableRemoved = game.removeCollidable(this);
        spriteRemoved = game.removeSprite(this);
        return collidableRemoved && spriteRemoved;
    }

    /**
     * Remove the argument hit listener.
     * @param hl The hit listener.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifies all of the registered HitListener objects whenever a hit()
     * occurs by calling their hitEvent method.
     * @param hitter The hitter ball.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Add the argument hit listener.
     * @param hl The hit listener.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

}

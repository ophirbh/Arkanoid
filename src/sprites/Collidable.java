package sprites;

import geometryprimitives.Rectangle;
import geometryprimitives.Point;

public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     * @return The "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a
     * given velocity.
     * The return is the new velocity expected after the hit (based on the
     * force the object inflicted on us).
     * @param hitter The hitter ball.
     * @param collisionPoint The collision point.
     * @param currentVelocity The current velocity.
     * @return The velocity after the collision.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}

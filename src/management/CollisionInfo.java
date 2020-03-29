package management;

import geometryprimitives.Point;
import sprites.Collidable;

/**
 * Represents a collision information.
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Create a new collision information object.
     * @param collisionP The collision point.
     * @param collisionObj The collision object.
     */
    public CollisionInfo(Point collisionP, Collidable collisionObj) {
        this.collisionPoint = collisionP;
        this.collisionObject = collisionObj;
    }

    /**
     * The point at which the collision occurs.
     * @return The point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * The collidable object involved in the collision.
     * @return The collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}

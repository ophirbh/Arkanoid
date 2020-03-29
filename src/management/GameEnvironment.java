package management;

import geometryprimitives.Line;
import geometryprimitives.Point;
import sprites.Collidable;


import java.util.ArrayList;

/**
 * Represents a collection of collidable objects.
 */
public class GameEnvironment {

    private ArrayList<Collidable> collidableCollection;

    /**
     * Creates a new game environment.
     */
    public GameEnvironment() {
        this.collidableCollection = new ArrayList<Collidable>();
    }

    /**
     * Add the given collidable to the environment.
     * @param c The collidable that will be added.
     */
    public void addCollidable(Collidable c) {
        this.collidableCollection.add(c);
    }

    /**
     * Remove the given collidable to the environment.
     * @param c The collidable that will be removed.
     * @return True if removed, false otherwise.
     */
    public boolean removeCollidable(Collidable c) {
        return this.collidableCollection.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory The trajectory of the possibly colliding object.
     * @return The collision information.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point collisionPoint;
        CollisionInfo closest = null;
        boolean hasCollision = false;

        for (Collidable c: this.collidableCollection) {
            collisionPoint = trajectory.closestIntersectionToStartOfLine(
                    c.getCollisionRectangle());
            if (collisionPoint != null) {
                if (!hasCollision) {
                    closest = new CollisionInfo(collisionPoint, c);
                    hasCollision = true;
                } else {
                    closest = getCloserCollisionOutOfTwo(new CollisionInfo(
                            collisionPoint, c), closest, trajectory);
                }
            }
        }
        return closest;
    }

    /**
     * Returns the closer collision out of two collisions information checked.
     * @param a The first collision.
     * @param b The second collision
     * @param trajectory The trajectory of the colliding object.
     * @return The closer collision.
     */
    private CollisionInfo getCloserCollisionOutOfTwo(CollisionInfo a,
                                                     CollisionInfo b,
                                                     Line trajectory) {
        if (trajectory.start().distance(a.collisionPoint())
                > trajectory.start().distance(b.collisionPoint())) {
            return a;
        }
        return b;
    }

}

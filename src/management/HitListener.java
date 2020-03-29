package management;

import sprites.Ball;
import sprites.Block;

/**
 * Indicates that Objects who implement it want to be notified of hit events.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     * @param beingHit The object being hit.
     * @param hitter The hitter object.
     */
    void hitEvent(Block beingHit, Ball hitter);
}

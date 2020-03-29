package management;

import sprites.Ball;
import sprites.Block;

/**
 * Represents a printing hit listener.
 */
public class PrintingHitListener implements HitListener {

    /**
     * Called where a hit happens.
     * @param beingHit The object being hit.
     * @param hitter The hitter object.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitPoints() + " points was hit.");
    }

}

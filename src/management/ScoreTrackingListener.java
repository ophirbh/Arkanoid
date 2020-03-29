package management;
import sprites.Block;
import sprites.Ball;

/**
 * Represents a score tracking listener. hitting a block is worth 5 points,
 * and destroying a block is worth and additional 10 points.
 * Clearning an entire level (destroying all blocks) is worth another 100 points.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Creates a new score tracking listener.
     * @param scoreCounter Score counter.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Called where a hit happens.
     * @param beingHit The object being hit.
     * @param hitter The hitter object.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        }
        this.currentScore.increase(5);
    }
}

package management;

import driver.GameLevel;
import sprites.Ball;
import sprites.Block;

/**
 * a BallRemover is in charge of removing balls from the game, as well as
 * keeping count of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * Create a ball remover object.
     * @param game The game.
     * @param remainingBalls The remaining balls counter.
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Balls that hit the "death block" should be removed from the game.
     * @param beingHit The block being hit.
     * @param hitter The hitter ball.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}

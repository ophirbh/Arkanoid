package management;
import driver.GameLevel;
import sprites.Block;
import sprites.Ball;

/**
 * a BlockRemover is in charge of removing blocks from the game, as well as
 * keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * Create a block remover object.
     * @param game The game.
     * @param remainingBlocks The
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }


    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the game. Remember to remove this listener from the block
     * that is being removed from the game.
     * @param beingHit The block being hit.
     * @param hitter The hitter.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}

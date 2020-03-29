package management;

import sprites.Velocity;
import java.util.List;
import sprites.Sprite;
import sprites.Block;


/**
 * Specifies the information required to fully describe a level.
 */
public interface LevelInformation {

    /**
     * Returns the number of balls the level has.
     * @return The number of balls the level has.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls().
     * @return The initial velocity of each ball.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns the paddle speed.
     * @return The paddle speed.
     */
    int paddleSpeed();

    /**
     * Returns the paddle width.
     * @return The paddle width.
     */
    int paddleWidth();

    /**
     * The level name will be displayed at the top of the screen.
      * @return Level name.
     */
    String levelName();


    /**
     * Returns a sprite with the background of the level.
     * @return A sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return The Blocks that make up this level
     * @throws Exception Exception.
     */
    List<Block> blocks() throws Exception;

    /**
     * Number of blocks that should be removed
     * before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * @return The number of blocks that should be removed before the
     * level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();
}

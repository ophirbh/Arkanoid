package levels;

import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import management.LevelInformation;
import sprites.Block;
import sprites.Fill;
import sprites.Sprite;
import sprites.Velocity;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents level 1.
 */
public class Level1 implements LevelInformation {

    /**
     * Returns the number of balls the level has.
     * @return The number of balls the level has.
     */
    @Override
    public int numberOfBalls() {
        return 1;
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls().
     * @return The initial velocity of each ball.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsInitVelocities = new ArrayList<>();
        ballsInitVelocities.add(new Velocity(0, -480));
        return ballsInitVelocities;
    }

    /**
     * Returns the paddle speed.
     * @return The paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return 480;
    }

    /**
     * Returns the paddle width.
     * @return The paddle width.
     */
    @Override
    public int paddleWidth() {
        return 80;
    }

    /**
     * The level name will be displayed at the top of the screen.
     * @return Level name.
     */
    @Override
    public String levelName() {
        return "Direct Hit";
    }

    /**
     * Returns a sprite with the background of the level.
     * @return A sprite with the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return new Background1();
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return The Blocks that make up this level
     */
    @Override
    public List<Block> blocks() {
        List<Block> pattern = new ArrayList<>();
        pattern.add(new Block(new Rectangle(new Point(380, 200),
                40, 40), new Fill(Color.red), Color.BLACK, 1));
        return pattern;
    }

    /**
     * Number of blocks that should be removed
     * before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     * @return The number of blocks that should be removed before the
     * level is considered to be "cleared".
     */
    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}

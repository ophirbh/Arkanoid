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
 * Represents level 2.
 */
public class Level2 implements LevelInformation {
    /**
     * Returns the number of balls the level has.
     * @return The number of balls the level has.
     */
    @Override
    public int numberOfBalls() {
        return 10;
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls().
     * @return The initial velocity of each ball.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsInitVelocities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ballsInitVelocities.add(Velocity.fromAngleAndSpeed(-50 + (i * 10), 300));
            ballsInitVelocities.add(Velocity.fromAngleAndSpeed(10 + (i * 10), 300));
        }
        return ballsInitVelocities;
    }

    /**
     * Returns the paddle speed.
     * @return The paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return 240;
    }

    /**
     * Returns the paddle width.
     * @return The paddle width.
     */
    @Override
    public int paddleWidth() {
        return 400;
    }

    /**
     * The level name will be displayed at the top of the screen.
     * @return Level name.
     */
    @Override
    public String levelName() {
        return "Wide Easy";
    }

    /**
     * Returns a sprite with the background of the level.
     * @return A sprite with the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return new Background2();
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return The Blocks that make up this level
     */
    @Override
    public List<Block> blocks() {
        List<Block> pattern = new ArrayList<>();
        Color[] colors = {Color.RED, Color.RED, Color.ORANGE, Color.ORANGE,
                Color.YELLOW, Color.YELLOW, Color.GREEN, Color.GREEN,
                Color.GREEN, Color.BLUE, Color.BLUE, Color.PINK,
                Color.PINK, Color.CYAN, Color.CYAN};
        for (int i = 0; i < 15; i++) {
            pattern.add(new Block(new Rectangle(new Point(25 + (i * 50), 240),
                    50, 30), new Fill(colors[i]), Color.BLACK, 1));
        }

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
        return 15;
    }
}

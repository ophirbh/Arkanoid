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
 * Represents level 4.
 */
public class Level4 implements LevelInformation {
    /**
     * Returns the number of balls the level has.
     * @return The number of balls the level has.
     */
    @Override
    public int numberOfBalls() {
        return 3;
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls().
     * @return The initial velocity of each ball.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsInitVelocities = new ArrayList<Velocity>();
        ballsInitVelocities.add(new Velocity(-300, -300));
        ballsInitVelocities.add(new Velocity(300, -300));
        ballsInitVelocities.add(new Velocity(0, -300));
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
        return "Final Four";
    }

    /**
     * Returns a sprite with the background of the level.
     * @return A sprite with the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return new Background4();
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return The Blocks that make up this level
     */
    @Override
    public List<Block> blocks() {
        List<Block> pattern = new ArrayList<>();
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.GREEN,
                Color.ORANGE, Color.PINK, Color.BLUE};
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 15; j++) {
                pattern.add(new Block(new Rectangle(new Point(25 + (j * 50),
                        100 + (i * 25)), 50, 25), new Fill(colors[i]), Color.BLACK, 1));
            }
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
        return 105;
    }
}

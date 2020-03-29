package driver;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import management.Counter;
import management.SpriteCollection;
import management.GameEnvironment;
import management.LevelInformation;
import management.Animation;
import management.AnimationRunner;
import management.ScoreTrackingListener;
import management.BlockRemover;
import management.CountdownAnimation;
import management.BallRemover;
import management.KeyPressStoppableAnimation;
import management.PauseScreen;
import management.ScoreIndicator;
import management.LivesIndicator;
import management.LevelNameIndicator;
import sprites.Block;
import sprites.Sprite;
import sprites.Collidable;
import sprites.Fill;
import sprites.Ball;
import sprites.Paddle;
import geometryprimitives.Rectangle;
import geometryprimitives.Point;

import java.awt.Color;
import java.util.List;

/**
 * Represents a game.
 */
public class GameLevel implements Animation {

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter numOfLives;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation levelInfo;
    private Paddle paddle;
    private int numberOfBlocksToRemove;

    /**
     * Create a new game level.
     * @param levelInfo The level informaion.
     * @param ks The keyboard sensor.
     * @param ar The animation runner.
     * @param lives The lives counter.
     * @param score The score counter.
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor ks,
                     AnimationRunner ar, Counter lives, Counter score) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.levelInfo = levelInfo;
        initializeCounters(lives, score);
        this.runner = ar;
        this.gui = runner.getGui();
        this.keyboard = this.gui.getKeyboardSensor();
        this.numberOfBlocksToRemove = levelInfo.numberOfBlocksToRemove();
    }

    /**
     * Add the given collidable to the game environment.
     * @param c The given collidable.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add the given sprite to sprite collection.
     * @param s The given sprite.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    public static final int PADDLE_OFFSET = 2;

    /**
     * Initialize a new game: create the Blocks and sprites.Ball (and sprites.Paddle)
     * and add them to the game.
     * @throws Exception Exception.
     */
    public void initialize() throws Exception {
        this.sprites.addSprite(this.levelInfo.getBackground());
        initializeGameIndicators();
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        addBlockBounds(ballRemover);
        this.addBlockPattern();

        initializePaddle();
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        while ((this.numOfLives.getValue() >= 0) && (this.remainingBlocks.getValue() > 0)) {
            this.playOneTurn();
        }
        return;
    }

    /**
     * Run one turn of the game.
     */
    public void playOneTurn() {

        this.paddle.setLeftXCoor((this.gui.getDrawSurface().getWidth() / 2)
                - (this.levelInfo.paddleWidth() / 2));
        initializeBalls();
        this.running = true;
        this.runner.run(new CountdownAnimation(3, 3, this.sprites, this.levelInfo));
        this.runner.run(this);


    }

    public static final int BLOCK_PAT_WIDTH = 50;
    public static final int BLOCK_PAT_HEIGHT = 20;
    public static final int VERT_BLOCK_BOUND_WIDTH = 25;
    public static final int HORIZ_BLOCK_BOUND_HEIGHT = 25;
    public static final int HORIZ_OFFSET = -75;
    public static final int VERT_OFFSET = 125;

    /**
     * Add blocks in a pattern similar to the example given in the assignment.
     * @throws Exception Exception.
     */
    public void addBlockPattern() throws Exception {
        ScoreTrackingListener scoreTrack = new ScoreTrackingListener(this.score);
        BlockRemover blkRemover = new BlockRemover(this, this.remainingBlocks);
        List<Block> blocksPattern = this.levelInfo.blocks();
        for (int i = 0; i < blocksPattern.size(); i++) {
            blocksPattern.get(i).addToGame(this);
            blocksPattern.get(i).addHitListener(scoreTrack);
            blocksPattern.get(i).addHitListener(blkRemover);

        }

        if (numberOfBlocksToRemove <= blocksPattern.size()) {
            this.remainingBlocks.setValue(numberOfBlocksToRemove);
        } else {
            this.remainingBlocks.setValue(blocksPattern.size());
        }
    }

    /**
     * Add bounding blocks to the game.
     * @param ballremover The ball remover.
     */
    public void addBlockBounds(BallRemover ballremover) {
        Block leftBound = new Block(new Rectangle(new Point(0, 20),
                VERT_BLOCK_BOUND_WIDTH, gui.getDrawSurface().getHeight()),
                new Fill(Color.DARK_GRAY), Color.DARK_GRAY, 0);
        leftBound.addToGame(this);
        Block rightBound = new Block(new Rectangle(new Point(
                gui.getDrawSurface().getWidth()
                        - VERT_BLOCK_BOUND_WIDTH, 20),
                VERT_BLOCK_BOUND_WIDTH, gui.getDrawSurface().getHeight()),
                new Fill(Color.DARK_GRAY), Color.DARK_GRAY, 0);
        rightBound.addToGame(this);
        Block upperBound = new Block(new Rectangle(new Point(0, 20),
                gui.getDrawSurface().getWidth(), HORIZ_BLOCK_BOUND_HEIGHT),
                new Fill(Color.DARK_GRAY), Color.DARK_GRAY, 0);
        upperBound.addToGame(this);
        addDeathBlock(ballremover);
    }

    /**
     * Removes a collidable object.
     * @param c The collidable to remove.
     * @return True if removed, false otherwise.
     */
    public boolean removeCollidable(Collidable c) {
        return this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite object.
     * @param s The sprite to remove.
     * @return True if removed, false otherwise.
     */
    public boolean removeSprite(Sprite s) {
        return this.sprites.removeSprite(s);
    }

    /**
     * Add death block at the bottom of the screen.
     * A death block is a block that eliminates balls that hit it.
     * @param ballRemover Ball remover listener.
     */
    public void addDeathBlock(BallRemover ballRemover) {
        Block lowerBound = new Block(new Rectangle(new Point(0,
                gui.getDrawSurface().getHeight()
                        + HORIZ_BLOCK_BOUND_HEIGHT),
                gui.getDrawSurface().getWidth(), HORIZ_BLOCK_BOUND_HEIGHT),
                new Fill(Color.DARK_GRAY), Color.DARK_GRAY, 0);
        lowerBound.addToGame(this);
        lowerBound.addHitListener(ballRemover);
    }

    /**
     * Check end of game conditions.
     * @return True if one or more conditions are met. False otherwise.
     */
    private boolean checkTurnEndConditions() {
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            return true;
        }
        if (this.remainingBalls.getValue() == 0) {
            this.numOfLives.decrease(1);
            return true;
        }
        return false;
    }

    /**
     * Initialize the game level balls.
     */
    public void initializeBalls() {
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(this.gui.getDrawSurface().getWidth()
                    / 2, 480), 5, Color.WHITE, this.environment);
            ball.setVelocity(this.levelInfo.initialBallVelocities().get(i));
            ball.addToGame(this);
            this.remainingBalls.increase(1);
        }
    }

    /**
     * Initialize the game level paddle.
     */
    public void initializePaddle() {
        this.paddle = new Paddle(new Point(this.gui.getDrawSurface().getWidth()
                / 2, 590), this.levelInfo.paddleWidth(), 10, Color.ORANGE,
                this.keyboard, this.levelInfo.paddleSpeed());
        this.paddle.addToGame(this);
    }



    /**
     * Set the paddle to the point that will place the paddle in the middle
     * of the screen.
     */
    private void setPaddleAtCenter() {
        this.paddle.setLeftXCoor((this.gui.getDrawSurface().getWidth() / 2)
        - (this.levelInfo.paddleWidth() / 2));
    }

    /**
     * Checks if the animation should stop.
     * @return True if should stop, false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * Do one frame of game level.
     * @param d The draw surface.
     * @param dt The amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.keyboard.isPressed("p")) {
            PauseScreen pauseScreen = new PauseScreen();
            KeyPressStoppableAnimation keyPressStoppableAnimation =
                    new KeyPressStoppableAnimation(this.keyboard,
                            KeyboardSensor.SPACE_KEY, pauseScreen);
            this.runner.run(keyPressStoppableAnimation);
        }
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        if (checkTurnEndConditions()) {
            this.running = false;
        }
    }

    /**
     * Initialize all counters.
     * @param lives Lives counter.
     * @param scr Score counter.
     */
    public void initializeCounters(Counter lives, Counter scr) {
        this.numOfLives = lives;
        this.score = scr;
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
    }

    /**
     * Initialize all g.
     */
    public void initializeGameIndicators() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);
        LivesIndicator livesIndicator = new LivesIndicator(this.numOfLives);
        livesIndicator.addToGame(this);
        LevelNameIndicator nameIndicator = new LevelNameIndicator(this.levelInfo.levelName());
        nameIndicator.addToGame(this);
    }

    /**
     * Get the number of remaining blocks.
     * @return The number of remaining blocks.
     */
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }

    /**
     * Get the number of remaining lives.
     * @return The number of remaining lives.
     */
    public int getRemainingLives() {
        return this.numOfLives.getValue();
    }

}

package management;

import biuoop.DrawSurface;
import driver.GameLevel;
import sprites.Sprite;
import java.awt.Color;

/**
 * Represents a score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;
    // adjusts the score indicator sprite to be in the middle of the screen
    public static final int SCORE_OFFSET = 30;

    /**
     * Create a new score indicator.
     * @param counter Score counter.
     */
    public ScoreIndicator(Counter counter) {
        this.scoreCounter = counter;
    }

    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, d.getWidth(), 20);
        d.setColor(Color.BLACK);
        d.drawText((d.getWidth() / 2) - SCORE_OFFSET, 15, "Score: " + this.scoreCounter.getValue(),
                15);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt The amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * Add sprite to game.
     * @param g The game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}

package management;

import biuoop.DrawSurface;
import driver.GameLevel;
import sprites.Sprite;

import java.awt.Color;

/**
 * Represents a lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter livesCounter;

    /**
     * Create a new lives indicator.
     * @param counter Lives counter.
     */
    public LivesIndicator(Counter counter) {
        this.livesCounter = counter;
    }

    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 6, 15,
                "Lives: " + this.livesCounter.getValue(), 15);
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

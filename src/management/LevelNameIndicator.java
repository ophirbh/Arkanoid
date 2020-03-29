package management;

import biuoop.DrawSurface;
import driver.GameLevel;
import sprites.Sprite;
import java.awt.Color;

/**
 * Represents a level name indicator.
 */
public class LevelNameIndicator implements Sprite {
    public static final int NAME_INDICATOR_OFFSET = 250;
    private String levelName;

    /**
     * Create a new lives indicator.
     * @param levelName The level name.
     */
    public LevelNameIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() - NAME_INDICATOR_OFFSET, 15,
                "Level Name: " + this.levelName, 15);
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

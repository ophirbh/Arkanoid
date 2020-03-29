package levels;

import biuoop.DrawSurface;
import sprites.Sprite;
import java.awt.Color;

/**
 * Represent the level 2 background.
 */
public class Background2 implements Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw sun.
        d.setColor(Color.yellow);
        d.fillCircle(180, 120, 50);

        // Draw sun rays.
        for (int i = 1; i < 70; i++) {
            d.drawLine(180, 120, 20 + (i * 10), 240);
        }
    }

    /**
     * notify the sprite that time has passed.
     * @param dt The amount of seconds passed since the last call.
     */
    @Override
    public void timePassed(double dt) {
    }
}

package levels;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

/**
 * Represent the level 3 background.
 */
public class Background3 implements Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw green background,
        d.setColor(Color.GREEN);
        d.fillRectangle(0, 20, d.getWidth(), d.getHeight());

        // Draw building.
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(50, 400, 150, 400);
        d.fillRectangle(100, 250, 50, 150);
        d.fillRectangle(120, 150, 15, 100);
        d.setColor(Color.RED);
        d.fillCircle(127, 145, 20);
        d.setColor(Color.WHITE);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                d.fillRectangle(60 + (i * 20), 415 + (j * 40), 10, 20);
            }
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

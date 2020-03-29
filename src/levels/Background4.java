package levels;

import biuoop.DrawSurface;
import sprites.Sprite;
import java.awt.Color;

/**
 * Represent the level 4 background.
 */
public class Background4 implements Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw background.
        d.setColor(Color.CYAN);
        // Draw first cloud.
        d.fillRectangle(0, 20, d.getWidth(), d.getHeight());
        d.setColor(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            d.drawLine(160 + (i * 10), 450, 190 + (i * 10), 600);
        }
        d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 3; i++) {
            d.fillCircle(150 + (i * 50), 430, 30);
        }
        for (int i = 0; i < 2; i++) {
            d.fillCircle(180 + (i * 40), 450, 25);
        }

        // Draw second cloud.
        d.setColor(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            d.drawLine(555 + (i * 10), 480, 585 + (i * 10), 600);
        }
        d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 3; i++) {
            d.fillCircle(550 + (i * 50), 500, 30);
        }
        for (int i = 0; i < 2; i++) {
            d.fillCircle(580 + (i * 40), 530, 25);
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

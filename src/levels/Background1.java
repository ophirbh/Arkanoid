package levels;

import biuoop.DrawSurface;
import sprites.Sprite;
import java.awt.Color;

/**
 * Represent the level 1 background.
 */
public class Background1 implements Sprite {

    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.GRAY);
        d.fillRectangle(0, 20, d.getWidth(), d.getHeight());
        d.setColor(Color.BLUE);

        // Draw circles.
        for (int i = 0; i < 3; i++) {
            d.drawCircle(400, 220, 60 + (i * 40));
        }
        // Draw vertical lines.
        for (int i = 0; i < 2; i++) {
            d.drawLine(400, 60 + (i * 200), 400, 180 + (i * 200));
        }
        // Draw horizontal lines.
        for (int i = 0; i < 2; i++) {
            d.drawLine(240 + (i * 200), 220, 360 + (i * 200), 220);
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

package sprites;

import biuoop.DrawSurface;

/**
 * Represents a moving object.
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d The given drawsurface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     * @param dt the amount of seconds passed since the last call.
     */
    void timePassed(double dt);
}

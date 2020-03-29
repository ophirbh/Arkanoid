package management;

import biuoop.DrawSurface;
import sprites.Sprite;
import java.util.ArrayList;

/**
 * Represents a sprite collection.
 */
public class SpriteCollection {

    private ArrayList<Sprite> spritesCollection;

    /**
     * Create a new sprite collection.
     */
    public SpriteCollection() {
        this.spritesCollection = new ArrayList<Sprite>();
    }

    /**
     * Add sprite to the collection.
     * @param s The sprite to add.
     */
    public void addSprite(Sprite s) {
        this.spritesCollection.add(s);
    }

    /**
     * Remove sprite from the collection.
     * @param s The sprite to remove.
     * @return True if removed, false otherwise.
     */
    public boolean removeSprite(Sprite s) {
        return this.spritesCollection.remove(s);
    }

    /**
     * Call timePassed() on all sprites.
     * @param dt The amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < this.spritesCollection.size(); i++) {
            this.spritesCollection.get(i).timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     * @param d The given draw surface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s: this.spritesCollection) {
            s.drawOn(d);
        }
    }
}

package sprites;

import biuoop.DrawSurface;

/**
 * Represents a background.
 */
public class Background implements Sprite {

    private Fill fill;

    /**
     * Constructor.
     * @param fill The background fill.
     */
    public Background(Fill fill) {
        this.fill = fill;
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (this.fill.isImage()) {
            d.drawImage(0, 20, this.fill.getImage()); // draw the image at location 10, 20.
        } else {
            d.setColor(this.fill.getColor());
            d.fillRectangle(0, 20, d.getWidth(), d.getHeight());
        }
    }

    @Override
    public void timePassed(double dt) {

    }
}

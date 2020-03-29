package sprites;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Represents a fill.
 */
public class Fill {

    private boolean isFillImage;
    private Color color;
    private Image image;

    /**
     * Return true if fill is image.
     * @return True if fill is image.
     */
    public boolean isImage() {
        return isFillImage;
    }

    /**
     * Return the fill color.
     * @return The fill color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Return the fill image.
     * @return The fill image.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Constructor for color fill.
     * @param color The fill color.
     */
    public Fill(Color color) {
        isFillImage = false;
        this.color = color;
    }

    /**
     * Constructor for image fill.
     * @param image The fill image.
     */
    public Fill(Image image) {
        isFillImage = true;
        this.image = image;
    }

    /**
     * Constructor for image fill.
     * @param imageFileName The image file name.
     */
    public Fill(String imageFileName) {
        Image img = null;

        try {
//            File imageFile = new File(imageFileName);

            InputStream imageFile = ClassLoader.getSystemClassLoader().getResourceAsStream(imageFileName);
            img = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.image = img;
        isFillImage = true;
    }
}

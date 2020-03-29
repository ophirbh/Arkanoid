package abstracts;

import biuoop.DrawSurface;
import geometryprimitives.Line;

/**
 * abstracts.Border specifies the borders in which an element is allowed to move.
 */
public class Border {

    public static final int XMIN = 0;
    public static final int YMIN = 0;

    private Line rightBorder;
    private Line leftBorder;
    private Line upperBorder;
    private Line lowerBorder;

    /**
     * Creates a new border using the given X and Y axis maximum and minimum.
     * @param xMax The maximal coordinate of the X axis.
     * @param xMin The minimal coordinate of the X axis.
     * @param yMax The maximal coordinate of the Y axis.
     * @param yMin The minimal coordinate of the Y axis.
     */
    public Border(int xMax, int xMin, int yMax, int yMin) {
        this.leftBorder = new Line(xMin, yMin, xMin, yMax);
        this.rightBorder = new Line(xMax, yMin, xMax, yMax);
        this.upperBorder = new Line(xMin, yMin, xMax, yMin);
        this.lowerBorder = new Line(xMin, yMax, xMax, yMax);
    }

    /**
     * Creates a new border using the given draw surface.
     * @param d Draw surface.
     */
    public Border(DrawSurface d) {
        this.leftBorder = new Line(this.XMIN, this.YMIN, this.XMIN,
                d.getHeight());
        this.rightBorder = new Line(d.getWidth(), this.YMIN, d.getWidth(),
                d.getHeight());
        this.upperBorder = new Line(this.XMIN, this.YMIN, d.getWidth(),
                this.YMIN);
        this.lowerBorder = new Line(this.XMIN, d.getHeight(), d.getWidth(),
                d.getHeight());
    }

    /**
     * Get the left border.
     * @return The left border.
     */
    public Line getLeftBorder() {
        return this.leftBorder;
    }

    /**
     * Get the right border.
     * @return The right border.
     */
    public Line getRightBorder() {
        return this.rightBorder;
    }

    /**
     * Get the upper border.
     * @return The upper border.
     */
    public Line getUpperBorder() {
        return this.upperBorder;
    }

    /**
     * Get the lower border.
     * @return The lower border.
     */
    public Line getLowerBorder() {
        return this.lowerBorder;
    }

    /**
     * Get the border width.
     * @return The border width.
     */
    public int getWidth() {
        return (int) this.getUpperBorder().length();
    }

    /**
     * Get the border Height.
     * @return The border height.
     */
    public int getHeight() {
        return  (int) this.getLeftBorder().length();
    }

    /**
     * Get the max X value of the border.
     * @return The max x value of the border.
     */
    public int getMaxX() {
        return (int) this.getRightBorder().start().getX();
    }

    /**
     * Get the min X value of the border.
     * @return The mix x value of the border.
     */
    public int getMinX() {
        return (int) this.getLeftBorder().start().getX();
    }

    /**
     * Get the max Y value of the border.
     * @return The max Y value of the border.
     */
    public int getMaxY() {
        return (int) this.getLowerBorder().start().getY();
    }

    /**
     * Get the min Y value of the border.
     * @return The mix Y value of the border.
     */
    public int getMinY() {
        return (int) this.getUpperBorder().start().getY();
    }
}

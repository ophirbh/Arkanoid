package geometryprimitives;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rectangle.
 */
public class Rectangle {

    public static final int NUM_RECT_EDGES = 4;
    public static final int UPPER_EDGE = 0;
    public static final int LOWER_EDGE = 1;
    public static final int LEFT_EDGE = 2;
    public static final int RIGHT_EDGE = 3;

    private Point upperLeft;
    private double width;
    private double height;
    private Line[] edges = new Line[this.NUM_RECT_EDGES];

    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeft The upper left point of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.edges[this.UPPER_EDGE] = new Line(upperLeft,
                new Point(upperLeft.getX() + width, upperLeft.getY()));
        this.edges[this.LOWER_EDGE] = new Line(upperLeft.getX(),
                upperLeft.getY() + height, upperLeft.getX() + width,
                upperLeft.getY()
                + height);
        this.edges[this.LEFT_EDGE] = new Line(upperLeft,
                new Point(upperLeft.getX(), upperLeft.getY() + height));
        this.edges[this.RIGHT_EDGE] = new Line(upperLeft.getX() + width,
                upperLeft.getY(), upperLeft.getX() + width,
                upperLeft.getY() + height);

        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Copy constructor.
     * @param rectangle The copied rectangle.
     */
    public Rectangle(Rectangle rectangle) {
        this.edges = rectangle.getEdges();
        this.upperLeft = rectangle.getUpperLeft();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
    }

    /**
     * Return the rectangle's edges, represented as an array of 4 line objects.
     * @return The rectangle's edges.
     */
    public Line[] getEdges() {
        Line[] copiedEdges = new Line[this.NUM_RECT_EDGES];
        copiedEdges[this.UPPER_EDGE] = this.getUpperEdge();
        copiedEdges[this.LOWER_EDGE] = this.getLowerEdge();
        copiedEdges[this.LEFT_EDGE] = this.getLeftEdge();
        copiedEdges[this.RIGHT_EDGE] = this.getRightEdge();
        return copiedEdges;
    }

    /**
     * Return a (possibly empty) List of intersection points with the
     * specified line.
     * @param line The line that intersections will be checked with.
     * @return The intersection points list.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> list = new ArrayList<Point>();

        for (int i = 0; i < this.NUM_RECT_EDGES; i++) {
            if (line.isIntersecting(this.edges[i])) {
                list.add(line.intersectionWith(this.edges[i]));
            }
        }
        return list;
    }

    /**
     * Returns the rectangle's width.
     * @return The rectangle's width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the rectangle's height.
     * @return The rectangle's height.
     */
    public double getHeight() {
        return  this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     * @return The upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return new Point(this.upperLeft.getX(), this.upperLeft.getY());
    }

    /**
     * Get the upper edge.
     * @return The upper edge.
     */
    public Line getUpperEdge() {
        return new Line(this.edges[this.UPPER_EDGE].start(),
                this.edges[this.UPPER_EDGE].end());
    }

    /**
     * Get the lower edge.
     * @return The lower edge.
     */
    public Line getLowerEdge() {
        return new Line(this.edges[this.LOWER_EDGE].start(),
                this.edges[this.LOWER_EDGE].end());
    }

    /**
     * Get the right edge.
     * @return The right edge.
     */
    public Line getRightEdge() {
        return new Line(this.edges[this.RIGHT_EDGE].start(),
                this.edges[this.RIGHT_EDGE].end());
    }

    /**
     * Get the left edge.
     * @return The left edge.
     */
    public Line getLeftEdge() {
        return new Line(this.edges[this.LEFT_EDGE].start(),
                this.edges[this.LEFT_EDGE].end());
    }

    /**
     * Get the upper right point.
     * @return The upper right point.
     */
    public Point getUpperRight() {
        return new Point(this.getUpperLeft().getX() + this.getWidth(),
                this.getUpperLeft().getY());
    }

    /**
     * Get the lower left point.
     * @return The lower left point.
     */
    public Point getLowerLeft() {
        return new Point(this.getUpperLeft().getX(),
                this.getUpperLeft().getY() + this.getHeight());
    }

    /**
     * Get the lower right point.
     * @return The lower right point.
     */
    public Point getLowerRight() {
        return new Point(this.getUpperLeft().getX() + this.getWidth(),
                this.getUpperLeft().getY() + this.getHeight());
    }

    /**
     * Returns true if the given point is on the corner of the rectangle.
     * @param p The given point.
     * @return True if the given point is on the corner of the rectangle.
     */
    public boolean isPointOnCorner(Point p) {
        if ((p.equals(this.getUpperLeft())) || (p.equals(this.getUpperRight()))
                || (p.equals(this.getLowerLeft()))
                || (p.equals(this.getLowerRight()))) {
            return true;
        }
        return false;
    }

    /**
     * Set upper left point.
     * @param upperL The new upper left point.
     */
    public void setUpperLeft(Point upperL) {
        this.upperLeft = upperL;
    }

    /**
     * Update the edges according to the upper left point.
     */
    public void updateEdgesAccordingToUpperLeft() {
        this.edges[this.UPPER_EDGE] = new Line(this.getUpperLeft(),
                this.getUpperRight());
        this.edges[this.LOWER_EDGE] = new Line(this.getLowerLeft(),
                this.getLowerRight());
        this.edges[this.LEFT_EDGE] = new Line(this.getUpperLeft(),
                this.getLowerLeft());
        this.edges[this.RIGHT_EDGE] = new Line(this.getUpperRight(),
                this.getLowerRight());
    }

    /**
     * Set new upper left X.
     * @param newX new X.
     */
    public void setXUpperLeft(double newX) {
        double oldY = this.getUpperLeft().getY();
        this.setUpperLeft(new Point(newX, oldY));
    }
}

package geometryprimitives;

/**
 * Represents a point in the plain.
 */
public class Point {

    private double x;
    private double y;

    /**
     * Creates a new point with the given X,Y coordinates.
     * @param x X coordinate of the point.
     * @param y Y coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor.
     * @param p Copied point.
     */
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Returns the distance between this point to the other point.
     * @param other The other point.
     * @return The distance to the other point.
     */
    public double distance(Point other) {

        // distance between two points (x1,y1) and (x2,y2)  is the square
        // root of: ((x1-x2)^2) + ((y1-y2)^2).
        return Math.sqrt(((this.x - other.x) * (this.x - other.x)) + ((this.y
                - other.y) * (this.y - other.y)));
    }

    /**
     * Returns true if this point has the same X and Y coordinates of
     * (equals to) another point.
     * @param other The other point.
     * @return True if the point are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return ((this.x == other.x) && (this.y == other.y));
    }

    /**
     * Returns the X coordinate value ot this point.
     * @return X coordinate value of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the Y coordinate value ot this point.
     * @return Y coordinate value of the point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set the X coordinate.
     * @param xCoor The new X value.
     */
    public void setX(double xCoor) {
        this.x = xCoor;
    }

    /**
     * Set the Y coordinate.
     * @param yCoor The new Y value.
     */
    public void setY(double yCoor) {
        this.y = yCoor;
    }

    /**
     * Returns true if the point is on the horizontal line.
     * @param horizontal The horizontal line.
     * @return True if the point is on the horizontal line.
     */
    public boolean isOnHorizontalLine(Line horizontal) {
        if (horizontal.isHorizontal()) {
            if (this.getY() == horizontal.start().getY()) {
                if (horizontal.isPointInXAxisProjRng(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the point is on the vertical line.
     * @param vertical The vertical line.
     * @return True if the point is on the vertical line.
     */
    public boolean isOnVerticalLine(Line vertical) {
        if (vertical.isVertical()) {
            if (this.getX() == vertical.start().getX()) {
                if (vertical.isPointInYAxisProjRng(this)) {
                    return true;
                }
            }
        }
        return false;
    }

}


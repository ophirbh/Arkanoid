package geometryprimitives;

import java.util.List;


/**
 * Represents a line segment in the plain.
 */
public class Line {

    public static final double EPSILON = 0.00001;

    private Point start;
    private Point end;

    /**
     * Creates a new line segment between the two given points.
     * @param start The starting point of the line.
     * @param end the ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Creates a new line segment between the two given points:
     * (x1,y1) and (x2,y2).
     * @param x1 X coordinate of the first point.i
     * @param y1 Y coordinate of the first point.
     * @param x2 X coordinate of the second point.
     * @param y2 Y coordinate of the second point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Copy constructor.
     * @param l Line copied.
     */
    public Line(Line l) {
        this.start = l.start();
        this.end = l.end();
    }

    /**
     * Returns the length of the line.
     * @return The length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     * @return The middle point of the line.
     */
    public Point middle() {
        // midpoint of line segment between points (x1,y1) and (x2,y2) is:
        // (midX, midY) = ( (x1+x2)/2 , (y1+y2)/2 ).
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;

        return new Point(midX, midY);
    }

    /**
     * Returns the start point of the line.
     * @return The start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     * @return The end point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other The other line.
     * @return True if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns the intersection point if the line intersect with the other
     * line, and null otherwise.
     * @param other The other line.
     * @return The intersection point.
     */
    public Point intersectionWith(Line other) {
        if (this.isVertical()) {
            if (other.isVertical()) {
                return null;
            } else {
                return this.vertAndNonVertInter(this, other);
            }
        } else {
            if (other.isVertical()) {
                return this.vertAndNonVertInter(other, this);
            } else if (Math.abs(this.slope() - other.slope()) < this.EPSILON) {
                return null;
            } else {
                if (this.isPointInXAxisProjRng(this.nonVertInter(this,
                        other)) && other.isPointInXAxisProjRng(
                        this.nonVertInter(this, other))) {
                    return this.nonVertInter(this, other);
                }
                return null;
            }
        }
    }

    /**
     * Returns a line that represents the lines' projection on the X axis with
     * order. Meaning that the minimum x value will be part of the start point
     * and the maximum x value will be part of the end point.
     * @return A line that represents the projection on the X axis.
     */
    private Line orderedXprojection() {
        double xMin = Math.min(this.start().getX(), this.end().getX());
        double xMax = Math.max(this.start().getX(), this.end().getX());

        return new Line(xMin, 0, xMax, 0);
    }

    /**
     * Returnes the slope of a 2 dimensional, non vertical line.
     * @return The slope of the line.
     */
    private double slope() {
        double deltaY = this.end().getY() - this.start().getY();
        double deltaX = this.end().getX() - this.start().getX();

        return deltaY / deltaX;
    }

    /**
     * Returns true if the line segment is vertical, false otherwise.
     * @return True if the line segment is vertical, false otherwise.
     */
    public boolean isVertical() {
        if (this.start().getX() == this.end().getX()) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the line segment is horizontal, false otherwise.
     * @return True if the line segment is vertical, false otherwise.
     */
    public boolean isHorizontal() {
        if (this.start().getY() == this.end().getY()) {
            return true;
        }
        return false;
    }

    /**
     * Get the Y value of a coordinate using the line equation by plugin in
     * the suitable X value.
     * @param xVal The coordinate X value.
     * @return The coordinate Y value.
     */
    private double getLineEqYValue(double xVal) {
        return this.slope() * (xVal - this.start().getX())
                + this.start().getY();
    }

    /**
     * Get the X value of a coordinate using the line equation by plugin in
     * the suitable Y value.
     * @param yVal The coordinate Y value.
     * @return The coordinate X value.
     */
    private double getLineEqXvalue(double yVal) {
        return this.start().getX() + (yVal - this.start().getY())
                / this.slope();
    }

    /**
     * Returns the n value of the line equation: y = mx + n.
     * Meaning the lines' Y coordinate that intersects with the Y axis.
     * @return The lines' Y coordinate that intersects with the Y axis.
     */
    private double yIntercepts() {
        return this.start().getY() - (this.slope() * this.start().getX());
    }

    /**
     * Returns true if the given point is within the Y axis projection range
     * of the open line segment, false otherwise.
     * @param point The point to check.
     * @return True if the given point is within the Y axis projection range
     * of the open line segment, false otherwise.
     */
    public boolean isPointInYAxisProjRng(Point point) {
        if (((this.start().getY() <= point.getY()) && (point.getY()
                <= this.end().getY())) || ((this.end().getY()
                <= point.getY()) && (point.getY() <= this.start().getY()))) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the given point is within the X axis projection range
     * of the open line segment, false otherwise.
     * @param point The point to check.
     * @return True if the given point is within the X axis projection range
     * of the open line segment, false otherwise.
     */
    public boolean isPointInXAxisProjRng(Point point) {
        if (((this.start().getX() <= point.getX() && point.getX()
                <= this.end().getX())) || ((this.end().getX()
                <= point.getX()) && (point.getX() <= this.start().getX()))) {
            return true;
        }
        return false;
    }

    /**
     * Returns intersection point of a vertical line with a non vertical line.
     * If the lines don't intersect returns null.
     * @param vert The vertical line.
     * @param nonVert The non vertical line.
     * @return Intersection point.
     */
    private Point vertAndNonVertInter(Line vert, Line nonVert) {
        Point vertXProj = new Point(vert.start().getX(), 0);
        Point posInter = new Point(vert.start().getX(),
                nonVert.getLineEqYValue(vert.start().getX()));

        if (nonVert.isPointInXAxisProjRng(vertXProj)
                && vert.isPointInYAxisProjRng(posInter)) {
            return new Point(accur(posInter.getX(), 2), accur(
                    posInter.getY(), 2));
        }
        return null;
    }

    /**
     * Correct accuracy.
     * @param num Number.
     * @param accu Accuracy.
     * @return The fixed accuracy.
     */
    private double accur(double num, int accu) {
        double ten = Math.pow(10, accu);

        return Math.round(num * ten) / ten;
    }

    /**
     * Returns intersection point of a non vertical line with another non
     * vertical line. If the lines don't intersect returns null.
     * @param a Non vertical line a.
     * @param b Non vertical line b.
     * @return Intersection point.
     */
    private Point nonVertInter(Line a, Line b) {
        double x = (b.yIntercepts() - a.yIntercepts()) / (a.slope()
                - b.slope());
        double y = (a.slope() * b.yIntercepts() - b.slope()
                * a.yIntercepts()) / (a.slope() - b.slope());
        x = accur(x, 2);
        y = accur(y, 2);
        return new Point(x, y);
    }

    /**
     * Returns true if the line equals to another line, false otherwise.
     * @param other The other line.
     * @return True if the line equals to another line, false otherwise.
     */
    public boolean equals(Line other) {
        return ((this.start().equals(other.start())
                && this.end().equals(other.end()))
                || (this.start().equals(other.end())
                && this.end().equals(other.start())));
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of
     * the line.
     * @param rect The rectangle we check intersections with.
     * @return The closest intersection to the start of the line or null
     * if such is non existent.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);

        if (intersections.isEmpty()) {
            return null;
        } else if (intersections.size() == 1) {
            return (Point) intersections.get(0);
        } else {
             double distance0 = this.start.distance((Point) intersections.get(0));
             double distance1 = this.start.distance((Point) intersections.get(1));
             if (distance0 > distance1) {
                 return (Point) intersections.get(1);
             }
             return (Point) intersections.get(0);
        }

    }

}

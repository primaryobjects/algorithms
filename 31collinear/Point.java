/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2)
        {
            int result = 0; // slope1 == slope2

            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);

            if (slope1 < slope2)
            {
                result = -1;
            }
            else if (slope1 > slope2)
            {
                result = 1;
            }

            return result;
        }
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double result = 0;
        int xDiff = (that.x - this.x);
        int yDiff = (that.y - this.y);

        if (xDiff == 0 && yDiff == 0)
        {
            result = Double.NEGATIVE_INFINITY;
        }
        else if (xDiff == 0)
        {
            // Vertical segment.
            result = Double.POSITIVE_INFINITY;
        }
        else if (yDiff == 0)
        {
            // Horizontal segment.
            result = 0.0;
        }
        else
        {
            result = (double) yDiff / xDiff;
        }

        return result;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        int result = 0; // this.y == that.y && this.x == that.x

        if (this.y < that.y)
        {
            result = -1;
        }
        else if (this.y > that.y)
        {
            result = 1;
        }
        else if (this.x < that.x)
        {
            result = -1;
        }
        else if (this.x > that.x)
        {
            result = 1;
        }

        return result;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 3);
        System.out.println(p1.slopeTo(p2));
        p1.draw();
        p2.draw();
        p1.drawTo(p2);

        p1 = new Point(8, 5);
        p2 = new Point(0, 0);
        System.out.println(p1.slopeTo(p2));
        p1.draw();
        p2.draw();
        p1.drawTo(p2);

        if (p1.compareTo(p2) < 0)
        {
            System.out.println("Less");
        }
        else if (p1.compareTo(p2) > 0)
        {
            System.out.println("Greater");
        }
        else
        {
            System.out.println("Equal");
        }
    }
}
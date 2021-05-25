import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points)
    {
        // Finds all line segments containing 4 points.
        if (points == null)
        {
            throw new IllegalArgumentException("No points passed to constructor.");
        }

        // Check for any null points.
        for (Point point : points)
        {
            if (point == null)
            {
                throw new IllegalArgumentException("Null point.");
            }
        }

        // Make a copy of the points.
        Point[] pointsSorted = points.clone();

        // Sort points to place duplicates together.
        Arrays.sort(pointsSorted);

        // Check for duplicates.
        for (int i = 0; i < pointsSorted.length - 1; i++)
        {
            Point p1 = pointsSorted[i];
            Point p2 = pointsSorted[i+1];

            if (p1.compareTo(p2) == 0)
            {
                throw new IllegalArgumentException("Duplicate point.");
            }
        }

        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        if (points.length > 3)
        {
            // Create a secondary array of points for comparison.
            Point[] secondaryPoints = pointsSorted.clone();

            // Find all 4-point line segments.
            for (Point point : pointsSorted)
            {
                // Sort the comparison points with respect to the current point.
                Arrays.sort(secondaryPoints, point.slopeOrder());

                findSegments(secondaryPoints, point, segmentsList);
            }
        }

        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    //#region Private Methods
    private boolean collinear(Point p1, Point p2, Point p3)
    {
        double slope1 = p1.slopeTo(p2);
        double slope2 = p1.slopeTo(p3);

        return Double.compare(slope1, slope2) == 0;
    }

    private void findSegments(Point[] points, Point point, ArrayList<LineSegment> res) {
        // start from position 1, since position 0 will be the point p itself
        int start = 1;

        for (int i = 2; i < points.length; i++) {
            if (!collinear(point, points[start], points[i])) {
                // check to see whether there have already 3 equal points
                if (i - start >= 3) {
                    Point[] ls = getSegment(points, point, start, i);
                    /**
                     * Important Point: only add line segment which starts form point p to avoid
                     * duplicate
                     */
                    if (ls[0].compareTo(point) == 0) {
                        res.add(new LineSegment(ls[0], ls[1]));
                    }
                }
                // update
                start = i;
            }
        }

        // situation when the last several points in the array are collinear
        if (points.length - start >= 3) {
            Point[] lastPoints = getSegment(points, point, start, points.length);
            if (lastPoints[0].compareTo(point) == 0) {
                res.add(new LineSegment(lastPoints[0], lastPoints[1]));
            }
        }
    }
    
    private Point[] getSegment(Point[] points, Point p, int start, int end) {
        ArrayList<Point> temp = new ArrayList<>();
        temp.add(p);
        for (int i = start; i < end; i++) {
            temp.add(points[i]);
        }
        temp.sort(null);
        return new Point[] { temp.get(0), temp.get(temp.size() - 1) };
    }
    //#endregion
    
    public int numberOfSegments()
    {
        // The number of line segments
        return segments.length;
    }

    public LineSegment[] segments()
    {
        return Arrays.copyOf(segments, segments.length);
    }

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            throw new IllegalArgumentException("Missing filename.");
        }

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

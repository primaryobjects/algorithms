import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points)
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
        for (int i = 0; i < points.length - 1; i++)
        {
            Point p1 = points[i];
            Point p2 = points[i+1];

            if (p1.compareTo(p2) == 0)
            {
                throw new IllegalArgumentException("Duplicate point.");
            }
        }

        // Find all 4-point line segments.
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        for (int i = 0; i < pointsSorted.length - 3; i++)
        {
            Point p1 = pointsSorted[i];
            for (int j = i + 1; j < pointsSorted.length - 2; j++)
            {
                Point p2 = pointsSorted[j];
                for (int k = j + 1; k < pointsSorted.length - 1; k++)
                {
                    Point p3 = pointsSorted[k];
                    for (int l = k + 1; l < pointsSorted.length; l++)
                    {
                        Point p4 = pointsSorted[l];

                        if (collinear(p1, p2, p3, p4))
                        {
                            // We have a line segment.
                            LineSegment segment = new LineSegment(p1, p4);
                            if (!segmentsList.contains(segment))
                            {
                                segmentsList.add(segment);
                            }
                        }
                    }
                }
            }
        }

        segments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    private boolean collinear(Point p1, Point p2, Point p3, Point p4)
    {
        boolean result = false;

        double slope1 = p1.slopeTo(p2);
        double slope2 = p1.slopeTo(p3);
        double slope3 = p1.slopeTo(p4);

        if (Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0)
        {
            result = true;
        }

        return result;
    }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

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

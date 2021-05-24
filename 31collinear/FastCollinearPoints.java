import java.util.ArrayList;
import java.util.Arrays;

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

        // Check for duplicate points, by first sorting so they're together.
        Point[] pointsSorted = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSorted);
        for (int i=0; i<points.length - 1; i++)
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
                        if (p1.slopeTo(p2) == p1.slopeTo(p4) && p1.slopeTo(p2) == p1.slopeTo(p3))
                        {
                            // We have a line segment.
                            LineSegment segment = new LineSegment(p1, p4);
                            if (segmentsList.contains(segment))
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

    public int numberOfSegments()
    {
        // The number of line segments
        return segments.length;
    }

    public LineSegment[] segments()
    {
        return Arrays.copyOf(segments, segments.length);
    }
}

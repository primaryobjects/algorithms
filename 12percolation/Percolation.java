import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private State[][] _arr;
    private int _openCount = 0;
    private WeightedQuickUnionUF _quickUnion;

    public enum State {
        OPEN,
        FULL
    }

    private class Point
    {
        public int x, y;

        public Point(int _x, int _y)
        {
            x = _x;
            y = _y;
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException();
        }

        _arr = new State[n][n];
        _openCount = n*n;
        _quickUnion = new WeightedQuickUnionUF(_openCount + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 || col < 1 || col > _arr.length || row > _arr[0].length)
        {
            throw new IllegalArgumentException();
        }
        
        if (_arr[row-1][col-1] != State.OPEN)
        {
            _arr[row-1][col-1] = State.OPEN;
            _openCount++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 1 || col < 1)
        {
            throw new IllegalArgumentException();
        }

        return _arr[row-1][col-1] == State.OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 1 || col < 1)
        {
            throw new IllegalArgumentException();
        }

        return _arr[row-1][col-1] == State.FULL || _arr[row-1][col-1] == null;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return _openCount;
    }

    private String flow(int row, int col, String path, int history[][])
    {
        if (row < 1 || col < 1 || row > _arr.length || col > _arr[0].length || history[row-1][col-1] == 1)
        {
            // Invalid row or col or already visited.
            return "";
        }
        else if (isOpen(row, col))
        {
            // The current cell is open, include in the path, and continue on.
            path += "(" + row + "," + col + ")";

            if (col == _arr.length)
            {
                // We've reached the bottom with an open space, we're done!
                path += "*";
                return path;
            }
            else
            {
                history[row-1][col-1] = 1;

                String temp;

                // Try to flow left, right, up, down.
                temp = flow(row, col - 1, path, history);
                if (temp.endsWith("*")) return temp;
                temp = flow(row, col + 1, path, history);
                if (temp.endsWith("*")) return temp;
                temp = flow(row - 1, col, path, history);
                if (temp.endsWith("*")) return temp;
                temp = flow(row + 1, col, path, history);
                if (temp.endsWith("*")) return temp;
            }
        }

        return "";
    }

    // does the system percolate?
    public boolean percolates()
    {
        // Using depth-first search, we will check for a path from any cell in the top row to the bottom row.
        String path = flow(1, 1, "", new int[_arr.length][_arr[0].length]);
        System.out.println(path);
        return path.endsWith("*");
    }

    public void draw()
    {
        int width = _arr[0].length;
        int height = _arr.length;

        StdDraw.setXscale(-.05*width, 1.05*width);
        StdDraw.setYscale(-.05*height, 1.20*height);

        for (int row=0; row<height; row++)
        {
            for (int col=0; col<width; col++)
            {
                if (isOpen(row+1, col+1))
                {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                }
                else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }

                StdDraw.filledSquare(row + 0.5, height - col, 0.45);
            }
        }
    }

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(3, 2);
        percolation.open(3, 3);
        percolation.open(2, 3);
        percolation.open(1, 3);
        percolation.open(1, 4);
        percolation.open(4, 3);
        percolation.open(4, 4);
        percolation.open(4, 5);
        percolation.draw();

        boolean result = percolation.percolates();
        System.out.println(result);
    }
}
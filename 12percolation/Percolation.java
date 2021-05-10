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

        return _arr[row-1][col-1] == State.FULL;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return _openCount;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return false;
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
        Percolation percolation = new Percolation(4);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(2, 3);
        percolation.open(4, 4);
        percolation.draw();
    }
}
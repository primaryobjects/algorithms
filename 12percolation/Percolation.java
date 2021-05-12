//import java.awt.Color;

//import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private State[][] _arr;
    private int _openCount = 0;
    private final int _topID;
    private final int _bottomID;
    private final WeightedQuickUnionUF _uf;

    private enum State {
        OPEN,
        FULL
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n < 1)
        {
            throw new IllegalArgumentException();
        }

        _arr = new State[n][n];
        _topID = n*n; // Use the next to last slot in the array as the virtual top cluster.
        _bottomID = _topID + 1; // Use the last slot in the array as the virtual bottom cluster.
        
        _uf = new WeightedQuickUnionUF(n*n + 2); // +1 for a virtual top and +1 for a virtual bottom cluster.
    }

    private int toID(int row, int col)
    {
        // Converts a 2D x,y coordinate into a 1D index value.
        if (row < 1 || col < 1 || row > _arr.length || col > _arr[0].length)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            // Offset by 1 since row and col are 1 to n.
            return (_arr.length * (row - 1)) + (col - 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 || col < 1 || col > _arr.length || row > _arr[0].length)
        {
            throw new IllegalArgumentException();
        }
        
        if (!isOpen(row, col))
        {
            _arr[row-1][col-1] = State.OPEN;
            _openCount++;

            // Convert 2D to 1D coordinate for UF array.
            int p = toID(row, col);

            // Union with adjacent open cells, top, right, bottom, left.
            if (row-1 > 0 && isOpen(row-1, col))
            {
                int q = toID(row-1, col);
                _uf.union(p, q);
            }
            if (row+1 <= _arr.length && isOpen(row+1, col))
            {
                int q = toID(row+1, col);
                _uf.union(p, q);
            }
            if (col-1 > 0 && isOpen(row, col-1))
            {
                int q = toID(row, col-1);
                _uf.union(p, q);
            }
            if (col+1 <= _arr[0].length && isOpen(row, col+1))
            {
                int q = toID(row, col+1);
                _uf.union(p, q);
            }

            // If this is the top or bottom row, connect with the virtual top or bottom cluster.
            if (row == 1)
            {
                _uf.union(p, _topID);
            }

            if (row == _arr.length)
            {
                _uf.union(p, _bottomID);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 1 || col < 1 || row > _arr.length || col > _arr[0].length)
        {
            throw new IllegalArgumentException();
        }

        return _arr[row-1][col-1] == State.OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 1 || col < 1 || row > _arr.length || col > _arr[0].length)
        {
            throw new IllegalArgumentException();
        }

        return _uf.find(toID(row, col)) == _uf.find(_topID);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return _openCount;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return _uf.find(_topID) == _uf.find(_bottomID);
    }

    /*private void draw()
    {
        int width = _arr[0].length;
        int height = _arr.length;

        StdDraw.setXscale(-0.05*width, 1.05*width);
        StdDraw.setYscale(-0.05*height, 1.20*height);

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                Color color = StdDraw.BLACK;

                if (isOpen(col+1, row+1))
                {
                    color = Color.LIGHT_GRAY;
                }

                StdDraw.setPenColor(color);
                StdDraw.filledSquare(row + 0.5, height - col, 0.45);
            }
        }
    }*/

    // test client (optional)
    public static void main(String[] args)
    {
        int width = 1;
        Percolation percolation = new Percolation(width);

        // Generate a random grid of open/close cells.
        for (int row = 1; row <= width; row++)
        {
            for (int col = 1; col <= width; col++)
            {
                if (StdRandom.bernoulli(0.6))
                {
                    percolation.open(row, col);
                }
            }
        }

        //percolation.draw();
        System.out.println(percolation.percolates());
    }

}
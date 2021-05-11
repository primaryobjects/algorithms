import java.awt.Color;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationDPS {
    private State[][] _arr;
    private int _openCount = 0;
    private Point[] _solution;

    private enum State {
        OPEN,
        FULL
    }

    private Point[] append(int n, Point arr[], Point p)
    { 
        int i;
 
        Point newArray[] = new Point[n + 1];
        
        // Copy original array into new array.
        for (i = 0; i < n; i++)
        {
            newArray[i] = arr[i];
        }
 
        // Add element to the new array.
        newArray[n] = p;
 
        return newArray; 
    }

    private class Point
    {
        public int row, col;

        public Point(int _row, int _col)
        {
            row = _row;
            col = _col;
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public PercolationDPS(int n)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException();
        }

        _arr = new State[n][n];
        _openCount = n*n;
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

    private Point[] flow(int row, int col, Point[] path, int history[][])
    {
        if (row < 1 || col < 1 || row > _arr.length || col > _arr[0].length || history[row-1][col-1] == 1)
        {
            // Invalid row or col or already visited.
            return null;
        }
        else if (isOpen(row, col))
        {
            // The current cell is open, include in the path, and continue on.
            path = append(path.length, path, new Point(row, col));

            if (col == _arr.length)
            {
                // We've reached the bottom with an open space, we're done!
                return path;
            }
            else
            {
                history[row-1][col-1] = 1;

                Point[] temp;

                // Try to flow left, right, up, down.
                temp = flow(row, col - 1, path, history);
                if (temp != null) return temp;
                temp = flow(row, col + 1, path, history);
                if (temp != null) return temp;
                temp = flow(row - 1, col, path, history);
                if (temp != null) return temp;
                temp = flow(row + 1, col, path, history);
                if (temp != null) return temp;
            }
        }

        return null;
    }

    // does the system percolate?
    public boolean percolates()
    {
        // Using depth-first search, we will check for a path from any cell in the top row to the bottom row.
        for (int col=1; col<=_arr[0].length; col++)
        {
            Point[] path = flow(col, 1, new Point[]{}, new int[_arr.length][_arr[0].length]);
            if (path != null)
            {
                // Print solution.
                for (int i=0; i<path.length; i++)
                {
                    System.out.println("(" + path[i].col + "," + path[i].row + ")");
                }

                _solution = path;
                break;
            }
        }

        return _solution != null;
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
                Color color = StdDraw.BLACK;

                if (isOpen(row+1, col+1))
                {
                    color = Color.LIGHT_GRAY;
                }

                if (_solution != null)
                {
                    for (int i=0; i<_solution.length; i++)
                    {
                        if (_solution[i].row == row+1 && _solution[i].col == col+1)
                        {
                            color = Color.GREEN;
                            break;
                        }
                    }
                }

                StdDraw.setPenColor(color);
                StdDraw.filledSquare(row + 0.5, height - col, 0.45);
            }
        }
    }

    // test client (optional)
    public static void main(String[] args)
    {
        int width = 40;        
        Percolation percolation = new Percolation(width);

        // Generate a random grid of open/close cells.
        for (int row=1; row<=width; row++)
        {
            for (int col=1; col<=width; col++)
            {
                if (StdRandom.bernoulli(0.6))
                {
                    percolation.open(row, col);
                }
            }
        }

        /*percolation.open(1, 1);
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
        percolation.open(4, 6);
        percolation.open(4, 7);
        percolation.open(5, 7);
        percolation.open(6, 7);
        percolation.open(6, 8);*/

        percolation.draw();
        
        // Calculate a flow path from top to bottom.
        boolean solution = percolation.percolates();
        System.out.println(solution);
        if (solution)
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            percolation.draw();
        }
    }

}
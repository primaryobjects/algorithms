public class Percolation {
    private State[][] _arr;
    private int _openCount = 0;

    public enum State {
        OPEN,
        FULL
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        _arr = new State[n][n];
        _openCount = n*n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (_arr[col][row] != State.OPEN)
        {
            _arr[col][row] = State.OPEN;
            _openCount++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        return _arr[col][row] == State.OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        return _arr[col][row] == State.FULL;
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

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(2);
        System.out.println("test " + percolation.numberOfOpenSites());
    }
}
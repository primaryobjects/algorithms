import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int _n;
    private int _T;
    private double[] _thresholds;

    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException();
        }

        _n = n;
        _T = trials;
        _thresholds = new double[_T];

        run();
    }

    private int trial()
    {
        int count = 0;
        Percolation percolation = new Percolation(_n);
            
        for (count=0; count<_n*_n; count++)
        {
            // Select a random row and col.
            int row = StdRandom.uniform(_n) + 1;
            int col = StdRandom.uniform(_n) + 1;
            
            if (!percolation.isOpen(row, col))
            {
                percolation.open(row, col);

                if (percolation.percolates())
                {
                    break;
                }
            }
            else
            {
                count--;
            }
        }

        return count;
    }

    private void run()
    {
        for (int trial = 0; trial < _T; trial++)
        {
            // Count how many "opens" it takes to percolate this trial.
            double count = trial();
            _thresholds[trial] = count / (_n * _n);
        }
    }

    public double mean()
    {
        return StdStats.mean(_thresholds);
    }

    public double stddev()
    {
        return StdStats.stddev(_thresholds);
    }

    public double confidenceLo()
    {
        return mean() - (1.96 * stddev() / Math.sqrt(_T));
    }

    public double confidenceHi()
    {
        return mean() + (1.96 * stddev() / Math.sqrt(_T));
    }

    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            throw new IllegalArgumentException("Error: Two arguments are required: n, trials");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
    }
}

public class PercolationStats {
    private double _T;
    private double _samples[];

    private int simulate(int N) {
        int time = 0;
        Percolation percolation = new Percolation(N);

        while (!percolation.percolates()) {
            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;
            if (percolation.isOpen(i, j)) continue;
            percolation.open(i, j);
            ++time;
        }

        return time;
    }

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();

        double size = N * N;
        _T = T;
        _samples = new double[T];

        for (int t = 0; t < T; ++t) _samples[t] = (double)simulate(N) / size;
    }

    public double mean() {
        return StdStats.mean(_samples);
    }

    public double stddev() {
        return StdStats.stddev(_samples);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(_T);
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(_T);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stat = new PercolationStats(N, T);
        StdOut.println("mean    = " + stat.mean());
        StdOut.println("stddev  = " + stat.stddev());
        StdOut.println("conf_lo = " + stat.confidenceLo());
        StdOut.println("conf_hi = " + stat.confidenceHi());
    }

}

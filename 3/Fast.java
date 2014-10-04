import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Fast {
    private final static Comparator<Point> heightCompare = new Comparator<Point>() {
        public int compare(Point a, Point b) {
            return a.compareTo(b);
        }
    };

    private static void printResult(ArrayList<Point> line) {
        // trick to fix: I'm having trouble avoiding subsegments Fast.java when there are 5 or more points on a line segment.
        for (int i = 1; i < line.size(); ++i) {
            if (line.get(i).compareTo(line.get(i - 1)) < 0) return;
        }
        StdOut.print(line.get(0).toString());
        for (int i = 1; i < line.size(); ++i) {
            Point item = line.get(i);
            StdOut.print(" -> ");
            StdOut.print(item.toString());
        }
        StdOut.println();
        line.get(0).drawTo(line.get(line.size() - 1));
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        In in = new In(args[0]);
        int num = in.readInt();
        Point points[] = new Point[num];
        for (int i = 0; i < num; ++i) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }
        Arrays.sort(points, heightCompare);

        Point sorted[] = new Point[points.length];
        for (int i = 0; i < num; ++i) {
            Point base = points[i];
            System.arraycopy(points, 0, sorted, 0, points.length);
            Arrays.sort(sorted, base.SLOPE_ORDER);

            double slope = Double.NaN;
            ArrayList<Point> line = new ArrayList<Point>();
            line.add(base);

            for (int j = 0; j < sorted.length; ++j) {
                Point current = sorted[j];
                // direct push when a line doesn't exist
                if (line.size() < 2) {
                    line.add(current);
                    slope = base.slopeTo(current);
                    continue;
                }

                // check whether a new line is found
                double newSlope = base.slopeTo(current);
                if (newSlope != slope) {
                    if (line.size() >= 4) printResult(line);
                    line.clear();
                    line.add(base);
                    slope = newSlope;
                }

                line.add(current);
            }
            if (line.size() >= 4) printResult(line);
        }

    }
}



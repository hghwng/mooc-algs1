import java.util.Arrays;

public class Brute {
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

        int round = 4;
        int i[] = new int[round];
        for (i[0] = 0; i[0] < num; ++i[0]) {
            for (i[1] = i[0] + 1; i[1] < num; ++i[1]) {
                double slope0 = points[i[0]].slopeTo(points[i[1]]);
                for (i[2] = i[1] + 1; i[2] < num; ++i[2]) {
                    double slope1 = points[i[1]].slopeTo(points[i[2]]);
                    if (slope0 != slope1) continue;
                    for (i[3] = i[2] + 1; i[3] < num; ++i[3]) {
                        double slope2 = points[i[2]].slopeTo(points[i[3]]);
                        if (slope1 != slope2) continue;

                        Point line[] = new Point[round];
                        for (int j = 0; j < round; ++j) line[j] = points[i[j]];
                        Arrays.sort(line);

                        StdOut.print(line[0].toString());
                        for (int j = 1; j < round; ++j) {
                            StdOut.print(" -> ");
                            StdOut.print(line[j].toString());
                        }
                        StdOut.println();
                        line[0].drawTo(line[round - 1]);
                    }
                }
            }
        }
    }
}

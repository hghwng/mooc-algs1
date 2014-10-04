import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // user code begin
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (this.x == that.x) return this.y == that.y ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        if (this.y == that.y) return +0.0;
        return (double)(that.y - this.y) / (double)(that.x - this.x);
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y == that.y) {
            if (this.x < that.x) return -1;
            if (this.x == that.x) return 0;
        }
        return 1;
    }

    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point a, Point b) {
            double aSlope = slopeTo(a);
            double bSlope = slopeTo(b);
            if (aSlope == bSlope) return 0;
            return aSlope < bSlope ? -1 : 1;
        }
    };

    public static void main(String[] args) {
        Point p = new Point(260, 17);
        Point q = new Point(260, 242);
        StdOut.println(p.slopeTo(q));
    }
}

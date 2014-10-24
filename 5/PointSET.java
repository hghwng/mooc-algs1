import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p: points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> ret = new ArrayList<Point2D>();
        for (Point2D p: points) {
            if (rect.contains(p)) {
                ret.add(p);
            }
        }
        return ret;
    }

    public Point2D nearest(Point2D p) {
        Point2D minPoint = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Point2D curPoint: points) {
            double curDist = curPoint.distanceSquaredTo(p);
            if (curDist < minDist) {
                minDist = curDist;
                minPoint = curPoint;
            }
        }

        return minPoint;
    }

    public static void main(String[] args) {

    }
}


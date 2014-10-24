import java.util.ArrayList;
import java.util.Iterator;

public class RangeSearchVisualizer {

    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);


        StdDraw.show(0);

        ArrayList<Point2D> points = new ArrayList<Point2D>();
        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
            points.add(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        brute.draw();

        while (true) {
            StdDraw.show(40);

            /*
            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }
            */

            Point2D p0 = points.get((int)(points.size() * Math.random()));
            Point2D p1 = points.get((int)(points.size() * Math.random()));
            x0 = p0.x();
            y0 = p0.y();
            x1 = p1.x();
            y1 = p1.y();

            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            Iterable<Point2D> bruteRet = brute.range(rect);
            Iterable<Point2D> kdtreeRet = kdtree.range(rect);

            boolean checkFail = false;
            for (Point2D brutePoint : bruteRet) {
                boolean found = false;
                    for (Point2D kdtreePoint : kdtreeRet) {
                    if (kdtreePoint.equals(brutePoint)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    checkFail = true;
                    break;
                }
            }

            if (checkFail) {
                // draw the range search results for brute-force data structure in red
                StdDraw.setPenRadius(.02);
                StdDraw.setPenColor(StdDraw.RED);
                for (Point2D p : bruteRet)
                    p.draw();

                // draw the range search results for kd-tree in blue
                StdDraw.setPenRadius(.015);
                StdDraw.setPenColor(StdDraw.BLUE);
                for (Point2D p : kdtreeRet)
                    p.draw();

                StdDraw.show(1000);
            } else {
                System.out.println("pass");
            }
        }
    }
}

public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        brute.draw();
        kdtree.draw();

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            x = 0.435547;
            y = 0.519336;
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            Point2D ok = brute.nearest(query);
            ok.draw();
            StdDraw.setPenRadius(.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D my = kdtree.nearest(query);
            my.draw();
            if (!my.equals(ok)) System.out.printf("%8.6f %8.6f\n", x, y);
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }
}


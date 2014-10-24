public class KdTreeVisualizer {

    public static void main(String[] args) {
        StdDraw.show(0);
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                System.out.printf("%d %8.6f %8.6f\n", kdtree.size(), x, y);
                StdDraw.clear();
                kdtree.draw();
            }
            StdDraw.show(100);
        }

    }
}

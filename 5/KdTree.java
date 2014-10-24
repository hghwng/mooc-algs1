import java.awt.*;
import java.util.ArrayList;

public class KdTree {
    private class Node {
        private Point2D point;
        private Node left, right, parent;
        private boolean isHorizontal;
        private int size;

        public Node(Point2D point, boolean isHorizontal) {
            this.size = 1;
            this.point = point;
            this.isHorizontal = isHorizontal;
        }

        public int size() {
            return size;
        }

        private boolean toLeftBranch(Point2D p) {
            if (isHorizontal) return p.y() < point.y();
            return p.x() < point.x();
        }

        public void insert(Point2D p) {
            if (point.equals(p)) return;

            if (toLeftBranch(p)) {
                if (left == null) {
                    left = new Node(p, !isHorizontal);
                    left.parent = this;
                } else {
                    left.insert(p);
                }
            } else {
                if (right == null) {
                    right = new Node(p, !isHorizontal);
                    right.parent = this;
                } else {
                    right.insert(p);
                }
            }

            size = 1;
            if (left != null) size += left.size();
            if (right != null) size += right.size();
        }

        public boolean contains(Point2D p) {
            if (point.equals(p)) return true;
            if (toLeftBranch(p)) {
                if (left == null) return false;
                return left.contains(p);
            } else {
                if (right == null) return false;
                return right.contains(p);
            }
        }

        public void draw() {
            double mid = isHorizontal ? point.y() : point.x();
            double begin = 0;
            double end = 1;

            if (parent != null) {
                double tmp = isHorizontal ? parent.point.x() : parent.point.y();
                if (parent.left == this) {
                    end = tmp;
                } else {
                    begin = tmp;
                }

                // trace to parents until find appropriate tmp
                Node delimiterNode = parent.parent;
                while (delimiterNode != null) {
                    tmp = isHorizontal ? delimiterNode.point.x() : delimiterNode.point.y();
                    if (delimiterNode.isHorizontal != isHorizontal) {
                        if (begin == 0 && tmp < end) {
                            begin = tmp;
                            break;
                        } else if (end == 1 && tmp > begin) {
                            end = tmp;
                            break;
                        }
                    }
                    delimiterNode = delimiterNode.parent;
                }
            }

            StdDraw.setPenColor(Color.black);
            point.draw();
            if (isHorizontal) {
                StdDraw.setPenColor(Color.blue);
                StdDraw.line(begin, mid, end, mid);
            } else {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(mid, begin, mid, end);
            }

            if (left != null) left.draw();
            if (right != null) right.draw();
        }

        public void range(ArrayList<Point2D> list, RectHV rect) {
            double min, max, cmp;
            if (isHorizontal) {
                min = rect.ymin();
                max = rect.ymax();
                cmp = point.y();
            } else {
                min = rect.xmin();
                max = rect.xmax();
                cmp = point.x();
            }

            if (rect.contains(this.point)) list.add(point);
            if (left != null && min < cmp) left.range(list, rect);
            if (right != null && max > cmp) right.range(list, rect);
        }

        public Point2D nearest(Point2D p) {
            Node firstNode, secondNode;
            if (toLeftBranch(p)) {
                firstNode = left;
                secondNode = right;
            } else {
                firstNode = right;
                secondNode = left;
            }

            Point2D bestPoint = point;
            double bestDist = point.distanceSquaredTo(p);

            if (firstNode != null) {
                Point2D newPoint = firstNode.nearest(p);
                double newDist = newPoint.distanceSquaredTo(p);
                if (newDist < bestDist) {
                    bestDist = newDist;
                    bestPoint = newPoint;
                }
            }

            if ((isHorizontal && bestDist > Math.pow(point.y() - p.y(), 2))
                    || (!isHorizontal && bestDist > Math.pow(point.x() - p.x(), 2))) {
                if (secondNode != null) {
                    Point2D newPoint = secondNode.nearest(p);
                    double newDist = newPoint.distanceSquaredTo(p);
                    if (newDist < bestDist) {
                        bestPoint = newPoint;
                    }
                }
            }

            return bestPoint;
        }
    }

    private Node root;

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (isEmpty()) return 0;
        return root.size();
    }

    public void insert(Point2D p) {
        if (isEmpty()) {
            root = new Node(p, false);
        } else {
            root.insert(p);
        }
    }

    public boolean contains(Point2D p) {
        if (isEmpty()) return false;
        return root.contains(p);
    }

    public void draw() {
        if (!isEmpty()) root.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        if (isEmpty()) return list;
        root.range(list, rect);
        return list;
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return root.nearest(p);
    }
}

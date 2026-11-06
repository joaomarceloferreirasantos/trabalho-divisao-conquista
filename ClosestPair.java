import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClosestPair {

    public static class Point {
        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double distance(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }


    public static double bruteForce(List<Point> points, int low, int high) {
        double min = Double.POSITIVE_INFINITY;

        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                double d = distance(points.get(i), points.get(j));
                if (d < min) {
                    min = d;
                }
            }
        }
        return min;
    }


    public static double closestUtil(List<Point> ptsSortedX, int low, int high) {
        if (ptsSortedX == null || low < 0 || high >= ptsSortedX.size() || low > high) {
            throw new IllegalArgumentException("Intervalo de pontos invalido.");
        }

        List<Point> pointsByX = new ArrayList<>(ptsSortedX.subList(low, high + 1));
        if (pointsByX.size() < 2) {
            return Double.POSITIVE_INFINITY;
        }


        List<Point> pointsByY = new ArrayList<>(pointsByX);
        pointsByY.sort(Comparator
                .comparingDouble((Point p) -> p.y)
                .thenComparingDouble(p -> p.x));

        return closestRecursive(pointsByX, pointsByY);
    }


    private static double closestRecursive(List<Point> pointsByX, List<Point> pointsByY) {
        int n = pointsByX.size();


        if (n <= 3) {
            return bruteForce(pointsByX, 0, n - 1);
        }

        int mid = n / 2;
        double middleX = pointsByX.get(mid).x;

        List<Point> leftX = new ArrayList<>(pointsByX.subList(0, mid));
        List<Point> rightX = new ArrayList<>(pointsByX.subList(mid, n));


        Set<Point> leftPoints = new HashSet<>(leftX);
        List<Point> leftY = new ArrayList<>(leftX.size());
        List<Point> rightY = new ArrayList<>(rightX.size());


        for (Point point : pointsByY) {
            if (leftPoints.contains(point)) {
                leftY.add(point);
            } else {
                rightY.add(point);
            }
        }

        double leftDistance = closestRecursive(leftX, leftY);
        double rightDistance = closestRecursive(rightX, rightY);
        double delta = Math.min(leftDistance, rightDistance);


        List<Point> strip = new ArrayList<>();
        for (Point point : pointsByY) {
            if (Math.abs(point.x - middleX) < delta) {
                strip.add(point);
            }
        }

        return Math.min(delta, stripClosest(strip, delta));
    }


    private static double stripClosest(List<Point> strip, double delta) {
        double min = delta;

        for (int i = 0; i < strip.size(); i++) {
            int limit = Math.min(i + 7, strip.size() - 1);

            for (int j = i + 1; j <= limit; j++) {

                if (strip.get(j).y - strip.get(i).y >= min) {
                    break;
                }

                double d = distance(strip.get(i), strip.get(j));
                if (d < min) {
                    min = d;
                }
            }
        }
        return min;
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        double min = Double.MAX_VALUE;
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

    private static double stripClosest(List<Point> strip, double d) {
        double min = d;
       
        strip.sort(Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.size(); i++) {
            // Compara com no máximo 7 vizinhos seguintes ordenados por y[cite: 2]
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < min; j++) {
                double dist = distance(strip.get(i), strip.get(j));
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    public static double closestUtil(List<Point> ptsSortedX, int low, int high) {
        if (high - low <= 3) {
            return bruteForce(ptsSortedX, low, high);
        }

        int mid = low + (high - low) / 2;
        Point midPoint = ptsSortedX.get(mid);

        double dl = closestUtil(ptsSortedX, low, mid);
        double dr = closestUtil(ptsSortedX, mid + 1, high);

        double delta = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            if (Math.abs(ptsSortedX.get(i).x - midPoint.x) < delta) {
                strip.add(ptsSortedX.get(i));
            }
        }

        return Math.min(delta, stripClosest(strip, delta));
    }
}
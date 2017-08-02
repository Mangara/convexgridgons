package convexgridgons;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ConvexUtils {

    private ConvexUtils() {

    }

    /**
     * Tests whether the given set of points is in convex position.
     *
     * @param points
     * @return
     */
    public static boolean isConvex(List<? extends Point2D> points) {
        if (points.size() <= 3) {
            return true;
        }

        // Perform the convex hull computation, abort if we find an interior point
        List<Point2D> sorted = new ArrayList<>(points);
        Collections.sort(sorted, Comparator.comparingDouble(Point2D::getX));

        LinkedList<Point2D> upperHull = new LinkedList<>(), lowerHull = new LinkedList<>();

        upperHull.push(sorted.get(0));
        upperHull.push(sorted.get(1));
        lowerHull.push(sorted.get(0));
        lowerHull.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {
            Point2D v = sorted.get(i);

            // Add to the upper hull
            while (upperHull.size() > 1 && !rightTurn(upperHull.get(1), upperHull.get(0), v)) {
                Point2D removed = upperHull.pop();

                if (!lowerHull.contains(removed)) {
                    return false;
                }
            }

            // Add to the lower hull
            while (lowerHull.size() > 1 && rightTurn(lowerHull.get(1), lowerHull.get(0), v)) {
                Point2D removed = lowerHull.pop();

                if (!upperHull.contains(removed)) {
                    return false;
                }
            }

            upperHull.push(v);
            lowerHull.push(v);
        }

        return true;
    }

    /**
     * Adds the given point to the given convex hull if possible. If the full
     * set of points is in convex position, this returns true and modifies the
     * two lists to reflect the new convex hull. If the given point is inside
     * the convex hull of the existing points, or adding it to the convex hull
     * would remove points from the existing hull, the method returns false and
     * leaves the lists unmodified.
     *
     * Pre-condition: upper and lower hull are sorted by x-coordinate.
     * 
     * @param <T>
     * @param upperHull
     * @param lowerHull
     * @param point
     * @return
     */
    public static <T extends Point2D> boolean addToConvexHull(List<T> upperHull, List<T> lowerHull, T point) {
        if (point.getX() > upperHull.get(upperHull.size() - 1).getX()) {
            
        }
        
        // Try adding to the upper hull
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Computes the convex hull with a graham scan.
     *
     * @param <T>
     * @param points
     * @return
     */
    public static <T extends Point2D> List<T> convexHull(List<T> points) {
        if (points.size() <= 3) {
            return new ArrayList<>(points);
        }

        // Perform the convex hull computation, abort if we find an interior point
        List<T> sorted = new ArrayList<>(points);
        Collections.sort(sorted, Comparator.comparingDouble(Point2D::getX));

        LinkedList<T> upperHull = new LinkedList<>(), lowerHull = new LinkedList<>();

        upperHull.push(sorted.get(0));
        upperHull.push(sorted.get(1));
        lowerHull.push(sorted.get(0));
        lowerHull.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {
            T v = sorted.get(i);

            // Add to the upper hull
            while (upperHull.size() > 1 && !rightTurn(upperHull.get(1), upperHull.get(0), v)) {
                upperHull.pop();
            }

            // Add to the lower hull
            while (lowerHull.size() > 1 && rightTurn(lowerHull.get(1), lowerHull.get(0), v)) {
                lowerHull.pop();
            }

            upperHull.push(v);
            lowerHull.push(v);
        }

        lowerHull.remove(0);
        lowerHull.remove(lowerHull.size() - 1);

        ArrayList<T> hull = new ArrayList<>(lowerHull);
        Collections.reverse(hull);
        hull.addAll(upperHull);

        return hull;
    }

    /**
     * Tests whether v3 lies to the right of the directed line through v1 -> v2
     * Pre-condition: v1.x < v2.x < v3.x
     *
     * @param v1
     * @param v2
     * @param v3
     * @return
     */
    public static boolean rightTurn(Point2D v1, Point2D v2, Point2D v3) {
        // Compute where the line v1 -> v2 intersects v3's x position
        double dx = v2.getX() - v1.getX();
        double dy = v2.getY() - v1.getY();

        if (dx == 0) {
            throw new IllegalArgumentException("V1 and V2 must have different x coordinates");
        }

        // l: y = ax + b. a = dy / dx. b = 
        double a = dy / dx;
        double b = v2.getY() - a * v2.getX();

        double intersection = a * v3.getX() + b;

        return v3.getY() < intersection;
    }
}

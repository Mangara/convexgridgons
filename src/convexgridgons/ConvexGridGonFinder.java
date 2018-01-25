package convexgridgons;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConvexGridGonFinder {

    public enum Algorithm {
        PURE_BRUTE_FORCE, EXPONENTIAL_BRUTE_FORCE;
    }

    private static final Algorithm DEFAULT_ALGORITHM = Algorithm.PURE_BRUTE_FORCE;

    /**
     * Returns the largest convex polygon whose vertices have the given x- and
     * y-coordinates.
     *
     * @param xCoords
     * @param yCoords
     * @return
     */
    public static List<Point2D.Double> findLargestConvexGridGon(Collection<Double> xCoords, Collection<Double> yCoords) {
        return findLargestConvexGridGon(xCoords, yCoords, DEFAULT_ALGORITHM);
    }

    /**
     * Returns the largest convex polygon whose vertices have the given x- and
     * y-coordinates.
     *
     * Uses the specified algorithm.
     *
     * @param xCoords
     * @param yCoords
     * @param alg
     * @return
     */
    public static List<Point2D.Double> findLargestConvexGridGon(Collection<Double> xCoords, Collection<Double> yCoords, Algorithm alg) {
        switch (alg) {
            case EXPONENTIAL_BRUTE_FORCE:
                return exponentialBruteForce(xCoords, yCoords);
            case PURE_BRUTE_FORCE:
                return pureBruteForce(xCoords, yCoords);
            default:
                throw new AssertionError("Unknown algorithm: " + alg);
        }
    }

    private static List<Point2D.Double> pureBruteForce(Collection<Double> xCoords, Collection<Double> yCoords) {
        // Try all sets of points
        List<Double> x = new ArrayList<>(xCoords);
        List<Double> y = new ArrayList<>(yCoords);

        return recursiveBruteForce(new ArrayList<>(), x, y, 0);
    }

    private static List<Point2D.Double> recursiveBruteForce(List<Point2D.Double> points, List<Double> xCoords, List<Double> yCoords, int largestSize) {
        //System.out.println("  rbf. points: " + points + " x: " + xCoords + " y: " + yCoords);

        if (xCoords.isEmpty() || largestSize >= points.size() + Math.min(xCoords.size(), yCoords.size())) {
            return new ArrayList<>(points);
        }

        List<Point2D.Double> largestGridgon = new ArrayList<>(points);

        // Try all y-coordinates for the next x-coordinate
        double x = xCoords.remove(xCoords.size() - 1);

        // or skip this x
        List<Point2D.Double> gridgon = recursiveBruteForce(points, xCoords, yCoords, largestSize);

        if (gridgon.size() > largestGridgon.size()) {
            largestGridgon = gridgon;

            if (largestGridgon.size() > largestSize) {
                largestSize = largestGridgon.size();
            }
        }

        for (int i = 0; i < yCoords.size(); i++) {
            double y = yCoords.get(i);

            points.add(new Point2D.Double(x, y));

            if (ConvexUtils.isConvex(points)) {
                yCoords.remove(i);

                List<Point2D.Double> gridGon = recursiveBruteForce(points, xCoords, yCoords, largestSize);

                if (gridGon.size() > largestGridgon.size()) {
                    largestGridgon = gridGon;

                    if (largestGridgon.size() > largestSize) {
                        largestSize = largestGridgon.size();
                    }
                }

                yCoords.add(i, y);
            }

            points.remove(points.size() - 1);
        }

        xCoords.add(x);

        return largestGridgon;
    }

    private static List<Point2D.Double> exponentialBruteForce(Collection<Double> xCoords, Collection<Double> yCoords) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

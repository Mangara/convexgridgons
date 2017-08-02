/*
 */
package convexgridgons;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Sander Verdonschot <sander.verdonschot at gmail.com>
 */
public class ConvexPointset {

    private static final Random RAND = new Random();

    public enum Algorithm {
        REJECTION_SAMPLING, ITERATIVE_REJECTION_SAMPLING, VALTR, SQUARE_CH, CIRCLE_CH, CIRCLE_REJECTION
    }

    public static List<Point2D.Double> generatePointsInConvexPosition(int n) {
        return generatePointsInConvexPosition(n, Algorithm.VALTR);
    }

    public static List<Point2D.Double> generatePointsInConvexPosition(int n, Algorithm algorithm) {
        switch (algorithm) {
            case REJECTION_SAMPLING:
                return rejectionSampling(n);
            case ITERATIVE_REJECTION_SAMPLING:
                return iterativeRejectionSampling(n);
            case VALTR:
                return valtrMethod(n);
            case SQUARE_CH:
                return squareConvexHull(n);
            case CIRCLE_CH:
                return circleConvexHull(n);
            case CIRCLE_REJECTION:
                return circleRejectionSampling(n);
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

    private static List<Point2D.Double> rejectionSampling(int n) {
        List<Point2D.Double> points = new ArrayList<>(n);

        do {
            // Generate random points in [0,1]^2
            points.clear();

            for (int i = 0; i < n; i++) {
                points.add(new Point2D.Double(RAND.nextDouble(), RAND.nextDouble()));
            }
        } while (!ConvexUtils.isConvex(points));

        return points;
    }
    
    private static List<Point2D.Double> circleRejectionSampling(int n) {
        List<Point2D.Double> points = new ArrayList<>(n);

        do {
            // Generate random points in the unit circle
            points.clear();

            for (int i = 0; i < n; i++) {
                // Random angle
                double theta = RAND.nextDouble() * 2 * Math.PI;
                // Random radius
                double r = 0.5 * Math.sqrt(RAND.nextDouble());
                points.add(new Point2D.Double(0.5 + r * Math.cos(theta), 0.5 + r * Math.sin(theta)));
            }
        } while (!ConvexUtils.isConvex(points));

        return points;
    }

    private static List<Point2D.Double> iterativeRejectionSampling(int n) {
        // Test while generating
        List<Point2D.Double> points = new ArrayList<>(n);

        while (points.size() < n) {
            points.add(new Point2D.Double(RAND.nextDouble(), RAND.nextDouble()));

            if (!ConvexUtils.isConvex(points)) {
                points.remove(points.size() - 1);
            }
        }

        return points;
    }

    private static List<Point2D.Double> valtrMethod(int n) {
        List<Point2D.Double> points = new ArrayList<>(n);

        // Generate x and y coordinates
        List<Double> xPool = new ArrayList<>(n);
        List<Double> yPool = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            xPool.add(RAND.nextDouble());
            yPool.add(RAND.nextDouble());
        }

        //System.out.println("xPool: " + xPool);
        //System.out.println("yPool: " + yPool);
        // Sort
        Collections.sort(xPool);
        Collections.sort(yPool);

        //System.out.println("xPool: " + xPool);
        //System.out.println("yPool: " + yPool);
        // Find min and max
        Double minX = xPool.get(0);
        Double maxX = xPool.get(n - 1);
        Double minY = yPool.get(0);
        Double maxY = yPool.get(n - 1);

        // Randomly assign to top and bottom / left and right chains and convert to vectors
        List<Double> xVec = new ArrayList<>(n);
        List<Double> yVec = new ArrayList<>(n);

        double lastTop = minX, lastBot = minX;

        //System.out.print("x: ");
        for (int i = 1; i < n - 1; i++) {
            double x = xPool.get(i);

            if (RAND.nextBoolean()) {
                //System.out.print("top ");
                xVec.add(x - lastTop);
                lastTop = x;
            } else {
                //System.out.print("bot ");
                xVec.add(lastBot - x);
                lastBot = x;
            }
        }
        //System.out.println();

        xVec.add(maxX - lastTop);
        xVec.add(lastBot - maxX);

        double lastLeft = minY, lastRight = minY;

        //System.out.print("y: ");
        for (int i = 1; i < n - 1; i++) {
            double y = yPool.get(i);

            if (RAND.nextBoolean()) {
                //System.out.print("left ");
                yVec.add(y - lastLeft);
                lastLeft = y;
            } else {
                //System.out.print("right ");
                yVec.add(lastRight - y);
                lastRight = y;
            }
        }
        //System.out.println();

        yVec.add(maxY - lastLeft);
        yVec.add(lastRight - maxY);

        //System.out.println("xVec: " + xVec);
        //System.out.println("yVec: " + yVec);
        // Shuffly y components
        Collections.shuffle(yVec);

        //System.out.println("yVec: " + yVec);
        // Build vectors
        List<Point2D.Double> vec = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            vec.add(new Point2D.Double(xVec.get(i), yVec.get(i)));
        }

        //System.out.println("vec: " + vec);
        // Sort vectors by direction
        Collections.sort(vec, Comparator.comparingDouble(v -> Math.atan2(v.getY(), v.getX())));

        //System.out.println("vec: " + vec);
        // Lay vectors end to end
        double x = 0, y = 0;
        double minPolygonX = 0;
        double minPolygonY = 0;

        for (int i = 0; i < n; i++) {
            points.add(new Point2D.Double(x, y));

            x += vec.get(i).getX();
            y += vec.get(i).getY();

            minPolygonX = Math.min(minPolygonX, x);
            minPolygonY = Math.min(minPolygonY, y);
        }

        //System.out.println("points: " + points);
        // Translate points into original bounding box
        double xShift = minX - minPolygonX;
        double yShift = minY - minPolygonY;

        for (int i = 0; i < n; i++) {
            Point2D.Double p = points.get(i);
            points.set(i, new Point2D.Double(p.x + xShift, p.y + yShift));
        }

        //System.out.println("points: " + points);
        return points;
    }

    private static List<Point2D.Double> squareConvexHull(int n) {
        List<Point2D.Double> hull = null;

        do {
            // Generate 2^(n/4) random points in a square
            int nPoints = Math.max(n, (int) Math.min(Integer.MAX_VALUE, Math.pow(2, n / 2.0)));
            List<Point2D.Double> random = new ArrayList<>(nPoints);

            for (int i = 0; i < nPoints; i++) {
                random.add(new Point2D.Double(RAND.nextDouble(), RAND.nextDouble()));
            }

            // Compute the convex hull
            hull = ConvexUtils.convexHull(random);
        } while (hull.size() < n);

        // Return n random points
        if (hull.size() == n) {
            return hull;
        } else {
            Collections.shuffle(hull);
            return new ArrayList<>(hull.subList(0, n));
        }
    }

    private static List<Point2D.Double> circleConvexHull(int n) {
        List<Point2D.Double> hull = null;

        do {
            // Generate n^3 random points in a circle
            int nPoints = Math.max(n, (int) Math.min(Integer.MAX_VALUE, Math.pow(n / 3.0, 3)));
            List<Point2D.Double> random = new ArrayList<>(nPoints);

            for (int i = 0; i < nPoints; i++) {
                // Random angle
                double theta = RAND.nextDouble() * 2 * Math.PI;
                // Random radius
                double r = 0.5 * Math.sqrt(RAND.nextDouble());
                random.add(new Point2D.Double(0.5 + r * Math.cos(theta), 0.5 + r * Math.sin(theta)));
            }

            // Compute the convex hull
            hull = ConvexUtils.convexHull(random);
        } while (hull.size() < n);

        // Return n random points
        if (hull.size() == n) {
            return hull;
        } else {
            Collections.shuffle(hull);
            return new ArrayList<>(hull.subList(0, n));
        }
    }

    public static void main(String[] args) throws IOException {
        int n = 12;
        int tests = 10000 / n;
        Instant start, end;

        /*start = Instant.now();
        for (int i = 0; i < tests; i++) {
            generatePointsInConvexPosition1(n);
        }
        end = Instant.now();
        System.out.println("Method 1 took " + Duration.between(start, end));*/

 /*start = Instant.now();
        for (int i = 0; i < tests; i++) {
            generatePointsInConvexPosition2(n);
        }
        end = Instant.now();
        System.out.println("Method 2 took " + Duration.between(start, end));*/
 /*start = Instant.now();
        for (int i = 0; i < tests; i++) {
            generatePointsInConvexPosition3(n);
        }
        end = Instant.now();
        System.out.println("Method 3 took " + Duration.between(start, end));*/
        //animateDistribution(Algorithm.VALTR, 500, 1000, 250);
        valtrMethod(8);
    }
}

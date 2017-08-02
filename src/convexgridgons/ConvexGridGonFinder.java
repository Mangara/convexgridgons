package convexgridgons;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConvexGridGonFinder {

    public enum Algorithm {
        PURE_BRUTE_FORCE, EXPONENTIAL_BRUTE_FORCE;
    }

    private static final Algorithm DEFAULT_ALGORITHM = Algorithm.PURE_BRUTE_FORCE;

    /**
     * Returns a convex polygon whose vertices have the given x- and
     * y-coordinates, or null if no such polygon exists.
     *
     * @param xCoords
     * @param yCoords
     * @return
     */
    public static List<Point2D.Double> findConvexGridGon(Set<Double> xCoords, Set<Double> yCoords) {
        return findConvexGridGon(xCoords, yCoords, DEFAULT_ALGORITHM);
    }

    /**
     * Returns a convex polygon whose vertices have the given x- and
     * y-coordinates, or null if no such polygon exists.
     *
     * Uses the specified algorithm.
     *
     * @param xCoords
     * @param yCoords
     * @return
     */
    public static List<Point2D.Double> findConvexGridGon(Set<Double> xCoords, Set<Double> yCoords, Algorithm alg) {
        switch (alg) {
            case EXPONENTIAL_BRUTE_FORCE:
                return exponentialBruteForce(xCoords, yCoords);
            case PURE_BRUTE_FORCE:
                return pureBruteForce(xCoords, yCoords);
            default:
                throw new AssertionError("Unknown algorithm: " + alg);
        }
    }

    private static List<Point2D.Double> pureBruteForce(Set<Double> xCoords, Set<Double> yCoords) {
        // Try all sets of points
        List<Double> x = new ArrayList<>(xCoords);
        List<Double> y = new ArrayList<>(yCoords);
        
        return recursiveBruteForce(new ArrayList<>(), x, y);
    }
    
    private static List<Point2D.Double> recursiveBruteForce(List<Point2D.Double> points, List<Double> xCoords, List<Double> yCoords) {
        //System.out.println("  rbf. points: " + points + " x: " + xCoords + " y: " + yCoords);
        
        if (xCoords.isEmpty()) {
            return points;
        }
        
        // Try all y-coordinates for the next x-coordinate
        double x = xCoords.remove(xCoords.size() - 1);
        
        for (int i = 0; i < yCoords.size(); i++) {
            double y = yCoords.get(i);
            
            points.add(new Point2D.Double(x, y));
            
            if (ConvexUtils.isConvex(points)) {
                yCoords.remove(i);
                
                List<Point2D.Double> gridGon = recursiveBruteForce(points, xCoords, yCoords);
                
                if (gridGon != null) {
                    return gridGon;
                }
                
                yCoords.add(i, y);
            }
            
            points.remove(points.size() - 1);
        }
        
        xCoords.add(x);
        
        return null; // No valid gridgon found
    }

    private static List<Point2D.Double> exponentialBruteForce(Set<Double> xCoords, Set<Double> yCoords) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

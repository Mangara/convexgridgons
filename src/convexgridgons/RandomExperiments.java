package convexgridgons;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomExperiments {
    private static Random rand;
    
    private static double bestQ = Double.NEGATIVE_INFINITY;
    private static Set<Double> bestXCoords;
    private static Set<Double> bestYCoords;
    
    public static void main(String[] args) {
        long seed = new Random().nextLong();
        System.out.println("Seed: " + seed);
        rand = new Random(seed);
        
        int n = 4, k = 4;
        int experiments = 1000000;
        
        int nNull = 0;
        
        for (int i = 0; i < experiments; i++) {
            if (experiment(n, k)) {
                nNull++;
            }
        }
        
        System.out.println(nNull + " / " + experiments + " = " + (nNull / (double) experiments));
    }
    
    private static boolean experiment(int n, int k) {
        Set<Double> xCoords = randomSet(n);
        Set<Double> yCoords = randomSet(n);
        
        List<Point2D.Double> polygon = ConvexGridGonFinder.findLargestConvexGridGon(xCoords, yCoords);
        
        if (polygon.size() < k) {
            double q = computeQuality(xCoords, yCoords);
            
            if (q > bestQ) {
                bestQ = q;
                bestXCoords = xCoords;
                bestYCoords = yCoords;
                System.out.println("new best: " + xCoords + " and " + yCoords + "(" + q + ")");
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    private static double computeQuality(Set<Double> xCoords, Set<Double> yCoords) {
        return computeQuality(xCoords) * computeQuality(yCoords);
    }
    
    private static double computeQuality(Set<Double> coords) {
        // Sort
        List<Double> sorted = new ArrayList<>(coords);
        Collections.sort(sorted);
        
        double q = 1;
        
        for (int i = 1; i < sorted.size(); i++) {
            q *= (sorted.size() - 1) * (sorted.get(i) - sorted.get(i - 1));
        }
        
        return q;
    }

    private static Set<Double> randomSet(int n) {
        Set<Double> result = new HashSet<>(n);
        result.add(0d);
        result.add(1d);
        
        for (int i = 0; i < n - 2; i++) {
            result.add(rand.nextDouble());
        }
        
        return result;
    }

    
}

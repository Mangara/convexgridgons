package convexgridgons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomExperiments2 {

    private static Random rand;

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        long seed = new Random().nextLong();
        rand = new Random(seed);

        System.out.println("n = " + n);
        System.out.println("Seed: " + seed);
        System.out.println();

        int largestSize = n;
        double bestQuality = 0;

        while (true) {
            Set<Double> xCoords = randomSet(n);
            Set<Double> yCoords = randomSet(n);
            
            int largestGridgon = ConvexGridGonFinder.largestConvexGridgonSize(xCoords, yCoords);
            
            if (largestGridgon < largestSize) {
                largestSize = largestGridgon;
                bestQuality = computeQuality(xCoords, yCoords);
                
                System.out.printf("** Found a %dx%d grid that does not support any convex %d-gon **%n", n, n, largestSize + 1);
                printGrid(xCoords, yCoords);
            } else if (largestGridgon == largestSize && largestGridgon < n) {
                double q = computeQuality(xCoords, yCoords);
                
                if (q > bestQuality) {
                    bestQuality = q;
                    printGrid(xCoords, yCoords);
                }
            }
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
        result.add(0.0);
        result.add(1.0);

        for (int i = 0; i < n - 2; i++) {
            result.add(rand.nextDouble());
        }

        return result;
    }

    private static void printGrid(Set<Double> xCoords, Set<Double> yCoords) {
        // Sort
        List<Double> x = new ArrayList<>(xCoords);
        List<Double> y = new ArrayList<>(yCoords);
        Collections.sort(x);
        Collections.sort(y);
        
        System.out.println("  X: " + x);
        System.out.println("  Y: " + y);
        System.out.println();
        System.out.flush();
    }

    
}

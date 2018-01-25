package convexgridgons;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomExperiments2 {

    private static Random rand;

    public static void main(String[] args) {
        /*Case tc = new Case();
        tc.xCoords = new HashSet<Double>(Arrays.asList(0.0,0.27,0.34,0.4,0.47,1.0)); 
        tc.yCoords = new HashSet<Double>(Arrays.asList(0.0,0.48,0.49,0.51,0.52,1.0)); 
        
        tc.largestConvex = ConvexGridGonFinder.findLargestConvexGridGon(
                tc.xCoords, tc.yCoords);
        
        tc.print();
        
        if (true)
        return;*/

        long seed = new Random().nextLong();
        System.out.println("Seed: " + seed);
        rand = new Random(seed);

        int[] ns = {12, 13, 14, 15, 16, 17, 18, 19, 20};

        int experiments = 1000;
        int progress = 100;

        for (int n : ns) {
            System.out.println("n : " + n);
            Case best = null; //NB there is always some grid with k=n is 

            for (int i = 0; i < experiments; i++) {
                Case c = experiment(n);

                if (c.largestConvex.size() < n
                        && (best == null
                        || c.largestConvex.size() < best.largestConvex.size()
                        || (c.largestConvex.size() == best.largestConvex.size() && c.getQuality() > best.getQuality()))) {
                    best = c;
                }

                if ((i + 1) % progress == 0) {
                    System.out.println(i + "/" + experiments);
                }
            }

            if (best == null) {
                System.out.println("Boring (n = " + n + ")");
            } else {
                System.out.println("No convex " + (best.largestConvex.size() + 1) + "-gon on " + n + " x " + n + " grid");
                best.print();
            }
            System.out.println();
        }
    }

    private static class Case {

        private Set<Double> xCoords;
        private Set<Double> yCoords;
        private List<Point2D.Double> largestConvex;

        void print() {
            System.out.println("Grid X");
            for (Double x : xCoords) {
                System.out.println("\t" + x);
            }
            System.out.println("Grid Y");
            for (Double y : yCoords) {
                System.out.println("\t" + y);
            }
            System.out.println("Polygon (k = " + largestConvex.size() + ")");
            for (Point2D.Double p : largestConvex) {
                System.out.println("\t" + p.getX() + "\t" + p.getY());
            }
        }

        public double getQuality() {
            return computeQuality(xCoords) * computeQuality(yCoords);
        }

        private double computeQuality(Set<Double> coords) {
            List<Double> sorted = new ArrayList<>(coords);
            Collections.sort(sorted);

            double q = 1;

            for (int i = 1; i < sorted.size(); i++) {
                q *= (sorted.size() - 1) * (sorted.get(i) - sorted.get(i - 1));
            }

            return q;
        }
    }

    private static Case experiment(int n) {
        Case c = new Case();

        c.xCoords = randomSet(n);
        c.yCoords = randomSet(n);
        c.largestConvex = ConvexGridGonFinder.findLargestConvexGridGon(c.xCoords, c.yCoords);

        return c;
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

}

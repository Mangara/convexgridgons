package convexgridgons;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomExperiments2 {

    private static Random rand;

    public static void main(String[] args) {
        Case tc = new Case();
        tc.xCoords = new HashSet<Double>(Arrays.asList(0.0,0.27,0.34,0.4,0.47,1.0)); 
        tc.yCoords = new HashSet<Double>(Arrays.asList(0.0,0.48,0.49,0.51,0.52,1.0)); 
        
        tc.largestConvex = ConvexGridGonFinder.findConvexGridGon(
                tc.xCoords, tc.yCoords);
        
        tc.print();
        
        if (true)
        return;




        long seed = new Random().nextLong();
        System.out.println("Seed: " + seed);
        rand = new Random(seed);

        int[] ns = {6, 7, 8, 9, 10};

        int experiments = 100;
        int progress = 50000;

        for (int n : ns) {

            System.out.println("n : " + n);
            Case best = null; //NB there is always some grid with k=n is 

            for (int i = 0; i < experiments; i++) {

                Case c = experiment(n);
                if (c.largestConvex.size() < n && (best == null || c.largestConvex.size() < best.largestConvex.size())) {
                    best = c;
                }
                if ((i + 1) % progress == 0) {
                    System.out.println(i);
                }
            }

            if (best == null) {
                System.out.println("Boring (n = " + n + ")");
            } else {

                System.out.println("No convex " + (best.largestConvex.size() + 1) + "-gon on " + n + " x " + n + " grid");

                best.print();
            }
        }
    }

    private static class Case {

        Set<Double> xCoords;
        Set<Double> yCoords;
        List<Point2D.Double> largestConvex;

        void print() {
            System.out.println("Grid X");
            for (Double x : xCoords) {
                System.out.println("\t" + x);
            }
            System.out.println("Grid Y");
            for (Double y : yCoords) {
                System.out.println("\t" + y);
            }
            System.out.println("Polygon (k = "+largestConvex.size()+")");
            for (Point2D.Double p : largestConvex) {
                System.out.println("\t" + p.getX() + "\t" + p.getY());
            }
        }
    }

    private static Case experiment(int n) {
        Case c = new Case();
        c.xCoords = randomSet(n);
        c.yCoords = randomSet(n);

        c.largestConvex = ConvexGridGonFinder.findConvexGridGon(c.xCoords, c.yCoords);

        return c;
//        
//        if (polygon.size() < k) {
//            System.out.println("No convex k-gon on nxn grid; largest = " + polygon.size());
//            System.out.println("Grid X");
//            for (Double x : xCoords) {
//                System.out.println("\t" + x);
//            }
//            System.out.println("Grid Y");
//            for (Double y : yCoords) {
//                System.out.println("\t" + y);
//            }
//            System.out.println("Polygon");
//            for (Point2D.Double p : polygon) {
//                System.out.println("\t" + p.getX() + "\t" + p.getY());
//            }
//
//            return true;
//        } else {
//            return false;
//        }
    }

    private static Set<Double> randomSet(int n) {
        Set<Double> result = new HashSet<>(n);

        for (int i = 0; i < n; i++) {
            result.add(rand.nextDouble());
        }

        return result;
    }

}

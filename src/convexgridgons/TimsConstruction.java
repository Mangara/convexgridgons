package convexgridgons;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimsConstruction {

    public static void main(String[] args) {
        for (int i = 1; i < 20; i++) {
            List<Double> xCoords = buildX(i);
            List<Double> yCoords = buildY(i);
            List<Point2D.Double> gridgon = ConvexGridGonFinder.findLargestConvexGridGon(xCoords, yCoords);
            
            System.out.printf("%d. %d x %d grid with largest convex polygon of size %d.%n", i, xCoords.size(), yCoords.size(), gridgon.size());
            System.out.println("X: " + xCoords);
            System.out.println("Y: " + yCoords);
            System.out.println(gridgon);
            System.out.println();
        }
    }
    
    private static final double SCALE = 0.01; // 0 < SCALE < (1 - 2 * X_SHIFT) / 3
    private static final double X_SHIFT = (1 - 3 * SCALE) / 2 - 0.01; // 0 <= X_SHIFT < 0.5
    private static final double Y_SHIFT = 0.5 - SCALE / 2; // 0 <= X_SHIFT < 0.5
    
    public static List<Double> buildX(int i) {
        if (i == 0) {
            return Arrays.asList(0d, 1d);
        }
        
        List<Double> prev = buildX(i - 1);
        List<Double> result = new ArrayList<>(prev.size() + 2);
        
        result.add(0d);
        
        for (Double x : prev) {
            result.add(X_SHIFT + SCALE * x);
        }
        
        result.add(1d);
        
        return result;
    }
    
    public static List<Double> buildY(int i) {
        if (i == 0) {
            return Arrays.asList(0d, 1d);
        }
        
        List<Double> prev = buildY(i - 1);
        List<Double> result = new ArrayList<>(prev.size() + 2);
        
        result.add(0d);
        
        for (Double y : prev) {
            result.add(Y_SHIFT + SCALE * y);
        }
        
        result.add(1d);
        
        return result;
    }
}

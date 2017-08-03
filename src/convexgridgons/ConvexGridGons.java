package convexgridgons;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConvexGridGons {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        /*Set<Double> xCoords = new HashSet<>(Arrays.asList(0.0, 0.3, 0.35, 0.48, 0.52, 0.65, 0.7, 1.0));
        Set<Double> yCoords = new HashSet<>(Arrays.asList(0.0, 0.3, 0.35, 0.475, 0.525, 0.65, 0.7, 1.0));
        
        System.out.println(ConvexGridGonFinder.findConvexGridGon(xCoords, yCoords));*/

        for (int i = 0; i < 10; i++) {
            int n = 6 + (int) (Math.random() * 5);
            
//            Graph g = new Graph();
//
//            for (Point2D.Double point : points) {
//                g.addVertex(new GraphVertex(point.x, point.y));
//            }
//
//            for (int j = 0; j < points.size(); j++) {
//                g.addEdge(g.getVertices().get(j), g.getVertices().get((j + 1) % points.size()));
//            }
//            
//            IPEExporter.exportGraph(new File("random" + i + ".ipe"), g);
        }
    }

}

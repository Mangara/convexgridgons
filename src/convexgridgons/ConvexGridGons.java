package convexgridgons;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConvexGridGons {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Set<Double> xCoords = new HashSet<>(Arrays.asList(128d,144d,160d,162d,164d,172d,188d,240d));
        Set<Double> yCoords = new HashSet<>(Arrays.asList(656d,708d,724d,732d,734d,736d,752d,768d));
        
        System.out.println(ConvexGridGonFinder.largestConvexGridgonSize(xCoords, yCoords));
        System.out.println(ConvexGridGonFinder.findConvexGridGon(xCoords, yCoords));

        /*for (int i = 0; i < 10; i++) {
            int n = 6 + (int) (Math.random() * 5);

            List<Point2D.Double> points = ConvexPointset.generatePointsInConvexPosition(n);
            Graph g = new Graph();

            for (Point2D.Double point : points) {
                g.addVertex(new GraphVertex(point.x, point.y));
            }

            for (int j = 0; j < points.size(); j++) {
                g.addEdge(g.getVertices().get(j), g.getVertices().get((j + 1) % points.size()));
            }
            
            IPEExporter.exportGraph(new File("random" + i + ".ipe"), g);
        }*/
    }

}

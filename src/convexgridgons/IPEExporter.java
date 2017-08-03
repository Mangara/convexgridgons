/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package convexgridgons;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.tue.geometrycore.geometry.Vector;
import nl.tue.geometrycore.geometry.linear.LineSegment;
import nl.tue.geometrycore.geometry.linear.Polygon;
import nl.tue.geometrycore.geometryrendering.styling.Dashing;
import nl.tue.geometrycore.geometryrendering.styling.Hashures;
import nl.tue.geometrycore.io.ipe.IPEWriter;

/**
 *
 * @author wmeulema
 */
public class IPEExporter {

    public static void main(String[] args) throws IOException {
        IPEWriter write = IPEWriter.clipboardWriter();

        write.initialize();

        Double[] xs = {
            0.28262635907672884,
            0.3605052346368033,
            0.3963615661882238,
            0.5111889156807728,
            0.003873790551719769,
            0.09512362077644843,
            0.5803448713497886,
            0.8418014191387686,
            0.7475844103325536
        };

        Double[] ys = {
            0.5377105535264926,
            0.8396439342481223,
            0.6111698182833606,
            0.33783170808856566,
            0.8975225528802496,
            0.6384700663903466,
            0.6285311647092903,
            0.07295833382588224,
            0.6019239368109215
        };

        String poly = "0.5803448713497886	0.5377105535264926\n"
                + "0.09512362077644843	0.6111698182833606\n"
                + "0.003873790551719769	0.33783170808856566\n"
                + "0.5111889156807728	0.6384700663903466\n"
                + "0.3963615661882238	0.07295833382588224\n"
                + "0.3605052346368033	0.8396439342481223\n"
                + "0.28262635907672884	0.8975225528802496";

        double minX = 1, minY = 1;
        double maxX = 0, maxY = 0;

        for (double x : xs) {
            minX = Math.min(x, minX);
            maxX = Math.max(x, maxX);
        }

        for (double y : ys) {
            minY = Math.min(y, minY);
            maxY = Math.max(y, maxY);
        }

        double scale = 200;

        write.setStroke(Color.black, 0.1, Dashing.SOLID);
        write.setFill(null, Hashures.SOLID);

        Vector dirX = Vector.up();
        dirX.scale(maxY - minY);
        for (double x : xs) {
            LineSegment seg = LineSegment.byStartAndOffset(new Vector(x, minY), dirX);
            seg.scale(scale);
            write.draw(seg);
        }

        Vector dirY = Vector.right();
        dirY.scale(maxX - minX);
        for (double y : ys) {
            LineSegment seg = LineSegment.byStartAndOffset(new Vector(minX, y), dirY);
            seg.scale(scale);
            write.draw(seg);
        }
        
        List<Vector> coords = new ArrayList();
        for (String coord : poly.split("\n")) {
            String[] xy = coord.split("\t");
            coords.add(new Vector(Double.parseDouble(xy[0]),Double.parseDouble(xy[1])));            
        }
        Polygon pol = new Polygon(coords);
        pol.scale(scale);
        write.setStroke(Color.red, 0.2, Dashing.SOLID);
        write.draw(pol);

        write.close();
    }
}

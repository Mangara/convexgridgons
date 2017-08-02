package convexgridgons;

import static convexgridgons.ConvexPointset.generatePointsInConvexPosition;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

public class DistributionAnimator {

    public static void main(String[] args) throws IOException {
        
        int n = 6;
        int gridSize = 200;
        double density = 50;
        int totalPoints = (int) Math.round(density * gridSize * gridSize);
        ConvexPointset.Algorithm algorithm = ConvexPointset.Algorithm.CIRCLE_REJECTION;
        
        //BufferedImage dist = drawDistribution(n, totalPoints / n, gridSize, algorithm);
        //ImageIO.write(dist, "png", new File(algorithm.name() + "_" + n + "_" + totalPoints + ".png"));
        
        animateDistribution(algorithm, density, 11, gridSize);
    }

    public static void animateDistribution(ConvexPointset.Algorithm algorithm, double density, int limit, int gridSize) throws IOException {
        int totalPoints = (int) Math.round(density * gridSize * gridSize);
        int n = 3;
        int inc = 1;
        int iteration = 0;

        while (n <= limit) {
            System.out.println("Drawing distribution for n = " + n + " with " + (totalPoints / n) + " sets.");
            BufferedImage dist = drawDistribution(n, totalPoints / n, gridSize, algorithm);
            ImageIO.write(dist, "png", new File(algorithm.name() + "_" + n + "_" + totalPoints + ".png"));

            n += inc;
            iteration++;

            if (iteration == 10) {
                inc *= 2;
                iteration = 0;
            }
        }
    }

    public static BufferedImage drawDistribution(int n, int nSets, int gridSize, ConvexPointset.Algorithm algorithm) {
        long[][] grid = new long[gridSize][gridSize];

        for (int i = 0; i < nSets; i++) {
            if ((nSets < 10)
                    || (nSets < 100 && i % 10 == 0)
                    || (nSets < 1000 && i % 100 == 0)
                    || (nSets < 10000 && i % 1000 == 0)
                    || (nSets < 100000 && i % 10000 == 0)
                    || (nSets < 1000000 && i % 100000 == 0)
                    || (i % 1000000 == 0)) {
                System.out.printf("  Generating set %d/%d (%.0f%%)%n", i, nSets, 100 * (i / (double) nSets));
            }

            List<Point2D.Double> points = generatePointsInConvexPosition(n, algorithm);

            for (Point2D.Double point : points) {
                int gridX = (int) Math.floor(gridSize * point.x);
                int gridY = (int) Math.floor(gridSize * point.y);
                grid[gridX][gridY]++;
            }
        }

        BufferedImage dist = new BufferedImage(gridSize, gridSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dist.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, dist.getWidth(), dist.getHeight());

        long maxCount = Arrays.stream(grid).flatMapToLong(Arrays::stream).max().getAsLong();

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                double value = grid[x][y] / (double) maxCount;
                int color = (int) Math.floor(value * COLOR_MAP.length);

                if (color == COLOR_MAP.length) {
                    color--;
                }

                g.setColor(COLOR_MAP[color]);
                g.fillRect(x, y, dist.getWidth(), dist.getHeight());
            }
        }

        return dist;
    }

    private static final Color[] COLOR_MAP = new Color[]{
        new Color(26, 0, 134),
        new Color(26, 0, 136),
        new Color(26, 0, 137),
        new Color(26, 0, 139),
        new Color(26, 1, 141),
        new Color(26, 1, 143),
        new Color(26, 2, 145),
        new Color(26, 3, 146),
        new Color(26, 4, 148),
        new Color(26, 4, 150),
        new Color(27, 5, 152),
        new Color(27, 6, 153),
        new Color(27, 7, 155),
        new Color(27, 8, 157),
        new Color(27, 9, 158),
        new Color(27, 10, 160),
        new Color(27, 11, 162),
        new Color(27, 12, 163),
        new Color(27, 13, 165),
        new Color(27, 14, 167),
        new Color(27, 15, 168),
        new Color(27, 16, 170),
        new Color(27, 17, 172),
        new Color(27, 18, 173),
        new Color(27, 19, 175),
        new Color(27, 21, 176),
        new Color(27, 22, 178),
        new Color(27, 23, 180),
        new Color(27, 24, 181),
        new Color(27, 25, 183),
        new Color(27, 26, 184),
        new Color(27, 27, 186),
        new Color(27, 28, 187),
        new Color(27, 29, 188),
        new Color(27, 30, 190),
        new Color(27, 31, 191),
        new Color(27, 32, 193),
        new Color(27, 33, 194),
        new Color(27, 34, 195),
        new Color(27, 35, 197),
        new Color(27, 36, 198),
        new Color(27, 37, 199),
        new Color(27, 39, 200),
        new Color(27, 40, 201),
        new Color(28, 41, 203),
        new Color(28, 42, 204),
        new Color(28, 43, 205),
        new Color(28, 44, 206),
        new Color(28, 45, 207),
        new Color(28, 47, 208),
        new Color(28, 48, 209),
        new Color(29, 49, 210),
        new Color(29, 50, 210),
        new Color(29, 52, 211),
        new Color(29, 53, 212),
        new Color(29, 54, 213),
        new Color(30, 55, 213),
        new Color(30, 57, 214),
        new Color(30, 58, 214),
        new Color(30, 59, 215),
        new Color(31, 61, 215),
        new Color(31, 62, 215),
        new Color(31, 64, 215),
        new Color(31, 65, 215),
        new Color(32, 67, 215),
        new Color(32, 68, 215),
        new Color(32, 70, 215),
        new Color(32, 71, 214),
        new Color(33, 73, 214),
        new Color(33, 75, 213),
        new Color(33, 76, 212),
        new Color(33, 78, 211),
        new Color(33, 80, 209),
        new Color(33, 82, 207),
        new Color(33, 84, 205),
        new Color(32, 86, 202),
        new Color(32, 88, 199),
        new Color(32, 90, 196),
        new Color(32, 92, 193),
        new Color(32, 94, 190),
        new Color(32, 96, 187),
        new Color(33, 98, 184),
        new Color(33, 100, 181),
        new Color(34, 101, 178),
        new Color(35, 103, 175),
        new Color(36, 105, 173),
        new Color(37, 106, 170),
        new Color(38, 108, 167),
        new Color(39, 109, 164),
        new Color(40, 111, 161),
        new Color(41, 112, 159),
        new Color(42, 113, 156),
        new Color(43, 115, 153),
        new Color(44, 116, 150),
        new Color(45, 118, 148),
        new Color(46, 119, 145),
        new Color(47, 120, 142),
        new Color(47, 121, 140),
        new Color(48, 123, 137),
        new Color(49, 124, 134),
        new Color(50, 125, 132),
        new Color(51, 126, 129),
        new Color(52, 128, 126),
        new Color(53, 129, 124),
        new Color(54, 130, 121),
        new Color(55, 131, 118),
        new Color(55, 132, 116),
        new Color(56, 134, 113),
        new Color(57, 135, 110),
        new Color(58, 136, 108),
        new Color(59, 137, 105),
        new Color(59, 138, 102),
        new Color(60, 139, 100),
        new Color(61, 140, 97),
        new Color(62, 142, 94),
        new Color(62, 143, 92),
        new Color(63, 144, 89),
        new Color(64, 145, 87),
        new Color(65, 146, 85),
        new Color(66, 147, 82),
        new Color(67, 148, 80),
        new Color(68, 149, 78),
        new Color(68, 150, 76),
        new Color(69, 151, 74),
        new Color(71, 152, 72),
        new Color(72, 153, 70),
        new Color(73, 154, 68),
        new Color(74, 155, 66),
        new Color(75, 156, 65),
        new Color(76, 157, 63),
        new Color(77, 158, 61),
        new Color(79, 159, 60),
        new Color(80, 160, 58),
        new Color(81, 161, 56),
        new Color(83, 161, 55),
        new Color(84, 162, 53),
        new Color(86, 163, 52),
        new Color(87, 164, 50),
        new Color(89, 165, 49),
        new Color(90, 166, 47),
        new Color(92, 167, 46),
        new Color(93, 168, 44),
        new Color(95, 168, 43),
        new Color(97, 169, 42),
        new Color(98, 170, 40),
        new Color(100, 171, 39),
        new Color(102, 172, 38),
        new Color(104, 173, 37),
        new Color(105, 173, 36),
        new Color(107, 174, 34),
        new Color(109, 175, 33),
        new Color(111, 176, 32),
        new Color(113, 177, 31),
        new Color(115, 177, 30),
        new Color(116, 178, 29),
        new Color(118, 179, 28),
        new Color(120, 180, 27),
        new Color(122, 180, 27),
        new Color(124, 181, 26),
        new Color(126, 182, 25),
        new Color(128, 183, 25),
        new Color(130, 183, 24),
        new Color(132, 184, 23),
        new Color(134, 185, 23),
        new Color(136, 186, 23),
        new Color(139, 186, 22),
        new Color(141, 187, 22),
        new Color(143, 188, 22),
        new Color(145, 188, 22),
        new Color(147, 189, 22),
        new Color(149, 190, 22),
        new Color(151, 190, 22),
        new Color(154, 191, 23),
        new Color(156, 192, 23),
        new Color(158, 192, 23),
        new Color(160, 193, 24),
        new Color(162, 194, 24),
        new Color(164, 194, 25),
        new Color(166, 195, 25),
        new Color(169, 196, 26),
        new Color(171, 196, 26),
        new Color(173, 197, 27),
        new Color(175, 198, 28),
        new Color(177, 198, 28),
        new Color(179, 199, 29),
        new Color(181, 200, 30),
        new Color(183, 200, 31),
        new Color(185, 201, 32),
        new Color(187, 202, 33),
        new Color(189, 202, 33),
        new Color(191, 203, 34),
        new Color(192, 204, 35),
        new Color(194, 204, 36),
        new Color(196, 205, 37),
        new Color(198, 206, 38),
        new Color(200, 206, 40),
        new Color(202, 207, 41),
        new Color(203, 208, 42),
        new Color(205, 208, 43),
        new Color(207, 209, 44),
        new Color(209, 210, 46),
        new Color(210, 210, 47),
        new Color(212, 211, 48),
        new Color(214, 212, 50),
        new Color(215, 213, 51),
        new Color(217, 213, 53),
        new Color(219, 214, 54),
        new Color(220, 215, 56),
        new Color(222, 216, 57),
        new Color(223, 216, 59),
        new Color(224, 217, 61),
        new Color(226, 218, 63),
        new Color(227, 219, 64),
        new Color(229, 220, 66),
        new Color(230, 220, 68),
        new Color(231, 221, 70),
        new Color(232, 222, 72),
        new Color(233, 223, 74),
        new Color(234, 224, 77),
        new Color(235, 225, 79),
        new Color(236, 226, 82),
        new Color(237, 227, 84),
        new Color(238, 228, 87),
        new Color(238, 229, 90),
        new Color(239, 230, 93),
        new Color(239, 231, 96),
        new Color(239, 232, 99),
        new Color(240, 233, 102),
        new Color(240, 234, 106),
        new Color(240, 235, 110),
        new Color(241, 236, 114),
        new Color(241, 237, 118),
        new Color(242, 238, 122),
        new Color(242, 239, 126),
        new Color(243, 240, 131),
        new Color(243, 241, 135),
        new Color(244, 242, 140),
        new Color(245, 243, 145),
        new Color(245, 244, 150),
        new Color(246, 244, 155),
        new Color(247, 245, 160),
        new Color(247, 246, 165),
        new Color(248, 247, 171),
        new Color(249, 247, 176),
        new Color(249, 248, 182),
        new Color(250, 249, 188),
        new Color(251, 250, 194),
        new Color(251, 250, 200),
        new Color(252, 251, 207),
        new Color(252, 252, 213),
        new Color(253, 252, 220),
        new Color(253, 253, 226),
        new Color(254, 253, 233),
        new Color(254, 254, 240),
        new Color(255, 254, 248),
        new Color(255, 255, 255)
    };
}

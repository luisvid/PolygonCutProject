import java.util.ArrayList;
import java.util.List;

/**
 * Main class to demonstrate the polygon cutting algorithm.
 */
public class Main {

    public static void main(String[] args) {
        runRectangleExample();
        runHexagonExample();
        runConcaveExample();
    }
    
    private static void runRectangleExample() {
        System.out.println("=== EXAMPLE 1: RECTANGLE ===");
        List<Point> rectangle = new ArrayList<>();
        rectangle.add(new Point(0, 0));
        rectangle.add(new Point(4, 0));
        rectangle.add(new Point(4, 3));
        rectangle.add(new Point(0, 3));
        Polygon rectPolygon = new Polygon(rectangle);

        // Horizontal line that cuts the rectangle at y = 1.5
        Line rectLine = new Line(new Point(-1, 1.5), new Point(5, 1.5));

        List<Polygon> rectResult = rectPolygon.cut(rectLine);
        printExample(rectPolygon, rectLine, rectResult);
    }
    
    private static void runHexagonExample() {
        System.out.println("=== EXAMPLE 2: CONVEX POLYGON (HEXAGON) ===");
        List<Point> convexPoints = new ArrayList<>();
        convexPoints.add(new Point(2, 1));
        convexPoints.add(new Point(4, 1));
        convexPoints.add(new Point(5, 2));
        convexPoints.add(new Point(4, 3));
        convexPoints.add(new Point(2, 3));
        convexPoints.add(new Point(1, 2));
        Polygon convexPolygon = new Polygon(convexPoints);

        // Diagonal line from (0,0) to (6,4)
        Line convexLine = new Line(new Point(0, 0), new Point(6, 4));

        List<Polygon> convexResult = convexPolygon.cut(convexLine);
        printExample(convexPolygon, convexLine, convexResult);
    }
    
    private static void runConcaveExample() {
        System.out.println("=== EXAMPLE 3: CONCAVE POLYGON (SHAPE OF 'C') ===");
        List<Point> concavePoints = new ArrayList<>();
        concavePoints.add(new Point(0, 3));
        concavePoints.add(new Point(4, 3));
        concavePoints.add(new Point(4, 2));
        concavePoints.add(new Point(2, 2));
        concavePoints.add(new Point(2, 1));
        concavePoints.add(new Point(4, 1));
        concavePoints.add(new Point(4, 0));
        concavePoints.add(new Point(0, 0));
        Polygon concavePolygon = new Polygon(concavePoints);

        // Vertical line at x = 2, from (2, -1) to (2, 4)
        Line concaveLine = new Line(new Point(2, -1), new Point(2, 4));

        List<Polygon> concaveResult = concavePolygon.cut(concaveLine);
        printExample(concavePolygon, concaveLine, concaveResult);
    }
    
    /**
     * Helper method to print the original polygon, line and resulting polygons.
     */
    private static void printExample(Polygon polygon, Line line, List<Polygon> resultPolygons) {
        System.out.println("Original polygon: " + polygon);
        System.out.println("Line: " + line);
        System.out.println("Resulting polygons:");
        for (Polygon p : resultPolygons) {
            System.out.println(p);
        }
        System.out.println();
    }
}

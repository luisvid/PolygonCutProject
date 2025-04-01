import java.util.ArrayList;
import java.util.List;

/**
 * Main class to demonstrate the polygon cutting algorithm.
 */
public class Main {

    /**
     * The main entry point.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        
        // === EXAMPLE 1: Rectangle ===
        System.out.println("=== EXAMPLE 1: RECTANGLE ===");
        List<Point> rectangle = new ArrayList<>();
        rectangle.add(new Point(0, 0));
        rectangle.add(new Point(4, 0));
        rectangle.add(new Point(4, 3));
        rectangle.add(new Point(0, 3));
        Polygon rectPolygon = new Polygon(rectangle);

        // Define a horizontal line that cuts the rectangle at y = 1.5
        Line rectLine = new Line(new Point(-1, 1.5), new Point(5, 1.5));

        List<Polygon> rectResult = rectPolygon.cut(rectLine);
        System.out.println("Original polygon: " + rectPolygon);
        System.out.println("Line: " + rectLine);
        System.out.println("Resulting polygons:");
        for (Polygon p : rectResult) {
            System.out.println(p);
        }
        System.out.println();

        // === EXAMPLE 2: Convex Polygon (Hexagon) ===
        System.out.println("=== EXAMPLE 2: CONVEX POLYGON (HEXAGON) ===");
        List<Point> convexPoints = new ArrayList<>();
        // A simple hexagon defined by its (x, y) coordinates:
        convexPoints.add(new Point(2, 1));
        convexPoints.add(new Point(4, 1));
        convexPoints.add(new Point(5, 2));
        convexPoints.add(new Point(4, 3));
        convexPoints.add(new Point(2, 3));
        convexPoints.add(new Point(1, 2));
        Polygon convexPolygon = new Polygon(convexPoints);

        // Define a diagonal line that passes through the hexagon (e.g., from (0,0) to (6,4))
        Line convexLine = new Line(new Point(0, 0), new Point(6, 4));

        List<Polygon> convexResult = convexPolygon.cut(convexLine);
        System.out.println("Original polygon: " + convexPolygon);
        System.out.println("Line: " + convexLine);
        System.out.println("Resulting polygons:");
        for (Polygon p : convexResult) {
            System.out.println(p);
        }
        System.out.println();

        // === EXAMPLE 3: Concave Polygon in the Shape of a "C" ===
        System.out.println("=== EXAMPLE 3: CONCAVE POLYGON (SHAPE OF 'C') ===");
        List<Point> concavePoints = new ArrayList<>();
        // A polygon that forms an inverted "C" shape:
        // (0,0) - (4,0) - (4,3) - (3,3) - (3,1) - (1,1) - (1,3) - (0,3)
        concavePoints.add(new Point(0, 0));
        concavePoints.add(new Point(4, 0));
        concavePoints.add(new Point(4, 3));
        concavePoints.add(new Point(3, 3));
        concavePoints.add(new Point(3, 1));
        concavePoints.add(new Point(1, 1));
        concavePoints.add(new Point(1, 3));
        concavePoints.add(new Point(0, 3));
        Polygon concavePolygon = new Polygon(concavePoints);

        // Define a vertical line at x = 2, from (2, -1) to (2, 4), to simulate an infinite line
        Line concaveLine = new Line(new Point(2, -1), new Point(2, 4));

        List<Polygon> concaveResult = concavePolygon.cut(concaveLine);
        System.out.println("Original polygon: " + concavePolygon);
        System.out.println("Line: " + concaveLine);
        System.out.println("Resulting polygons:");
        for (Polygon p : concaveResult) {
            System.out.println(p);
        }
        System.out.println();
    }
}

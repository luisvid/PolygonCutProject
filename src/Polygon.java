import java.util.ArrayList;
import java.util.List;

/**
 * Represents a polygon defined by a list of vertices.
 */
public class Polygon {
    public List<Point> vertices;

    /**
     * Constructs a polygon with the specified vertices.
     *
     * @param vertices the list of vertices that define the polygon.
     */
    public Polygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    /**
     * Cuts the current polygon with the specified line and returns a list of resulting polygons.
     *
     * @param line the line used to cut the polygon.
     * @return a list of polygons resulting from the cut operation.
     */
    public List<Polygon> cut(Line line) {
        List<Point> newVertices = new ArrayList<>();
        List<Integer> intersectionIndices = new ArrayList<>();
        int N = vertices.size();

        // Process each edge of the polygon.
        for (int i = 0; i < N; i++) {
            Point p_current = vertices.get(i);
            Point p_next = vertices.get((i + 1) % N);

            // Add the current vertex to the new list.
            newVertices.add(p_current);

            // Determine the side of the line for both vertices.
            int s_current = sign(line, p_current);
            int s_next = sign(line, p_next);

            // If both vertices are collinear with the line, skip processing this edge.
            if (s_current == 0 && s_next == 0) {
                continue;
            }

            // If the vertices lie on opposite sides or one touches the line, compute the intersection.
            if ((s_current * s_next < 0)
                || (s_current == 0 && s_next != 0)
                || (s_next == 0 && s_current != 0))
            {
                Point inter = computeIntersection(p_current, p_next, line);
                if (inter != null) {
                    newVertices.add(inter);
                    intersectionIndices.add(newVertices.size() - 1);
                }
            }
        }

        // If no intersections are found, return the original polygon.
        if (intersectionIndices.isEmpty()) {
            List<Polygon> res = new ArrayList<>();
            res.add(this);
            return res;
        }

        // Partition newVertices into sub-polygons based on intersection points.
        List<Polygon> result = new ArrayList<>();
        int numIntersections = intersectionIndices.size();
        for (int j = 0; j < numIntersections; j++) {
            int indexStart = intersectionIndices.get(j);
            int indexEnd = intersectionIndices.get((j + 1) % numIntersections);

            List<Point> cycle = extractCycle(newVertices, indexStart, indexEnd);
            // Ensure the sub-polygon has at least three vertices.
            if (cycle.size() >= 3) {
                result.add(new Polygon(cycle));
            }
        }

        return result;
    }

    /**
     * Determines on which side of the line the point lies using the cross product.
     *
     * @param line the reference line.
     * @param p    the point to evaluate.
     * @return +1 if the point is on one side, -1 if on the opposite side, or 0 if collinear.
     */
    private int sign(Line line, Point p) {
        double cross = (line.p2.x - line.p1.x) * (p.y - line.p1.y)
                     - (line.p2.y - line.p1.y) * (p.x - line.p1.x);
        double tolerance = 1e-9;
        if (cross > tolerance) {
            return +1;
        } else if (cross < -tolerance) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Computes the intersection point between the segment [p1, p2] and the infinite line.
     *
     * @param p1   the starting point of the segment.
     * @param p2   the ending point of the segment.
     * @param line the line to intersect with.
     * @return the intersection point, or null if the segment and line are parallel or collinear.
     */
    private Point computeIntersection(Point p1, Point p2, Line line) {
        double tolerance = 1e-9;
        double denominator = (p2.x - p1.x) * (line.p2.y - line.p1.y)
                           - (p2.y - p1.y) * (line.p2.x - line.p1.x);
        if (Math.abs(denominator) < tolerance) {
            // The segment and line are parallel or collinear.
            return null;
        }
        double t = ((line.p1.x - p1.x) * (line.p2.y - line.p1.y)
                  - (line.p1.y - p1.y) * (line.p2.x - line.p1.x)) / denominator;
        double x = p1.x + t * (p2.x - p1.x);
        double y = p1.y + t * (p2.y - p1.y);
        return new Point(x, y);
    }

    /**
     * Extracts a sub-polygon (cycle) from a list of vertices between the specified indices.
     *
     * @param vertices   the list of vertices including intersection points.
     * @param indexStart the starting index of the cycle.
     * @param indexEnd   the ending index of the cycle (inclusive).
     * @return a list of points representing the extracted cycle.
     */
    private List<Point> extractCycle(List<Point> vertices, int indexStart, int indexEnd) {
        List<Point> cycle = new ArrayList<>();
        if (indexStart <= indexEnd) {
            for (int k = indexStart; k <= indexEnd; k++) {
                cycle.add(vertices.get(k));
            }
        } else {
            // For the wrap-around case: traverse from indexStart to the end, and then from the beginning to indexEnd.
            for (int k = indexStart; k < vertices.size(); k++) {
                cycle.add(vertices.get(k));
            }
            for (int k = 0; k <= indexEnd; k++) {
                cycle.add(vertices.get(k));
            }
        }
        return cycle;
    }

    /**
     * Returns a string representation of the polygon.
     *
     * @return a string describing the polygon.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Polygon: ");
        for (Point p : vertices) {
            sb.append(p).append(" ");
        }
        return sb.toString();
    }
}

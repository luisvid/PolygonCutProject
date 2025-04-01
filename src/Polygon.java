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
        double tolerance = 1e-9; // Used in sign() and computeIntersection()

        // Iterate over each edge of the polygon (the last vertex connects to the first)
        for (int i = 0; i < N; i++) {
            Point p_current = vertices.get(i);
            Point p_next = vertices.get((i + 1) % N);

            // Add the current vertex.
            newVertices.add(p_current);
            int currentIndex = newVertices.size() - 1;

            int s_current = sign(line, p_current);
            int s_next = sign(line, p_next);

            // If the current vertex lies on the line, mark it as an intersection point.
            if (s_current == 0) {
                if (!intersectionIndices.contains(currentIndex)) {
                    intersectionIndices.add(currentIndex);
                }
            }

            // If both endpoints are on the line, discard this edge.
            if (s_current == 0 && s_next == 0) {
                continue;
            }

            // If a real crossing occurs: endpoints lie on opposite sides.
            if (s_current * s_next < 0) {
                // Compute the intersection point and add it.
                Point inter = computeIntersection(p_current, p_next, line);
                if (inter != null) {
                    newVertices.add(inter);
                    intersectionIndices.add(newVertices.size() - 1);
                }
            }
            // Case where only one endpoint is on the line (edge "touches" the line)
            // In this scenario, the collinear vertex has already been added, so no intersection is computed.
        }

        // If no intersection points are found, return the original polygon.
        if (intersectionIndices.size() == 0) {
            List<Polygon> res = new ArrayList<>();
            res.add(this);
            return res;
        }

        // Partition newVertices into sub-polygons using the intersection indices.
        List<Polygon> result = new ArrayList<>();
        int numIntersections = intersectionIndices.size();
        for (int j = 0; j < numIntersections; j++) {
            int indexStart = intersectionIndices.get(j);
            int indexEnd = intersectionIndices.get((j + 1) % numIntersections);

            List<Point> cycle = extractCycle(newVertices, indexStart, indexEnd);
            // Discard degenerate cycles (fewer than 3 points or near-zero area).
            if (cycle.size() >= 3 && Math.abs(computeArea(cycle)) > tolerance) {
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
     * Helper method to calculate the area of a polygon using the shoelace formula.
     *
     * @param pts the list of points representing the polygon.
     * @return the computed area of the polygon.
     */
    private double computeArea(List<Point> pts) {
        double area = 0;
        int n = pts.size();
        for (int i = 0; i < n; i++) {
            Point p1 = pts.get(i);
            Point p2 = pts.get((i + 1) % n);
            area += p1.x * p2.y - p2.x * p1.y;
        }
        return area / 2.0;
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

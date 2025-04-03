// This class represents a line segment defined by two points.
public record Line(Point p1, Point p2) {
    public Line {
        if (p1.equals(p2)) {
            throw new IllegalArgumentException("The two points of a line must be different.");
        }
    }
    
    @Override
    public String toString() {
        return "Line[" + p1 + " - " + p2 + "]";
    }
}

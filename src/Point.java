// This class represents a point in 2D space with x and y coordinates.
public record Point(double x, double y) {
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

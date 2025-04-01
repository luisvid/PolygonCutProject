// This class represents a line segment defined by two points.
public class Line {
    public Point p1, p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String toString(){
        return "Line[" + p1 + " - " + p2 + "]";
    }
}

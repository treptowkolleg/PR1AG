package pr1.a10;

import java.awt.geom.Ellipse2D;
import java.util.Objects;

public final class Point {
    private final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Point p)) {
            return false;
        }
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Ellipse2D.Double toEllipse(double diameter) {
        double px = center(x, diameter);
        double py = center(y, diameter);

        return new Ellipse2D.Double(px, py , diameter, diameter);
    }

    private double center(int position, double diameter) {
        return position - diameter / 2;
    }
}

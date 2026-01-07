package pr1.a10;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class SquareFilter implements PointFilter {

    @Override
    public boolean accept(Ellipse2D.Double e) {
        Rectangle2D.Double shape = new Rectangle2D.Double(250, 350, 300, 300);
        double px = e.x + e.width / 2.0;
        double py = e.y + e.height / 2.0;

        // falls Berühren ausreicht
        // return shape.intersects(e.getBounds2D());
        // tatsächlich innerhalb (am Mittelpunkt)
        return shape.contains(px, py);
    }
}

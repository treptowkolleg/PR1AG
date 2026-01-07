package pr1.a10;

import java.awt.geom.Ellipse2D;

public class CircleFilter implements PointFilter {

    @Override
    public boolean accept(Ellipse2D.Double e) {
        Ellipse2D.Double shape = new Ellipse2D.Double(100, 200, 400, 400);
        double px = e.x + e.width / 2.0;
        double py = e.y + e.height / 2.0;

        // falls Berühren ausreicht
        // return shape.intersects(e.getBounds2D());
        // tatsächlich innerhalb (am Mittelpunkt)
        return shape.contains(px, py);
    }
}

package pr1.a10;

import java.awt.geom.Ellipse2D;

public class CircleFilter implements PointFilter {

    @Override
    public boolean accept(Ellipse2D.Double e) {
        Ellipse2D.Double shape = new Ellipse2D.Double(300, 400, 400, 400);

        return shape.intersects(e.getBounds2D());
    }
}

package pr1.a10;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class SquareFilter implements PointFilter {

    @Override
    public boolean accept(Ellipse2D.Double e) {
        Rectangle2D.Double shape = new Rectangle2D.Double(400, 500, 300, 300);

        return shape.intersects(e.getBounds2D());
    }
}

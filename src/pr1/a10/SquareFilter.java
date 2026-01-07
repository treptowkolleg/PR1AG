package pr1.a10;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class SquareFilter implements PointFilter {

    @Override
    public boolean accept(Ellipse2D.Double e) {
        // Koordinaten sind leicht angepasst, damit vollständig sichtbar.
        Rectangle2D.Double shape = new Rectangle2D.Double(400, 200, 300, 300);

        return shape.intersects(e.getBounds2D());
    }
}

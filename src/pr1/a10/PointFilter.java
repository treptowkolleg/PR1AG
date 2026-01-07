package pr1.a10;

import java.awt.geom.Ellipse2D;

public interface PointFilter {
    boolean accept(Ellipse2D.Double e);
}

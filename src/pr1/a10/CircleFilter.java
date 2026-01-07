package pr1.a10;

import java.awt.geom.Ellipse2D;

public class CircleFilter extends ShapeFilter {

    public CircleFilter() {
        super(new Ellipse2D.Double(100, 200, 400, 400));
    }
}

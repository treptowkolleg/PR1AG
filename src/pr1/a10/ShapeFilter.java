package pr1.a10;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * Wie zu Beginn in Aufgabe 10 subtil erwähnt, implementiert
 * {@link Ellipse2D.Double} das Interface {@link Shape}. Gleiches gilt
 * natürlich auch für alle anderen primitiven Formen unter awt.
 * <p>
 * Daher kann diese Oberklasse die Prüfung durch Generalisierung für alle
 * spezialisierten FormFilter zentral implementieren (DRY).
 */
public abstract class ShapeFilter implements PointFilter {
    private final Shape shape;

    public ShapeFilter(Shape shape) {
        this.shape = shape;
    }

    @Override
    public boolean accept(Ellipse2D.Double e) {
        double px = e.x + e.width / 2.0;
        double py = e.y + e.height / 2.0;

        // falls Berühren ausreicht:
        // return shape.intersects(e.getBounds2D());
        // tatsächlich innerhalb (am Mittelpunkt):
        return shape.contains(px, py);
    }
}

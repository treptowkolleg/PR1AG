package pr1.a10;

import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Convert {
    private static final double DIAMETER = 5.0;

    /**
     * Erwartet eine Liste ganzer Zahlen, die als aufeinanderfolgende
     * (x, y)-Koordinaten interpretiert werden. Ist die Liste ungerade,
     * wird das letzte Element ignoriert, um eine
     * {@link IndexOutOfBoundsException} zu vermeiden.
     *
     * @param ints Liste mit ganzen Zahlen
     * @return Menge von Ellipsen, eine pro eindeutigem (x, y)-Paar
     */
    public static Set<Ellipse2D.Double> mapIntsToEllipses(List<Integer> ints) {
        // Nutze immutable Point-Klasse, um Duplikate (Koordinaten) zu
        // vermeiden (erster Test hatte ca. 1500 Duplikate).
        Set<Point> points = new HashSet<>();

        for (int i = 0; i < ints.size(); i += 2) {
            if (i + 1 >= ints.size()) {
                // für y gibt es keinen Wert mehr, also aufhören.
                break;
            }
            int x = ints.get(i);
            int y = ints.get(i + 1);
            points.add(new Point(x, y));
        }
        return points.stream()
                .map(p -> p.toEllipse(DIAMETER))
                .collect(Collectors.toSet());
    }

    public static Set<Ellipse2D.Double> filtered(Set<Ellipse2D.Double> points,
                                                 PointFilter filter) {
        return points.stream().filter(filter::accept).collect(Collectors.toSet());
    }
}

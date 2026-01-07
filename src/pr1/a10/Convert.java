package pr1.a10;

import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Convert {
    private static final double DIAMETER = 5.0;

    /**
     * Die Größe der Zahlenliste muss gerade sein. Dies wird hier nicht weiter
     * geprüft.
     *
     * @param ints Liste mit ganzen Zahlen
     * @return Liste mit Ellipsen
     */
    public static Set<Ellipse2D.Double> mapIntsToEllipses(List<Integer> ints) {
        Set<Ellipse2D.Double> ellipses = new HashSet<>();

        for (int i = 0; i < ints.size(); i += 2) {
            int x = ints.get(i);
            int y = ints.get(i + 1);

            ellipses.add(new Ellipse2D.Double(x, y, DIAMETER, DIAMETER));
        }
        return ellipses;
    }

    public static Set<Ellipse2D.Double> filtered(Set<Ellipse2D.Double> points,
                                                 PointFilter filter) {
        return points.stream().filter(filter::accept).collect(Collectors.toSet());
    }
}

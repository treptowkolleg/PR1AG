package pr1.a10;

import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Convert {

    public static Set<Ellipse2D.Double> mapIntsToEllipses(List<Integer> ints) {
        Set<Ellipse2D.Double> ellipses = new HashSet<>();

        for (int i = 0; i < ints.size(); i += 2) {
            int x = ints.get(i);
            int y = ints.get(i + 1);

            ellipses.add(new Ellipse2D.Double(x, y, 5, 5));
        }
        return ellipses;
    }

    public static Set<Ellipse2D.Double> filtered(Set<Ellipse2D.Double> points,
                                                 PointFilter filter) {
        return points.stream().filter(filter::accept).collect(Collectors.toSet());
    }
}

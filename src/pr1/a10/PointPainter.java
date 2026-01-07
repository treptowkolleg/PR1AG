package pr1.a10;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;
import schimkat.berlin.lernhilfe2025ws.graphics.FunnyFirstPainter;

import java.awt.Color;
import java.awt.Window;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PointPainter {

    public static void main(String[] args) {
        FunnyFirstPainter painter = new FunnyFirstPainter();
        String filename = "myIntegers.txt";
        DataProvider.writeIntNumberFile(filename, 150_000);
        List<Integer> integers = DataProvider.integerListFrom(filename);
        List<Drawable> drawables;
        Set<Ellipse2D.Double> a = Convert.mapIntsToEllipses(integers);
        Set<Ellipse2D.Double> b = Convert.filtered(a, new CircleFilter());
        Set<Ellipse2D.Double> c = Convert.filtered(a, new SquareFilter());
        Set<Ellipse2D.Double> d = new HashSet<>(b);
        Set<Ellipse2D.Double> e = new HashSet<>(b);

        d.addAll(c);
        e.retainAll(c);
        drawables = Arrays.asList(
                new VisiblePoints(Color.LIGHT_GRAY, a, "A (Alle Punkte)"),
                new VisiblePoints(Color.MAGENTA, b, "B (Kreisfilter)"),
                new VisiblePoints(Color.GREEN, c, "C (Quadratfilter)"),
                new VisiblePoints(Color.ORANGE, d, "D (Vereinigung aus B und C)"),
                new VisiblePoints(Color.YELLOW, e, "E (Schnittmenge aus B und C)")
        );
        painter.add(new BackgroundShape(Color.DARK_GRAY));
        painter.showDrawing();
        while (Window.getWindows().length > 0) {
            drawSequence(painter, drawables);
        }
    }

    private static void drawSequence(FunnyFirstPainter painter,
                                     List<Drawable> drawables) {
        for (Drawable drawable : drawables) {
            painter.add(drawable);
            painter.showDrawingAfterWaiting(3_000);
            painter.remove(drawable);
        }
    }
}

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
        String filename = "myIntegers.txt";
        DataProvider.writeIntNumberFile(filename, 75_000);
        List<Drawable> drawables;
        List<Integer> integers = DataProvider.integerListFrom(filename);
        Set<Ellipse2D.Double> a = Convert.mapIntsToEllipses(integers);
        Set<Ellipse2D.Double> b = Convert.filtered(a, new CircleFilter());
        Set<Ellipse2D.Double> c = Convert.filtered(a, new SquareFilter());
        Set<Ellipse2D.Double> d = new HashSet<>(b);
        Set<Ellipse2D.Double> e = new HashSet<>(b);
        FunnyFirstPainter painter = new FunnyFirstPainter();

        d.addAll(c);
        e.retainAll(c);
        drawables = Arrays.asList(
                new VisiblePoints(Color.GRAY, a, "A (Obermenge)"),
                new VisiblePoints(Color.MAGENTA, b, "B ⊂ A"),
                new VisiblePoints(Color.GREEN, c, "C ⊂ A"),
                new VisiblePoints(Color.ORANGE, d, "D = B ∪ C"),
                new VisiblePoints(Color.YELLOW, e, "E = B ∩ C")
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

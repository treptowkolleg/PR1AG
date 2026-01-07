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
        DataProvider.writeIntNumberFile(filename, 75000);
        List<Integer> integers = DataProvider.integerListFrom(filename);
        List<Drawable> drawables;
        Set<Ellipse2D.Double> mengeA = Convert.mapIntsToEllipses(integers);
        Set<Ellipse2D.Double> mengeB = Convert.filtered(mengeA, new CircleFilter());
        Set<Ellipse2D.Double> mengeC = Convert.filtered(mengeA, new SquareFilter());
        Set<Ellipse2D.Double> mengeD = new HashSet<>(mengeB);
        Set<Ellipse2D.Double> mengeE = new HashSet<>(mengeB);

        mengeD.addAll(mengeC);
        mengeE.retainAll(mengeC);
        drawables = Arrays.asList(
                new VisiblePoints(Color.LIGHT_GRAY, mengeA),
                new VisiblePoints(Color.MAGENTA, mengeB),
                new VisiblePoints(Color.GREEN, mengeC),
                new VisiblePoints(Color.ORANGE, mengeD),
                new VisiblePoints(Color.YELLOW, mengeE)
        );
        painter.add(new BackgroundShape(Color.DARK_GRAY));
        painter.showDrawing();
        while (Window.getWindows().length > 0) {
            drawSequence(painter, drawables);
        }
    }

    private static void drawSequence(FunnyFirstPainter painter,
                                     List<Drawable> drawables) {
        for (Drawable d : drawables) {
            painter.add(d);
            painter.showDrawingAfterWaiting(2000);
            painter.remove(d);
        }
    }

}

package pr1.a08;

import treptowkolleg.plot.EmptyGrid;
import treptowkolleg.plot.PlotApplication;
import treptowkolleg.plot.PlotSet;
import schimkat.berlin.lernhilfe2025ws.graphics.FunnyFirstPainter;

public class Test {

    public static void main(String[] args) {
        testEinfacheLandschaft();
        testLandschaft();
        testWinterLandschaft();
    }

    public static void testEinfacheLandschaft() {
        /*
        Da die Landschaften sowohl von PlotGraph erben, als auch Drawable
        implementieren, können wir deren Instanzen sowohl der PlotApplication
        als auch dem FunnyFirstPainter hinzufügen.
        Zoom funktioniert nur, wenn der Graph dies implementiert, was hier
        nicht der Fall ist.
         */
        PlotApplication app = new PlotApplication("Einfache Landschaft", 600, 600, true);
        PlotSet<EinfacheLandschaft> set = new PlotSet<>() {
        };
        set.setTitle("Einfach");
        set.setGrid(new EmptyGrid());
        set.addGraph(new EinfacheLandschaft());
        app.addPlotSet(set);
        app.start();
    }

    public static void testLandschaft() {
        FunnyFirstPainter painter = new FunnyFirstPainter();

        painter.add(new Landschaft(3, 12));
        painter.showDrawing();
    }

    public static void testWinterLandschaft() {
        PlotApplication app = new PlotApplication("Winterlandschaft", 600, 600, true);
        PlotSet<EinfacheLandschaft> set = new PlotSet<>() {
        };
        set.setTitle("Winter");
        set.addGraph(new WinterLandschaft());
        app.addPlotSet(set);
        app.start();
    }
}

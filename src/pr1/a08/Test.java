package pr1.a08;

import pr1.a07.plot.EmptyGrid;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotSet;

public class Test {

    public static void main(String[] args) {
        testEinfacheLandschaft();
        testLandschaft();
        testWinterLandschaft();
    }

    public static void testEinfacheLandschaft() {
        PlotApplication app = new PlotApplication("Einfache Landschaft", 600, 400);
        PlotSet<EinfacheLandschaft> set = new PlotSet<>() {
        };
        set.setGrid(new EmptyGrid());
        set.addGraph(new EinfacheLandschaft());
        app.addPlotSet(set);
        app.start();
    }

    public static void testLandschaft() {
        PlotApplication app = new PlotApplication("Landschaft", 600, 400);
        PlotSet<EinfacheLandschaft> set = new PlotSet<>() {
        };
        set.setGrid(new EmptyGrid());
        set.addGraph(new Landschaft(3, 12));
        set.createControl(app);
        app.addPlotSet(set);
        app.start();
    }

    public static void testWinterLandschaft() {
        PlotApplication app = new PlotApplication("Winterlandschaft", 600, 400);
        PlotSet<EinfacheLandschaft> set = new PlotSet<>() {
        };
        set.setGrid(new EmptyGrid());
        set.addGraph(new WinterLandschaft());
        app.addPlotSet(set);
        app.start();
    }
}

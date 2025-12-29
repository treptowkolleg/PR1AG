package pr1.a07.plot.demo;

import pr1.a07.plot.EmptyGrid;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotSet;
import pr1.a08.EinfacheLandschaft;
import pr1.a08.WinterLandschaft;

public class TestDemo {

    public static void main(String[] args) {
        PlotApplication app = new PlotApplication("Demo-Plotter", 1280, 720);
        PlotSet<EinfacheLandschaft> set = new PlotSet<>() {
        };
        set.setTitle("Winter");
        set.setGrid(new EmptyGrid());
        set.addGraph(new WinterLandschaft());

        app.addPlotSet(new TestSet());
        app.addPlotSet(new SerialSet());
        app.addPlotSet(new TrigonometrieSet());
        app.addPlotSet(new MusterSet());
        app.addPlotSet(set);
        app.start();
    }
}

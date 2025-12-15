package pr1.a07.plot.demo;

import pr1.a07.plot.PlotApplication;

public class TestDemo {

    public static void main(String[] args) {
        PlotApplication app = new PlotApplication("Demo-Plotter", 1280, 720);

        app.addPlotSet(new TestSet());
        app.addPlotSet(new TrigonometrieSet());
        app.addPlotSet(new MusterSet());
        app.start();
    }
}

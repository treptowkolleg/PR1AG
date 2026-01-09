package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.plot.PlotApplication;

import java.util.Arrays;

public class Demo {

    public static void main(String[] args) {
        PlotApplication.DEV_MODE = Arrays.asList(args).contains("--dev");
        PlotApplication app = new PlotApplication("Demo-Plotter", 1280, 720);

        app.addPlotSet(new TestSet());
        app.addPlotSet(new SerialSet());
        app.addPlotSet(new TrigonometrieSet());
        app.addPlotSet(new MusterSet());
        app.start();
    }
}

package pr1.a07.plot.demo;

import pr1.a07.plot.PlotApplication;

public class TestDemo {

    public static void main(String[] args) {
        PlotApplication app = new PlotApplication();
        app.addPlotSet(new TestSet());
        app.start();
    }
}

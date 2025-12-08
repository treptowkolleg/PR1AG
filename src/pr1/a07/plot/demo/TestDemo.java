package pr1.a07.plot.demo;

import pr1.a07.plot.PlotGrid;
import pr1.a07.plot.PlotApplication;

public class TestDemo {

    public static void main(String[] args) {
        PlotApplication app = new PlotApplication();
        TestSet set = new TestSet();
        set.setGrid(new PlotGrid());
        app.addPlotSet(set);
        app.setVisible(true);
    }
}

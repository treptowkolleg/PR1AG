package pr1.a07.plot.demo;

import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotGrid;
import pr1.a07.plot.PlotSet;
import pr1.a07.plot.PlotApplication;

import java.awt.Color;

public class TestSet extends PlotSet<TestGraph> {

    public TestSet() {
        TestGraph customGraph = new TestGraph();

        customGraph.setTitle("Blue Box");
        customGraph.setColor(Color.BLUE);
        customGraph.setX(200);
        customGraph.setY(-100);
        addGraph(new TestGraph("Red Box", Color.RED));
        addGraph(customGraph);
        setGrid(new PlotGrid());
    }

    @Override
    public PlotControl<TestGraph> createControl(PlotApplication app) {
        return new TestControl(app, this);
    }
}

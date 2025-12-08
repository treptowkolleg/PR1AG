package pr1.a07.plot.demo;

import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;
import pr1.a07.plot.PlotApplication;

public class TestSet extends PlotSet<TestGraph> {

    public TestSet() {
        TestGraph customGraph = new TestGraph();
        customGraph.setX(200);
        customGraph.setY(-100);
        addGraph(new TestGraph());
        addGraph(customGraph);
    }

    @Override
    public PlotControl<TestGraph> createControl(PlotApplication app) {
        return new TestControl(app, getGraphs());
    }
}

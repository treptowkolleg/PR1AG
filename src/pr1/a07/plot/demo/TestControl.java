package pr1.a07.plot.demo;

import pr1.a07.plot.ControlBuilder;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;

import javax.swing.JPanel;

public class TestControl extends PlotControl<TestGraph> {

    public TestControl(PlotApplication application, PlotSet<TestGraph> plotSet) {
        super(application, plotSet);
        setTitle("Test-Control");
    }

    @Override
    public JPanel configureControls(ControlBuilder<TestGraph> build) {
        return build
                .selector("Plot-Auswahl", "Plot")
                .slider("X-Wert", -400, 400, TestGraph::getX, TestGraph::setX)
                .slider("Y-Wert", -200, 200, TestGraph::getY, TestGraph::setY)
                .getPanel();
    }
}

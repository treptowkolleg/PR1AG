package pr1.a07.plot.demo;

import pr1.a07.plot.ControlBuilder;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotGraphList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestControl extends PlotControl<TestGraph> {

    public TestControl(JFrame application, PlotGraphList<TestGraph> graphs) {
        super(application, graphs);
    }

    @Override
    public JPanel configureControls(ControlBuilder<TestGraph> build) {
        return build
                .selector("Plot")
                .slider("X-Wert", -200, 200, TestGraph::getX, TestGraph::setX)
                .slider("Y-Wert", -200, 200, TestGraph::getY, TestGraph::setY)
                .getPanel();
    }
}

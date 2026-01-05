package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.plot.ControlBuilder;
import treptowkolleg.edu.swing.plot.PlotApplication;
import treptowkolleg.edu.swing.plot.PlotControl;
import treptowkolleg.edu.swing.plot.PlotSet;

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
                .addDoubleColumn("Koordinaten")
                .slider("X-Wert", -400, 400, TestGraph::getX, TestGraph::setX)
                .slider("Y-Wert", -200, 200, TestGraph::getY, TestGraph::setY)
                .getPanel();
    }
}

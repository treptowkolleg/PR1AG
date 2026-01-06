package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.plot.ControlBuilder;
import treptowkolleg.edu.swing.plot.PlotApplication;
import treptowkolleg.edu.swing.plot.PlotControl;
import treptowkolleg.edu.swing.plot.PlotSet;

import javax.swing.JPanel;

public class MusterControl extends PlotControl<MusterGraph> {

    public MusterControl(PlotApplication application, PlotSet<MusterGraph> plotSet) {
        super(application, plotSet);
    }

    @Override
    public JPanel configureControls(ControlBuilder<MusterGraph> build) {
        return build
                .addDoubleColumn()
                .sliderDouble("Start", -100, 0, 50, MusterGraph::getxMin, MusterGraph::setxMin)
                .sliderDouble("Ende", 0, 300, 50, MusterGraph::getxMax, MusterGraph::setxMax)
                .getPanel();
    }
}

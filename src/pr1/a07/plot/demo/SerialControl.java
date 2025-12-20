package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.ControlBuilder;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;

import javax.swing.JPanel;

public class SerialControl extends PlotControl<SerialGraph> {

    public SerialControl(PlotApplication application,
                         PlotSet<SerialGraph> plotSet) {
        super(application, plotSet);
        setTitle("Arduino-Control");
    }

    @Override
    public JPanel configureControls(ControlBuilder<SerialGraph> build) {
        return build
                .button("Zurücksetzen", SerialGraph::reset)
                .button("Start/Pause", SerialGraph::toggle)
                .button(Colors.DARK_GREEN, "Starte Messung", SerialGraph::sendStartCommand)
                .getPanel();
    }
}

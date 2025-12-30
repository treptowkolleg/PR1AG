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
                .headline("Plot-Steuerung")
                .button(Colors.BLUE, "Start", SerialGraph::start)
                .button("Stop", SerialGraph::stop)
                .button("Zurücksetzen", SerialGraph::reset)
                .headline("Messsteuerung")
                .button(Colors.DARK_GREEN, "Starte Messung", SerialGraph::sendStartCommand)
                .outputTimed("Entladezeit", "t", SerialGraph::getStoppedTimeFormatted, 100)
                .outputTimed("gemessene Schwellenspannung", "u", SerialGraph::getThresholdVoltageFormatted, 100)
                .outputTimed("ermittelte Diode", "d", SerialGraph::getUsedDiode, 100)
                .button("Diodencharakteristik abhören", SerialGraph::playDiodeCurveSonified)
                .button("Diodencharakteristik speichern", SerialGraph::saveDiodeCurveSonified)
                .checkbox("Kondensatorkennlinie anzeigen", SerialGraph::isIdealLineIsVisible, SerialGraph::setIdealLineIsVisible)
                .checkbox("Diodenkennlinie anzeigen", SerialGraph::isDiodeLineIsVisible, SerialGraph::setDiodeLineIsVisible)
                .headline("Legende")
                .legend(Colors.RED, "Kondensatorkennlinie f(x)")
                .legend(Colors.DARKER_GREEN, "Messkurve g(x)")
                .legend(Colors.BLUE, "Diodencharakteristik d(x) = f(x) - g(x)")
                .getPanel();
    }
}

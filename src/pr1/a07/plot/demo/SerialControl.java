package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.graphics.Colors;
import treptowkolleg.edu.swing.plot.ControlBuilder;
import treptowkolleg.edu.swing.plot.PlotApplication;
import treptowkolleg.edu.swing.plot.PlotControl;
import treptowkolleg.edu.swing.plot.PlotSet;

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
                .selector(null, "Port")
                .addDoubleColumn()
                .buttonPrimary("Start", SerialGraph::start)
                .buttonSecondary("Stop", SerialGraph::stop)
                .buttonSecondary("Zurücksetzen", SerialGraph::reset)
                .headline("Messung")
                .buttonSuccess("Messung starten", SerialGraph::sendStartCommand)
                .checkbox("Messung bei 0 V stoppen", SerialGraph::isAutoStop, SerialGraph::setAutoStop)
                .addDoubleColumn()
                .outputTimed("Entladezeit", "t", SerialGraph::getStoppedTimeFormatted, 100)
                .outputTimed("Schwellenspannung", "u", SerialGraph::getThresholdVoltageFormatted, 100)
                .headline("Auswertung")
                .outputTimed("ermittelte Diode", "d", SerialGraph::getUsedDiode, 100)
                .addDoubleColumn("Diodenkennlinie als LIGO-Sonification")
                .buttonSecondary("Abhören", SerialGraph::playDiodeCurveSonified)
                .buttonSecondary("Speichern", SerialGraph::saveDiodeCurveSonified)
                .addDoubleColumn("Kennlinien anzeigen")
                .checkbox("Kondensator", SerialGraph::isIdealLineIsVisible, SerialGraph::setIdealLineIsVisible)
                .checkbox("Diode", SerialGraph::isDiodeLineIsVisible, SerialGraph::setDiodeLineIsVisible)
                .headline("Legende")
                .legend(Colors.DARKER_GREEN, "Messkurve g(x)")
                .legend(Colors.RED, "Kondensatorkennlinie f(x)")
                .legend(Colors.BLUE, "Diodencharakteristik d(x) = f(x) - g(x)")
                .getPanel();
    }
}

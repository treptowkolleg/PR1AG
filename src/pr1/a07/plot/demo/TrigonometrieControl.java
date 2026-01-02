package pr1.a07.plot.demo;

import pr1.a07.plot.ControlBuilder;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;

import javax.swing.JPanel;

public class TrigonometrieControl extends PlotControl<TrigonometrieGraph> {

    public TrigonometrieControl(PlotApplication application, PlotSet<TrigonometrieGraph> plotSet) {
        super(application, plotSet);
        setTitle("Trigonometrie-Control");
    }

    @Override
    public JPanel configureControls(ControlBuilder<TrigonometrieGraph> build) {
        return build
                .headline("Plotauswahl")
                .addDoubleColumn()
                .selector(null, "Graph")
                .checkbox("Sichtbar", TrigonometrieGraph::isVisible, TrigonometrieGraph::setVisible)
                .headline("Funktionsparameter")
                .addDoubleColumn("y-Achsensteuerung")
                .sliderDouble("Amplitude", -2, 2, .05, TrigonometrieGraph::getAmplitude, TrigonometrieGraph::setAmplitude)
                .sliderDouble("dY", -2, 2, .1, TrigonometrieGraph::getDy, TrigonometrieGraph::setDy)
                .addDoubleColumn("x-Achsensteuerung")
                .sliderDouble("Frequenz", -2, 2, .05, TrigonometrieGraph::getFrequency, TrigonometrieGraph::setFrequency)
                .sliderDouble("dX", -2, 2, .1, TrigonometrieGraph::getDx, TrigonometrieGraph::setDx)
                .headline("Optionen")
                .sliderDouble("Intervall-Start", -12, 12, .25, TrigonometrieGraph::getIntervalStart, TrigonometrieGraph::setIntervalStart)
                .sliderDouble("Intervall-Ende", -12, 12, .25, TrigonometrieGraph::getIntervalEnd, TrigonometrieGraph::setIntervalEnd)
                .sliderDouble("Auflösung", .1, 1, .1, TrigonometrieGraph::getResolution, TrigonometrieGraph::setResolution)
                .getPanel();
    }
}

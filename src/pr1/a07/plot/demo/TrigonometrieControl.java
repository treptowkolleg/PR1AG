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
                .selector("Plot-Auswahl", "Graph")
                .sliderDouble("Amplitude", -2, 2, .1, TrigonometrieGraph::getAmplitude, TrigonometrieGraph::setAmplitude)
                .sliderDouble("Frequenz", -2, 2, .1, TrigonometrieGraph::getWaveLength, TrigonometrieGraph::setWaveLength)
                .sliderDouble("dX", -2, 2, .1, TrigonometrieGraph::getDx, TrigonometrieGraph::setDx)
                .sliderDouble("dY", -2, 2, .1, TrigonometrieGraph::getDy, TrigonometrieGraph::setDy)
                .sliderDouble("Auflösung", .1, 1, .1, TrigonometrieGraph::getResolution, TrigonometrieGraph::setResolution)
                .sliderDouble("Intervall-Start", -12, 12, .5, TrigonometrieGraph::getIntervalStart, TrigonometrieGraph::setIntervalStart)
                .sliderDouble("Intervall-Ende", -12, 12, .5, TrigonometrieGraph::getIntervalEnd, TrigonometrieGraph::setIntervalEnd)
                .getPanel();
    }
}

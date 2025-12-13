package pr1.a07.plot.demo;

import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;

public class TrigonometrieSet extends PlotSet<TrigonometrieGraph> {

    public TrigonometrieSet() {
        int scale = 50;

        setTitle("Trigonometrie-Funktionen");
        setGrid(new TrigonometrieGrid(scale));
        addGraph(GraphFactory.sinus(scale));
        addGraph(GraphFactory.cosinus(scale));
        addGraph(GraphFactory.sinusSquared(scale));
        addGraph(GraphFactory.sinusMalCosinus(scale));
    }

    @Override
    public PlotControl<TrigonometrieGraph> createControl(PlotApplication app) {
        return new TrigonometrieControl(app, this);
    }

}

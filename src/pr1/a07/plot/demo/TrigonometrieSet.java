package pr1.a07.plot.demo;

import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;

public class TrigonometrieSet extends PlotSet<TrigonometrieGraph> {

    public TrigonometrieSet() {
        setTitle("Trigonometrie-Funktionen");
        setGrid(new TrigonometrieGrid());
        addGraph(GraphFactory.sinus());
        addGraph(GraphFactory.cosinus());
        addGraph(GraphFactory.sinusSquared());
        addGraph(GraphFactory.sinusMalCosinus());
    }

    @Override
    public PlotControl<TrigonometrieGraph> createControl(PlotApplication app) {
        return new TrigonometrieControl(app, this);
    }

}

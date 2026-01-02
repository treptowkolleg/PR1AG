package pr1.a07.plot.demo;

import treptowkolleg.plot.GridPosition;
import treptowkolleg.plot.PlotApplication;
import treptowkolleg.plot.PlotControl;
import treptowkolleg.plot.PlotSet;

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

    @Override
    public void preSetup(PlotApplication frame) {
        setGridPosition(GridPosition.CENTER, frame.getDrawablePanel());
        setZoomY(2);
    }
}

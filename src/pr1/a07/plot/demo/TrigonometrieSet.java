package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.plot.GridPosition;
import treptowkolleg.edu.swing.plot.PlotApplication;
import treptowkolleg.edu.swing.plot.PlotControl;
import treptowkolleg.edu.swing.plot.PlotSet;

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

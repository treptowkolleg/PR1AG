package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.graphics.Colors;
import treptowkolleg.edu.swing.plot.GridPosition;
import treptowkolleg.edu.swing.plot.PlotApplication;
import treptowkolleg.edu.swing.plot.PlotControl;
import treptowkolleg.edu.swing.plot.PlotGrid;
import treptowkolleg.edu.swing.plot.PlotSet;

public class MusterSet extends PlotSet<MusterGraph> {

    public MusterSet() {
        addGraph(new MusterGraph(Colors.PINK, 0, 300, 50));
        setGrid(new PlotGrid(25));
        setTitle("Übungsmuster");
    }

    @Override
    public PlotControl<MusterGraph> createControl(PlotApplication app) {
        return new MusterControl(app, this);
    }

    @Override
    public void preSetup(PlotApplication app) {
        app.setGridPosition(GridPosition.NORTH_WEST);
    }
}

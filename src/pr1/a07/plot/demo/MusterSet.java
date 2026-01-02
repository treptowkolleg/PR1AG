package pr1.a07.plot.demo;

import treptowkolleg.plot.Colors;
import treptowkolleg.plot.GridPosition;
import treptowkolleg.plot.PlotApplication;
import treptowkolleg.plot.PlotGrid;
import treptowkolleg.plot.PlotSet;

public class MusterSet extends PlotSet<MusterGraph> {

    public MusterSet() {
        addGraph(new MusterGraph(Colors.PINK, 0, 300, 50));
        setGrid(new PlotGrid(25));
        setTitle("Übungsmuster");
    }

    @Override
    public void preSetup(PlotApplication frame) {
        setGridPosition(GridPosition.NORTH_WEST, frame.getDrawablePanel());
    }
}

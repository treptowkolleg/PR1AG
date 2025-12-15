package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotGrid;
import pr1.a07.plot.PlotSet;

public class MusterSet extends PlotSet<MusterGraph> {

    public MusterSet() {
        addGraph(new MusterGraph(Colors.PINK, 0, 300, 50));
        setGrid(new PlotGrid(25));
        setTitle("Übungsmuster");
    }

}

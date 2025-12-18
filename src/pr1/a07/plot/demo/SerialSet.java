package pr1.a07.plot.demo;

import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;
import pr1.a08.Colors;

public class SerialSet extends PlotSet<SerialGraph> {

    public SerialSet() {
        setGrid(new MathGrid());
        addGraph(new SerialGraph(Colors.DARKER_GREEN, "Spannung"));
        setTitle("Kondensatorentladung mit Arduino messen");
    }

    @Override
    public PlotControl<SerialGraph> createControl(PlotApplication app) {
        return new SerialControl(app, this);
    }
}

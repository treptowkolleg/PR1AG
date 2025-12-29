package pr1.a07.plot.demo;

import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;
import pr1.a07.Colors;

public class SerialSet extends PlotSet<SerialGraph> {
    private final SerialGraph serialGraph;

    public SerialSet() {
        serialGraph = new SerialGraph(Colors.DARKER_GREEN, "Spannung");
        setGrid(new MathGrid("t in s", "U in V"));
        addGraph(serialGraph);
        setTitle("Schwellenspannung von Dioden");
    }

    @Override
    public PlotControl<SerialGraph> createControl(PlotApplication app) {
        boolean serialAvailable = serialGraph.serialIsAvailable();

        app.setArduinoEnabled(serialAvailable);
        if (serialAvailable) {
            return new SerialControl(app, this);
        }
        return null;
    }
}

package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.graphics.Colors;
import treptowkolleg.edu.swing.plot.GridPosition;
import treptowkolleg.edu.swing.plot.PlotApplication;
import treptowkolleg.edu.swing.plot.PlotControl;
import treptowkolleg.edu.swing.plot.PlotSet;

public class SerialSet extends PlotSet<SerialGraph> {
    private final SerialGraph serialGraph;

    public SerialSet() {
        serialGraph = new SerialGraph(Colors.DARKER_GREEN, "COM3");
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

    @Override
    public void preSetup(PlotApplication app) {
        app.setGridPosition(GridPosition.SOUTH_EAST);
        app.setZoom(.2, 1.5);
    }
}

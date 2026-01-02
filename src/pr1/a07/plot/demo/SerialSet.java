package pr1.a07.plot.demo;

import treptowkolleg.plot.Colors;
import treptowkolleg.plot.GridPosition;
import treptowkolleg.plot.PlotApplication;
import treptowkolleg.plot.PlotControl;
import treptowkolleg.plot.PlotSet;

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
    public void preSetup(PlotApplication frame) {
        setGridPosition(GridPosition.SOUTH_EAST, frame.getDrawablePanel());
        setZoomX(.1);
        setZoomY(1.5);
    }
}

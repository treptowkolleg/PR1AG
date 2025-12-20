package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotGraph;
import pr1.a07.plot.SerialReader;
import pr1.a07.plot.Stroke;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class SerialGraph extends PlotGraph<SerialGraph> {
    private final ArrayList<Integer> yValues = new ArrayList<>();
    private final double xScale = 5;
    private final double yScale = .5;
    private final SerialReader reader = new SerialReader();

    public SerialGraph(Color color, String title) {
        this.color = color;
        this.title = title;
        reader.startReading(yValues);
    }

    public boolean serialIsAvailable() {
        return reader.isAvailable();
    }

    public void reset() {
        yValues.clear();
    }

    public void sendStartCommand() {
        reader.sendStartCommand();
    }

    public void play() {
        reader.setRunning(true);
    }

    public void stop() {
        reader.setRunning(false);
    }

    public void toggle() {
        reader.setRunning(!reader.isRunning());
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        int prevX = centerX;
        int prevY;
        final int size;
        final double xFactor = xScale * PlotApplication.X_SCALE;
        final double yFactor = yScale * PlotApplication.Y_SCALE;

        g.setColor(color);
        g.setStroke(Stroke.BEVEL_MEDIUM);
        synchronized (yValues) {
            size = yValues.size();
            if (size <= 1) {
                return;
            }
        }
        prevY = (int) (centerY - yValues.get(0) * yFactor);
        for (int i = 1; i < size; i++) {
            int currX = (int) (centerX + i * xFactor);
            int currY = (int) (centerY - yValues.get(i) * yFactor);

            updateColorBasedOnSlope(g, prevY, currY);
            if (prevY < centerY && currY == centerY) {
                stop();
            }
            g.drawLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }
        if (reader.isRunning()) {
            adjustXDelta();
        }
    }

    private void updateColorBasedOnSlope(Graphics2D g, int prevY, int currY) {
        if (prevY > currY) {
            g.setColor(Colors.BLUE);
        } else if (prevY == currY) {
            g.setColor(Colors.YELLOW);
        } else {
            g.setColor(color);
        }
    }

    private void adjustXDelta() {
        PlotApplication.X_DELTA -= (PlotApplication.X_SCALE / xScale) * 4.25;
    }
}

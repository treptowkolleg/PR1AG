package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotGraph;
import pr1.a07.plot.SerialReader;

import java.awt.BasicStroke;
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
        g.setColor(color);
        g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL
        ));
        synchronized (yValues) {
            if (yValues.isEmpty()) {
                return;
            }
            int prevX = (int) (centerX + 0 * xScale * PlotApplication.X_SCALE);
            int prevY = (int) (centerY - yValues.get(0) * yScale * PlotApplication.Y_SCALE);

            for (int i = 1; i < yValues.size(); i++) {
                int currX = (int) (centerX + i * xScale * PlotApplication.X_SCALE);
                int currY = (int) (centerY - yValues.get(i) * yScale * PlotApplication.Y_SCALE);

                if (prevY > currY) {
                    g.setColor(Colors.BLUE);
                } else if (prevY == currY) {
                    g.setColor(Colors.YELLOW);
                } else {
                    g.setColor(color);
                }
                if (prevY < centerY && currY == centerY) {
                    stop();
                }
                g.drawLine(prevX, prevY, currX, currY);
                prevX = currX;
                prevY = currY;
            }
        }
        if (reader.isRunning()) {
            PlotApplication.X_DELTA = PlotApplication.X_DELTA - PlotApplication.X_SCALE / xScale * 4.25;
        }
    }
}

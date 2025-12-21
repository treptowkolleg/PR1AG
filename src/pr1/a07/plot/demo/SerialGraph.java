package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotGraph;
import pr1.a07.plot.SerialReader;
import pr1.a07.plot.Stroke;
import pr1.helper.core.StopWatch;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class SerialGraph extends PlotGraph<SerialGraph> {
    private final double xScale;
    private final double yScale;
    private final ArrayList<Integer> yValues = new ArrayList<>();
    private final SerialReader reader = new SerialReader();
    private final StopWatch stopWatch = new StopWatch();

    public SerialGraph(Color color, String title) {
        this(color, title, 5.0, 0.5);
    }

    public SerialGraph(Color color, String title, double xScale,
                       double yScale) {
        this.color = color;
        this.title = title;
        this.xScale = xScale;
        this.yScale = yScale;
        reader.startReading(yValues);
    }

    public boolean serialIsAvailable() {
        return reader.isAvailable();
    }

    public void reset() {
        yValues.clear();
        stop();
    }

    public void sendStartCommand() {
        reader.sendStartCommand();
        if (!stopWatch.isRunning()) {
            stopWatch.setPreTime(1);
            stopWatch.start();
        }
    }

    public void start() {
        reader.setRunning(true);
        if (!stopWatch.isRunning()) {
            stopWatch.setPreTime(0);
            stopWatch.start();
        }
    }

    public void stop() {
        reader.setRunning(false);
        if (stopWatch.isRunning()) {
            stopWatch.stop();
        }
    }

    public String getStoppedTimeFormatted() {
        return String.format("%.1f s", stopWatch.getElapsedSeconds());
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
                if (reader.isRunning()) {
                    stop();
                }
            }
            g.drawLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }
        if (reader.isRunning()) {
            adjustXDelta();
        } else {
            drawApproximatedExponentialCurve(g);
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

    private void drawApproximatedExponentialCurve(Graphics2D g) {
        if (yValues.size() < 2) {
            return;
        }
        final double xFactor = xScale * PlotApplication.X_SCALE;
        final double yFactor = yScale * PlotApplication.Y_SCALE;
        int startY = yValues.get(0);
        int endY = yValues.get(yValues.size() - 1);
        int size = yValues.size();
        double a = startY - endY;
        double targetY = endY + 0.01 * a;
        double b = -Math.log((targetY - (double) endY) / a) / (size - 1);
        int prevX = centerX;
        int prevY = (int) (centerY - (a * Math.exp(-b * 0) + (double) endY) * yFactor);

        g.setColor(Colors.PINK);
        g.setStroke(Stroke.LINE_THICK);
        for (int i = 1; i < size; i++) {
            int currX = (int) (centerX + i * xFactor);
            double currYReal = a * Math.exp(-b * i) + (double) endY;
            int currY = (int) (centerY - currYReal * yFactor);

            g.drawLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }
    }
}
package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotGraph;
import pr1.a07.plot.SerialReader;
import pr1.a07.plot.Sonifier;
import pr1.a07.plot.Stroke;
import pr1.helper.core.StopWatch;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SerialGraph extends PlotGraph<SerialGraph> {
    private static final double TARGET_FRACTION = 0.002;
    private final double xScale;
    private final double yScale;
    private final ArrayList<Integer> yValues = new ArrayList<>();
    private final ArrayList<Double> diffValues = new ArrayList<>();
    private final SerialReader reader = new SerialReader();
    private final StopWatch stopWatch = new StopWatch();
    private boolean diffComputed = false;
    private double thresholdVoltage = 0;
    private String usedDiode = "-";
    private boolean audioIsPlaying = false;

    public SerialGraph(Color color, String title) {
        this(color, title, 10.0, 0.1);
    }

    public SerialGraph(Color color, String title, double xScale,
                       double yScale) {
        this.color = color;
        this.title = title;
        this.xScale = xScale;
        this.yScale = yScale;
        // TODO: x- und y-Scale in Oberklasse überführen!
        reader.startReading(yValues);
    }

    public boolean serialIsAvailable() {
        return reader.isAvailable();
    }

    public String getThresholdVoltageFormatted() {
        correctedVoltage(thresholdVoltage);
        return String.format("%.2f V", thresholdVoltage);
    }

    public String getUsedDiode() {
        return usedDiode;
    }

    private void correctedVoltage(double measuredU) {
        if (measuredU < 0.1) {
            usedDiode = "-";
        } else if (measuredU < 0.5) {
            usedDiode = "Germanium-Diode";
        } else if (measuredU < 0.54) {
            usedDiode = "Germanium- oder Silizium-Diode";
        } else if (measuredU < 1) {
            usedDiode = "Silizium-Diode";
        } else if (measuredU < 1.8) {
            usedDiode = "rote LED";
        } else if (measuredU < 1.85) {
            usedDiode = "gelbe LED";
        } else if (measuredU < 1.89) {
            usedDiode = "gelbe oder grüne LED";
        } else if (measuredU < 2) {
            usedDiode = "grüne LED";
        } else if (measuredU < 2.65) {
            usedDiode = "blaue LED";
        } else if (measuredU < 2.67) {
            usedDiode = "blaue oder weiße LED";
        } else {
            usedDiode = "weiße LED";
        }
    }

    public void reset() {
        yValues.clear();
        diffValues.clear();
        diffComputed = false;
        stop();
    }

    public void sendStartCommand() {
        reader.sendStartCommand();
        if (!stopWatch.isRunning()) {
            stopWatch.setPreTime(1);
            stopWatch.start();
            thresholdVoltage = 0;
            correctedVoltage(thresholdVoltage);
        }
    }

    public void playDiodeCurveSonified() {
        if (diffValues.isEmpty() | audioIsPlaying) {
            return;
        }
        Thread t = new Thread(() -> {
            audioIsPlaying = true;
            try {
                Sonifier.sonify(diffValues);
            } finally {
                audioIsPlaying = false;
            }
        }, "Sonification");
        t.setDaemon(true);
        t.start();
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

        g.setColor(color);
        g.setStroke(Stroke.BEVEL_MEDIUM);
        synchronized (yValues) {
            if (yValues.size() <= 1) {
                return;
            }
        }
        prevY = centerY - getScaledY(yValues.get(0));
        for (int i = 1; i < yValues.size(); i++) {
            int currX = centerX + getScaledX(i);
            int currY = centerY - getScaledY(yValues.get(i));

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
            drawIdealGraph(g);
            if (!diffComputed) {
                computeDifferenceCurve();
            }
            drawDifferenceCurve(g);
        }
    }

    private void updateColorBasedOnSlope(Graphics2D g, int prevY, int currY) {
        if (prevY > currY) {
            g.setColor(Colors.BLUE);
        } else {
            g.setColor(color);
        }
    }

    private void adjustXDelta() {
        PlotApplication.X_DELTA -= (PlotApplication.X_SCALE / xScale) * (xScale * xScale * 1.0 / 3.0);
    }

    private void computeDifferenceCurve() {
        double[] params = computeIdealExponentialParams();
        double a = params[0];
        double b = params[1];

        if (a == 0 && b == 0) {
            return;
        }
        diffValues.clear();
        for (int i = 0; i < yValues.size(); i++) {
            double yIdeal = a * Math.exp(-b * i);
            double yMeasured = yValues.get(i);

            diffValues.add(yIdeal - yMeasured);
        }
        diffComputed = true;
    }

    private double[] computeIdealExponentialParams() {
        if (yValues.size() < 2) {
            return new double[]{0, 0};
        }
        int startY = getMaxValue(yValues);
        double b = -Math.log(TARGET_FRACTION) / (yValues.size() - 1);

        return new double[]{startY, b};
    }

    private void drawIdealGraph(Graphics2D g) {
        if (yValues.size() < 2) {
            return;
        }
        int startY = getMaxValue(yValues);
        double b = -Math.log(TARGET_FRACTION) / (yValues.size() - 1);
        List<Double> idealY = new ArrayList<>(yValues.size());

        for (int i = 0; i < yValues.size(); i++) {
            idealY.add(startY * Math.exp(-b * i));
        }
        drawCurve(g, Colors.RED, idealY);
    }

    private void drawDifferenceCurve(Graphics2D g) {
        int maxIndex = IntStream.range(0, diffValues.size()).reduce((i, j)
                -> diffValues.get(i) > diffValues.get(j) ? i : j).orElse(0);
        double maxDiff = yValues.get(maxIndex);
        thresholdVoltage = maxDiff / 1000;
        int x = centerX + getScaledX(maxIndex);
        int y = centerY - getScaledY(maxDiff);

        drawCurve(g, Colors.BLUE, diffValues);
        g.setColor(Colors.BLUE);
        g.fillOval(x - 3, y - 3, 6, 6);
        g.setColor(Colors.BLACK);
        g.drawString("Schwellspannung", x + 5, y - 2);
    }

    private void drawCurve(Graphics2D g, Color color, List<Double> yData) {
        if (yData.isEmpty()) {
            return;
        }
        int prevX = centerX;
        int prevY = centerY - getScaledY(yData.get(0));

        g.setColor(color);
        g.setStroke(Stroke.BEVEL_MEDIUM);
        for (int i = 1; i < yData.size(); i++) {
            int currX = centerX + getScaledX(i);
            int currY = centerY - getScaledY(yData.get(i));

            g.drawLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }
    }

    private int getScaledX(int value) {
        return getScaledX((double) value);
    }

    private int getScaledX(double value) {
        return (int) (value * xScale * PlotApplication.X_SCALE);
    }

    private int getScaledY(int value) {
        return getScaledY((double) value);
    }

    private int getScaledY(double value) {
        return (int) (value * yScale * PlotApplication.Y_SCALE);
    }

    private int getMaxValue(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).max().orElse(0);
    }
}
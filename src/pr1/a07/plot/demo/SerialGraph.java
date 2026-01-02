package pr1.a07.plot.demo;

import treptowkolleg.plot.Colors;
import treptowkolleg.plot.PlotGraph;
import treptowkolleg.plot.SerialReader;
import treptowkolleg.plot.Sonifier;
import treptowkolleg.plot.Stroke;
import pr1.helper.core.StopWatch;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import static pr1.a07.plot.demo.DiodeType.classifyDiode;

public class SerialGraph extends PlotGraph<SerialGraph> {
    private static final double TARGET_FRACTION = 0.002;
    private final ArrayList<Integer> recordedValues = new ArrayList<>();
    private final ArrayList<Double> diffValues = new ArrayList<>();
    private final SerialReader reader = new SerialReader();
    private final StopWatch stopWatch = new StopWatch();
    private boolean diffComputed = false;
    private double thresholdVoltage = 0;
    private String usedDiode = "-";
    private boolean audioIsPlaying = false;
    private boolean isAutoStop = true;
    private boolean idealLineIsVisible = false;
    private boolean diodeLineIsVisible = false;

    public SerialGraph(Color color, String title) {
        this(color, title, 10.0, 0.1);
    }

    public SerialGraph(Color color, String title, double xScale,
                       double yScale) {
        this.color = color;
        this.title = title;
        this.scaleX = xScale;
        this.scaleY = yScale;
        reader.startReading(recordedValues);
    }

    public boolean serialIsAvailable() {
        return reader.isAvailable();
    }

    public String getThresholdVoltageFormatted() {
        setDiode(thresholdVoltage);
        return String.format("%.2f V", thresholdVoltage);
    }

    public String getUsedDiode() {
        return usedDiode;
    }

    public void reset() {
        synchronized (recordedValues) {
            recordedValues.clear();
        }
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
            setDiode(thresholdVoltage);
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

    public void saveDiodeCurveSonified() {
        if (diffValues.isEmpty()) {
            return;
        }
        Thread t = new Thread(() -> {
            Sonifier.sonifyAndSave(diffValues, usedDiode);
        }, "SonificationFile");
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

    public boolean isAutoStop() {
        return isAutoStop;
    }

    public void setAutoStop(boolean autoStop) {
        isAutoStop = autoStop;
    }

    public boolean isIdealLineIsVisible() {
        return idealLineIsVisible;
    }

    public void setIdealLineIsVisible(boolean idealLineIsVisible) {
        this.idealLineIsVisible = idealLineIsVisible;
    }

    public boolean isDiodeLineIsVisible() {
        return diodeLineIsVisible;
    }

    public void setDiodeLineIsVisible(boolean diodeLineIsVisible) {
        this.diodeLineIsVisible = diodeLineIsVisible;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        List<Integer> localValues;
        int prevX = centerX;
        int prevY;

        synchronized (recordedValues) {
            if (recordedValues.size() < 2) {
                return;
            }
            localValues = new ArrayList<>(recordedValues);
        }
        prevY = getScaledY(localValues.get(0));
        g.setColor(color);
        g.setStroke(Stroke.BEVEL_MEDIUM);
        for (int i = 1; i < localValues.size(); i++) {
            int currX = getScaledX(i);
            int currY = getScaledY(localValues.get(i));

            updateColorBasedOnSlope(g, prevY, currY);
            if (prevY < centerY && currY == centerY) {
                if (reader.isRunning() && isAutoStop) {
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
            drawIdealGraph(g, localValues);
            if (!diffComputed) {
                computeDifferenceCurve(localValues);
            }
            drawDifferenceCurve(g, localValues);
        }
    }

    private void setDiode(double measuredU) {
        usedDiode = switch (classifyDiode(measuredU)) {
            case NONE -> "-";
            case GERMANIUM -> "Germanium-Diode";
            case GERMANIUM_OR_SILICON -> "Germanium- oder Silizium-Diode";
            case SILICON -> "Silizium-Diode";
            case RED_LED -> "rote LED";
            case YELLOW_LED -> "gelbe LED";
            case YELLOW_OR_GREEN_LED -> "gelbe oder grüne LED";
            case GREEN_LED -> "grüne LED";
            case BLUE_LED -> "blaue LED";
            case BLUE_OR_WHITE_LED -> "blaue oder weiße LED";
            case WHITE_LED -> "weiße LED";
        };
    }

    private void updateColorBasedOnSlope(Graphics2D g, int prevY, int currY) {
        if (prevY > currY) {
            g.setColor(Colors.BLUE);
        } else {
            g.setColor(color);
        }
    }

    private void computeDifferenceCurve(List<Integer> values) {
        double[] params = computeIdealExponentialParams(values);
        double a = params[0];
        double b = params[1];

        if (a == 0 && b == 0) {
            return;
        }
        diffValues.clear();
        for (int i = 0; i < values.size(); i++) {
            double yIdeal = a * Math.exp(-b * i);
            double yMeasured = values.get(i);

            diffValues.add(yIdeal - yMeasured);
        }
        diffComputed = true;
    }

    private double[] computeIdealExponentialParams(List<Integer> values) {
        if (values.size() < 2) {
            return new double[]{0, 0};
        }
        int startY = getMaxValue(values);
        double b = -Math.log(TARGET_FRACTION) / (values.size() - 1);

        return new double[]{startY, b};
    }

    private void drawIdealGraph(Graphics2D g, List<Integer> values) {
        if (values.size() < 2) {
            return;
        }
        int startY = getMaxValue(values);
        double b = -Math.log(TARGET_FRACTION) / (values.size() - 1);
        List<Double> idealY = new ArrayList<>(values.size());

        for (int i = 0; i < values.size(); i++) {
            idealY.add(startY * Math.exp(-b * i));
        }
        if (idealLineIsVisible) {
            drawCurve(g, Colors.RED, idealY);
        }
    }

    private void drawDifferenceCurve(Graphics2D g, List<Integer> values) {
        int px = indexOfMax(diffValues);
        double py = values.get(px);
        int x = getScaledX(px);
        int y = getScaledY(py);
        thresholdVoltage = py / 1000;

        if (diodeLineIsVisible) {
            drawCurve(g, Colors.BLUE, diffValues);
        }
        g.setColor(Colors.BLUE);
        g.drawOval(x - 3, y - 3, 6, 6);
        g.setColor(Colors.BLACK);
        g.drawString("Schwellenspannung", x + 5, y - 2);
    }

    private void drawCurve(Graphics2D g, Color color, List<Double> yData) {
        if (yData.isEmpty()) {
            return;
        }
        int prevX = centerX;
        int prevY = getScaledY(yData.get(0));

        g.setColor(color);
        g.setStroke(Stroke.BEVEL_MEDIUM);
        for (int i = 1; i < yData.size(); i++) {
            int currX = getScaledX(i);
            int currY = getScaledY(yData.get(i));

            g.drawLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }
    }
}
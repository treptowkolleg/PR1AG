package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TrigonometrieGraph extends PlotGraph<TrigonometrieGraph> {
    private final int scale;
    protected double resolution;
    protected double intervalStart;
    protected double intervalEnd;
    protected double amplitude;
    protected double waveLength;
    protected double dx;
    protected double dy;

    public TrigonometrieGraph(Color color, Double resolution,
                              Integer intervalStart,
                              Integer intervalEnd, Double amplitude,
                              Double width,
                              Double dx, Double dy, Integer scale,
                              String title) {
        this.color = null != color ? color : Colors.RED;
        this.resolution = null != resolution ? resolution : 1;
        this.intervalStart = null != intervalStart ? intervalStart : -9;
        this.intervalEnd = null != intervalEnd ? intervalEnd : 9;
        this.amplitude = null != amplitude ? amplitude : 1.0;
        this.waveLength = null != width ? width : 1.0;
        this.dx = null != dx ? dx : 0;
        this.dy = null != dy ? dy : 0;
        this.scale = null != scale ? scale : 50;
        if (null != title) {
            setTitle(title);
        }
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public double getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(double intervalStart) {
        this.intervalStart = intervalStart;
    }

    public double getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(double intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getWaveLength() {
        return waveLength;
    }

    public void setWaveLength(double waveLength) {
        this.waveLength = waveLength;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        List<Point2D> points = new ArrayList<>();
        double xMin = intervalStart * Math.PI;
        double xMax = intervalEnd * Math.PI;

        for (double x = xMin; x <= xMax; x += resolution) {
            double y =
                    amplitude * Math.sin(this.waveLength * (x - dx * Math.PI)) + dy;
            int px = (int) ((double) centerX + x * scale);
            int py = (int) (centerY - y * scale);

            if (px >= 0 && px <= panelWidth) {
                points.add(new Point2D.Double(px, py));
            }
        }
        g.setPaint(color);
        g.setStroke(new BasicStroke(2.0f));
        for (int i = 1; i < points.size(); i++) {
            Point2D p1 = points.get(i - 1);
            Point2D p2 = points.get(i);
            g.draw(new Line2D.Double(p1, p2));
        }
    }
}

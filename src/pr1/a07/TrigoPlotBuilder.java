package pr1.a07;

import java.awt.Color;

public class TrigoPlotBuilder {
    private Color color;
    private Double resolution;
    private Integer intervalStart;
    private Integer intervalEnd;
    private Double amplitude;
    private Double width;
    private Double dx;
    private Double dy;
    private Double scaleX;
    private Double scaleY;

    public TrigoPlotBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public TrigoPlotBuilder setResolution(Double resolution) {
        this.resolution = resolution;
        return this;
    }

    public TrigoPlotBuilder setIntervalStart(Integer intervalStart) {
        this.intervalStart = intervalStart;
        return this;
    }

    public TrigoPlotBuilder setIntervalEnd(Integer intervalEnd) {
        this.intervalEnd = intervalEnd;
        return this;
    }

    public TrigoPlotBuilder setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
        return this;
    }

    public TrigoPlotBuilder setWidth(Double width) {
        this.width = width;
        return this;
    }

    public TrigoPlotBuilder setDx(Double dx) {
        this.dx = dx;
        return this;
    }

    public TrigoPlotBuilder setDy(Double dy) {
        this.dy = dy;
        return this;
    }

    public TrigoPlotBuilder setScaleX(Double scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public TrigoPlotBuilder setScaleY(Double scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public TrigoPlot createTrigoPlot() {
        return new TrigoPlot(color, resolution, intervalStart, intervalEnd,
                amplitude, width, dx, dy, scaleX, scaleY);
    }
}
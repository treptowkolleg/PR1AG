package pr1.a07.plot.demo;

import java.awt.Color;

public class TrigonometrieGraphBuilder {
    private Color color;
    private Double resolution;
    private Double intervalStart;
    private Double intervalEnd;
    private Double amplitude;
    private Double waveLength;
    private Double dx;
    private Double dy;
    private Integer scale;
    private String title;

    public TrigonometrieGraphBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public TrigonometrieGraphBuilder setResolution(Double resolution) {
        this.resolution = resolution;
        return this;
    }

    public TrigonometrieGraphBuilder setIntervalStart(Double intervalStart) {
        this.intervalStart = intervalStart;
        return this;
    }

    public TrigonometrieGraphBuilder setIntervalEnd(Double intervalEnd) {
        this.intervalEnd = intervalEnd;
        return this;
    }

    public TrigonometrieGraphBuilder setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
        return this;
    }

    public TrigonometrieGraphBuilder setWaveLength(Double waveLength) {
        this.waveLength = waveLength;
        return this;
    }

    public TrigonometrieGraphBuilder setDx(Double dx) {
        this.dx = dx;
        return this;
    }

    public TrigonometrieGraphBuilder setDy(Double dy) {
        this.dy = dy;
        return this;
    }

    public TrigonometrieGraphBuilder setScale(Integer scale) {
        this.scale = scale;
        return this;
    }

    public TrigonometrieGraphBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TrigonometrieGraph build() {
        return new TrigonometrieGraph(color, resolution, intervalStart, intervalEnd, amplitude, waveLength, dx, dy, scale, title);
    }
}
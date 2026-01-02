package pr1.a07.plot.demo;

import treptowkolleg.plot.Colors;

public class GraphFactory {
    private static final int DEFAULT_SCALE = 50;
    private static final double DEFAULT_RESOLUTION = .1;

    public static TrigonometrieGraph sinus() {
        return sinus(DEFAULT_SCALE);
    }

    public static TrigonometrieGraph sinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Sinus-Funktion (rot)")
                .setResolution(DEFAULT_RESOLUTION)
                .setScale(scale)
                .setColor(Colors.RED)
                .build();
    }

    public static TrigonometrieGraph cosinus() {
        return cosinus(DEFAULT_SCALE);
    }

    public static TrigonometrieGraph cosinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Kosinus-Funktion (blau)")
                .setResolution(DEFAULT_RESOLUTION)
                .setScale(scale)
                .setColor(Colors.BLUE)
                .setDx(-.5)
                .build();
    }

    public static TrigonometrieGraph sinusSquared() {
        return sinusSquared(DEFAULT_SCALE);
    }

    public static TrigonometrieGraph sinusSquared(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Sinus-Quadrat (lila)")
                .setResolution(DEFAULT_RESOLUTION)
                .setAmplitude(0.5)
                .setFrequency(2.0)
                .setDy(0.5)
                .setDx(0.25)
                .setScale(scale)
                .setColor(Colors.PURPLE)
                .build();
    }

    public static TrigonometrieGraph sinusMalCosinus() {
        return sinusMalCosinus(DEFAULT_SCALE);
    }

    public static TrigonometrieGraph sinusMalCosinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("sin(x)·cos(x) (orange)")
                .setResolution(DEFAULT_RESOLUTION)
                .setAmplitude(0.5)
                .setFrequency(2.0)
                .setScale(scale)
                .setColor(Colors.ORANGE)
                .build();
    }
}
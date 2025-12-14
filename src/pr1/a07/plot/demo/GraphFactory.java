package pr1.a07.plot.demo;

import pr1.a07.Colors;

public class GraphFactory {

    public static TrigonometrieGraph sinus() {
        return sinus(50);
    }

    public static TrigonometrieGraph sinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Sinus-Funktion (rot)")
                .setResolution(.1)
                .setScale(scale)
                .setColor(Colors.RED)
                .build();
    }

    public static TrigonometrieGraph cosinus() {
        return cosinus(50);
    }

    public static TrigonometrieGraph cosinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Kosinus-Funktion (blau)")
                .setResolution(.1)
                .setScale(scale)
                .setColor(Colors.BLUE)
                .setDx(-.5)
                .build();
    }

    public static TrigonometrieGraph sinusSquared() {
        return sinusSquared(50);
    }

    public static TrigonometrieGraph sinusSquared(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Sinus-Quadrat (lila)")
                .setResolution(.1)
                .setAmplitude(0.5)
                .setFrequency(2.0)
                .setDy(0.5)
                .setDx(0.25)
                .setScale(scale)
                .setColor(Colors.PURPLE)
                .build();
    }

    public static TrigonometrieGraph sinusMalCosinus() {
        return sinusMalCosinus(50);
    }

    public static TrigonometrieGraph sinusMalCosinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("sin(x)·cos(x) (orange)")
                .setResolution(.1)
                .setAmplitude(0.5)
                .setFrequency(2.0)
                .setScale(scale)
                .setColor(Colors.ORANGE)
                .build();
    }
}
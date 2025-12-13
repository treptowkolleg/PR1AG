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
}

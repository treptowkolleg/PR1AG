package pr1.a07;

import java.awt.Color;

public class TrigoFactory {

    public static TrigoPlot createDefaultTrigoPlot() {
        TrigoPlotBuilder builder = new TrigoPlotBuilder();
        return builder
                .setResolution(.1)
                .setColor(Color.RED)
                .createTrigoPlot();
    }
}

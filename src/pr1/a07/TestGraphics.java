package pr1.a07;

import pr1.helper.core.GraphicsApplication;

import javax.swing.SwingUtilities;
import java.awt.Color;

public class TestGraphics {
    public static double scaleX = 50;
    public static double scaleY = 50;

    public static void main(String[] args) {
        GraphicsApplication g = new GraphicsApplication(1440, 920);
        TrigoPlotBuilder builder = new TrigoPlotBuilder();
        TrigoPlot plot = builder
                .setResolution(.1)
                .setIntervalStart(-2)
                .setIntervalEnd(4)
                .setScaleX(scaleX)
                .setScaleY(scaleY)
                .setWidth(2.0)
                .setDx(0.0)
                .setDy(0.5)
                .createTrigoPlot();

        g.setTitle("Übungsaufgabe A07 + Zusatz");
        g.add(new Gitter());
        g.add(new Muster(Color.RED));
        g.add(new TrigoGrid(scaleX, scaleY));
        g.add(plot);
        g.showDrawing();

        SwingUtilities.invokeLater(() -> new TrigoControlPanel(plot, g).setVisible(true));
    }
}

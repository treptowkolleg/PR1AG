package pr1.a07;

import pr1.helper.core.Drawable;
import pr1.helper.core.GraphicsApplication;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.List;

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
                .setColor(Color.BLUE)
                .setScaleX(scaleX)
                .setScaleY(scaleY)
                .setWidth(.5)
                .setDx(1.0)
                .setDy(1.5)
                .createTrigoPlot();
        List<Drawable> plotSet1 = List.of(new Gitter(), new Muster(Color.RED));
        List<Drawable> plotSet2 = List.of(new TrigoGrid(scaleX, scaleY), plot);

        g.setTitle("Übungsaufgabe A07 + Zusatz");
        g.addPlotList(plotSet1);
        g.addPlotList(plotSet2);
        g.showDrawing();
        SwingUtilities.invokeLater(() -> new TrigoControlPanel(plot, g));
    }
}

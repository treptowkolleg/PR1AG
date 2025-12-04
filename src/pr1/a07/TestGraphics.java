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
                .setColor(Color.BLUE)
                .setScaleX(scaleX)
                .setScaleY(scaleY)
                .setWidth(1.0)
                .setDx(1.0)
                .setDy(1.5)
                .createTrigoPlot();
        TrigoPlot plot2 = TrigoFactory.createDefaultTrigoPlot();
        plot2.setScaleX(scaleX);
        plot2.setScaleY(scaleY);

        g.setTitle("Übungsaufgabe A07 + Zusatz");
        //g.add(new Gitter());
        //g.add(new Muster(Color.RED));
        g.add(new TrigoGrid(scaleX, scaleY));
        g.add(plot);
        g.add(plot2);
        g.showDrawing();

        //SwingUtilities.invokeLater(() -> new TrigoControlPanel(plot, g).setVisible(true));
    }
}

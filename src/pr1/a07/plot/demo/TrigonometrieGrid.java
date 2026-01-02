package pr1.a07.plot.demo;

import treptowkolleg.plot.PlotApplication;
import treptowkolleg.plot.PlotGrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class TrigonometrieGrid extends PlotGrid {
    protected int scale;

    public TrigonometrieGrid() {
        this(50);
    }

    public TrigonometrieGrid(int scale) {
        this.scale = scale;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        int kMin;
        int kMax;
        double yPlusOne = centerY - scale * PlotApplication.Y_SCALE;
        double yMinusOne = centerY + scale * PlotApplication.Y_SCALE;
        double pixelsPerPi = Math.PI * scale * PlotApplication.X_SCALE;

        if (pixelsPerPi <= 0) {
            return;
        }
        g.setPaint(Color.GRAY);
        g.setStroke(new BasicStroke(1.0f));
        g.draw(new Line2D.Double(0, centerY, panelWidth, centerY));
        g.draw(new Line2D.Double(centerX, 0, centerX, panelHeight));
        g.setStroke(new BasicStroke(
                1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{5.0f, 5.0f}, 0.0f
        ));
        g.draw(new Line2D.Double(0, yPlusOne, panelWidth, yPlusOne));
        g.draw(new Line2D.Double(0, yMinusOne, panelWidth, yMinusOne));
        kMin = (int) Math.floor((-centerX) / pixelsPerPi) - 1;
        kMax = (int) Math.ceil((panelWidth - centerX) / pixelsPerPi) + 1;
        g.setStroke(new BasicStroke(1.0f));
        g.setPaint(Color.GRAY);
        for (int k = kMin; k <= kMax; k++) {
            double xPixel =
                    centerX + k * Math.PI * scale * PlotApplication.X_SCALE;
            if (xPixel < 0 || xPixel > panelWidth) {
                continue;
            }
            String label = switch (k) {
                case 0 -> "0";
                case 1 -> "π";
                case -1 -> "-π";
                default -> k + "π";
            };
            g.draw(new Line2D.Double(xPixel, centerY, xPixel, centerY + 10));
            g.setPaint(Color.DARK_GRAY);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            int textX = (int) xPixel + 5;
            g.drawString(label, textX, centerY + 10);
            g.setPaint(Color.GRAY);
        }
        g.setPaint(Color.DARK_GRAY);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.drawString("+2", centerX + 5, (int) (yPlusOne - scale * PlotApplication.Y_SCALE - 2));
        g.drawString("+1", centerX + 5, (int) (yPlusOne - 2));
        g.drawString("-1", centerX + 5, (int) (yMinusOne + 12));
        g.drawString("-2", centerX + 5, (int) (yMinusOne + scale * PlotApplication.Y_SCALE + 12));
    }
}

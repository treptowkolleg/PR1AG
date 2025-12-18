package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotGrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class MathGrid extends PlotGrid {
    protected int scale;
    protected double xFactor;

    public MathGrid() {
        this(50);
    }

    public MathGrid(int scale) {
        this.scale = scale;
        xFactor = (double) scale / 100;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        int kMin;
        int kMax;
        double yPlusOne = centerY - scale * PlotApplication.Y_SCALE;
        double yMinusOne = centerY + scale * PlotApplication.Y_SCALE;
        double pixelsPerPi = xFactor * scale * PlotApplication.X_SCALE;

        if (pixelsPerPi <= 0) {
            return;
        }
        kMin = (int) Math.floor((-centerX) / pixelsPerPi) - 1;
        kMax = (int) Math.ceil((panelWidth - centerX) / pixelsPerPi) + 1;
        g.setStroke(new BasicStroke(1.0f));
        for (int k = kMin; k <= kMax; k++) {
            double xPixel = centerX + k * xFactor * scale * PlotApplication.X_SCALE;

            if (xPixel < 0 || xPixel > panelWidth) {
                continue;
            }
            g.setPaint(Colors.GRAY3);
            g.draw(new Line2D.Double(xPixel, 0, xPixel, panelHeight));
            g.setPaint(Colors.GRAY);
            g.draw(new Line2D.Double(xPixel, centerY, xPixel, centerY + 10));
            g.setPaint(Color.DARK_GRAY);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            int textX = (int) xPixel + 5;
            g.drawString(String.valueOf(k), textX, centerY + 10);
            g.setPaint(Color.GRAY);
        }
        g.setPaint(Color.DARK_GRAY);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.setStroke(new BasicStroke(.5f));
        g.setPaint(Colors.GRAY3);
        for (int dy = 1; dy <= panelHeight; dy++) {
            double yPositiv = (centerY - dy * (scale * PlotApplication.Y_SCALE));
            double yNegativ = (centerY + dy * (scale * PlotApplication.Y_SCALE));
            g.draw(new Line2D.Double(0, yPositiv, panelWidth, yPositiv));
            g.draw(new Line2D.Double(0, yNegativ, panelWidth, yNegativ));
        }
        g.setPaint(Colors.GRAY);
        g.setStroke(new BasicStroke(1.0f));
        g.draw(new Line2D.Double(0, centerY, panelWidth, centerY));
        g.draw(new Line2D.Double(centerX, 0, centerX, panelHeight));
        g.setPaint(Colors.BLACK);
        g.drawString("+1", centerX + 5, (int) (yPlusOne - 2));
        g.drawString("-1", centerX + 5, (int) (yMinusOne + 12));
    }
}

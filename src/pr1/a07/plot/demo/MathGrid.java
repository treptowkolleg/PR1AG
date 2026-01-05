package pr1.a07.plot.demo;

import treptowkolleg.edu.swing.graphics.Colors;
import treptowkolleg.edu.swing.plot.PlotApplication;
import treptowkolleg.edu.swing.plot.PlotGrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class MathGrid extends PlotGrid {
    protected int scale;
    protected double xFactor;
    protected String xAxisLabel;
    protected String yAxisLabel;

    public MathGrid(String xAxisLabel, String yAxisLabel) {
        this(100, xAxisLabel, yAxisLabel);
    }

    public MathGrid(int scale, String xAxisLabel, String yAxisLabel) {
        this.scale = scale;
        xFactor = (double) scale / 100;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        AffineTransform transform = new AffineTransform();
        int kMin;
        int kMax;
        double pixelsPerPi = xFactor * scale * PlotApplication.X_SCALE;
        FontMetrics fm = g.getFontMetrics();
        int textLengthY = fm.stringWidth(String.valueOf(yAxisLabel));

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
        for (int dy = 1; dy <= panelHeight; dy++) {
            double yPositiv = (centerY - dy * (scale * PlotApplication.Y_SCALE));
            double yNegativ = (centerY + dy * (scale * PlotApplication.Y_SCALE));

            g.setPaint(Colors.GRAY3);
            g.draw(new Line2D.Double(0, yPositiv, panelWidth, yPositiv));
            g.draw(new Line2D.Double(0, yNegativ, panelWidth, yNegativ));
            g.setPaint(Colors.BLACK);

            int textWidth = fm.stringWidth(String.valueOf(dy));
            g.drawString(String.valueOf(dy), centerX  - 10 - textWidth, (int) (yPositiv + 4));
            textWidth = fm.stringWidth("-" + dy);
            g.drawString("-" + dy, centerX  - 10 - textWidth, (int) (yNegativ + 4));
        }
        g.setPaint(Colors.GRAY);
        g.setStroke(new BasicStroke(1.0f));
        g.draw(new Line2D.Double(0, centerY, panelWidth, centerY));
        g.draw(new Line2D.Double(centerX, 0, centerX, panelHeight));
        g.setPaint(Colors.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        transform.rotate(Math.toRadians(-90), centerX - 30, 10 + textLengthY);
        g.drawString(xAxisLabel, 10, centerY - 10);
        g.setTransform(transform);
        g.drawString(yAxisLabel, centerX - 30, 10 + textLengthY);
        resetTransform();
    }
}

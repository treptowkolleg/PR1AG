package pr1.a07;

import pr1.helper.core.ControlPanel;
import pr1.helper.core.Drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

public class TrigoGrid implements Drawable {
    private final double scaleX;
    private final double scaleY;

    public TrigoGrid() {
        this(50.0, 50.0);
    }

    public TrigoGrid(double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int kMin;
        int kMax;
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;
        int centerY = height / 2;
        int centerX = width / 2;
        double yPlusOne = centerY - scaleY;
        double yMinusOne = centerY + scaleY;
        double pixelsPerPi = Math.PI * scaleX;

        if (pixelsPerPi <= 0) {
            return;
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(Color.GRAY);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(new Line2D.Double(0, centerY, width, centerY));
        g2d.draw(new Line2D.Double(centerX, 0, centerX, height));
        g2d.setStroke(new BasicStroke(
                1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{5.0f, 5.0f}, 0.0f
        ));
        g2d.draw(new Line2D.Double(0, yPlusOne, width, yPlusOne));
        g2d.draw(new Line2D.Double(0, yMinusOne, width, yMinusOne));
        kMin = (int) Math.floor((-centerX) / pixelsPerPi) - 1;
        kMax = (int) Math.ceil((width - centerX) / pixelsPerPi) + 1;
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.setPaint(Color.GRAY);
        for (int k = kMin; k <= kMax; k++) {
            double xPixel = centerX + k * Math.PI * scaleX;
            if (xPixel < 0 || xPixel > width) {
                continue;
            }
            String label = switch (k) {
                case 0 -> "0";
                case 1 -> "π";
                case -1 -> "-π";
                default -> k + "π";
            };
            g2d.draw(new Line2D.Double(xPixel, centerY, xPixel, centerY + 10));
            g2d.setPaint(Color.DARK_GRAY);
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            int textX = (int) xPixel + 5;
            g2d.drawString(label, textX, centerY + 10);
            g2d.setPaint(Color.GRAY);
        }
        g2d.setPaint(Color.DARK_GRAY);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g2d.drawString("+1", centerX + 5, (int) (yPlusOne - 2));
        g2d.drawString("-1", centerX + 5, (int) (yMinusOne + 12));
    }

    @Override
    public void setControlPanel(ControlPanel controlPanel) {
    }

    @Override
    public void showControlPanel() {
    }

    @Override
    public void hideControlPanel() {
    }
}
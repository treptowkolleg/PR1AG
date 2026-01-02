package treptowkolleg.plot.components;

import javax.swing.border.AbstractBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

public class RoundedLineBorder extends AbstractBorder {
    private final Color color;
    private final double thickness;
    private final int arcWidth;
    private final int arcHeight;

    public RoundedLineBorder(Color color, double thickness, int arc) {
        this(color, thickness, arc, arc);
    }

    public RoundedLineBorder(Color color, double thickness, int arcWidth, int arcHeight) {
        this.color = color;
        this.thickness = thickness;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke((float) thickness));
        g2d.drawRoundRect(x, y, width - 1, height - 1, arcWidth, arcHeight);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        int inset = (int) (thickness + (int) Math.ceil(arcHeight / 20.0));
        return new Insets(inset, inset, inset, inset);
    }
}

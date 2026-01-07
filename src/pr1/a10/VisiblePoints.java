package pr1.a10;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.Set;

public class VisiblePoints implements Drawable {
    private final Color color;
    private final Set<Ellipse2D.Double> points;
    private final String label;

    public VisiblePoints(Color color, Set<Ellipse2D.Double> points) {
        this(color, points, null);
    }

    public VisiblePoints(Color color, Set<Ellipse2D.Double> points,
                         String label) {
        this.color = color;
        this.points = new HashSet<>(points);
        this.label = label;
    }

    @Override
    public void draw(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        points.forEach(g2d::fill);
        drawLabel(g2d);
        g2d.dispose();
    }

    private void drawLabel(Graphics2D g2d) {
        if (null == label) {
            return;
        }
        int panelHeight = g2d.getClipBounds().height;
        int textLength = g2d.getFontMetrics().stringWidth(label);

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, panelHeight - 47, textLength + 40, 32);
        g2d.setColor(Color.WHITE);
        g2d.drawString(label, 25, panelHeight - 25);
        g2d.setColor(color);
    }
}

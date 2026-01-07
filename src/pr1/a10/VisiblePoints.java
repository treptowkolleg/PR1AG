package pr1.a10;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Set;

public class VisiblePoints implements Drawable {
    private final Color color;
    private final Set<Ellipse2D.Double> points;
    private final String label;

    public VisiblePoints(Color color, Set<Ellipse2D.Double> points) {
        this(color, points, "unbenannt");
    }

    public VisiblePoints(Color color, Set<Ellipse2D.Double> points,
                         String label) {
        this.color = color;
        this.points = points;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public void draw(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        int panelHeight = g2.getClipBounds().height;
        int textLength = g2.getFontMetrics().stringWidth(String.valueOf(label));

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        points.forEach(g2::fill);

        g2.setColor(Color.GRAY);
        g2.fillRect(0, panelHeight - 42, textLength + 40, 25);
        g2.setColor(Color.WHITE);
        g2.drawString(label, 25, panelHeight - 25);
        g2.dispose();
    }
}

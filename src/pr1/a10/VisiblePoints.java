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

    public VisiblePoints(Color color, Set<Ellipse2D.Double> points) {
        this.color = color;
        this.points = points;
    }

    @Override
    public void draw(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        points.forEach(g2d::fill);
        g2d.dispose();
    }
}

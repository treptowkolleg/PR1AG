package pr1.a07;

import pr1.helper.core.Drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

public class Gitter extends CustomShape implements Drawable {

    public Gitter() {
        this(Color.LIGHT_GRAY, 25);
    }

    public Gitter(Color color, int dist) {
        super(color, dist);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        int centerX = width / 2;
        int centerY = height / 2;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        for (int x = centerX; x >= 0; x -= dist) {
            g.drawLine(x, 0, x, height);
        }
        for (int x = centerX + dist; x < width; x += dist) {
            g.drawLine(x, 0, x, height);
        }
        for (int y = centerY; y >= 0; y -= dist) {
            g.drawLine(0, y, width, y);
        }
        for (int y = centerY + dist; y < height; y += dist) {
            g.drawLine(0, y, width, y);
        }

        g2d.setPaint(Color.GRAY);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(new Line2D.Double(0, centerY, width, centerY));
        g2d.draw(new Line2D.Double(centerX, 0, centerX, height));
        g2d.setPaint(Color.DARK_GRAY);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g2d.drawString("0", centerX + 5, centerY + 10);

    }


}

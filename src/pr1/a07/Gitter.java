package pr1.a07;

import pr1.helper.core.Drawable;

import java.awt.Color;
import java.awt.Graphics;

public class Gitter extends CustomShape implements Drawable {

    public Gitter() {
        this(Color.LIGHT_GRAY, 25);
    }

    public Gitter(Color color, int dist) {
        super(color, dist);
    }

    @Override
    public void draw(Graphics g) {
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        int centerX = width / 2;
        int centerY = height / 2;

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
    }
}

// pr1.a07.Muster.java
package pr1.a07;

import pr1.helper.core.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Muster extends CustomShape implements Drawable {
    private final double xMin;
    private final double xMax;
    private final double step;
    private final List<Rectangle> rects;

    public Muster(Color color,
                  double xMin, double xMax, double step) {
        super(color, 0);
        this.xMin = xMin;
        this.xMax = xMax;
        this.step = step;
        this.rects = new ArrayList<>();
    }

    public Muster(Color color) {
        this(color, 0, 300, 50);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = g2d.getClipBounds().width;
        int panelHeight = g2d.getClipBounds().height;
        int centerX = panelWidth / 2;
        int centerY = panelHeight / 2;

        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        rects.clear();
        for (double x = xMin; x <= xMax; x += step) {
            double b = CMath.b(x);
            double y = CMath.y(x);
            int rectWidth = (int) Math.abs(b);
            int rectHeight = (int) Math.abs(b);
            int px = (int) (centerX + x);
            int py = (int) (centerY + y);

            if (rectWidth > 0 && rectHeight > 0) {
                Rectangle rect = new Rectangle(px, py, rectWidth, rectHeight);
                rects.add(rect);
            }
        }
        rects.forEach(g2d::fill);
    }
}
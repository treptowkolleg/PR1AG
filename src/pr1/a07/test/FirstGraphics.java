package pr1.a07.test;

import pr1.helper.core.Drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class FirstGraphics implements Drawable {

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Hintergrund
        g2d.setPaint(new GradientPaint(0, 0, Color.DARK_GRAY, 0, 600, Color.BLACK));
        g2d.fill(new Rectangle2D.Double(0, 0, 800, 600));

        // 1. Blaue diagonale Linie
        g2d.setPaint(Color.BLUE);
        g2d.draw(new Line2D.Double(50, 50, 750, 550));

        // 2. Rotes Rechteck
        g2d.setPaint(Color.RED);
        g2d.fill(new Rectangle2D.Double(100, 100, 120, 80));

        // 3. Grüner Kreis
        g2d.setPaint(Color.GREEN.darker());
        g2d.fill(new Ellipse2D.Double(300, 150, 100, 100));

        // 4. Lila abgerundetes Rechteck
        g2d.setPaint(new Color(180, 0, 200)); // Violett
        g2d.fill(new RoundRectangle2D.Double(500, 120, 110, 90, 20, 20));

        // 5. Oranger Kreisbogen (Pie-Arc)
        g2d.setPaint(new GradientPaint(0, 0, Color.YELLOW, 400, 600, Color.RED));
        g2d.fill(new Arc2D.Double(200, 300, 90, 90, 45, 270, Arc2D.PIE));

        // 6. Cyan gestrichelte Linie
        float[] dash = {10.0f, 5.0f};
        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND, 10.0f, dash, 0));
        g2d.setPaint(Color.CYAN);
        g2d.draw(new Line2D.Double(50, 500, 750, 500));
        // optional Stroke zurücksetzen: g2d.setStroke(new BasicStroke());

        // 7. Gelbes Dreieck (via Path2D)
        Path2D triangle = new Path2D.Double();
        triangle.moveTo(600, 400);
        triangle.lineTo(650, 300);
        triangle.lineTo(700, 400);
        triangle.closePath();
        g2d.setPaint(Color.YELLOW);
        g2d.fill(triangle);
    }
}

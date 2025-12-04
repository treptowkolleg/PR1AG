package pr1.a07;

import pr1.helper.core.Drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class TrigoPlot implements Drawable {
    protected Color color;
    protected double resolution;
    protected int intervalStart;
    protected int intervalEnd;
    protected double amplitude;
    protected double width;
    protected double dx;
    protected double dy;
    protected double scaleX;
    protected double scaleY;

    public TrigoPlot(Color color, Double resolution, Integer intervalStart,
                     Integer intervalEnd, Double amplitude, Double width,
                     Double dx, Double dy, Double scaleX, Double scaleY) {
        this.color = null != color ? color : Color.RED;
        this.resolution = null != resolution ? resolution : 1;
        this.intervalStart = null != intervalStart ? intervalStart : -4;
        this.intervalEnd = null != intervalEnd ? intervalEnd : 4;
        this.amplitude = null != amplitude ? amplitude : 1.0;
        this.width = null != width ? width : 1.0;
        this.dx = null != dx ? dx : 0;
        this.dy = null != dy ? dy : 0;
        this.scaleX = null != scaleX ? scaleX : 50;
        this.scaleY = null != scaleY ? scaleY : 50;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;
        int centerY = height / 2;
        List<Point2D> points = new ArrayList<>();
        double xMin = intervalStart * Math.PI;
        double xMax = intervalEnd * Math.PI;

        for (double x = xMin; x <= xMax; x += resolution) {
            double y = amplitude * Math.sin(this.width  * (x - dx * Math.PI)) + dy;
            int px = (int) ((double) width / 2 + x * scaleX);
            int py = (int) (centerY - y * scaleY);

            if (px >= 0 && px <= width) {
                points.add(new Point2D.Double(px, py));
            }
        }
        g2d.setPaint(Color.BLUE);
        g2d.setStroke(new BasicStroke(2.0f));
        for (int i = 1; i < points.size(); i++) {
            Point2D p1 = points.get(i - 1);
            Point2D p2 = points.get(i);
            g2d.draw(new Line2D.Double(p1, p2));
        }
    }

    public void update(double resolution, int intervalStart, int intervalEnd,
                       double amplitude, double width, double dx, double dy) {
        this.resolution = resolution;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.amplitude = amplitude;
        this.width = width;
        this.dx = dx;
        this.dy = dy;
    }
}

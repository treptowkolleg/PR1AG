package pr1.a07.plot.demo;

import pr1.a07.CMath;
import treptowkolleg.edu.swing.plot.PlotGraph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class MusterGraph extends PlotGraph<MusterGraph> {
    private double xMin;
    private double xMax;
    private final double step;
    private final List<Rectangle> rects;

    public MusterGraph(Color color, double xMin, double xMax, double step) {
        this.color = color;
        this.xMin = xMin;
        this.xMax = xMax;
        this.step = step;
        this.rects = new ArrayList<>();
    }

    public double getxMin() {
        return xMin;
    }

    public void setxMin(double xMin) {
        this.xMin = xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public void setxMax(double xMax) {
        this.xMax = xMax;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        g.setColor(color);
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
        rects.forEach(g::fill);
    }
}

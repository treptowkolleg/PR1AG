package pr1.a07.plot.demo;

import pr1.a07.CMath;
import treptowkolleg.edu.swing.graphics.Colors;
import treptowkolleg.edu.swing.plot.Context;
import treptowkolleg.edu.swing.plot.Draw;
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
    protected void configureData() {
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
    }

    @Draw(when = Context.IS_DEV)
    private void drawTest(Graphics2D g) {
        g.setColor(Colors.BROWN);
        g.drawRect(100, 100, panelWidth - 200, panelHeight - 200);
    }

    @Draw(order = 1)
    private void rectangles(Graphics2D g) {
        g.setColor(color);
        rects.forEach(g::fill);
    }
}

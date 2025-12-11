package pr1.a07.plot.demo;

import pr1.a07.plot.PlotGraph;

import java.awt.Color;
import java.awt.Graphics2D;

public class TestGraph extends PlotGraph<TestGraph> {
    private int x = 0;
    private int y = 0;

    public TestGraph() {
        this(null);
    }

    public TestGraph(String title) {
        this(title, Color.BLACK);
    }

    public TestGraph(String title, Color color) {
        this.title = title;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        g.setColor(color);
        g.drawRect(centerX - 50 + x, centerY - 50 + y, 100, 100);
    }
}

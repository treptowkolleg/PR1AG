package pr1.training;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Ringe implements Drawable {
    private final double r;
    private final double k;
    private ArrayList<Shape> shapes;

    public Ringe(double r, double k) {
        this.r = r;
        this.k = k;
        createShapes();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.BLUE);
        shapes.forEach(g2d::draw);
        g2d.dispose();
    }

    private void createShapes() {
        double x;
        double y;
        double d;

        shapes = new ArrayList<>();
        for (double i = 100; i <= 800; i += 100) {
            x = i - r;
            y = i + 100 / i * k - r;
            d = 2 * r;
            shapes.add(new Ellipse2D.Double(x, y, d, d));
        }
    }
}

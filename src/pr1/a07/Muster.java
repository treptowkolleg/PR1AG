package pr1.a07;

import pr1.helper.core.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Muster extends CustomShape implements Drawable {
    protected List<Rectangle> rectangles;

    public Muster(Color color) {
        super(color, 0);
        this.rectangles = new ArrayList<>();
        int b;

        for (int x = 0; x <= 300; x = x + 50) {
            b = CMath.b(x);
            rectangles.add(new Rectangle(x, CMath.y(x), b, b));
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        rectangles.stream()
                // nur vollständig sichtbare Rechtecke zeichnen.
                .filter(r -> r.getMinX() >= 0 && r.getMinY() >= 0)
                .forEach(g2d::fill);
    }
}

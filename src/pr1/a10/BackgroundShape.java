package pr1.a10;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BackgroundShape implements Drawable {
    private final Color color;

    public BackgroundShape(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics.create();
        Rectangle rectangle = g2d.getClipBounds();

        g2d.setColor(color);
        g2d.fill(rectangle);
        g2d.dispose();
    }
}

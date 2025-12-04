package pr1.a07;

import pr1.helper.core.Drawable;

import java.awt.Color;
import java.awt.Graphics;

public class Gitter extends CustomShape implements Drawable {
    protected static int LOWER_BORDER = -100;
    protected static int UPPER_BORDER = 1000;

    public Gitter(Color color, int dist) {
        super(color, dist);
    }

    public Color getColor() {
        return color;
    }

    public int getDist() {
        return dist;
    }

    @Override
    public void draw(Graphics g) {
        for (int i = LOWER_BORDER; i <= UPPER_BORDER; i = i + dist) {
            g.setColor(color);
            g.drawLine(i, LOWER_BORDER, i, UPPER_BORDER);
            g.drawLine(LOWER_BORDER, i, UPPER_BORDER, i);
        }
    }
}

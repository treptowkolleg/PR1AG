package pr1.a08;

import treptowkolleg.edu.swing.plot.Draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Landschaft extends EinfacheLandschaft {
    protected int hillNumber;
    protected int treeNumber;
    protected Color leavesColor = Colors.DARKER_GREEN;
    protected Color treeColor = Colors.BROWN;
    Random rand = new Random(4071);

    public Landschaft() {
        this(2, 7);
    }

    public Landschaft(int hillNumber, int treeNumber) {
        this(Colors.GREEN, Colors.CYAN, Colors.DARK_GREEN, hillNumber,
                treeNumber);
    }

    public Landschaft(Color foregroundColor, Color backgroundColor) {
        this(foregroundColor, backgroundColor, Colors.DARK_GREEN);
    }

    public Landschaft(Color foregroundColor, Color backgroundColor,
                      Color horizonColor) {
        this(foregroundColor, backgroundColor, horizonColor, 2, 7);
    }

    public Landschaft(Color foregroundColor, Color backgroundColor,
                      Color horizonColor, int hillNumber, int treeNumber) {
        super(foregroundColor, backgroundColor, horizonColor);
        this.hillNumber = hillNumber;
        this.treeNumber = treeNumber;
    }

    @Draw(order = 10)
    protected void drawHills(Graphics2D g) {
        int horizontalPadding = 40;
        int usableWidth = panelWidth - 2 * horizontalPadding;
        int MAX_BOTTOM_Y = centerY + 155;

        if (hillNumber <= 0 || usableWidth <= 0) return;
        g.setColor(horizonColor);
        for (int i = 0; i < hillNumber; i++) {
            int size = 100 + rand.nextInt(151);
            double step = (double) usableWidth / Math.max(1, hillNumber - 1);
            int nominalX = horizontalPadding + (int) (i * step);
            int jitterX = (hillNumber > 1) ? rand.nextInt(40) - 20 : 0;
            int xCenter = Math.max(horizontalPadding + size / 2,
                    Math.min(panelWidth - horizontalPadding - size / 2,
                            nominalX + jitterX));
            int desiredYMin = centerY - 60;
            int desiredYMax = centerY - 5;
            int maxYArcDueToBottom = MAX_BOTTOM_Y - size;
            int yMin = Math.max(desiredYMin, 0);
            int yMax = Math.min(desiredYMax, maxYArcDueToBottom);

            if (yMax < yMin) {
                yMin = Math.max(0, MAX_BOTTOM_Y - size);
                yMax = yMin;
            }
            int yArc = yMin + rand.nextInt(Math.max(1, yMax - yMin + 1));
            Arc2D hill = new Arc2D.Double();

            hill.setArc(xCenter - (double) size / 2, yArc, size, size, 0, 180
                    , Arc2D.PIE);
            g.fill(hill);
        }
    }

    @Draw(order = 20)
    protected void drawTrees(Graphics2D g) {
        final int TREE_WIDTH = 45;
        final int TREE_HEIGHT = 85;
        final int MAX_ATTEMPTS = 50;
        int horizontalPadding = 30;
        int verticalPadding = 10;
        int minX = horizontalPadding;
        int maxX = panelWidth - horizontalPadding;
        int minY = (2 * panelHeight) / 3;
        int maxY = panelHeight - verticalPadding;
        List<Rectangle> placedTrees = new ArrayList<>();
        Path2D trunk = new Path2D.Double();
        Path2D leaves = new Path2D.Double();

        trunk.moveTo(-10, 0);
        trunk.lineTo(10, 0);
        trunk.lineTo(0, -50);
        trunk.closePath();
        leaves.moveTo(-20, -20);
        leaves.lineTo(20, -20);
        leaves.lineTo(0, -80);
        leaves.closePath();
        g.setColor(treeColor);
        for (int i = 0; i < treeNumber; i++) {
            int attempt = 0;
            int x, y;
            Rectangle candidate;

            do {
                x = minX + rand.nextInt(maxX - minX + 1);
                y = minY + rand.nextInt(maxY - minY + 1);
                int bx = x - TREE_WIDTH / 2;
                int by = y - TREE_HEIGHT;
                candidate = new Rectangle(bx, by, TREE_WIDTH, TREE_HEIGHT);
                if (attempt++ > MAX_ATTEMPTS) {
                    break;
                }
            } while (overlapsAny(candidate, placedTrees));
            AffineTransform at = AffineTransform.getTranslateInstance(x, y);

            g.fill(at.createTransformedShape(trunk));
            g.setColor(leavesColor);
            g.fill(at.createTransformedShape(leaves));
            g.setColor(treeColor);
            placedTrees.add(candidate);
        }
    }

    private boolean overlapsAny(Rectangle r, List<Rectangle> others) {
        for (Rectangle other : others) {
            if (r.intersects(other)) {
                return true;
            }
        }
        return false;
    }
}

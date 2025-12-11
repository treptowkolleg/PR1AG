package pr1.a08;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

public class WinterLandschaft extends Landschaft {
    public static int SNOW_FLAKES = 100;

    public WinterLandschaft() {
        this(2, 7);
    }

    public WinterLandschaft(int hillNumber, int treeNumber) {
        super(Colors.GRAY3, Colors.CYAN, Colors.GRAY2, hillNumber, treeNumber);
        leavesColor = Colors.GRAY;
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
        g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        drawBackground(g);
        drawForeground(g);
        drawHorizon(g);
        drawHills(g);
        drawSnowman(g);
        drawTrees(g);
        drawSnow(g);
    }

    protected void drawSnowman(Graphics2D g) {
        g.setColor(Colors.GRAY5);
        Ellipse2D head = new Ellipse2D.Double(100, panelHeight - 130, 40, 40);
        Ellipse2D body = new Ellipse2D.Double(90, panelHeight - 110, 60, 60);
        Ellipse2D feet = new Ellipse2D.Double(80, panelHeight - 80, 80, 80);

        AffineTransform at = AffineTransform.getTranslateInstance(40, 200);
        AffineTransform at2 = AffineTransform.getScaleInstance(.5, .5);

        g.fill(at2.createTransformedShape(at.createTransformedShape(head)));
        g.fill(at2.createTransformedShape(at.createTransformedShape(body)));
        g.fill(at2.createTransformedShape(at.createTransformedShape(feet)));
    }

    protected void drawSnow(Graphics2D g) {
        int flakeCount = Math.max(SNOW_FLAKES, panelWidth / 3);

        g.setColor(Colors.WHITE);
        for (int i = 0; i < flakeCount; i++) {
            int x = rand.nextInt(panelWidth);
            int y = rand.nextInt(panelHeight);
            g.fillRect(x, y, 2, 2);
        }
    }

}

package pr1.a08;

import treptowkolleg.edu.swing.plot.Draw;
import treptowkolleg.edu.swing.plot.PlotGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class EinfacheLandschaft extends PlotGraph<EinfacheLandschaft> {
    protected Color backgroundColor;
    protected Color horizonColor;
    protected Color foregroundColor;
    protected Stroke smStroke = new BasicStroke(1);
    protected Stroke mdStroke = new BasicStroke(2);
    protected Stroke lgStroke = new BasicStroke(4);

    public EinfacheLandschaft() {
        this(Colors.GREEN, Colors.CYAN);
    }

    public EinfacheLandschaft(Color foregroundColor, Color backgroundColor) {
        this(foregroundColor, backgroundColor, Colors.DARK_GREEN);
    }

    public EinfacheLandschaft(Color foregroundColor, Color backgroundColor, Color horizonColor) {
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
        this.horizonColor = horizonColor;
    }

    @Draw(order = 0)
    protected void drawBackground(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, panelWidth, panelHeight);
    }

    @Draw(order = 1)
    protected void drawForeground(Graphics2D g) {
        g.setColor(foregroundColor);
        g.fillRect(0, centerY, panelWidth, panelHeight - centerY);
    }

    @Draw(order = 2)
    protected void drawHorizon(Graphics2D g) {
        g.setColor(horizonColor);
        g.setStroke(mdStroke);
        g.drawLine(0, centerY, panelWidth, centerY);
    }
}

package pr1.a07.plot.components;

import pr1.a07.Colors;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ModernCheckBoxIcon implements Icon {
    private final boolean selected;
    private final int size = 18;
    private final int arc = 4;

    public ModernCheckBoxIcon(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

        // Hintergrund (weiß bei ausgewählt, transparent/leer bei nicht ausgewählt)
        if (selected) {
            g2d.setColor(Colors.WHITE);
            g2d.fillRoundRect(x, y, size, size, arc, arc);
        }

        // Rahmen
        g2d.setColor(Colors.GRAY3);
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.drawRoundRect(x, y, size - 1, size - 1, arc, arc);

        // Häkchen (nur wenn ausgewählt)
        if (selected) {
            g2d.setStroke(new BasicStroke(1.8f));
            g2d.setColor(Colors.BLACK);
            int[] xPoints = {x + 5, x + 8, x + 13};
            int[] yPoints = {y + 9, y + 12, y + 7};
            g2d.drawPolyline(xPoints, yPoints, 3);
        }

        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}

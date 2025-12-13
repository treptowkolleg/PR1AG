package pr1.a07.plot.components;

import pr1.a07.Colors;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

public class ModernButton extends JButton {
    private static final int ARC = 12;
    private Color baseColor = Colors.BLUE;

    public ModernButton(String text) {
        super(text);
        init();
    }

    public ModernButton(String text, Color color) {
        super(text);
        this.baseColor = color;
        init();
    }

    private void init() {
        setFont(new Font("SF Pro Text, Helvetica Neue, Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(4, 16, 4, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Color buttonColor = baseColor;
        if (!isEnabled()) {
            buttonColor = new Color(142, 142, 147);
        } else if (getModel().isPressed()) {
            buttonColor = adjustBrightness(buttonColor, -0.2f);
        } else if (getModel().isRollover()) {
            buttonColor = adjustBrightness(buttonColor, -0.1f);
        }

        RoundRectangle2D shape = new RoundRectangle2D.Float(
                0, 0, getWidth(), getHeight(), ARC, ARC
        );
        g2.setColor(buttonColor);
        g2.fill(shape);
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Kein Standard-Rahmen
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), ARC, ARC);
        return shape.contains(x, y);
    }

    private Color adjustBrightness(Color color, float factor) {
        int r = Math.max(0, Math.min(255, (int) (color.getRed() * (1 + factor))));
        int g = Math.max(0, Math.min(255, (int) (color.getGreen() * (1 + factor))));
        int b = Math.max(0, Math.min(255, (int) (color.getBlue() * (1 + factor))));
        return new Color(r, g, b);
    }

    public void setBaseColor(Color color) {
        this.baseColor = color;
        repaint();
    }
}

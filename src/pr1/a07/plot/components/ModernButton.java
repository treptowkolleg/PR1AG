/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms_of_the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl-3
 * .0.html>.
 */
package pr1.a07.plot.components;

import pr1.a07.Colors;

import javax.swing.JButton;
import java.awt.BasicStroke;
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

/**
 * A custom-styled, modern-looking button with rounded corners, dynamic color
 * states,
 * and precise hit detection. Supports hover, pressed, and disabled visual
 * states.
 * Text is always centered both horizontally and vertically.
 *
 * <p>This button uses anti-aliased rendering for smooth edges and text, and
 * responds to mouse interactions by changing its background color based on
 * the current button model state (rollover, pressed, disabled).</p>
 *
 * <p>The default base color is {@link Colors#BLUE}, but can be customized
 * via constructor or {@link #setBaseColor(Color)}.</p>
 */
public class ModernButton extends JButton {
    protected static final int ARC = 12;
    protected Color baseColor = Colors.BLUE;

    /**
     * Constructs a modern button with the specified label and the default
     * base color
     * ({@link Colors#BLUE}).
     *
     * @param text the text to display on the button; must not be null
     */
    public ModernButton(String text) {
        super(text);
        init();
    }

    /**
     * Constructs a modern button with the specified label and base color.
     *
     * @param text      the text to display on the button; must not be null
     * @param baseColor the primary background color for the enabled state;
     *                  must not be null
     */
    public ModernButton(String text, Color baseColor) {
        super(text);
        this.baseColor = baseColor;
        init();
    }

    /**
     * Overrides the default hit detection to match the visual rounded shape.
     * Only points inside the rounded rectangle are considered part of the
     * button.
     *
     * @param x the x-coordinate of the point to test
     * @param y the y-coordinate of the point to test
     * @return {@code true} if the point lies within the rounded button area,
     * {@code false} otherwise
     */
    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(),
                getHeight(), ARC, ARC);

        return shape.contains(x, y);
    }

    public Color getBaseColor() {
        return baseColor;
    }

    /**
     * Sets a new base color for this button and triggers a repaint.
     * The new color will be used in the normal (enabled, not hovered) state,
     * and serves as the basis for hover/pressed variants.
     *
     * @param color the new base color; must not be null
     */
    public void setBaseColor(Color color) {
        this.baseColor = color;
        repaint();
    }

    /**
     * Initializes common button properties: font, cursor, margins, and disables
     * standard Swing painting to allow full custom rendering.
     */
    protected void init() {
        setFont(new Font("SF Pro Text, Helvetica Neue, Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(4, 16, 4, 16));
    }

    /**
     * Adjusts the brightness of a given color by a multiplicative factor.
     * Negative factors darken the color, positive factors lighten it.
     *
     * @param color  the input color to adjust; must not be null
     * @param factor the brightness adjustment factor (e.g., -0.2 for 20%
     *               darker)
     * @return a new {@link Color} instance with adjusted RGB values
     */
    protected Color adjustBrightness(Color color, float factor) {
        int r = Math.max(0, Math.min(255,
                (int) (color.getRed() * (1 + factor))));
        int g = Math.max(0, Math.min(255,
                (int) (color.getGreen() * (1 + factor))));
        int b = Math.max(0, Math.min(255,
                (int) (color.getBlue() * (1 + factor))));

        return new Color(r, g, b);
    }

    protected Color getEffectiveBackgroundColor() {
        Color base = getBaseColor();

        if (!isEnabled()) {
            return Colors.GRAY4;
        } else if (getModel().isPressed()) {
            return adjustBrightness(base, -0.2f);
        } else if (getModel().isRollover()) {
            return adjustBrightness(base, -0.1f);
        }
        return base;
    }

    /**
     * Paints the button with a rounded background, centered text, and
     * state-dependent colors.
     * The following states are supported:
     * <ul>
     *   <li><strong>Disabled</strong>: background = {@link Colors#GRAY4},
     *   text = {@link Colors#GRAY2}</li>
     *   <li><strong>Pressed</strong>: background = base color darkened by
     *   20%</li>
     *   <li><strong>Rollover</strong>: background = base color darkened by
     *   10%</li>
     *   <li><strong>Normal</strong>: background = base color, text = white</li>
     * </ul>
     *
     * @param g the graphics context used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        FontMetrics fm = g2.getFontMetrics();
        Color buttonColor = baseColor;
        RoundRectangle2D shape = new RoundRectangle2D.Float(
                0, 0, getWidth(), getHeight(), ARC, ARC
        );
        RoundRectangle2D borderShape = new RoundRectangle2D.Float(
                0, 0, getWidth() - 1, getHeight() - 1, ARC, ARC
        );
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (!isEnabled()) {
            buttonColor = Colors.GRAY4;
            setForeground(Colors.GRAY2);
        } else if (getModel().isPressed()) {
            buttonColor = adjustBrightness(buttonColor, -0.2f);
        } else if (getModel().isRollover()) {
            buttonColor = adjustBrightness(buttonColor, -0.1f);
        }
        if (isEnabled()) {
            setForeground(Colors.WHITE);
        }
        g2.setColor(buttonColor);
        g2.fill(shape);
        if (baseColor == Colors.GRAY6) {
            setForeground(Colors.GRAY.darker());
            g2.setColor(Colors.GRAY3);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(borderShape);
        }
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);
        g2.dispose();
    }

    /**
     * Suppresses the default button border. This button draws only a filled
     * shape
     * with no outline.
     *
     * @param g the graphics context (ignored)
     */
    @Override
    protected void paintBorder(Graphics g) {
        // No border is drawn
    }
}
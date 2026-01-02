/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package treptowkolleg.plot.components;

import treptowkolleg.plot.Colors;

import javax.swing.border.Border;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

/**
 * A custom Swing border that displays a title label above or alongside an optional rounded rectangle outline.
 * Designed to provide a modern, clean visual grouping for UI components such as control panels or option sections.
 *
 * <p>The border supports two modes: with or without a visible outline. When enabled, a subtle rounded rectangle
 * is drawn using the application's neutral color palette. The title is rendered in a bold, sans-serif font
 * at a configurable offset, ensuring visual hierarchy and readability.
 *
 * <p>This class is fully compatible with Swing layout managers via its {@link #getBorderInsets(Component)}
 * implementation, which accounts for both the title height and the optional border stroke.
 */
public class ModernLabeledBorder implements Border {
    private final String title;
    private final Font font;
    private final Color color;
    private final int topMargin;
    private final int leftMargin;
    private final int contentTopGap;
    private final boolean hasBorder;

    public ModernLabeledBorder(String title) {
        this(title, false);
    }

    public ModernLabeledBorder(String title, boolean hasBorder) {
        this(title, new Font("SF Pro Text, Helvetica Neue, Arial", Font.BOLD, 12), Colors.BLACK, 16, 0, 8, hasBorder);
    }

    public ModernLabeledBorder(String title, Font font, Color color, int topMargin, int leftMargin, int contentTopGap, boolean hasBorder) {
        this.title = title;
        this.font = font;
        this.color = color;
        this.topMargin = topMargin;
        this.leftMargin = leftMargin;
        this.contentTopGap = contentTopGap;
        this.hasBorder = hasBorder;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setFont(font);
        g2d.setColor(color);
        int n = 0;
        if (hasBorder) {
            n = 8;
        }
        g2d.drawString(title, x + leftMargin + 8 + n, y + topMargin);
        if (hasBorder) {
            g2d.setStroke(new BasicStroke(1.2f));
            g2d.setColor(Colors.GRAY4);
            g2d.drawRoundRect(x, y, width - 1, height - 1, 12, 12);
        }
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        if (hasBorder) {
            return new Insets(topMargin + contentTopGap + 4, leftMargin + 8, 8, 8);
        } else {
            return new Insets(topMargin + contentTopGap, leftMargin, 4, 0);
        }
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}

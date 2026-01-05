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
package treptowkolleg.edu.swing.components;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * A small, visually styled panel that represents a single color entry in a plot legend.
 * It renders a rounded rectangle filled with a specified color and outlined with a
 * slightly darker border, providing clear visual association between a data series
 * and its graphical representation.
 *
 * <p>This component is non-opaque and fixed at 18×18 pixels to ensure consistent
 * alignment within legend layouts. It is designed to be paired with a descriptive
 * label (e.g., in a horizontal Box layout) to form a complete legend entry.
 */
public class LegendItem extends JPanel {
    private final Color fillColor;
    private final Color borderColor;
    private final int arc = 6;

    public LegendItem(Color color) {
        this.fillColor = color;
        this.borderColor = color.darker();
        setOpaque(false);
        setPreferredSize(new Dimension(18, 18));
        setMinimumSize(new Dimension(18, 18));
        setMaximumSize(new Dimension(18, 18));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        g2d.setColor(fillColor);
        g2d.fillRoundRect(0, 0, w, h, arc, arc);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
        g2d.dispose();
    }
}

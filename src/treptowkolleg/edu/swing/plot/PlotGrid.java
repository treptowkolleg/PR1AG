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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package treptowkolleg.edu.swing.plot;

import treptowkolleg.edu.swing.graphics.Colors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * A drawable object that renders a simple coordinate grid consisting of
 * horizontal and vertical axes centered in the panel, along with a label
 * at the origin point (0,0).
 *
 * <p>This class provides a minimal, default grid suitable for basic plotting
 * scenarios. It is intended for simple use cases such as demonstrations,
 * prototypes, or educational examples. For more advanced grid features
 * (e.g., tick marks, scaling, multiple axes, or labeled units), a custom
 * implementation should be used.</p>
 */
public class PlotGrid extends DrawableObject {
    protected int gap;
    protected GridPosition gridPosition;

    /**
     * Constructs a new PlotGrid with a default gap of 10 pixels between grid lines.
     */
    public PlotGrid() {
        this(10);
    }

    /**
     * Constructs a new PlotGrid with the specified gap between grid lines.
     *
     * @param gap the distance in pixels between adjacent grid lines; must be positive
     */
    public PlotGrid(int gap) {
        this.gap = gap;
    }

    /**
     * Configures basic rendering using the Graphics context.
     * This implementation does nothing, as all drawing is handled in
     * the Graphics2D method.
     *
     * @param g the basic graphics context
     */
    @Override
    public void configureGraphics(Graphics g) {
    }

    /**
     * Renders the coordinate axes and origin label using the Graphics2D context.
     * Draws a horizontal and vertical gray line through the center of the panel
     * to represent the x and y axes, and labels the origin with "0" in dark gray.
     *
     * @param g the enhanced 2D graphics context
     */
    @Override
    public void configureGraphics2D(Graphics2D g) {
        if (gap > 0) {
            g.setPaint(Colors.GRAY4);
            for (int x = centerX; x >= 0; x -= gap) {
                g.drawLine(x, 0, x, panelHeight);
            }
            for (int x = centerX + gap; x < panelWidth; x += gap) {
                g.drawLine(x, 0, x, panelHeight);
            }
            for (int y = centerY; y >= 0; y -= gap) {
                g.drawLine(0, y, panelWidth, y);
            }
            for (int y = centerY + gap; y < panelHeight; y += gap) {
                g.drawLine(0, y, panelWidth, y);
            }
        }
        g.setPaint(Colors.GRAY);
        g.setStroke(new BasicStroke(1.0f));
        g.draw(new Line2D.Double(0, centerY, panelWidth, centerY));
        g.draw(new Line2D.Double(centerX, 0, centerX, panelHeight));
        g.setPaint(Color.DARK_GRAY);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g.drawString("0", centerX + 5, centerY + 10);
    }
}
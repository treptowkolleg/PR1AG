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

import javax.swing.border.AbstractBorder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

/**
 * A custom Swing border that renders a rounded rectangle outline with configurable
 * stroke thickness and corner curvature. This border is fully anti-aliased and
 * designed for modern, visually smooth UI elements.
 *
 * <p>The border insets are automatically calculated based on the line thickness
 * and arc dimensions to ensure proper spacing between the border and contained
 * components. It extends {@link AbstractBorder} and integrates seamlessly with
 * standard Swing layout managers.
 *
 * <p>Typical use cases include framing input fields, panels, or buttons in a
 * cohesive visual language within the {@code treptowkolleg.plot} framework.
 */
public class RoundedLineBorder extends AbstractBorder {
    private final Color color;
    private final double thickness;
    private final int arcWidth;
    private final int arcHeight;

    public RoundedLineBorder(Color color, double thickness, int arc) {
        this(color, thickness, arc, arc);
    }

    public RoundedLineBorder(Color color, double thickness, int arcWidth, int arcHeight) {
        this.color = color;
        this.thickness = thickness;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke((float) thickness));
        g2d.drawRoundRect(x, y, width - 1, height - 1, arcWidth, arcHeight);
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        int inset = (int) (thickness + (int) Math.ceil(arcHeight / 20.0));

        return new Insets(inset, inset, inset, inset);
    }
}

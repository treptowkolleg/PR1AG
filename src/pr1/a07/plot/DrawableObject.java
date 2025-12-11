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
package pr1.a07.plot;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * An abstract base class for drawable objects that provides common rendering infrastructure.
 * This class automatically computes the drawing area dimensions and center coordinates
 * from the graphics context before invoking custom drawing logic.
 */
public abstract class DrawableObject implements Drawable, CustomDrawable {
    protected int panelWidth;
    protected int panelHeight;
    protected int centerX;
    protected int centerY;

    /**
     * Renders this object by first extracting the current drawing area dimensions
     * from the graphics context, computing the center point, and then delegating
     * to the configure methods for custom drawing implementation.
     *
     * @param g the graphics context used for rendering
     */
    public void draw(Graphics g) {
        panelWidth = g.getClipBounds().width;
        panelHeight = g.getClipBounds().height;
        centerX = panelWidth / 2;
        centerY = panelHeight / 2;
        configureGraphics(g);
        configureGraphics2D((Graphics2D) g);
    }
}
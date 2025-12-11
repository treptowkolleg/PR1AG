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

package pr1.a07.plot;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * An abstract base class for drawable objects that provides common rendering infrastructure.
 * This class automatically computes the drawing area dimensions and center coordinates
 * from the graphics context before invoking custom drawing logic.
 *
 * <p><strong>Important licensing notice:</strong> This class implements the
 * {@link schimkat.berlin.lernhilfe2025ws.graphics.Drawable} interface, which is provided
 * exclusively for use within the courses taught by Dr. Schimkat at the Berlin University
 * of Applied Sciences (Berliner Hochschule für Technik, BHT). Redistribution, public release,
 * or use outside this educational context is not permitted under Dr. Schimkat’s license terms.</p>
 *
 * <p>For any reuse beyond the original course setting (e.g., in personal projects, open-source
 * repositories, or other educational contexts), you <em>must</em> create a project-local
 * alternative interface (e.g., {@code your.package.Drawable}) and refactor all implementing
 * classes to depend on that instead.</p>
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
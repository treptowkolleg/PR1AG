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
package treptowkolleg.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Defines the contract for objects that can be drawn on a graphics context.
 * Implementing classes must provide custom drawing logic by overriding
 * the configure methods.
 */
public interface CustomDrawable {

    /**
     * Draws this object using the provided graphics context.
     * This method typically delegates to {@link #configureGraphics(Graphics)}
     * and {@link #configureGraphics2D(Graphics2D)} to separate basic and advanced rendering logic.
     *
     * <p>Implementations should not assume the graphics context is a specific subclass;
     * safe casting to {@code Graphics2D} should be performed only when needed.</p>
     *
     * @param g the graphics context to draw on; must not be null
     */
    void draw(Graphics g);

    /**
     * Customizes the rendering using the basic Graphics context.
     * This method is intended for simple drawing operations that do not require
     * advanced features of Graphics2D.
     *
     * @param g the basic graphics context
     */
    void configureGraphics(Graphics g);

    /**
     * Customizes the rendering using the enhanced Graphics2D context.
     * This method is intended for advanced drawing operations such as shapes,
     * transformations, and anti-aliased rendering.
     *
     * @param g the enhanced 2D graphics context
     */
    void configureGraphics2D(Graphics2D g);
}
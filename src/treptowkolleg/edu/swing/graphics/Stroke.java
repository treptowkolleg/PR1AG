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
package treptowkolleg.edu.swing.graphics;

import java.awt.BasicStroke;

/**
 * A collection of predefined {@link BasicStroke} instances for consistent and semantic
 * line styling in plot rendering. Each constant is named according to its visual weight
 * and intended use (e.g., axes, grid, data curves).
 */
public abstract class Stroke {

    /**
     * Medium-weight beveled stroke for general-purpose lines such as data curves.
     * Width: 2.0 pixels; beveled joins; butt caps.
     */
    public static final BasicStroke BEVEL_MEDIUM = new BasicStroke(2.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    /**
     * Thin stroke for fine grid lines.
     * Width: 0.5 pixels; miter joins; butt caps.
     */
    public static final BasicStroke GRID_FINE = new BasicStroke(0.5f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    /**
     * Medium-weight stroke for main coordinate axes (x and y).
     * Width: 1.5 pixels; miter joins; butt caps.
     */
    public static final BasicStroke AXIS_MEDIUM = new BasicStroke(1.5f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    /**
     * Thick stroke for emphasized axes or zero-reference lines.
     * Width: 2.5 pixels; miter joins; butt caps.
     */
    public static final BasicStroke AXIS_THICK = new BasicStroke(2.5f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    /**
     * Dashed stroke for grid lines that should be visually subtle.
     * Width: 0.75 pixels; dash pattern [5, 5]; miter joins; butt caps.
     */
    public static final BasicStroke GRID_DASHED = new BasicStroke(0.75f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[]{5.0f, 5.0f}, 0.0f);

    /**
     * Very thin stroke for delicate guide or helper lines.
     * Width: 0.25 pixels; miter joins; butt caps.
     */
    public static final BasicStroke GUIDE_THIN = new BasicStroke(0.25f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    /**
     * Thick, rounded stroke for prominent or highlighted curves.
     * Width: 3.0 pixels; rounded caps and joins for smooth appearance.
     */
    public static final BasicStroke LINE_THICK = new BasicStroke(3.0f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    /**
     * Dashed stroke for secondary or auxiliary curves.
     * Width: 2.0 pixels; dash pattern [10, 5]; miter joins; butt caps.
     */
    public static final BasicStroke LINE_DASHED = new BasicStroke(2.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[]{10.0f, 5.0f}, 0.0f);

    /**
     * Dotted stroke for subtle reference or helper lines.
     * Width: 1.0 pixel; dot pattern [1, 3]; rounded caps and joins.
     */
    public static final BasicStroke DOTTED = new BasicStroke(1.0f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f,
            new float[]{1.0f, 3.0f}, 0.0f);
}
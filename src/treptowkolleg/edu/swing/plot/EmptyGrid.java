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
package treptowkolleg.edu.swing.plot;

import java.awt.Graphics2D;

/**
 * A {@link PlotGrid} implementation that renders no visual grid lines or background elements.
 * This class is useful when a clean, unadorned plotting area is desired—e.g., for minimalistic
 * visualizations or custom grid rendering handled externally.
 */
public class EmptyGrid extends PlotGrid {

    public EmptyGrid() {
        super(0);
    }

    @Override
    public void configureGraphics2D(Graphics2D g) {
    }
}
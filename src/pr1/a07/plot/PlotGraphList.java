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

import java.util.ArrayList;

/**
 * A type-safe list for storing plot graph instances of a specific concrete type.
 * This class extends ArrayList to provide a dedicated container that preserves
 * the self-referential generic type relationship defined by PlotGraph.
 *
 * @param <T> the concrete subclass of PlotGraph stored in this list
 */
public class PlotGraphList<T extends PlotGraph<T>> extends ArrayList<T> {
}
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

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An abstract base class that groups a plot grid and a type-safe list of plot graphs
 * of the same concrete type. This class provides a structured container for managing
 * a coherent set of visual elements that belong together in a single plotting context.
 *
 * @param <T> the concrete subclass of PlotGraph contained in this set
 */
public abstract class PlotSet<T extends PlotGraph<T>> {
    protected PlotGrid grid;
    protected GridPosition gridPosition = GridPosition.CENTER;
    protected PlotGraphList<T> graphs = new PlotGraphList<>();
    protected String title = "unbenannt";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the coordinate grid used for this plot set.
     *
     * @param grid the PlotGrid instance to associate with this set
     */
    public void setGrid(PlotGrid grid) {
        this.grid = grid;
    }

    /**
     * Returns the coordinate grid associated with this plot set.
     *
     * @return the current PlotGrid instance, or null if none has been set
     */
    public PlotGrid getGrid() {
        return grid;
    }

    public void setGridPosition(GridPosition gridPosition, JPanel frame) {
        getGrid().setGridPosition(gridPosition, frame);
    }

    /**
     * Adds a plot graph to this set.
     *
     * @param graph the graph to add; must be of type T
     */
    public void addGraph(T graph) {
        graphs.add(graph);
        graph.setPlotSet(this);
    }

    /**
     * Retrieves the plot graph at the specified index.
     *
     * @param index the zero-based index of the graph to retrieve
     * @return the graph at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T getGraph(int index) {
        return graphs.get(index);
    }

    /**
     * Returns the list of plot graphs contained in this set.
     * The returned list is type-safe and preserves the self-referential
     * generic relationship of the graph type.
     *
     * @return the list of graphs
     */
    public PlotGraphList<T> getGraphs() {
        return graphs;
    }

    /**
     * Creates a plot control window associated with this plot set.
     * This method is called by {@link PlotApplication} when user interaction
     * with the set is required (e.g., when switching to this set for the first time).
     *
     * <p>Subclasses that support interactive parameter adjustment should override
     * this method to return a concrete {@link PlotControl} instance.
     * If no control is needed, the default implementation returns {@code null}.</p>
     *
     * @param app the plot application that will manage this control
     * @return a new PlotControl instance, or {@code null} if this set has no UI controls
     */
    public PlotControl<T> createControl(PlotApplication app) {
        return null;
    }

    public void preSwitching(JPanel frame) {

    }

    public void postSwitching(JPanel frame) {

    }
}
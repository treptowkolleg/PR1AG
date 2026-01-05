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
    protected PlotGraphList<T> graphs = new PlotGraphList<>();
    protected String title = "unbenannt";

    /**
     * Constructs a new PlotSet with an empty grid as the default coordinate system.
     * Subclasses may replace the grid by calling {@link #setGrid(PlotGrid)} in their constructor.
     */
    public PlotSet() {
        setGrid(new EmptyGrid());
    }

    /**
     * Returns the display title of this plot set.
     *
     * @return the current title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the display title for this plot set.
     *
     * @param title the new title to be shown in the application UI
     */
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

    /**
     * Sets the position of the coordinate grid relative to the drawing panel.
     * This method delegates to the contained {@link PlotGrid} instance.
     *
     * @param gridPosition the desired grid position of ({@link GridPosition})
     * @param frame the drawing panel used to compute layout dimensions
     */
    public void setGridPosition(GridPosition gridPosition, JPanel frame) {
        grid.setGridPosition(gridPosition, frame);
    }

    /**
     * Sets the horizontal zoom factor for the plot grid.
     * Values greater than 1.0 zoom in; values between 0 and 1 zoom out.
     * This method delegates to the contained {@link PlotGrid} instance.
     *
     * @param amount the zoom factor to apply on the x-axis
     */
    public void setZoomX(double amount) {
        grid.setZoomX(amount);
    }

    /**
     * Sets the vertical zoom factor for the plot grid.
     * Values greater than 1.0 zoom in; values between 0 and 1 zoom out.
     * This method delegates to the contained {@link PlotGrid} instance.
     *
     * @param amount the zoom factor to apply on the y-axis
     */
    public void setZoomY(double amount) {
        grid.setZoomY(amount);
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

    /**
     * Called before this plot set becomes active.
     * Subclasses may override this method to configure grid position,
     * zoom levels, or other visual properties specific to this set.
     *
     * @param frame the PlotApplication instance that is switching to this set
     */
    public void preSetup(PlotApplication frame) {

    }

    /**
     * Called after this plot set is deactivated.
     * Restores default grid settings (centered grid position and unit zoom).
     * Subclasses may override this method if a different cleanup behavior is desired.
     *
     * @param frame the PlotApplication instance that is leaving this set
     */
    public void postSetup(PlotApplication frame) {
        setGridPosition(GridPosition.CENTER, frame.getDrawablePanel());
        setZoomX(1.0);
        setZoomY(1.0);
    }
}
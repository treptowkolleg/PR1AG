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
package treptowkolleg.plot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * An abstract base class for plot graphs that supports self-referential
 * generics to enable type-safe interaction with associated plot controls.
 * Subclasses can define custom rendering logic and expose parameters
 * that are adjustable via a linked PlotControl instance.
 *
 * @param <T> the concrete subclass type (enables fluent and type-safe method
 *            chaining)
 */
public abstract class PlotGraph<T extends PlotGraph<T>> extends DrawableObject {
    protected PlotControl<T> plotControl;
    protected PlotSet<T> plotSet;
    protected String title;
    protected Color color = Color.BLACK;
    protected boolean isVisible = true;

    /**
     * Returns the plot set that owns this graph.
     * This reference is used to access shared context such as grid settings,
     * application state, or lifecycle callbacks.
     *
     * @return the associated PlotSet instance, or null if not yet assigned
     */
    public PlotSet<T> getPlotSet() {
        return plotSet;
    }

    /**
     * Associates this graph with a specific plot set.
     * This method is typically called automatically when the graph is added
     * to a plot set via {@link PlotSet#addGraph(PlotGraph)} and should not
     * be invoked manually under normal circumstances.
     *
     * @param plotSet the PlotSet instance to associate with this graph
     */
    public void setPlotSet(PlotSet<T> plotSet) {
        this.plotSet = plotSet;
    }

    /**
     * Associates this graph with a plot control window.
     * The control can later be shown or hidden using {@link #showPlotControl()}
     * and {@link #hidePlotControl()}.
     *
     * @param control the plot control instance to link with this graph
     */
    public void setPlotControl(PlotControl<T> control) {
        plotControl = control;
    }

    /**
     * Checks whether this graph has a user-defined title.
     *
     * @return {@code true} if a title has been set, {@code false} otherwise
     */
    public boolean hasTitle() {
        return null != title;
    }

    /**
     * Returns the title of this graph, or {@code null} if no title has been set.
     *
     * @return the graph's title, or {@code null}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a descriptive title for this graph.
     * The title may be displayed in UI elements such as graph selectors.
     *
     * @param title the new title; may be {@code null}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the primary drawing color used by this graph.
     *
     * @return the current color; never {@code null} (defaults to {@link Color#BLACK})
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the primary drawing color for this graph.
     *
     * @param color the new color; must not be {@code null}
     */
    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Displays the associated plot control window, if one has been set.
     * If no control is attached, this method has no effect.
     */
    public void showPlotControl() {
        if (plotControl != null) {
            plotControl.setVisible(true);
        }
    }

    /**
     * Hides the associated plot control window, if one has been set.
     * If no control is attached, this method has no effect.
     */
    public void hidePlotControl() {
        if (plotControl != null) {
            plotControl.setVisible(false);
        }
    }

    /**
     * Customizes rendering using the basic Graphics context.
     * Subclasses may override this method to provide additional drawing logic.
     * This implementation does nothing by default.
     *
     * @param g the basic graphics context
     */
    public void configureGraphics(Graphics g) {
    }

    /**
     * Customizes rendering using the enhanced Graphics2D context.
     * Subclasses should override this method to define how the plot is drawn.
     * This implementation does nothing by default.
     *
     * @param g the enhanced 2D graphics context
     */
    public void configureGraphics2D(Graphics2D g) {
    }
}
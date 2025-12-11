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
 *          chaining)
 */
public abstract class PlotGraph<T extends PlotGraph<T>> extends DrawableObject {
    protected PlotControl<T> plotControl;
    protected String title;
    protected Color color;

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

    public boolean hasTitle() {
        return null != title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
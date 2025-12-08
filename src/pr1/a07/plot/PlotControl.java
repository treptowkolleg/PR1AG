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
 * An abstract base class for plot control windows that manage user interaction
 * with a homogeneous list of plot graphs of the same concrete type.
 * This class provides a framework for creating embedded UI controls (e.g., sliders,
 * selectors) that update the properties of the currently active graph in real time.
 *
 * <p>Each control window is associated with a {@link PlotApplication} instance,
 * which provides the central rendering context and repaint coordination for all
 * managed plot sets.</p>
 *
 * @param <T> the concrete subclass of {@link PlotGraph} managed by this control
 */
public abstract class PlotControl<T extends PlotGraph<T>> extends JFrame {
    protected PlotApplication application;
    protected PlotGraphList<T> graphs;
    protected T activeGraph;
    protected JPanel controlPanel;

    /**
     * Constructs a new plot control window associated with the given application
     * and a list of plot graphs. The first graph in the list is set as the
     * initially active graph. All graphs are linked to this control instance.
     * The control panel is created via {@link #configureControls(ControlBuilder)},
     * added to the window, and the window is made visible.
     *
     * @param application the parent {@link PlotApplication} used for repaint coordination
     * @param plotSet     the corresponding {@link PlotSet} holding the plots
     * @throws IllegalArgumentException if the graph list is null or empty
     */
    public PlotControl(PlotApplication application, PlotSet<T> plotSet) {
        PlotGraphList<T> graphs = plotSet.getGraphs();
        if (graphs == null || graphs.isEmpty()) {
            throw new IllegalArgumentException("Graph list must not be null or empty");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.application = application;
        this.graphs = graphs;
        this.activeGraph = graphs.get(0);
        for (T graph : graphs) {
            graph.setPlotControl(this);
        }
        controlPanel = configureControls(createBuilder());
        if (null != controlPanel) {
            add(controlPanel);
        }
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    /**
     * Switches the active graph to the one at the specified index.
     * This triggers a repaint of the application view to reflect changes
     * in the newly selected graph.
     *
     * @param index the index of the graph to activate
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void setActiveGraph(int index) {
        this.activeGraph = graphs.get(index);
        application.repaint();
    }

    /**
     * Returns the currently active graph whose parameters are controlled
     * by the UI elements (e.g., sliders).
     *
     * @return the active graph instance
     */
    public T getActiveGraph() {
        return activeGraph;
    }

    /**
     * Returns the full list of graphs managed by this control window.
     *
     * @return the list of graphs; never null
     */
    public PlotGraphList<T> getGraphs() {
        return graphs;
    }

    /**
     * Shows or hides this control window.
     * This method has no effect if no control panel was created.
     *
     * @param visible true to show the window, false to hide it
     */
    @Override
    public void setVisible(boolean visible) {
        if (null != controlPanel) {
            super.setVisible(visible);
        }
    }

    /**
     * Creates the control panel for this plot.
     * This method is called during construction and the returned panel
     * is automatically added to the window.
     *
     * @param build the control builder used to construct UI elements
     * @return the control panel or {@code null} if no controls are desired
     */
    public abstract JPanel configureControls(ControlBuilder<T> build);

    /**
     * Creates a new control builder instance associated with this control
     * and its graph list.
     *
     * @return a new {@link ControlBuilder} for this plot control
     */
    private ControlBuilder<T> createBuilder() {
        return new ControlBuilder<>(this, graphs);
    }
}
/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms ol the GNU Lesser General Public License as published by
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main application window that coordinates rendering and user interaction
 * for multiple plot sets. This class manages a central drawing panel,
 * maintains a registry of {@link PlotSet} instances, and handles switching
 * between them while automatically showing or hiding the corresponding
 * {@link PlotControl} windows.
 *
 * <p><strong>Note:</strong> This class is a work in progress. Future enhancements
 * may include support for UI navigation (e.g., menus or toolbars to select
 * active plot sets), persistence of plot configurations, and improved lifecycle
 * management of controls.</p>
 */
public class PlotApplication extends JFrame {
    private final DrawablePanel panel = new DrawablePanel();
    private final List<PlotSet<?>> plotSets = new ArrayList<>();
    private PlotSet<?> activeSet = null;
    private final Map<PlotSet<?>, PlotControl<?>> controlMap = new HashMap<>();

    /**
     * Constructs a new plot application window with default properties.
     * Initializes the drawing panel, sets the window title, size, and close
     * behavior, and centers the window on the screen.
     */
    public PlotApplication() {
        this(800, 600);
    }

    public PlotApplication(String title) {
        this(title, 800, 600);
    }

    public PlotApplication(int width, int height) {
        this("Plot Application", width, height);
    }

    public PlotApplication(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    /**
     * Adds a plot set to the application's registry.
     * If no set is currently active, the added set becomes the active one
     * and is immediately displayed.
     *
     * @param set the plot set to add; must not be null
     */
    public void addPlotSet(PlotSet<?> set) {
        plotSets.add(set);
        if (activeSet == null) {
            switchToSet(set);
        }
    }

    /**
     * Switches the application's view to the specified plot set.
     * Hides the control window of the previously active set (if any),
     * updates the drawing panel with the new set's grid and graphs,
     * and shows the associated control window.
     *
     * @param set the plot set to activate; must be registered via {@link #addPlotSet}
     */
    public void switchToSet(PlotSet<?> set) {
        if (activeSet != null) {
            PlotControl<?> oldControl = controlMap.get(activeSet);
            if (oldControl != null) oldControl.setVisible(false);
        }
        activeSet = set;
        panel.clearDrawables();
        panel.addDrawable(set.getGrid());
        panel.addDrawables(set.getGraphs());
        PlotControl<?> control = controlMap.computeIfAbsent(set, this::createControl);
        if (control != null) {
            control.setVisible(true);
        }
        panel.repaint();
    }

    /**
     * Starts the application by setting the main frame visible.
     */
    public void start() {
        setVisible(true);
    }

    /**
     * Delegates control creation to the plot set itself.
     * This method is called only once per plot set, when first needed.
     *
     * @param set the plot set for which to create a control
     * @return a new {@link PlotControl} instance, or {@code null} if the set
     *         does not require user interaction
     */
    private PlotControl<?> createControl(PlotSet<?> set) {
        return set.createControl(this);
    }
}
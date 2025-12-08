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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * A builder class for creating UI control panels for plot graphs.
 * This class provides a fluent API to add interactive controls such as sliders
 * and graph selectors that dynamically update properties of the currently active
 * plot graph instance. Sliders are automatically synchronized with the active
 * graph when the selection changes.
 *
 * <p><strong>Future enhancements:</strong> Additional control types such as
 * choice fields, text fields, checkboxes, and color pickers will be added
 * in upcoming versions to support richer parameter customization.</p>
 *
 * @param <T> the concrete subclass of {@link PlotGraph} that this builder configures
 */
public class ControlBuilder<T extends PlotGraph<T>> {
    private final PlotControl<T> control;
    private final PlotGraphList<T> graphs;
    private final T activeGraph;
    private final List<JSlider> sliders = new ArrayList<>();
    private final List<Function<T, Integer>> getters = new ArrayList<>();
    private final JPanel panel = new JPanel(new GridLayout(0, 1));

    /**
     * Constructs a new control builder associated with the given plot control
     * and list of graphs. The first graph in the list is used as the initial
     * active graph.
     *
     * @param control the plot control that manages repaint and active graph state
     * @param graphs  the non-empty list of graphs to manage; must not be null
     * @throws IllegalArgumentException if the graph list is null or empty
     */
    public ControlBuilder(PlotControl<T> control, PlotGraphList<T> graphs) {
        if (graphs == null || graphs.isEmpty()) {
            throw new IllegalArgumentException("Graph list must not be null or empty");
        }
        this.control = control;
        this.graphs = graphs;
        this.activeGraph = graphs.get(0);
    }

    /**
     * Adds a labeled slider to the control panel.
     * The slider allows the user to select an integer value within a specified range.
     * When the slider value changes, the provided setter is invoked on the currently
     * active graph, and the application view is repainted.
     *
     * <p>When the active graph is changed (e.g., via a selector), the slider value
     * is automatically updated to reflect the new graph's current property value.</p>
     *
     * @param label  the label displayed on the slider border
     * @param min    the minimum value of the slider
     * @param max    the maximum value of the slider
     * @param getter a function that retrieves the current integer value from a graph
     * @param setter a bi-consumer that accepts a graph and an integer value to update it
     * @return this builder instance for method chaining
     */
    public ControlBuilder<T> slider(String label, int min, int max,
                                    Function<T, Integer> getter,
                                    BiConsumer<T, Integer> setter) {
        int initialValue = getter.apply(activeGraph);
        JSlider slider = createSlider(min, max, initialValue, label);

        slider.addChangeListener(e -> {
            int value = slider.getValue();
            T currentGraph = control.getActiveGraph();
            setter.accept(currentGraph, value);
            control.application.repaint();
        });
        sliders.add(slider);
        getters.add(getter);
        panel.add(slider);
        return this;
    }

    /**
     * Adds a drop-down selector that allows the user to choose which graph
     * in the list should be actively controlled. When a new graph is selected,
     * all associated sliders are updated to reflect that graph's current parameter values.
     *
     * @param label the base label used to generate item names (e.g., "Plot" → "Plot 1", "Plot 2")
     * @return this builder instance for method chaining
     */
    public ControlBuilder<T> selector(String label) {
        String[] items = new String[graphs.size()];
        JComboBox<String> combo;

        for (int i = 0; i < items.length; i++) {
            items[i] = label + " " + (i + 1);
        }
        combo = new JComboBox<>(items);
        combo.setSelectedIndex(0);
        combo.addActionListener(e -> {
            int index = combo.getSelectedIndex();
            if (index >= 0 && index < graphs.size()) {
                control.setActiveGraph(index);
                syncSlidersToActiveGraph();
                control.application.repaint();
            }
        });
        panel.add(combo);
        return this;
    }

    /**
     * Returns the constructed JPanel containing all added controls.
     *
     * @return the control panel ready to be added to a container
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Creates and configures a JSlider with ticks, labels, and a titled border.
     *
     * @param min   the minimum value of the slider
     * @param max   the maximum value of the slider
     * @param value the initial value of the slider
     * @param title the title displayed on the slider border
     * @return a fully configured JSlider instance
     */
    private JSlider createSlider(int min, int max, int value, String title) {
        JSlider slider = new JSlider(min, max, value);

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(Math.max(1, (max - min) / 10));
        slider.setBorder(BorderFactory.createTitledBorder(title));
        return slider;
    }

    /**
     * Updates all registered sliders to reflect the current property values
     * of the active graph. This method is called automatically when the
     * graph selector changes the active graph.
     */
    private void syncSlidersToActiveGraph() {
        T active = control.getActiveGraph();
        for (int i = 0; i < sliders.size(); i++) {
            JSlider slider = sliders.get(i);
            Function<T, Integer> getter = getters.get(i);
            int value = getter.apply(active);
            slider.setValue(value);
        }
    }
}
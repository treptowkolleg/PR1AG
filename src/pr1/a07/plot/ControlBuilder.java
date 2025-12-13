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
package pr1.a07.plot;

import pr1.a07.Colors;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Builder class for plot control panels.
 *
 * @param <T> concrete PlotGraph type
 */
public class ControlBuilder<T extends PlotGraph<T>> {
    private final PlotControl<T> control;
    private final PlotGraphList<T> graphs;
    private final T activeGraph;
    private final List<JSlider> sliders = new ArrayList<>();
    private final List<Function<T, Integer>> getters = new ArrayList<>();
    private final JPanel panel = new JPanel(new GridBagLayout());
    private final GridBagConstraints constraints = new GridBagConstraints();

    /**
     * Constructs a new control builder for the given plot control and graph list.
     *
     * @param control the associated plot control instance
     * @param graphs  the list of plot graphs to manage; must not be null or empty
     * @throws IllegalArgumentException if the graph list is null or empty
     */
    public ControlBuilder(PlotControl<T> control, PlotGraphList<T> graphs) {
        if (graphs == null || graphs.isEmpty()) {
            throw new IllegalArgumentException("Graph list must not be null " +
                    "or empty");
        }
        this.control = control;
        this.graphs = graphs;
        this.activeGraph = graphs.get(0);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.setBackground(Colors.GRAY5);
    }

    /**
     * Ensures that the minimum and maximum values are always present as labels
     * in the slider's label table.
     *
     * @param labels      the label table to update
     * @param min         the minimum value
     * @param max         the maximum value
     * @param keyMapper   function to map value to integer slider key
     * @param formatter   function to format value as display string
     * @param <V>         the type of the min/max values (e.g., Integer or Double)
     */
    private static <V> void ensureMinAndMaxLabels(
            Dictionary<Integer, JComponent> labels,
            V min,
            V max,
            Function<V, Integer> keyMapper,
            Function<V, String> formatter
    ) {
        labels.put(keyMapper.apply(min), new JLabel(formatter.apply(min)));
        labels.put(keyMapper.apply(max), new JLabel(formatter.apply(max)));
    }

    /**
     * Computes a preferred number of ticks for a slider based on the value range
     * and step size. Small ranges show all discrete values; larger ranges use
     * a logarithmic heuristic to yield 4–8 readable ticks.
     *
     * @param min   the minimum value of the range
     * @param max   the maximum value of the range
     * @param step  the smallest increment (must be positive)
     * @return a target number of ticks between 3 and 8
     */
    private static int computePreferredTickCount(double min, double max,
                                                 double step) {
        if (step <= 0 || min >= max) {
            return 5;
        }
        double range = max - min;
        long values = (long) Math.ceil(range / step) + 1;
        double logRange = Math.log10(range);
        int ticks = (int) Math.round(6.0 - 0.7 * Math.abs(logRange));

        if (values <= 6) {
            return (int) values;
        }
        return Math.max(4, Math.min(8, ticks));
    }

    /**
     * Computes a "nice" (human-readable) tick spacing such as 1, 2, 5, 10, etc.,
     * based on the value range and desired number of ticks.
     *
     * @param range           the total range (max - min)
     * @param preferredTicks  the approximate number of desired ticks
     * @return a rounded, user-friendly tick interval
     */
    private static double computeNiceTickSpacing(double range,
                                                 int preferredTicks) {
        if (range <= 0) {
            return 1.0;
        }
        double raw = range / preferredTicks;
        double exponent = Math.floor(Math.log10(raw));
        double fraction = raw / Math.pow(10, exponent);
        double niceFraction = fraction <= 1 ? 1 : fraction <= 2 ? 2 :
                fraction <= 5 ? 5 : 10;

        return niceFraction * Math.pow(10, exponent);
    }

    /**
     * Formats a double value for display by omitting unnecessary decimal places.
     * Examples: 2.0 → "2", 1.5 → "1.5", 0.75 → "0.75".
     *
     * @param value the numeric value to format
     * @return a compact string representation
     */
    private static String formatDouble(double value) {
        if (Math.abs(value - Math.round(value)) < 1e-9) {
            return String.format("%.0f", value);
        } else if (Math.abs(value * 10 - Math.round(value * 10)) < 1e-9) {
            return String.format("%.1f", value);
        }
        return String.format("%.2f", value);
    }

    /**
     * Returns the constructed control panel containing all added UI components.
     *
     * @return the control panel ready for embedding in a window
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Adds a labeled integer slider to the control panel.
     *
     * @param label  the slider label
     * @param min    the minimum integer value (inclusive)
     * @param max    the maximum integer value (inclusive)
     * @param getter function to retrieve the current value from a graph
     * @param setter function to update the graph with a new integer value
     * @return this builder instance for method chaining
     */
    public ControlBuilder<T> slider(
            String label,
            int min,
            int max,
            Function<T, Integer> getter,
            BiConsumer<T, Integer> setter
    ) {
        int initialValue = getter.apply(activeGraph);
        JSlider slider = createIntSlider(min, max, initialValue, label);

        slider.setBackground(Colors.GRAY5);
        add(slider);
        attachSliderListener(slider, getter, Function.identity(), setter);
        return this;
    }

    /**
     * Adds a labeled double slider to the control panel.
     * Internally uses integer-based JSlider scaled by 1/step.
     *
     * @param label  the slider label
     * @param min    the minimum double value (inclusive)
     * @param max    the maximum double value (inclusive)
     * @param step   the smallest increment (e.g., 0.01); must be positive
     * @param getter function to retrieve the current double value from a graph
     * @param setter function to update the graph with a new double value
     * @return this builder instance for method chaining
     * @throws IllegalArgumentException if step is not positive or min >= max
     */
    public ControlBuilder<T> sliderDouble(
            String label,
            double min,
            double max,
            double step,
            Function<T, Double> getter,
            BiConsumer<T, Double> setter
    ) {
        if (step <= 0 || min >= max) {
            throw new IllegalArgumentException("Invalid min/max/step " +
                    "configuration");
        }
        double scale = 1.0 / step;
        int intMin = (int) Math.round(min * scale);
        int intMax = (int) Math.round(max * scale);
        int initial = (int) Math.round(getter.apply(activeGraph) * scale);
        double range = max - min;
        int preferredTicks = computePreferredTickCount(min, max, step);
        int tickSpacing = (int) Math.max(1,
                Math.round(computeNiceTickSpacing(range, preferredTicks) * scale));
        JSlider slider = new JSlider(intMin, intMax, initial);
        Dictionary<Integer, JComponent> labels = new Hashtable<>();

        slider.setBackground(Colors.GRAY5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setBorder(BorderFactory.createTitledBorder(label));

        double tickStart =
                Math.floor(min / (tickSpacing / scale)) * (tickSpacing / scale);

        for (double v = tickStart; v <= max + 1e-9; v += tickSpacing / scale) {
            if (v >= min - 1e-9) {
                labels.put((int) Math.round(v * scale),
                        new JLabel(formatDouble(v)));
            }
        }
        if (min <= 0 && max >= 0) {
            labels.put(0, new JLabel("0"));
        }
        ensureMinAndMaxLabels(
                labels,
                min, max,
                v -> (int) Math.round(v * scale),
                ControlBuilder::formatDouble
        );
        slider.setLabelTable(labels);
        add(slider);
        attachSliderListener(
                slider,
                g -> (int) Math.round(getter.apply(g) * scale),
                i -> i / scale,
                setter
        );
        return this;
    }

    /**
     * Adds a graph selector combo box to switch the active graph.
     *
     * @param title the title displayed above the combo box
     * @param label the base label for fallback item names (e.g., "Graph")
     * @return this builder instance for method chaining
     */
    public ControlBuilder<T> selector(String title, String label) {
        String[] items = new String[graphs.size()];

        for (int i = 0; i < items.length; i++) {
            items[i] = graphs.get(i).hasTitle()
                    ? graphs.get(i).getTitle()
                    : label + " " + (i + 1);
        }
        JComboBox<String> combo = new JComboBox<>(items);

        combo.setBackground(Colors.GRAY5);
        combo.setSelectedIndex(0);
        combo.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(0, 0
                , 0, 0), title));
        add(combo);
        combo.addActionListener(e -> {
            int index = combo.getSelectedIndex();
            if (index >= 0 && index < graphs.size()) {
                control.setActiveGraph(index);
                syncSlidersToActiveGraph();
                control.application.repaint();
            }
        });
        return this;
    }

    /**
     * Attaches a change listener to a slider that updates the active graph
     * and triggers a repaint.
     *
     * @param slider     the slider to attach the listener to
     * @param intGetter  function to extract integer state from a graph (for sync)
     * @param mapper     function to convert slider integer value to parameter type
     * @param setter     function to apply the new value to the graph
     * @param <V>        the parameter type (Integer or Double)
     */
    private <V> void attachSliderListener(
            JSlider slider,
            Function<T, Integer> intGetter,
            Function<Integer, V> mapper,
            BiConsumer<T, V> setter
    ) {
        sliders.add(slider);
        getters.add(intGetter);
        slider.addChangeListener(e -> {
            V value = mapper.apply(slider.getValue());

            setter.accept(control.getActiveGraph(), value);
            control.application.repaint();
        });
    }

    /**
     * Synchronizes all sliders to reflect the current state of the active graph.
     */
    private void syncSlidersToActiveGraph() {
        T active = control.getActiveGraph();

        for (int i = 0; i < sliders.size(); i++) {
            sliders.get(i).setValue(getters.get(i).apply(active));
        }
    }

    /**
     * Creates and configures a JSlider for integer values with automatic,
     * human-readable ticks and labels.
     *
     * @param min   the minimum value (inclusive)
     * @param max   the maximum value (inclusive)
     * @param value the initial slider value
     * @param title the border title for the slider
     * @return a fully configured JSlider
     * @throws IllegalArgumentException if min >= max
     */
    private JSlider createIntSlider(int min, int max, int value, String title) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }
        int range = max - min;
        int preferredTicks = computePreferredTickCount(min, max, 1.0);
        int tickSpacing = (int) Math.max(1,
                computeNiceTickSpacing(range, preferredTicks));
        JSlider slider = new JSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setBorder(BorderFactory.createTitledBorder(title));
        Dictionary<Integer, JComponent> labels = new Hashtable<>();
        int start = (int) Math.floor((double) min / tickSpacing) * tickSpacing;

        for (int v = start; v <= max; v += tickSpacing) {
            if (v >= min) {
                labels.put(v, new JLabel(String.valueOf(v)));
            }
        }
        if (min <= 0 && max >= 0) {
            labels.put(0, new JLabel("0"));
        }
        ensureMinAndMaxLabels(labels, min, max, Function.identity(),
                String::valueOf);
        slider.setLabelTable(labels);
        return slider;
    }

    /**
     * Adds a component to the internal control panel using the current layout
     * constraints and increments the grid Y position.
     *
     * @param component the UI component to add
     */
    private void add(Component component) {
        panel.add(component, constraints);
        constraints.gridy++;
    }
}
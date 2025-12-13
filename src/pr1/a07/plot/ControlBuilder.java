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
 * A builder class for creating UI control panels for plot graphs.
 * This class provides a fluent API to add interactive controls such as sliders
 * and graph selectors that dynamically update properties of the currently
 * active plot graph instance. Sliders are automatically synchronized with
 * the active graph when the selection changes.
 *
 * <p><strong>Future enhancements:</strong> Additional control types such as
 * choice fields, text fields, checkboxes, and color pickers will be added
 * in upcoming versions to support richer parameter customization.</p>
 *
 * @param <T> the concrete subclass of {@link PlotGraph} that this builder
 *            configures
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
     * Constructs a new control builder associated with the given plot control
     * and list of graphs. The first graph in the list is used as the initial
     * active graph.
     *
     * @param control the plot control that manages repaint and active graph
     *                state
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
        this.constraints.fill = GridBagConstraints.HORIZONTAL;
        this.constraints.insets = new Insets(5, 5, 5, 5);
        this.constraints.weightx = 1.0;
        this.constraints.gridx = 0;
        this.constraints.gridy = 0;
        panel.setBackground(Colors.GRAY5);
    }

    /**
     * Adds a labeled slider to the control panel for integer values.
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
        JSlider slider = createIntSlider(min, max, initialValue, label);

        slider.setBackground(Colors.GRAY5);
        sliders.add(slider);
        getters.add(getter);
        add(slider);

        slider.addChangeListener(e -> {
            int value = slider.getValue();
            T currentGraph = control.getActiveGraph();
            setter.accept(currentGraph, value);
            control.application.repaint();
        });

        return this;
    }

    /**
     * Adds a labeled slider for double-valued parameters.
     * Tick count is automatically determined based on min, max, and step.
     * Labels appear at every tick, always at 0 (if in range),
     * and always at min and max.
     *
     * @param label  the label displayed on the slider border
     * @param min    the minimum double value (inclusive)
     * @param max    the maximum double value (inclusive)
     * @param step   the smallest incremental change (e.g., 0.01)
     * @param getter a function that retrieves the current double value from a graph
     * @param setter a bi-consumer that updates the graph with a double value
     * @return this builder instance for method chaining
     */
    public ControlBuilder<T> sliderDouble(String label, double min, double max, double step,
                                          Function<T, Double> getter,
                                          BiConsumer<T, Double> setter) {
        if (step <= 0) {
            throw new IllegalArgumentException("Step must be positive");
        }
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }

        double range = max - min;
        int preferredTicks = computePreferredTickCount(min, max, step);
        double tickSpacing = computeNiceTickSpacing(range, preferredTicks);
        double scale = 1.0 / step;
        int intMin = (int) Math.round(min * scale);
        int intMax = (int) Math.round(max * scale);
        double initialDouble = getter.apply(activeGraph);
        int initialInt = (int) Math.round(initialDouble * scale);
        int intTickSpacing = (int) Math.round(tickSpacing * scale);
        double tickStart = Math.floor(min / tickSpacing) * tickSpacing;
        JSlider slider = new JSlider(intMin, intMax, initialInt);
        Dictionary<Integer, JComponent> labelTable = new Hashtable<>();

        if (intTickSpacing < 1) {
            intTickSpacing = 1;
        }

        slider.setBackground(Colors.GRAY5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(intTickSpacing);
        add(slider);

        for (double v = tickStart; v <= max + 1e-9; v += tickSpacing)
            if (v >= min - 1e-9) {
                int key = (int) Math.round(v * scale);
                labelTable.put(key, new JLabel(formatDouble(v)));
            }

        if (min <= 0.0 && 0.0 <= max) {
            int zeroKey = (int) Math.round(0.0 * scale);
            labelTable.put(zeroKey, new JLabel("0"));
        }

        // Always show min and max labels
        int minKey = (int) Math.round(min * scale);
        int maxKey = (int) Math.round(max * scale);
        labelTable.put(minKey, new JLabel(formatDouble(min)));
        labelTable.put(maxKey, new JLabel(formatDouble(max)));

        slider.setLabelTable(labelTable);
        slider.setBorder(BorderFactory.createTitledBorder(label));

        slider.addChangeListener(e -> {
            int intValue = slider.getValue();
            double doubleValue = intValue / scale;
            T currentGraph = control.getActiveGraph();
            setter.accept(currentGraph, doubleValue);
            control.application.repaint();
        });

        sliders.add(slider);
        getters.add(g -> (int) Math.round(getter.apply(g) * scale));

        return this;
    }

    /**
     * Adds a drop-down selector that allows the user to choose which graph
     * in the list should be actively controlled.
     *
     * @param title the title displayed above the combo box
     * @param label the base label used to generate fallback item names
     * @return this builder instance for method chaining
     */
    public ControlBuilder<T> selector(String title, String label) {
        String[] items = new String[graphs.size()];

        for (int i = 0; i < items.length; i++)
            if (graphs.get(i).hasTitle()) {
                items[i] = graphs.get(i).getTitle();
            } else {
                items[i] = label + " " + (i + 1);
            }

        JComboBox<String> combo = new JComboBox<>(items);

        combo.setBackground(Colors.GRAY5);
        combo.setSelectedIndex(0);
        add(combo);

        combo.addActionListener(e -> {
            int index = combo.getSelectedIndex();
            if (index >= 0 && index < graphs.size()) {
                control.setActiveGraph(index);
                syncSlidersToActiveGraph();
                control.application.repaint();
            }
        });

        combo.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(0, 0, 0, 0), title));

        return this;
    }

    private void add(Component component) {
        panel.add(component, constraints);
        constraints.gridy++;
    }

    public JPanel getPanel() {
        return panel;
    }

    private JSlider createIntSlider(int min, int max, int value, String title) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }

        int range = max - min;
        int preferredTicks = computePreferredTickCount(min, max, 1.0);
        int tickSpacing = computeNiceIntTickSpacing(range, preferredTicks);
        int tickStart = (int) Math.floor((double) min / tickSpacing) * tickSpacing;
        JSlider slider = new JSlider(min, max, value);
        Dictionary<Integer, JComponent> labelTable = new Hashtable<>();

        if (tickSpacing < 1) {
            tickSpacing = 1;
        }

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setBorder(BorderFactory.createTitledBorder(title));

        for (int v = tickStart; v <= max; v += tickSpacing)
            if (v >= min) {
                labelTable.put(v, new JLabel(String.valueOf(v)));
            }

        if (min <= 0 && 0 <= max) {
            labelTable.put(0, new JLabel("0"));
        }

        // Always show min and max labels
        labelTable.put(min, new JLabel(String.valueOf(min)));
        labelTable.put(max, new JLabel(String.valueOf(max)));

        slider.setLabelTable(labelTable);

        return slider;
    }

    private void syncSlidersToActiveGraph() {
        T active = control.getActiveGraph();

        for (int i = 0; i < sliders.size(); i++) {
            JSlider slider = sliders.get(i);
            Function<T, Integer> getter = getters.get(i);
            int value = getter.apply(active);
            slider.setValue(value);
        }
    }

    private static int computePreferredTickCount(double min, double max, double step) {
        if (step <= 0 || min >= max) {
            return 5;
        }
        double range = max - min;
        long possibleValues = (long) Math.ceil(range / step) + 1;
        if (possibleValues <= 6) {
            return (int) possibleValues;
        }
        double logRange = Math.log10(range);
        int ticks = (int) Math.round(6.0 - 0.7 * Math.abs(logRange));
        return Math.max(4, Math.min(8, ticks));
    }

    private static double computeNiceTickSpacing(double range, int preferredTicks) {
        if (range <= 0) {
            return 1.0;
        }
        double raw = range / preferredTicks;
        double exponent = Math.floor(Math.log10(raw));
        double fraction = raw / Math.pow(10, exponent);
        double niceFraction;
        if (fraction <= 1.0) {
            niceFraction = 1.0;
        } else if (fraction <= 2.0) {
            niceFraction = 2.0;
        } else if (fraction <= 5.0) {
            niceFraction = 5.0;
        } else {
            niceFraction = 10.0;
        }
        return niceFraction * Math.pow(10, exponent);
    }

    private static int computeNiceIntTickSpacing(int range, int preferredTicks) {
        if (range <= 0) {
            return 1;
        }
        double raw = (double) range / preferredTicks;
        double exponent = Math.floor(Math.log10(raw));
        double fraction = raw / Math.pow(10, exponent);
        int niceFraction;
        if (fraction <= 1.0) {
            niceFraction = 1;
        } else if (fraction <= 2.0) {
            niceFraction = 2;
        } else if (fraction <= 5.0) {
            niceFraction = 5;
        } else {
            niceFraction = 10;
        }
        return (int) Math.max(1, niceFraction * Math.pow(10, exponent));
    }

    private static String formatDouble(double value) {
        if (Math.abs(value - Math.round(value)) < 1e-9) {
            return String.format("%.0f", value);
        } else if (Math.abs(value * 10 - Math.round(value * 10)) < 1e-9) {
            return String.format("%.1f", value);
        } else {
            return String.format("%.2f", value);
        }
    }
}
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
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl-3
 * .0.html>.
 */
package pr1.a07.plot;

import pr1.a07.Colors;
import pr1.a07.plot.components.LegendItem;
import pr1.a07.plot.components.ModernButton;
import pr1.a07.plot.components.ModernCheckBox;
import pr1.a07.plot.components.ModernLabeledBorder;
import pr1.a07.plot.components.ModernSlider;
import pr1.a07.plot.components.RoundedLineBorder;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
    private final Map<String, JTextField> outputs = new HashMap<>();
    private final Map<String, Function<T, String>> outputGetters = new HashMap<>();
    private final List<JSlider> sliders = new ArrayList<>();
    private final List<Function<T, Integer>> getters = new ArrayList<>();
    private final List<JCheckBox> checkBoxes = new ArrayList<>();
    private final List<Function<T, Boolean>> checkBoxGetters = new ArrayList<>();
    private final JPanel panel = new JPanel(new GridBagLayout());
    private final GridBagConstraints constraints = new GridBagConstraints();
    private int currentColumn = 0;
    private boolean inMultiColumnRow = false;

    /**
     * Constructs a new control builder for the given plot control and graph
     * list.
     *
     * @param control the associated plot control instance
     * @param graphs  the list of plot graphs to manage; must not be null or
     *                empty
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
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.setBackground(Colors.GRAY5);
    }

    /**
     * Ensures that the minimum and maximum values are always present as labels
     * in the slider's label table.
     *
     * @param labels    the label table to update
     * @param min       the minimum value
     * @param max       the maximum value
     * @param keyMapper function to map value to integer slider key
     * @param formatter function to format value as display string
     * @param <V>       the type of the min/max values (e.g., Integer or Double)
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
     * Computes a preferred number of ticks for a slider based on the value
     * range
     * and step size. Small ranges show all discrete values; larger ranges use
     * a logarithmic heuristic to yield 4–8 readable ticks.
     *
     * @param min  the minimum value of the range
     * @param max  the maximum value of the range
     * @param step the smallest increment (must be positive)
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
     * Computes a "nice" (human-readable) tick spacing such as 1, 2, 5, 10,
     * etc.,
     * based on the value range and desired number of ticks.
     *
     * @param range          the total range (max - min)
     * @param preferredTicks the approximate number of desired ticks
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
     * Formats a double value for display by omitting unnecessary decimal
     * places.
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

    public ControlBuilder<T> addDoubleColumn(String label) {
        return headline(label, Font.BOLD, 11f).addDoubleColumn();
    }

    public ControlBuilder<T> addDoubleColumn() {
        this.inMultiColumnRow = true;
        this.currentColumn = 0;
        constraints.weightx = .5;
        constraints.gridwidth = 1;
        return this;
    }

    /**
     * Returns the constructed control panel containing all added UI components.
     *
     * @return the control panel ready for embedding in a window
     */
    public JPanel getPanel() {
        return panel;
    }

    public ControlBuilder<T> divider() {
        return divider(10);
    }

    public ControlBuilder<T> divider(int height) {
        Component verticalSpacer = Box.createVerticalStrut(height);
        addComponent(verticalSpacer);
        return this;
    }

    public ControlBuilder<T> headline(String text) {
        return headline(text, Font.BOLD, 13f);
    }

    public ControlBuilder<T> headline(String text, int fontType,
                                      float fontSize) {
        JLabel headlineLabel = new JLabel(text);
        headlineLabel.setFont(headlineLabel.getFont().deriveFont(fontType,
                fontSize));
        headlineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addComponent(headlineLabel);
        return this;
    }

    public ControlBuilder<T> legend(Color color, String label) {
        JPanel legendItem = new JPanel();
        JComponent colorBox = new LegendItem(color);
        JLabel labelComponent = new JLabel(label);

        labelComponent.setFont(labelComponent.getFont().deriveFont(Font.PLAIN, 12f));
        legendItem.setLayout(new BoxLayout(legendItem, BoxLayout.X_AXIS));
        legendItem.setOpaque(false);
        legendItem.add(colorBox);
        legendItem.add(Box.createHorizontalStrut(10));
        legendItem.add(labelComponent);
        legendItem.add(Box.createHorizontalGlue());
        addComponent(legendItem);
        return this;
    }

    public ControlBuilder<T> button(String label, Consumer<T> action) {
        button(Colors.GRAY2, label, action);
        return this;
    }

    public ControlBuilder<T> buttonPrimary(String label, Consumer<T> action) {
        button(Colors.BLUE, label, action);
        return this;
    }

    public ControlBuilder<T> buttonSecondary(String label, Consumer<T> action) {
        button(Colors.GRAY6, label, action);
        return this;
    }

    public ControlBuilder<T> buttonSuccess(String label, Consumer<T> action) {
        button(Colors.DARKER_GREEN, label, action);
        return this;
    }

    public ControlBuilder<T> button(Color color, String label,
                                    Consumer<T> action) {
        JButton button = new ModernButton(label, color);

        addComponent(button);
        button.addActionListener(e -> {
            action.accept(control.getActiveGraph());
            control.application.repaint();
        });
        return this;
    }

    public ControlBuilder<T> outputTimed(String label, String key,
                                         Function<T, String> getter,
                                         int delayMs) {
        JTextField valueField = new JTextField("0.0 s");
        JPanel container = new JPanel(new BorderLayout());

        valueField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedLineBorder(Colors.GRAY3, 1.2f, 12),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        valueField.setEditable(false);
        valueField.setFocusable(false);
        container.setBackground(Colors.GRAY5);
        container.setBorder(new ModernLabeledBorder(label));
        container.add(valueField, BorderLayout.CENTER);
        outputs.put(key, valueField);
        Timer timer = new Timer(delayMs, e -> {
            T active = control.getActiveGraph();
            String value = getter.apply(active);
            valueField.setText(value);
        });
        timer.start();
        addComponent(container);
        return this;
    }

    public ControlBuilder<T> checkbox(
            String label,
            Function<T, Boolean> getter,
            BiConsumer<T, Boolean> setter
    ) {
        boolean initialValue = getter.apply(activeGraph);
        JCheckBox checkBox = new ModernCheckBox(label, initialValue);

        checkBox.setBackground(Colors.GRAY5);
        checkBox.setFocusPainted(false);
        addComponent(checkBox);
        checkBox.addActionListener(e -> {
            boolean newValue = checkBox.isSelected();

            setter.accept(control.getActiveGraph(), newValue);
            control.application.repaint();
        });
        checkBoxes.add(checkBox);
        checkBoxGetters.add(getter);
        return this;
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
        addComponent(slider);
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
        JSlider slider = new ModernSlider(intMin, intMax, initial);
        Dictionary<Integer, JComponent> labels = new Hashtable<>();

        slider.setBackground(Colors.GRAY5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setBorder(new ModernLabeledBorder(label, true));
        double tickStart =
                Math.floor(min / (tickSpacing / scale)) * (tickSpacing / scale);

        for (double v = tickStart; v <= max + 1e-9; v += tickSpacing / scale) {
            if (v >= min - 1e-9) {
                labels.put((int) Math.round(v * scale), new JLabel(formatDouble(v)));
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
        addComponent(slider);
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
        combo.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(0, 0,
                0, 0), title));
        addComponent(combo);
        combo.addActionListener(e -> {
            int index = combo.getSelectedIndex();
            if (index >= 0 && index < graphs.size()) {
                control.setActiveGraph(index);
                syncCheckBoxesToActiveGraph();
                syncSlidersToActiveGraph();
                syncOutputsToActiveGraph();
                control.application.repaint();
            }
        });
        return this;
    }

    private void endRow() {
        if (inMultiColumnRow) {
            constraints.gridy++;
            currentColumn = 0;
            constraints.gridwidth = 2;
            constraints.weightx = 1.0;
            inMultiColumnRow = false;
        }
    }

    private void addComponent(Component comp) {
        constraints.gridx = currentColumn;
        panel.add(comp, constraints);
        if (!inMultiColumnRow) {
            constraints.gridy++;
        } else {
            currentColumn++;
            if (currentColumn >= 2) {
                endRow();
            }
        }
    }

    private void updateOutput(String key, String value) {
        JTextField field = outputs.get(key);

        if (field != null) {
            field.setText(value);
        }
    }

    /**
     * Attaches a change listener to a slider that updates the active graph
     * and triggers a repaint.
     *
     * @param slider    the slider to attach the listener to
     * @param intGetter function to extract integer state from a graph (for
     *                  sync)
     * @param mapper    function to convert slider integer value to parameter
     *                  type
     * @param setter    function to apply the new value to the graph
     * @param <V>       the parameter type (Integer or Double)
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

    private void syncOutputsToActiveGraph() {
        T active = control.getActiveGraph();

        for (String key : outputs.keySet()) {
            Function<T, String> getter = outputGetters.get(key);

            if (getter != null) {
                String value = getter.apply(active);
                JTextField field = outputs.get(key);

                if (field != null) {
                    field.setText(value);
                }
            }
        }
    }

    /**
     * Synchronizes all checkboxes to reflect the current state of the active
     * graph.
     */
    private void syncCheckBoxesToActiveGraph() {
        T active = control.getActiveGraph();

        for (int i = 0; i < checkBoxes.size(); i++) {
            boolean modelValue = checkBoxGetters.get(i).apply(active);

            checkBoxes.get(i).setSelected(modelValue);
        }
    }

    /**
     * Synchronizes all sliders to reflect the current state of the active
     * graph.
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
        JSlider slider = new ModernSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setBorder(new ModernLabeledBorder(title, true));
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
}
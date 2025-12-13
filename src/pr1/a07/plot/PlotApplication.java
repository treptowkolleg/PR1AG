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

import pr1.a07.plot.components.ModernButton;
import pr1.a08.Colors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
 * <p><strong>Note:</strong> This class is a work in progress. Future
 * enhancements may include support for UI navigation (e.g., menus or
 * toolbars to select
 * active plot sets), persistence of plot configurations, and improved lifecycle
 * management of controls.</p>
 */
public class PlotApplication extends JFrame {

    /**
     * Horizontal offset (in pixels) applied to the origin of all drawings.
     * This value is shared globally across all drawable objects to enable
     * panning of the entire coordinate system.
     */
    public static double X_DELTA = 0;

    /**
     * Vertical offset (in pixels) applied to the origin of all drawings.
     * This value is shared globally across all drawable objects to enable
     * panning of the entire coordinate system.
     */
    public static double Y_DELTA = 0;

    private final DrawablePanel panel = new DrawablePanel();
    private final List<PlotSet<?>> plotSets = new ArrayList<>();
    private final Map<PlotSet<?>, PlotControl<?>> controlMap = new HashMap<>();
    private final JLabel plotLabel = new JLabel("plot name");
    private final JButton resetBtn = new ModernButton("Reset", Colors.BLUE);
    private final JButton nextBtn = new ModernButton(">>");
    private final JButton prevBtn = new ModernButton("<<");
    private final ModernButton toggleControlBtn = new ModernButton("Steuerung"
            , Colors.BLUE);
    private Point dragStart = null;
    private PlotSet<?> activeSet = null;
    private int currentPlot = 0;
    private PlotControl<?> control;

    /**
     * Constructs a new plot application window with default properties.
     * Initializes the drawing panel, sets the window title, size, and close
     * behavior, and centers the window on the screen.
     */
    public PlotApplication() {
        this(800, 600);
    }

    /**
     * Constructs a new plot application window with the specified title and
     * default size.
     *
     * @param title the title to display in the window's title bar
     */
    public PlotApplication(String title) {
        this(title, 800, 600);
    }

    /**
     * Constructs a new plot application window with the specified dimensions
     * and a default title.
     *
     * @param width  the initial width of the window in pixels
     * @param height the initial height of the window in pixels
     */
    public PlotApplication(int width, int height) {
        this("Plot Application", width, height);
    }

    /**
     * Constructs a new plot application window with the specified title and
     * dimensions.
     *
     * @param title  the title to display in the window's title bar
     * @param width  the initial width of the window in pixels
     * @param height the initial height of the window in pixels
     */
    public PlotApplication(String title, int width, int height) {
        JPanel btnContainer = new JPanel();
        JPanel infoContainer = new JPanel();

        btnContainer.setLayout(new BoxLayout(btnContainer, BoxLayout.X_AXIS));
        btnContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btnContainer.setBackground(Colors.GRAY5);
        infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.X_AXIS));
        infoContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoContainer.setBackground(Colors.GRAY5);
        infoContainer.add(new JLabel("Sie können das Koordinatensystem per " +
                "Drag-and-Drop verschieben."));
        panel.setBackground(Colors.GRAY6);
        setMinimumSize(new Dimension(800, 600));
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nextBtn.addActionListener(this::nextPlotSet);
        prevBtn.addActionListener(this::prevPlotSet);
        toggleControlBtn.addActionListener(this::toggleControl);
        resetBtn.addActionListener(this::resetMove);
        resetBtn.setEnabled(false);
        btnContainer.add(prevBtn);
        btnContainer.add(Box.createRigidArea(new Dimension(5, 0)));
        btnContainer.add(nextBtn);
        btnContainer.add(Box.createRigidArea(new Dimension(15, 0)));
        btnContainer.add(toggleControlBtn);
        btnContainer.add(Box.createRigidArea(new Dimension(15, 0)));
        btnContainer.add(plotLabel);
        btnContainer.add(Box.createHorizontalGlue());
        btnContainer.add(Box.createRigidArea(new Dimension(5, 0)));
        btnContainer.add(resetBtn);
        add(panel);
        add(btnContainer, BorderLayout.NORTH);
        add(infoContainer, BorderLayout.SOUTH);

        setSize(width, height);
        setLocationRelativeTo(null);
        setupMouseListener();
    }

    /**
     * Sets up mouse listeners on the drawing panel to enable drag-and-drop
     * panning of the coordinate system. The offset values {@link #X_DELTA}
     * and {@link #Y_DELTA} are updated in real time during dragging,
     * and the reset button state is refreshed when dragging ends.
     */
    public void setupMouseListener() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
                updateResetButtonState();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart == null) return;
                Point current = e.getPoint();
                int dx = current.x - dragStart.x;
                int dy = current.y - dragStart.y;
                double scale = 1.0;

                PlotApplication.X_DELTA += dx * scale;
                PlotApplication.Y_DELTA += dy * scale;
                dragStart = current;
                panel.repaint();
            }
        });
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
     * @param set the plot set to activate; must be registered via
     *            {@link #addPlotSet}
     */
    public void switchToSet(PlotSet<?> set) {
        if (activeSet != null) {
            PlotControl<?> oldControl = controlMap.get(activeSet);
            if (oldControl != null) oldControl.setVisible(false);
        }
        activeSet = set;
        plotLabel.setText(activeSet.getTitle());
        panel.clearDrawables();
        panel.addDrawable(set.getGrid());
        panel.addDrawables(set.getGraphs());
        control = controlMap.computeIfAbsent(set, this::createControl);

        if (control != null) {
            control.setVisible(true);
            toggleControlBtn.setBaseColor(Colors.BLUE);
            toggleControlBtn.setEnabled(true);
        } else {
            toggleControlBtn.setEnabled(false);
        }
        if (set.getGraphs().size() == 1) {
            nextBtn.setEnabled(false);
            prevBtn.setEnabled(false);
        } else {
            nextBtn.setEnabled(true);
            prevBtn.setEnabled(true);
        }
        panel.repaint();
    }

    /**
     * Toggles the visibility of the currently active plot control window.
     * Updates the button color to reflect the current state (blue = visible,
     * gray = hidden).
     *
     * @param e the action event triggering this method (ignored)
     */
    public void toggleControl(ActionEvent e) {
        if (null != control) {
            boolean willBeVisible = !control.isVisible();
            control.setVisible(willBeVisible);
            toggleControlBtn.setBaseColor(willBeVisible ? Colors.BLUE :
                    Colors.GRAY2);
        }
    }

    /**
     * Cycles to the next registered plot set in a circular manner.
     * If the last set is active, it wraps around to the first one.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void nextPlotSet(ActionEvent e) {
        if (currentPlot < plotSets.size() - 1) {
            currentPlot++;
        } else {
            currentPlot = 0;
        }
        switchToSet(plotSets.get(currentPlot));
    }

    /**
     * Cycles to the previous registered plot set in a circular manner.
     * If the first set is active, it wraps around to the last one.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void prevPlotSet(ActionEvent e) {
        if (currentPlot > 0) {
            currentPlot--;
        } else {
            currentPlot = plotSets.size() - 1;
        }
        switchToSet(plotSets.get(currentPlot));
    }

    /**
     * Resets the coordinate system offset to zero (centered view).
     * Disables the reset button afterward and triggers a repaint.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void resetMove(ActionEvent e) {
        X_DELTA = 0;
        Y_DELTA = 0;
        updateResetButtonState();
        panel.repaint();
    }

    /**
     * Starts the application by making the main window visible.
     */
    public void start() {
        setVisible(true);
    }

    /**
     * Delegates control creation to the plot set itself via its
     * {@link PlotSet#createControl(PlotApplication)} method.
     * This method is called only once per plot set, when first needed.
     *
     * @param set the plot set for which to create a control
     * @return a new {@link PlotControl} instance, or {@code null} if the set
     * does not require user interaction
     */
    private PlotControl<?> createControl(PlotSet<?> set) {
        return set.createControl(this);
    }

    /**
     * Updates the enabled state of the reset button based on whether the
     * coordinate system has been moved from its origin (0,0).
     * The button is enabled only if at least one of {@link #X_DELTA} or
     * {@link #Y_DELTA} differs from zero (within a small tolerance to
     * account for floating-point precision).
     */
    private void updateResetButtonState() {
        boolean isAtOrigin =
                (Math.abs(X_DELTA) < 1e-9) && (Math.abs(Y_DELTA) < 1e-9);
        resetBtn.setEnabled(!isAtOrigin);
    }
}
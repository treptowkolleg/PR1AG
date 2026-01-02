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
package treptowkolleg.plot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Cursor;
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

import static pr1.a07.CMath.scaleFactor;

/**
 * The main application window that coordinates rendering and user interaction
 * for multiple plot sets. This class manages a central drawing panel,
 * maintains a registry of {@link PlotSet} instances, and handles switching
 * between them while automatically showing or hiding the corresponding
 * {@link PlotControl} windows.
 *
 * <p><strong>Note:</strong> This class is a work in progress. Future
 * enhancements may include support for UI navigation (e.g., menus or
 * toolbars to select active plot sets), persistence of plot configurations,
 * and improved lifecycle management of controls.</p>
 */
public class PlotApplication extends JFrame {
    public static final double MIN_SCALE = .2;
    public static final double MAX_SCALE = 6;
    public static double X_DELTA = 0;
    public static double Y_DELTA = 0;
    public static double X_SCALE = 1;
    public static double Y_SCALE = 1;
    private final DrawablePanel panel = new DrawablePanel();
    private final PlotButtonPanel buttonPanel;
    private final PlotInfoPanel infoPanel;
    private final List<PlotSet<?>> plotSets = new ArrayList<>();
    private final Map<PlotSet<?>, PlotControl<?>> controlMap = new HashMap<>();
    private Timer zoomTimer = null;
    private double targetXScale = 1.0;
    private double targetYScale = 1.0;
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
        this(title, width, height, false);
    }

    /**
     * Constructs a new plot application window with the specified title and
     * dimensions.
     *
     * @param title                    the title to display in the window's
     *                                 title bar
     * @param width                    the initial width of the window in pixels
     * @param height                   the initial height of the window in
     *                                 pixels
     * @param disableExtendedFunctions use extended functions or not
     */
    public PlotApplication(String title, int width, int height,
                           boolean disableExtendedFunctions) {
        buttonPanel = new PlotButtonPanel();
        infoPanel = new PlotInfoPanel();
        if (disableExtendedFunctions) {
            disableExtendedFunctions();
        } else {
            add(infoPanel, BorderLayout.SOUTH);
            setupEventHandlers();
            setupMouseListener();
        }
        updateResetButtonState();
        updateZoomButtonState();
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
        configureWindowProperties(title, width, height);
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
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragStart = null;
                updateResetButtonState();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragStart == null) {
                    return;
                }
                Point current = e.getPoint();
                int dx = current.x - dragStart.x;
                int dy = current.y - dragStart.y;

                PlotApplication.X_DELTA += dx;
                PlotApplication.Y_DELTA += dy;
                dragStart = current;
                panel.repaint();
            }
        });
        panel.addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            double sensitivity = 0.55;
            double exponent = Math.abs(notches) * sensitivity;
            double factor = notches < 0
                    ? Math.pow(1.2, exponent)
                    : Math.pow(1.0 / 1.2, exponent);

            applyZoom(factor, e.isControlDown());
            startZoomAnimation();
        });
    }

    /**
     * Adds a plot set to the application's registry.
     * If no set is currently active, the added set becomes the active one
     * and is immediately displayed.
     *
     * @param set the plot set to add; must not be null
     * @throws NullPointerException if {@code set} is {@code null}
     */
    public void addPlotSet(PlotSet<?> set) {
        if (set == null) {
            throw new NullPointerException("PlotSet must not be null");
        }
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
     *            {@link #addPlotSet(PlotSet)}
     * @throws IllegalArgumentException if the set is not registered
     */
    public void switchToSet(PlotSet<?> set) {
        if (!plotSets.contains(set)) {
            throw new IllegalArgumentException("PlotSet is not registered");
        }
        if (activeSet != null) {
            PlotControl<?> oldControl = controlMap.get(activeSet);

            activeSet.postSetup(this);
            if (oldControl != null) {
                oldControl.setVisible(false);
            }
        }
        activeSet = set;

        buttonPanel.setPlotTitle(activeSet.getTitle());
        panel.clearDrawables();
        panel.addDrawable(set.getGrid());
        panel.addDrawables(set.getGraphs());
        control = controlMap.computeIfAbsent(set, this::createControl);
        if (control != null) {
            control.setVisible(true);
            buttonPanel.setToggleControlButtonColor(Colors.BLUE);
            buttonPanel.setToggleControlButtonEnabled(true);
        } else {
            buttonPanel.setToggleControlButtonEnabled(false);
        }
        activeSet.preSetup(this);
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
        if (control != null) {
            boolean willBeVisible = !control.isVisible();

            control.setVisible(willBeVisible);
            buttonPanel.setToggleControlButtonColor(willBeVisible ?
                    Colors.BLUE :
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
        currentPlot = (currentPlot + 1) % plotSets.size();
        switchToSet(plotSets.get(currentPlot));
    }

    /**
     * Cycles to the previous registered plot set in a circular manner.
     * If the first set is active, it wraps around to the last one.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void prevPlotSet(ActionEvent e) {
        currentPlot = (currentPlot - 1 + plotSets.size()) % plotSets.size();
        switchToSet(plotSets.get(currentPlot));
    }

    public JPanel getDrawablePanel() {
        return panel;
    }

    /**
     * Resets the coordinate system offset and scale to their defaults
     * (zero offset, unit scale). Disables the reset button afterward and
     * triggers a repaint.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void reset(ActionEvent e) {
        X_DELTA = 0;
        Y_DELTA = 0;
        X_SCALE = 1;
        Y_SCALE = 1;
        targetXScale = 1;
        targetYScale = 1;
        updateZoomInfo();
        updateZoomButtonState();
        updateResetButtonState();
        panel.repaint();
    }

    /**
     * Zooms in uniformly on both X and Y axes by a factor of 1.2.
     * Triggers a smooth zoom animation.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void zoomIn(ActionEvent e) {
        applyZoom(1.2, true);
        applyZoom(1.2, false);
        startZoomAnimation();
    }

    /**
     * Zooms out uniformly on both X and Y axes by a factor of 0.8.
     * Triggers a smooth zoom animation.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void zoomOut(ActionEvent e) {
        applyZoom(0.8, true);
        applyZoom(0.8, false);
        startZoomAnimation();
    }

    /**
     * Visually disables the toggle control button by changing its color to
     * gray, indicating that the associated control window is no longer
     * available
     * (e.g., because it was closed externally).
     * <p>
     * Note: This method does <em>not</em> disable the button's functionality;
     * it only updates its appearance.
     */
    public void softDisableToggleControlButton() {
        buttonPanel.setToggleControlButtonColor(Colors.GRAY2);
    }

    public void setArduinoEnabled(boolean enabled) {
        buttonPanel.setArduinoAvailable(enabled);
    }

    public void disableExtendedFunctions() {
        buttonPanel.setZoomInButtonEnabled(false);
        buttonPanel.setZoomOutButtonEnabled(false);
        buttonPanel.setResetButtonEnabled(false);
    }

    /**
     * Starts the application by making the main window visible.
     * Also configures navigation button states based on the number of
     * registered plot sets (disables next/prev buttons if only one set exists).
     */
    public void start() {
        boolean singleSet = plotSets.size() == 1;
        buttonPanel.setNavigationEnabled(!singleSet);
        setVisible(true);
    }

    private PlotControl<?> createControl(PlotSet<?> set) {
        return set.createControl(this);
    }

    private void applyZoom(double factor, boolean isXAxis) {
        if (isXAxis) {
            targetXScale *= factor;
            targetXScale = Math.max(MIN_SCALE, Math.min(MAX_SCALE,
                    targetXScale));
        } else {
            targetYScale *= factor;
            targetYScale = Math.max(MIN_SCALE, Math.min(MAX_SCALE,
                    targetYScale));
        }
    }

    private void updateResetButtonState() {
        final double EPSILON = 1e-9;
        boolean isAtOrigin = Math.abs(X_DELTA) < EPSILON &&
                Math.abs(Y_DELTA) < EPSILON &&
                Math.abs(X_SCALE - 1.0) < EPSILON &&
                Math.abs(Y_SCALE - 1.0) < EPSILON;

        buttonPanel.setResetButtonEnabled(!isAtOrigin);
    }

    private void updateZoomButtonState() {
        final double EPSILON = 1e-9;
        boolean isMinimum =
                scaleFactor(MAX_SCALE, X_SCALE) <= MIN_SCALE + EPSILON &&
                        scaleFactor(MAX_SCALE, Y_SCALE) <= MIN_SCALE + EPSILON;
        boolean isMaximum =
                scaleFactor(MAX_SCALE, X_SCALE) >= MAX_SCALE - EPSILON &&
                        scaleFactor(MAX_SCALE, Y_SCALE) >= MAX_SCALE - EPSILON;

        buttonPanel.setZoomInButtonEnabled(!isMaximum);
        buttonPanel.setZoomOutButtonEnabled(!isMinimum);
    }

    private void startZoomAnimation() {
        if (zoomTimer != null) {
            zoomTimer.stop();
        }
        zoomTimer = new Timer(10, e -> {
            X_SCALE += (targetXScale - X_SCALE) * 0.2;
            Y_SCALE += (targetYScale - Y_SCALE) * 0.2;
            if (Math.abs(X_SCALE - targetXScale) < 1e-4 &&
                    Math.abs(Y_SCALE - targetYScale) < 1e-4) {
                X_SCALE = targetXScale;
                Y_SCALE = targetYScale;
                zoomTimer.stop();
                zoomTimer = null;
            }
            updateZoomInfo();
            updateZoomButtonState();
            updateResetButtonState();
            panel.repaint();
        });
        zoomTimer.start();
    }

    private void configureWindowProperties(String title, int width,
                                           int height) {
        panel.setBackground(Colors.GRAY6);
        setIconImage(Icon.get(Icon.TK_LOGO));
        setMinimumSize(new Dimension(800, 600));
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    private void setupEventHandlers() {
        buttonPanel.addNextActionListener(this::nextPlotSet);
        buttonPanel.addPrevActionListener(this::prevPlotSet);
        buttonPanel.addZoomInActionListener(this::zoomIn);
        buttonPanel.addZoomOutActionListener(this::zoomOut);
        buttonPanel.addToggleControlActionListener(this::toggleControl);
        buttonPanel.addResetActionListener(this::reset);
    }

    private void updateZoomInfo() {
        infoPanel.getZoomInfo().setText(String.format("%.0f%% | %.0f%%",
                scaleFactor(MAX_SCALE, X_SCALE) * 100,
                scaleFactor(MAX_SCALE, Y_SCALE) * 100
        ));
    }
}
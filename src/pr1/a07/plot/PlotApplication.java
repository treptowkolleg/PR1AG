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
import pr1.a07.plot.components.ModernButton;
import pr1.a07.plot.components.ModernIconButton;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    public static double X_DELTA = 0;
    public static double Y_DELTA = 0;
    public static double X_SCALE = 1;
    public static double Y_SCALE = 1;
    private static final double MIN_SCALE = 1;
    private static final double MAX_SCALE = 6;
    private final DrawablePanel panel = new DrawablePanel();
    private final List<PlotSet<?>> plotSets = new ArrayList<>();
    private final Map<PlotSet<?>, PlotControl<?>> controlMap = new HashMap<>();
    private final JLabel plotLabel = new JLabel("plot name");
    private final JButton resetBtn = new ModernButton("Reset", Colors.BLUE);
    private final JButton nextBtn = new ModernIconButton("/icons/icons8" +
            "-doppelt-rechts-16.png");
    private final JButton prevBtn = new ModernIconButton("/icons/icons8" +
            "-doppelt-links-16.png");
    private final JButton zoomInBtn = new ModernIconButton("/icons/icons8" +
            "-hineinzoomen-16.png");
    private final JButton zoomOutBtn = new ModernIconButton("/icons/icons8" +
            "-rauszoomen-16.png");
    private final ModernButton toggleControlBtn = new ModernButton("Steuerung"
            , Colors.BLUE);
    private final JLabel zoomInfoText = new JLabel();
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
        JPanel btnContainer = new JPanel();
        JPanel infoContainer = new JPanel();
        JLabel infoText = new JLabel();

        btnContainer.setLayout(new BoxLayout(btnContainer, BoxLayout.X_AXIS));
        btnContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btnContainer.setBackground(Colors.GRAY5);
        infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.X_AXIS));
        infoContainer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoContainer.setBackground(Colors.GRAY5);
        infoText.setForeground(Colors.GRAY);
        infoText.setText("Verschieben: Ziehen | Zoomen: Strg+Mausrad bzw. nur" +
                " Mausrad");
        zoomInfoText.setForeground(Colors.GRAY);
        infoContainer.add(infoText);
        infoContainer.add(Box.createHorizontalGlue());
        updateZoomInfo();
        updateZoomButtonState();
        infoContainer.add(zoomInfoText);
        panel.setBackground(Colors.GRAY6);
        setMinimumSize(new Dimension(800, 600));
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nextBtn.addActionListener(this::nextPlotSet);
        prevBtn.addActionListener(this::prevPlotSet);
        zoomInBtn.addActionListener(this::zoomIn);
        zoomOutBtn.addActionListener(this::zoomOut);
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
        btnContainer.add(Box.createRigidArea(new Dimension(15, 0)));
        btnContainer.add(zoomOutBtn);
        btnContainer.add(Box.createRigidArea(new Dimension(5, 0)));
        btnContainer.add(zoomInBtn);
        btnContainer.add(Box.createRigidArea(new Dimension(15, 0)));
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
                double scale = 1.0;

                PlotApplication.X_DELTA += dx * scale;
                PlotApplication.Y_DELTA += dy * scale;
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

    /**
     * Resets the coordinate system offset to zero (centered view).
     * Disables the reset button afterward and triggers a repaint.
     *
     * @param e the action event triggering this method (ignored)
     */
    public void resetMove(ActionEvent e) {
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

    public void zoomIn(ActionEvent e) {
        applyZoom(1.2, true);
        applyZoom(1.2, false);
        startZoomAnimation();
    }

    public void zoomOut(ActionEvent e) {
        applyZoom(.8, true);
        applyZoom(.8, false);
        startZoomAnimation();
    }

    public void softDisableToggleControlButton() {
        toggleControlBtn.setBaseColor(Colors.GRAY2);
    }

    public void setEnabled(JComponent component, boolean enabled) {
        component.setEnabled(enabled);
    }

    /**
     * Starts the application by making the main window visible.
     */
    public void start() {
        boolean singleSet = plotSets.size() == 1;

        nextBtn.setEnabled(!singleSet);
        prevBtn.setEnabled(!singleSet);
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

    /**
     * Updates the enabled state of the reset button based on whether the
     * coordinate system has been moved from its origin (0,0).
     * The button is enabled only if at least one of {@link #X_DELTA} or
     * {@link #Y_DELTA} differs from zero (within a small tolerance to
     * account for floating-point precision).
     */
    private void updateResetButtonState() {
        final double EPSILON = 1e-9;
        boolean isAtOrigin = Math.abs(X_DELTA) < EPSILON &&
                Math.abs(Y_DELTA) < EPSILON &&
                Math.abs(X_SCALE - 1.0) < EPSILON &&
                Math.abs(Y_SCALE - 1.0) < EPSILON;

        resetBtn.setEnabled(!isAtOrigin);
    }

    private void updateZoomButtonState() {
        final double EPSILON = 1e-9;
        boolean isMinimum = scaleFactor(MAX_SCALE, X_SCALE) == MIN_SCALE &&
                scaleFactor(MAX_SCALE, Y_SCALE) <= MIN_SCALE + EPSILON;
        boolean isMaximum = scaleFactor(MAX_SCALE, X_SCALE) == MAX_SCALE &&
                scaleFactor(MAX_SCALE, Y_SCALE) >= MAX_SCALE - EPSILON;

        zoomInBtn.setEnabled(!isMaximum);
        zoomOutBtn.setEnabled(!isMinimum);
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

    private void updateZoomInfo() {
        zoomInfoText.setText(String.format("%.0f%% | %.0f%%",
                scaleFactor(MAX_SCALE, X_SCALE) * 100,
                scaleFactor(MAX_SCALE, Y_SCALE) * 100
        ));
    }

    private double scaleFactor(double maxScale, double axisScale) {
        return 1.0 + maxScale * (axisScale - 1.0) / axisScale;
    }
}
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
package treptowkolleg.edu.swing.graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for simple graphical Swing applications with plot navigation.
 * <p>
 * This class provides a ready-to-use window with a drawing area and a control
 * panel containing "Next" and "Previous" buttons to cycle through a sequence of
 * drawable plot lists. Each plot list contains a collection of {@link Drawable}
 * objects that can be rendered on the {@link DrawingPanel}. The class handles
 * basic window configuration, layout, and navigation logic.
 * </p>
 * <p>
 * Subclasses typically populate the application with plot data using
 * {@link #addPlotList(List)} and call {@link #showDrawing()} to display the
 * initial state.
 * </p>
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @see DrawingPanel
 * @see Drawable
 * @see Controllable
 * @see JFrame
 * @since 2025
 */
public class GraphicsApplication extends JFrame {
    protected final DrawingPanel panel;
    protected final JPanel controlPanel;
    protected final JButton nextBtn;
    protected final JButton prevBtn;
    protected ArrayList<List<Drawable>> plotLists;
    protected int lastPlot = 0;
    protected int currentPlot = 0;

    /**
     * Constructs a new {@code GraphicsApplication} with a default window size
     * of 800×600 pixels.
     *
     * @see #GraphicsApplication(int, int)
     */
    public GraphicsApplication() {
        this(800, 600);
    }

    /**
     * Constructs a new {@code GraphicsApplication} with the specified window
     * dimensions.
     *
     * @param width  the preferred width of the application window.
     * @param height the preferred height of the application window.
     */
    public GraphicsApplication(int width, int height) {
        plotLists = new ArrayList<>();
        panel = new DrawingPanel();
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.LIGHT_GRAY);
        nextBtn = new JButton("Nächster PlotGraph");
        nextBtn.setPreferredSize(new Dimension(200, 30));
        nextBtn.addActionListener(this::nextPlot);
        prevBtn = new JButton("Vorheriger PlotGraph");
        prevBtn.setPreferredSize(new Dimension(200, 30));
        prevBtn.addActionListener(this::prevPlot);
        controlPanel.add(prevBtn);
        controlPanel.add(nextBtn);
        setTitle("GraphicsApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(600, 300));
        add(panel);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds a new plot list to the internal sequence of drawable collections.
     * <p>
     * Each plot list represents one visual state that can be displayed using
     * the navigation controls.
     *
     * @param drawings a {@link List} of {@link Drawable} objects to be rendered
     *                 as a single plot.
     * @see Drawable
     */
    public void addPlotList(List<Drawable> drawings) {
        this.plotLists.add(drawings);
    }

    /**
     * Advances to the next plot in the sequence, wrapping around to the first
     * plot if the end is reached.
     * <p>
     * This method is typically triggered by the "Next PlotGraph" button and updates
     * the currently displayed drawings accordingly.
     *
     * @param e the {@link ActionEvent} that triggered this navigation
     *          (ignored).
     * @see #setDrawings(List)
     */
    public void nextPlot(ActionEvent e) {
        if (currentPlot < plotLists.size() - 1) {
            lastPlot = currentPlot;
            currentPlot++;
        } else {
            lastPlot = plotLists.size() - 1;
            currentPlot = 0;
        }
        setDrawings(plotLists.get(currentPlot));
        repaint();
    }

    /**
     * Navigates to the previous plot in the sequence, wrapping around to the
     * last plot if the beginning is reached.
     * <p>
     * This method is typically triggered by the "Previous PlotGraph" button and
     * updates the currently displayed drawings accordingly.
     *
     * @param e the {@link ActionEvent} that triggered this navigation
     *          (ignored).
     * @see #setDrawings(List)
     */
    public void prevPlot(ActionEvent e) {
        if (currentPlot > 0) {
            lastPlot = currentPlot;
            currentPlot--;
        } else {
            lastPlot = 0;
            currentPlot = plotLists.size() - 1;
        }
        setDrawings(plotLists.get(currentPlot));
        repaint();
    }

    /**
     * Adds a drawable object to the drawing panel.
     * <p>
     * This internal helper method delegates to the panel and is used when
     * displaying the current plot.
     *
     * @param object the {@link Drawable} instance to add.
     */
    private void add(Drawable object) {
        panel.add(object);
    }

    /**
     * Replaces the current drawings on the panel with the given list and
     * updates
     * visibility of associated control panels.
     * <p>
     * This method hides control panels of the previously active plot and shows
     * those of the new one.
     *
     * @param drawings the {@link List} of {@link Drawable} objects to display.
     * @see Controllable#showControlPanel()
     * @see Controllable#hideControlPanel()
     */
    public void setDrawings(List<Drawable> drawings) {
        toggleControlPanels();
        panel.setDrawings(drawings);
    }

    /**
     * Finalizes the visual setup and displays the application window.
     * <p>
     * This method hides control panels of the last plot, adds all drawable
     * objects of the current plot to the drawing panel, sizes the window to its
     * preferred dimensions, centers it on screen, and makes it visible.
     */
    public void showDrawing() {
        toggleControlPanels();
        plotLists.get(currentPlot).forEach(this::add);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Toggles the visibility of control panels between the last and current
     * plot.
     * <p>
     * Control panels associated with the previously displayed plot are hidden,
     * while those of the newly active plot are shown.
     */
    public void toggleControlPanels() {
        plotLists.get(lastPlot).forEach(Controllable::hideControlPanel);
        plotLists.get(currentPlot).forEach(Controllable::showControlPanel);
    }
}
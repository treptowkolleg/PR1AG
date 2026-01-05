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
package treptowkolleg.edu.swing.plot;

import treptowkolleg.edu.swing.components.ModernButton;
import treptowkolleg.edu.swing.components.ModernIconButton;
import treptowkolleg.edu.swing.graphics.Colors;
import treptowkolleg.edu.swing.graphics.Icon;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * A panel containing navigation, zoom, control toggle, and status buttons for the plot application.
 * This panel provides a consistent UI for switching between plot sets, adjusting view scale,
 * toggling control windows, and indicating hardware availability (e.g., Arduino).
 */
public class PlotButtonPanel extends JPanel {
    protected final ModernButton nextBtn;
    protected final ModernButton prevBtn;
    protected final ModernButton zoomInBtn;
    protected final ModernButton zoomOutBtn;
    protected final ModernButton resetBtn;
    protected final ModernButton toggleControlBtn;
    protected final ModernButton arduinoPresentButton;
    protected final JLabel plotLabel = new JLabel("plot name");

    /**
     * Constructs a new PlotButtonPanel with default buttons and layout.
     * All buttons are initialized with appropriate icons or labels and styled using
     * the application's color scheme. The Arduino button is initially disabled.
     */
    public PlotButtonPanel() {
        nextBtn = new ModernIconButton(Icon.CHEVRON_RIGHT);
        prevBtn = new ModernIconButton(Icon.CHEVRON_LEFT);
        zoomInBtn = new ModernIconButton(Icon.ZOOM_IN);
        zoomOutBtn = new ModernIconButton(Icon.ZOOM_OUT);
        arduinoPresentButton = new ModernIconButton(Icon.ARDUINO);
        resetBtn = new ModernButton("Reset", Colors.BLUE);
        toggleControlBtn = new ModernButton("Steuerung", Colors.BLUE);
        init();
    }

    /**
     * Returns the "Next" navigation button used to switch to the next plot set.
     *
     * @return the next button instance
     */
    public ModernButton getNextBtn() {
        return nextBtn;
    }

    /**
     * Returns the "Previous" navigation button used to switch to the previous plot set.
     *
     * @return the previous button instance
     */
    public ModernButton getPrevBtn() {
        return prevBtn;
    }

    /**
     * Returns the "Zoom In" button used to increase the scale of the plot.
     *
     * @return the zoom-in button instance
     */
    public ModernButton getZoomInBtn() {
        return zoomInBtn;
    }

    /**
     * Returns the "Zoom Out" button used to decrease the scale of the plot.
     *
     * @return the zoom-out button instance
     */
    public ModernButton getZoomOutBtn() {
        return zoomOutBtn;
    }

    /**
     * Returns the "Reset" button used to restore the default view (centered, no zoom).
     *
     * @return the reset button instance
     */
    public ModernButton getResetBtn() {
        return resetBtn;
    }

    /**
     * Returns the "Steuerung" (Control) toggle button used to show or hide the control panel.
     *
     * @return the toggle control button instance
     */
    public ModernButton getToggleControlBtn() {
        return toggleControlBtn;
    }

    /**
     * Returns the label that displays the current plot set's title.
     *
     * @return the plot title label
     */
    public JLabel getPlotLabel() {
        return plotLabel;
    }

    /**
     * Adds an action listener to the "Next" button.
     *
     * @param listener the listener to receive action events
     */
    public void addNextActionListener(ActionListener listener) {
        nextBtn.addActionListener(listener);
    }

    /**
     * Adds an action listener to the "Previous" button.
     *
     * @param listener the listener to receive action events
     */
    public void addPrevActionListener(ActionListener listener) {
        prevBtn.addActionListener(listener);
    }

    /**
     * Adds an action listener to the "Zoom In" button.
     *
     * @param listener the listener to receive action events
     */
    public void addZoomInActionListener(ActionListener listener) {
        zoomInBtn.addActionListener(listener);
    }

    /**
     * Adds an action listener to the "Zoom Out" button.
     *
     * @param listener the listener to receive action events
     */
    public void addZoomOutActionListener(ActionListener listener) {
        zoomOutBtn.addActionListener(listener);
    }

    /**
     * Adds an action listener to the "Reset" button.
     *
     * @param listener the listener to receive action events
     */
    public void addResetActionListener(ActionListener listener) {
        resetBtn.addActionListener(listener);
    }

    /**
     * Adds an action listener to the "Steuerung" (Control) toggle button.
     *
     * @param listener the listener to receive action events
     */
    public void addToggleControlActionListener(ActionListener listener) {
        toggleControlBtn.addActionListener(listener);
    }

    /**
     * Enables or disables the "Steuerung" (Control) toggle button.
     *
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    public void setToggleControlButtonEnabled(boolean enabled) {
        toggleControlBtn.setEnabled(enabled);
    }

    /**
     * Sets the base color of the "Steuerung" (Control) toggle button.
     *
     * @param color the new base color for the button
     */
    public void setToggleControlButtonColor(Color color) {
        toggleControlBtn.setBaseColor(color);
    }

    /**
     * Enables or disables both navigation buttons ("Previous" and "Next").
     *
     * @param enabled {@code true} to enable navigation, {@code false} to disable it
     */
    public void setNavigationEnabled(boolean enabled) {
        setPrevButtonEnabled(enabled);
        setNextButtonEnabled(enabled);
    }

    /**
     * Enables or disables the "Previous" navigation button.
     *
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    public void setPrevButtonEnabled(boolean enabled) {
        prevBtn.setEnabled(enabled);
    }

    /**
     * Enables or disables the "Next" navigation button.
     *
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    public void setNextButtonEnabled(boolean enabled) {
        nextBtn.setEnabled(enabled);
    }

    /**
     * Enables or disables the "Zoom In" button.
     *
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    public void setZoomInButtonEnabled(boolean enabled) {
        zoomInBtn.setEnabled(enabled);
    }

    /**
     * Enables or disables the "Zoom Out" button.
     *
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    public void setZoomOutButtonEnabled(boolean enabled) {
        zoomOutBtn.setEnabled(enabled);
    }

    /**
     * Enables or disables the "Reset" button.
     *
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    public void setResetButtonEnabled(boolean enabled) {
        resetBtn.setEnabled(enabled);
    }

    /**
     * Indicates whether an Arduino device is available by enabling or disabling
     * the Arduino status button. When enabled, the button is visually active;
     * when disabled, it appears grayed out.
     *
     * @param enabled {@code true} if Arduino is connected, {@code false} otherwise
     */
    public void setArduinoAvailable(boolean enabled) {
        arduinoPresentButton.setEnabled(enabled);
    }

    /**
     * Sets the text displayed in the plot title label.
     *
     * @param title the new title to show
     */
    public void setPlotTitle(String title) {
        plotLabel.setText(title);
    }

    /**
     * Initializes the panel's layout, styling, and component arrangement.
     * Buttons are ordered from left to right: navigation, control toggle,
     * plot title, Arduino indicator, zoom controls, and reset button.
     * Rigid spacers ensure consistent horizontal spacing.
     */
    protected void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(Colors.GRAY5);
        add(prevBtn);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(nextBtn);
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(toggleControlBtn);
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(plotLabel);
        add(Box.createHorizontalGlue());
        add(Box.createRigidArea(new Dimension(15, 0)));
        arduinoPresentButton.setBaseColor(Colors.GREEN);
        arduinoPresentButton.setEnabled(false);
        add(arduinoPresentButton);
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(zoomOutBtn);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(zoomInBtn);
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(resetBtn);
        resetBtn.setEnabled(false);
    }
}
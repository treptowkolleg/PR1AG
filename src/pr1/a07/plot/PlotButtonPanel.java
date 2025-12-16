package pr1.a07.plot;

import pr1.a07.Colors;
import pr1.a07.plot.components.ModernButton;
import pr1.a07.plot.components.ModernIconButton;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class PlotButtonPanel extends JPanel {
    private final ModernButton nextBtn;
    private final ModernButton prevBtn;
    private final ModernButton zoomInBtn;
    private final ModernButton zoomOutBtn;
    private final ModernButton resetBtn;
    private final ModernButton toggleControlBtn;
    private final JLabel plotLabel = new JLabel("plot name");

    public PlotButtonPanel() {
        nextBtn = new ModernIconButton(Icon.CHEVRON_RIGHT);
        prevBtn = new ModernIconButton(Icon.CHEVRON_LEFT);
        zoomInBtn = new ModernIconButton(Icon.ZOOM_IN);
        zoomOutBtn = new ModernIconButton(Icon.ZOOM_OUT);
        resetBtn = new ModernButton("Reset", Colors.BLUE);
        toggleControlBtn = new ModernButton("Steuerung", Colors.BLUE);
        init();
    }

    public ModernButton getNextBtn() {
        return nextBtn;
    }

    public ModernButton getPrevBtn() {
        return prevBtn;
    }

    public ModernButton getZoomInBtn() {
        return zoomInBtn;
    }

    public ModernButton getZoomOutBtn() {
        return zoomOutBtn;
    }

    public ModernButton getResetBtn() {
        return resetBtn;
    }

    public ModernButton getToggleControlBtn() {
        return toggleControlBtn;
    }

    public JLabel getPlotLabel() {
        return plotLabel;
    }

    void setToggleControlButtonEnabled(boolean enabled) {
    }

    void setToggleControlButtonColor(Color color) {
        toggleControlBtn.setBaseColor(color);
    }

    void setNavigationEnabled(boolean enabled) {
        setPrevButtonEnabled(enabled);
        setNextButtonEnabled(enabled);
    }

    void setPrevButtonEnabled(boolean enabled) {
        prevBtn.setEnabled(enabled);
    }

    void setNextButtonEnabled(boolean enabled) {
        nextBtn.setEnabled(enabled);
    }

    void setZoomInButtonEnabled(boolean enabled) {
        zoomInBtn.setEnabled(enabled);
    }

    void setZoomOutButtonEnabled(boolean enabled) {
        zoomOutBtn.setEnabled(enabled);
    }

    void setResetButtonEnabled(boolean enabled) {
        resetBtn.setEnabled(enabled);
    }

    void setPlotTitle(String title) {
        plotLabel.setText(title);
    }

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
        add(zoomOutBtn);
        add(Box.createRigidArea(new Dimension(5, 0)));
        add(zoomInBtn);
        add(Box.createRigidArea(new Dimension(15, 0)));
        add(resetBtn);
        resetBtn.setEnabled(false);
    }
}

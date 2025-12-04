package pr1.a07;

import pr1.helper.core.GraphicsApplication;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

public class TrigoControlPanel extends JFrame {
    private final TrigoPlot plot;
    private final GraphicsApplication app;
    private final JSlider amplitudeSlider;
    private final JSlider frequencySlider;
    private final JSlider phaseSlider;
    private final JSlider verticalSlider;
    private final JSlider resolutionSlider;
    private final JSlider startSlider;
    private final JSlider endSlider;

    public TrigoControlPanel(TrigoPlot plot, GraphicsApplication app) {
        this.plot = plot;
        this.app = app;
        ChangeListener listener = e -> onParameterChange();
        GridBagConstraints gbc = new GridBagConstraints();
        int ampValue = (int) Math.round(plot.amplitude * 100);
        int freqValue = (int) Math.round(plot.width * 100);
        int phaseValue = (int) Math.round(plot.dx * 100);
        int vertValue = (int) Math.round(plot.dy * 100);
        int resValue = (int) Math.round(plot.resolution * 100);
        int startValue = plot.intervalStart;
        int endValue = plot.intervalEnd;

        setTitle("Trigonometry Control");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        ampValue = Math.max(-200, Math.min(200, ampValue));
        amplitudeSlider = createSlider(-200, 200, ampValue, "Amplitude");
        freqValue = Math.max(0, Math.min(400, freqValue));
        frequencySlider = createSlider(0, 400, freqValue, "Frequenz");
        phaseValue = Math.max(-200, Math.min(200, phaseValue));
        phaseSlider = createSlider(-200, 200, phaseValue, "Phase (dx)");
        vertValue = Math.max(-200, Math.min(200, vertValue));
        verticalSlider = createSlider(-200, 200, vertValue, "Vertikal (dy)");
        resValue = Math.max(1, Math.min(100, resValue));
        resolutionSlider = createSlider(1, 100, resValue, "Auflösung");
        startSlider = createSlider(-12, 12, startValue, "Intervall Start");
        endSlider = createSlider(-12, 12, endValue, "Intervall Ende");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(amplitudeSlider, gbc);
        gbc.gridy = 1;
        add(frequencySlider, gbc);
        gbc.gridy = 2;
        add(phaseSlider, gbc);
        gbc.gridy = 3;
        add(verticalSlider, gbc);
        gbc.gridy = 4;
        add(resolutionSlider, gbc);
        gbc.gridy = 5;
        add(startSlider, gbc);
        gbc.gridy = 6;
        add(endSlider, gbc);
        amplitudeSlider.addChangeListener(listener);
        frequencySlider.addChangeListener(listener);
        phaseSlider.addChangeListener(listener);
        verticalSlider.addChangeListener(listener);
        resolutionSlider.addChangeListener(listener);
        startSlider.addChangeListener(listener);
        endSlider.addChangeListener(listener);
        setPreferredSize(new Dimension(400, 600));
        pack();
        positionLeftOf(app);
    }

    private void positionLeftOf(JFrame mainFrame) {
        SwingUtilities.invokeLater(() -> {
            Point mainLoc = mainFrame.getLocation();
            int mainHeight = mainFrame.getHeight();
            int thisWidth = getWidth();
            int thisHeight = getHeight();
            int x = mainLoc.x - thisWidth + 450;
            int y = mainLoc.y + (mainHeight - thisHeight) / 2 + 100;
            Rectangle screen = mainFrame.getGraphicsConfiguration().getBounds();

            x = Math.max(screen.x, x);
            y = Math.max(screen.y, Math.min(y,
                    screen.y + screen.height - thisHeight));
            setLocation(x, y);
        });
    }

    private JSlider createSlider(int min, int max, int value, String title) {
        JSlider slider = new JSlider(min, max, value);

        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing((max - min) / 10);
        slider.setBorder(BorderFactory.createTitledBorder(title));
        return slider;
    }

    private void onParameterChange() {
        plot.amplitude = amplitudeSlider.getValue() / 100.0;
        plot.width = frequencySlider.getValue() / 100.0;
        plot.dx = phaseSlider.getValue() / 100.0;
        plot.dy = verticalSlider.getValue() / 100.0;
        plot.resolution = resolutionSlider.getValue() / 100.0;
        plot.intervalStart = startSlider.getValue();
        plot.intervalEnd = endSlider.getValue();
        app.repaint();
    }
}
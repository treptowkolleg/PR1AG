package treptowkolleg.plot;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlotInfoPanel extends JPanel {
    protected final JLabel zoomInfo = new JLabel();
    protected final JLabel info = new JLabel();

    /**
     * Constructs a PlotInfoPanel with a default informational message describing
     * basic plot interaction controls (panning and zooming).
     */
    public PlotInfoPanel() {
        this("Verschieben: Ziehen | Zoomen: Strg+Mausrad bzw. nur Mausrad");
    }

    /**
     * Constructs a PlotInfoPanel with the specified informational text.
     *
     * @param infoText the message to display in the info label; must not be null
     */
    public PlotInfoPanel(String infoText) {
        info.setText(infoText);
        init();
    }

    /**
     * Returns the label that displays dynamic zoom or scale information.
     * This label is typically updated in response to user zoom actions.
     *
     * @return the zoom info label
     */
    public JLabel getZoomInfo() {
        return zoomInfo;
    }

    /**
     * Returns the label that displays static usage instructions or context help.
     *
     * @return the main info label
     */
    public JLabel getInfo() {
        return info;
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setBackground(Colors.GRAY5);
        info.setForeground(Colors.GRAY);
        zoomInfo.setForeground(Colors.GRAY);
        add(info);
        add(Box.createHorizontalGlue());
        add(zoomInfo);
    }
}

package treptowkolleg.plot;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlotInfoPanel extends JPanel {
    protected final JLabel zoomInfo = new JLabel();
    protected final JLabel info = new JLabel();

    public PlotInfoPanel() {
        this("Verschieben: Ziehen | Zoomen: Strg+Mausrad bzw. nur Mausrad");
    }

    public PlotInfoPanel(String infoText) {
        info.setText(infoText);
        init();
    }

    public JLabel getZoomInfo() {
        return zoomInfo;
    }

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

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
package treptowkolleg.plot;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel that displays user guidance and dynamic feedback for plot interaction.
 * It shows static instructions (e.g., how to pan or zoom) and can also present
 * real-time information such as current zoom level or scale.
 *
 * <p>The layout consists of two labels: one for persistent help text on the left,
 * and one for transient status updates (e.g., zoom factor) on the right.
 * Both labels use a consistent muted color scheme matching the application's UI theme.
 */
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

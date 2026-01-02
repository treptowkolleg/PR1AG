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
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package treptowkolleg.plot.components;

import treptowkolleg.plot.Colors;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ModernSlider  extends JSlider {
    public ModernSlider(int min, int max, int value) {
        super(min, max, value);
        setOpaque(false);
        setFocusable(false);
        setUI(new ModernSliderUI());
    }

    private static class ModernSliderUI extends BasicSliderUI {
        private static final int TRACK_HEIGHT = 4;
        private static final int THUMB_SIZE = 15;
        private static final int ARC = 8;

        public ModernSliderUI() {
            super(null);
        }

        @Override
        public void installUI(JComponent c) {
            super.installUI(c);
            c.setOpaque(false);
        }

        @Override
        protected Dimension getThumbSize() {
            return new Dimension(THUMB_SIZE, THUMB_SIZE);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            int trackY = thumbRect.y + (thumbRect.height - TRACK_HEIGHT) / 2;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Colors.WHITE);
            g2d.fillRoundRect(trackRect.x, trackY, trackRect.width, TRACK_HEIGHT, ARC, ARC);
            g2d.dispose();
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Colors.BLUE);
            g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            g2d.dispose();
        }
    }
}

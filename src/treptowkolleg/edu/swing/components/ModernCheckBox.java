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
package treptowkolleg.edu.swing.components;

import treptowkolleg.edu.swing.graphics.Colors;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import java.awt.Font;

/**
 * A visually enhanced checkbox component that integrates with the application's
 * custom design system. It replaces the default Swing checkbox appearance with
 * a modern, flat-style icon and uses the framework's color and typography scheme.
 *
 * <p>The checkbox is non-opaque, uses a clean sans-serif font, and provides
 * consistent spacing and visual feedback. It relies on {@link ModernCheckBoxIcon}
 * for rendering both selected and unselected states.
 *
 * <p>This component is intended for use in control panels and settings dialogs
 * within the {@code treptowkolleg.edu.swing.plot} framework.
 */
public class ModernCheckBox extends JCheckBox {

    public ModernCheckBox() {
        initStyle();
    }

    public ModernCheckBox(String text) {
        super(text);
        initStyle();
    }

    public ModernCheckBox(String text, boolean defaultValue) {
        super(text, defaultValue);
        initStyle();
    }

    private void initStyle() {
        setOpaque(false);
        setFont(new Font("SF Pro Text, Helvetica Neue, Arial", Font.PLAIN, 12));
        setForeground(Colors.BLACK);
        setIcon(new ModernCheckBoxIcon(false));
        setSelectedIcon(new ModernCheckBoxIcon(true));
        setIconTextGap(10);
        setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        setFocusPainted(false);
    }
}

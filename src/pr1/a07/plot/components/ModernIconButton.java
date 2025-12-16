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
package pr1.a07.plot.components;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.net.URL;

/**
 * A modern-styled icon-only button that extends {@link ModernButton}.
 * Instead of displaying text, it displays a centered icon loaded from a
 * classpath resource.
 */
public class ModernIconButton extends ModernButton {
    private final Image iconImage;

    /**
     * Constructs an icon-only button using the given classpath resource.
     *
     * @param resourcePath the absolute classpath path to the icon (e.g.,
     *                     "/icons/zoom-in.png")
     * @throws IllegalArgumentException if the resource is not found
     */
    public ModernIconButton(String resourcePath) {
        super("");
        URL imageUrl = getClass().getResource(resourcePath);
        if (imageUrl == null) {
            throw new IllegalArgumentException("Icon resource not found: " + resourcePath);
        }
        ImageIcon icon = new ImageIcon(imageUrl);
        this.iconImage = icon.getImage();
        setMargin(new Insets(13, 16, 13, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (iconImage != null) {
            int w = iconImage.getWidth(this);
            int h = iconImage.getHeight(this);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            if (w > 0 && h > 0) {
                int x = (getWidth() - w) / 2;
                int y = (getHeight() - h) / 2;

                g2.drawImage(iconImage, x, y, this);
            }
            g2.dispose();
        }
    }
}
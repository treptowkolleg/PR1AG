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
package pr1.a07.plot;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A custom JPanel that manages and renders a list of drawable objects.
 * This panel automatically redraws all registered DrawableObject instances
 * during its paint cycle.
 */
public class DrawablePanel extends JPanel {
    private final ArrayList<Drawable> drawableObjects = new ArrayList<>();

    /**
     * Adds a single drawable object to this panel.
     *
     * @param object the drawable object to add
     */
    public void addDrawable(DrawableObject object) {
        drawableObjects.add(object);
    }

    /**
     * Adds all drawable objects from the given list to this panel.
     *
     * @param objects the list of drawable objects to add
     */
    public void addDrawables(ArrayList<? extends DrawableObject> objects) {
        drawableObjects.addAll(objects);
    }

    public void clearDrawables() {
        drawableObjects.clear();
    }

    /**
     * Paints the component by first painting any child components and then
     * rendering all registered drawable objects.
     *
     * @param g the graphics context used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawableObjects.forEach(object -> object.draw(g));
    }
}
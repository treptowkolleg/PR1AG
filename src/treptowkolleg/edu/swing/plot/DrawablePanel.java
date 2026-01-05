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
 * along with this program.  If not, see <https://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package treptowkolleg.edu.swing.plot;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A custom JPanel that manages and renders a list of drawable objects.
 * This panel automatically redraws all registered DrawableObject instances
 * during its paint cycle.
 *
 * <p><strong>Important licensing notice:</strong> This class implements the
 * {@link schimkat.berlin.lernhilfe2025ws.graphics.Drawable} interface, which is provided
 * exclusively for use within the courses taught by Dr. Schimkat at the Berlin University
 * of Applied Sciences (Berliner Hochschule für Technik, BHT). Redistribution, public release,
 * or use outside this educational context is not permitted under Dr. Schimkat’s license terms.</p>
 *
 * <p>For any reuse beyond the original course setting (e.g., in personal projects, open-source
 * repositories, or other educational contexts), you <em>must</em> create a project-local
 * alternative interface such as:</p>
 * <pre><code>
 * package your.package;
 *
 * import java.awt.Graphics;
 *
 * public interface Drawable {
 *     void draw(Graphics g);
 * }
 * </code></pre>
 * <p>and refactor all implementing classes to depend on that instead.</p>
 */
public class DrawablePanel extends JPanel {
    private final ArrayList<Drawable> drawableObjects = new ArrayList<>();

    public DrawablePanel() {
        Timer repaintTimer = new Timer(33, e -> repaint());
        repaintTimer.setRepeats(true);
        repaintTimer.start();
    }

    /**
     * Adds a single drawable object to this panel.
     *
     * @param object the drawable object to add
     */
    public void addDrawable(Drawable object) {
        drawableObjects.add(object);
    }

    /**
     * Adds all drawable objects from the given list to this panel.
     *
     * @param objects the list of drawable objects to add
     */
    public void addDrawables(ArrayList<? extends Drawable> objects) {
        drawableObjects.addAll(objects);
    }

    /**
     * Removes all currently registered drawable objects from this panel.
     */
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
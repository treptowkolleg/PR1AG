/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty t of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package treptowkolleg.edu.swing.plot;

import schimkat.berlin.lernhilfe2025ws.graphics.Drawable;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * An abstract base class for drawable objects that provides common rendering
 * infrastructure.
 * This class automatically computes the drawing area dimensions and center
 * coordinates
 * from the graphics context before invoking custom drawing logic.
 *
 * <p><strong>Important licensing notice:</strong> This class implements the
 * {@link schimkat.berlin.lernhilfe2025ws.graphics.Drawable} interface, which
 * is provided
 * exclusively for use within the courses taught by Dr. Schimkat at the
 * Berlin University
 * of Applied Sciences (Berliner Hochschule fuer Technik, BHT).
 * Redistribution, public release,
 * or use outside this educational context is not permitted under Dr.
 * Schimkat’s license terms.</p>
 *
 * <p>For any reuse beyond the original course setting (e.g., in personal
 * projects, open-source
 * repositories, or other educational contexts), you <em>must</em> create a
 * project-local
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
public abstract class DrawableObject implements Drawable, CustomDrawable {
    protected int panelWidth;
    protected int panelHeight;
    protected int centerX;
    protected int centerY;
    protected double scaleX = 10;
    protected double scaleY = 10;
    private AffineTransform originalTransform;
    private Graphics2D g2d;

    protected void configureData() {
    }

    /**
     * Renders this object by first extracting the current drawing area
     * dimensions
     * from the graphics context, computing the center point, and then
     * delegating
     * to the configure methods for custom drawing implementation.
     *
     * @param g the graphics context used for rendering
     */
    public void draw(Graphics g) {
        List<Method> drawMethods = new ArrayList<>();
        Class<?> clazz = this.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Draw.class)) {
                    continue;
                }
                if (method.getParameterCount() != 1 ||
                        !method.getParameterTypes()[0].equals(Graphics2D.class)) {
                    continue;
                }
                Context condition = method.getAnnotation(Draw.class).when();
                if (!evaluateCondition(condition)) {
                    continue;
                }
                method.setAccessible(true);
                drawMethods.add(method);
            }
            clazz = clazz.getSuperclass();
        }
        drawMethods.sort(Comparator.comparingInt(
                method -> method.getAnnotation(Draw.class).order()
        ));
        g2d = (Graphics2D) g.create();
        originalTransform = g2d.getTransform();
        panelWidth = g.getClipBounds().width;
        panelHeight = g.getClipBounds().height;
        centerX = (int) (PlotApplication.X_DELTA + (double) panelWidth / 2);
        centerY = (int) (PlotApplication.Y_DELTA + (double) panelHeight / 2);
        configureData();
        configureGraphics(g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        configureGraphics2D(g2d);
        for (Method method : drawMethods) {
            try {
                Graphics2D g2 = (Graphics2D) g.create();
                method.invoke(this, g2);
                g2.dispose();
            } catch (Exception e) {
                System.err.println("Fehler bei " + method.getName());
            }
        }
    }

    private boolean evaluateCondition(Context condition) {
        if (Context.IS_PROD.equals(condition)) return !PlotApplication.DEV_MODE;
        if (Context.IS_DEV.equals(condition)) return PlotApplication.DEV_MODE;
        return true;
    }

    /**
     * Adjusts the global X offset to enable smooth horizontal scrolling
     * during live data acquisition (e.g., in serial plotting).
     * This method modifies {@link PlotApplication#X_DELTA} based on the
     * current scale factors.
     */
    protected void adjustXDelta() {
        PlotApplication.X_DELTA -= (PlotApplication.X_SCALE / scaleX) * (scaleX * scaleX / 3.0);
    }

    /**
     * Converts an integer x-coordinate from data space to screen space
     * using the current scaling and center offset.
     *
     * @param value the x-coordinate in data units
     * @return the corresponding x-coordinate in pixels
     */
    protected int getScaledX(int value) {
        return getScaledX((double) value);
    }

    /**
     * Converts a double x-coordinate from data space to screen space
     * using the current scaling and center offset.
     *
     * @param value the x-coordinate in data units
     * @return the corresponding x-coordinate in pixels
     */
    protected int getScaledX(double value) {
        return (int) (centerX + value * scaleX * PlotApplication.X_SCALE);
    }

    /**
     * Converts an integer y-coordinate from data space to screen space
     * using the current scaling and center offset.
     * Note: The y-axis is inverted (higher data values appear higher on screen).
     *
     * @param value the y-coordinate in data units
     * @return the corresponding y-coordinate in pixels
     */
    protected int getScaledY(int value) {
        return getScaledY((double) value);
    }

    /**
     * Converts a double y-coordinate from data space to screen space
     * using the current scaling and center offset.
     * Note: The y-axis is inverted (higher data values appear higher on screen).
     *
     * @param value the y-coordinate in data units
     * @return the corresponding y-coordinate in pixels
     */
    protected int getScaledY(double value) {
        return (int) (centerY - value * scaleY * PlotApplication.Y_SCALE);
    }

    /**
     * Returns the maximum value in the given list of integers.
     * If the list is empty, returns 0.
     *
     * @param list the list of integer values
     * @return the maximum value, or 0 if the list is empty
     */
    protected int getMaxValue(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).max().orElse(0);
    }

    /**
     * Returns the index of the maximum value in the given list of doubles.
     * If the list is empty, returns 0.
     *
     * @param list the list of double values
     * @return the index of the maximum element, or 0 if the list is empty
     */
    protected int indexOfMax(List<Double> list) {
        return IntStream.range(0, list.size()).reduce((i, j)
                -> list.get(i) > list.get(j) ? i : j).orElse(0);
    }

    /**
     * Resets the transformation of the internal Graphics2D context to its
     * state at the beginning of the {@link #draw(Graphics)} call.
     * This is useful when custom drawing code has applied rotations,
     * translations, or scaling that should not persist.
     */
    protected void resetTransform() {
        resetTransform(g2d);
    }

    /**
     * Resets the transformation of the given Graphics2D context to the
     * original state captured at the start of rendering.
     *
     * @param g2d the graphics context to reset; must not be null
     */
    protected void resetTransform(Graphics2D g2d) {
        g2d.setTransform(originalTransform);
    }

    /**
     * Returns the internal Graphics2D context used for advanced rendering.
     * This context has anti-aliasing enabled and is ready for custom drawing.
     *
     * @return the current Graphics2D instance
     */
    protected Graphics2D getG2D() {
        return g2d;
    }

    /**
     * Returns the width of the current drawing panel in pixels.
     *
     * @return the panel width
     */
    protected int getPanelWidth() {
        return panelWidth;
    }

    /**
     * Returns the height of the current drawing panel in pixels.
     *
     * @return the panel height
     */
    protected int getPanelHeight() {
        return panelHeight;
    }
}
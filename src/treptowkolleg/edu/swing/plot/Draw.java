package treptowkolleg.edu.swing.plot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method to be automatically invoked during the rendering phase of a
 * {@link DrawableObject}.
 * <p>
 * For each method annotated with {@code @Draw}, the framework creates a
 * dedicated, isolated {@link java.awt.Graphics2D} instance by calling
 * {@code Graphics.create()}. This ensures that any modifications (e.g., color,
 * transform, clip) made within the method do not affect other drawing
 * operations.
 * <p>
 * After the method returns, the {@code Graphics2D} instance is automatically
 * disposed via {@code dispose()} to prevent resource leaks.
 * <p>
 * Example:
 * <pre>{@code
 * @Draw(order = 10, when = "DEV")
 * private void debugOverlay(Graphics2D g) {
 *     g.setColor(Color.RED);
 *     g.drawRect(0, 0, 100, 100);
 * }
 * }</pre>
 *
 * @see DrawableObject#draw(java.awt.Graphics)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Draw {

    /**
     * Defines the drawing order. Lower values are rendered first (e.g., backgrounds),
     * higher values later (e.g., overlays or labels).
     * The default value is {@link Integer#MAX_VALUE}, which ensures that methods without
     * an explicit order are drawn last.
     */
    int order() default Integer.MAX_VALUE;

    /**
     * Specifies the execution context in which this drawing method should be invoked.
     * The method is only called if the current runtime context matches this value.
     * For example, use {@link Context#IS_DEV} to enable debug visuals during development,
     * or {@link Context#IS_PROD} (the default) for production rendering.
     */
    Context when() default Context.IS_PROD;
}

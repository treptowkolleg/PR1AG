package treptowkolleg.edu.swing.graphics;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Objects;

/**
 * A centralized registry of application icons, providing static paths and a utility method
 * to load images from embedded resources. All icons are expected to reside in the
 * {@code /icons/} directory of the classpath and are accessed via their resource paths.
 *
 * <p>The {@link #get(String)} method safely loads an image and throws a {@link NullPointerException}
 * if the specified icon path does not correspond to an existing resource.
 */
public abstract class Icon {
    public static String CHEVRON_RIGHT = "/icons/icons8-doppelt-rechts-16.png";
    public static String CHEVRON_LEFT = "/icons/icons8-doppelt-links-16.png";
    public static String ZOOM_IN = "/icons/icons8-hineinzoomen-16.png";
    public static String ZOOM_OUT = "/icons/icons8-rauszoomen-16.png";
    public static String ARDUINO = "/icons/icons8-arduino-16.png";
    public static String TK_LOGO = "/icons/tk.png";

    public static Image get(String iconPath) {
        return new ImageIcon(Objects
                .requireNonNull(Icon.class.getResource(iconPath))).getImage();
    }
}

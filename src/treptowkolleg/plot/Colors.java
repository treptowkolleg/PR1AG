package treptowkolleg.plot;

import java.awt.Color;

/**
 * A centralized collection of predefined, application-specific {@link Color} constants
 * for consistent and semantic visual styling across the plot framework.
 *
 * <p>Colors are grouped by hue and semantic intent (e.g., alert colors like {@link #RED},
 * success indicators like {@link #GREEN}, and neutral UI grays like {@link #GRAY5}).
 * All colors are defined using RGB values and are immutable by convention.
 *
 * <p>This class is designed to be used statically, e.g.:
 * <pre>{@code
 * g.setColor(Colors.BLUE);
 * label.setForeground(Colors.DARKER_GREEN);
 * }</pre>
 */
public abstract class Colors {
    public static Color RED = new Color(255, 56, 60);
    public static Color ORANGE = new Color(255, 141, 40);
    public static Color YELLOW = new Color(255, 204, 0);
    public static Color GREEN = new Color(52, 199, 89);
    public static Color DARK_GREEN = new Color(32, 179, 69);
    public static Color DARKER_GREEN = new Color(12, 159, 49);
    public static Color MINT = new Color(0, 200, 179);
    public static Color TEAL = new Color(0, 195, 208);
    public static Color CYAN = new Color(0, 192, 232);
    public static Color BLUE = new Color(0, 136, 255);
    public static Color INDIGO = new Color(97, 85, 245);
    public static Color PURPLE = new Color(203, 48, 224);
    public static Color PINK = new Color(255, 45, 85);
    public static Color BROWN = new Color(172, 127, 94);
    public static Color BLACK = new Color(34, 34, 39);
    public static Color GRAY = new Color(142, 142, 147);
    public static Color GRAY2 = new Color(174, 174, 178);
    public static Color GRAY3 = new Color(199, 199, 204);
    public static Color GRAY4 = new Color(209, 209, 214);
    public static Color GRAY5 = new Color(229, 229, 234);
    public static Color GRAY6 = new Color(242, 242, 247);
    public static Color WHITE = new Color(249, 249, 254);
}

package pr1.a07.plot;

import java.awt.BasicStroke;

public abstract class Stroke {
    // Mittlerer Strich für allgemeine Linien, z.B. Datenkurven
    public static final BasicStroke BEVEL_MEDIUM = new BasicStroke(2.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    // Dünner Strich für feine Gitterlinien
    public static final BasicStroke GRID_FINE = new BasicStroke(0.5f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    // Mittlerer Strich für Hauptachsen (x/y)
    public static final BasicStroke AXIS_MEDIUM = new BasicStroke(1.5f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    // Dicker Strich für hervorgehobene Achsen oder Null-Linien
    public static final BasicStroke AXIS_THICK = new BasicStroke(2.5f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    // Strich für gestrichelte Gitterlinien
    public static final BasicStroke GRID_DASHED = new BasicStroke(0.75f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[]{5.0f, 5.0f}, 0.0f);

    // Sehr feiner Strich für feine Hilfslinien
    public static final BasicStroke GUIDE_THIN = new BasicStroke(0.25f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    // Dicker, fett gedruckter Strich für hervorstehende Kurven
    public static final BasicStroke LINE_THICK = new BasicStroke(3.0f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    // Strich für gestrichelte Kurven
    public static final BasicStroke LINE_DASHED = new BasicStroke(2.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[]{10.0f, 5.0f}, 0.0f);

    // Strich für gepunktete Hilfslinien
    public static final BasicStroke DOTTED = new BasicStroke(1.0f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f,
            new float[]{1.0f, 3.0f}, 0.0f);
}
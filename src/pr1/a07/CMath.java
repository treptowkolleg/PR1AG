package pr1.a07;

public class CMath {

    public static int b(double x) {
        return doubleToInt(10 + .0725 * x);
    }

    public static int y(double x) {
        return doubleToInt(400 - .01 * Math.pow(x - 100, 2));
    }

    /**
     * Computes a human-readable zoom percentage based on the raw scale value.
     * This transformation compensates for the non-linear perception of scale
     * in the UI, mapping the internal scale range [1, target] to a more
     * intuitive [100%, ~max%] range.
     *
     * @param maxScale  the maximum allowed scale factor (used for
     *                  normalization)
     * @param axisScale the current raw scale value for one axis
     * @return a normalized scale factor suitable for display as a percentage
     */
    public static double scaleFactor(double maxScale, double axisScale) {
        return 1.0 + maxScale * (axisScale - 1.0) / axisScale;
    }

    private static int doubleToInt(Double x) {
        return x.intValue();
    }
}

package treptowkolleg.edu.extension.math;

public class CMath {

    public static int b(double x) {
        return doubleToInt(10 + .0725 * x);
    }

    public static int y(double x) {
        return doubleToInt(400 - .01 * Math.pow(x - 100, 2));
    }

    public static double scaleFactor(double maxScale, double axisScale) {
        return 1.0 + maxScale * (axisScale - 1.0) / axisScale;
    }

    private static int doubleToInt(Double x) {
        return x.intValue();
    }
}

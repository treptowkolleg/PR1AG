package pr1.a07;

public class CMath {

    public static int b(double x) {
        return doubleToInt(10 + .0725 * x);
    }

    public static int y(double x) {
        return doubleToInt(400 - .01 * Math.pow(x - 100, 2));
    }

    private static int doubleToInt(Double x) {
        return x.intValue();
    }
}

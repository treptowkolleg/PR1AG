package pr1.helper.extension;

import java.util.ArrayList;

public class BetterRandom {

    public static int indexOf(ArrayList<?> list) {
        return get(list.size() - 1);
    }

    public static int get(int upperBound) {
        return get(0, upperBound);
    }

    public static int get(int lowerBound, int upperBound) {
        double f = Math.random() / Math.nextDown(1.0);
        double x = lowerBound * (1.0 - f) + upperBound * f;
        return (int) x;
    }

}

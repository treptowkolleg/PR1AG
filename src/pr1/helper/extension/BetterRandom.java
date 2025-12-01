package pr1.helper.extension;

import java.util.List;
import java.util.Random;

public class BetterRandom {
    private static final Random random = new Random();

    public static int indexOf(List<?> list) {
        return get(list.size() - 1);
    }

    /**
     * Wählt zufällig ein Element aus der gegebenen Liste aus.
     *
     * @param list die Liste, aus der ein Element gewählt werden soll
     * @param <T>  der Typ der Elemente
     * @return ein zufälliges Element aus der Liste
     * @throws IllegalArgumentException wenn die Liste leer ist
     */
    public static <T> T pick(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Can not pick from empty list");
        }
        return list.get(random.nextInt(list.size()));
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

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
    public static <T> T pickOne(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Can not pick from empty list");
        }
        return list.get(random.nextInt(list.size()));
    }

    public static <T> T pickOne(T[] list) {
        if (list.length == 0) {
            throw new IllegalArgumentException("Can not pick from empty list");
        }
        return list[get(list)];
    }

    public static <T> int get(T[] array) {
        return get(0, array.length);
    }

    public static int get(int upperBound) {
        return get(0, upperBound);
    }

    public static int get(int lowerBound, int upperBound) {
        return lowerBound + (int) (Math.random() * (upperBound - lowerBound));
    }

}

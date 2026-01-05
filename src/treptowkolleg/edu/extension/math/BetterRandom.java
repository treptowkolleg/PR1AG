package treptowkolleg.edu.extension.math;

import java.util.List;
import java.util.Random;
import java.util.function.IntFunction;

public class BetterRandom {
    private static final Random random = new Random();

    /**
     * Sets the seed of the internal random number generator.
     * Useful for reproducible results in testing or debugging.
     */
    public static void setSeed(long seed) {
        random.setSeed(seed);
    }

    /**
     * Returns a random valid index for the given list.
     *
     * @param list the list to get a random index for
     * @return a random index in the range [0, list.size())
     * @throws IllegalArgumentException if the list is empty
     */
    public static int indexOf(List<?> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Cannot get index of empty " +
                    "list");
        }
        return get(list.size());
    }

    /**
     * Returns a random integer in the range [0, upperBound).
     *
     * @param upperBound the exclusive upper bound (must be > 0)
     * @return a random integer >= 0 and < upperBound
     * @throws IllegalArgumentException if upperBound <= 0
     */
    public static int get(int upperBound) {
        if (upperBound <= 0) {
            throw new IllegalArgumentException("upperBound must be > 0");
        }
        return random.nextInt(upperBound);
    }

    /**
     * Returns a random integer in the range [lowerBound, upperBound).
     *
     * @param lowerBound the inclusive lower bound
     * @param upperBound the exclusive upper bound (must be > lowerBound)
     * @return a random integer >= lowerBound and < upperBound
     * @throws IllegalArgumentException if upperBound <= lowerBound
     */
    public static int get(int lowerBound, int upperBound) {
        if (upperBound <= lowerBound) {
            throw new IllegalArgumentException("upperBound must be > " +
                    "lowerBound");
        }
        return lowerBound + random.nextInt(upperBound - lowerBound);
    }

    /**
     * Picks a random element from the given list.
     *
     * @param <T>  the type of elements in the list
     * @param list the list to pick from
     * @return a randomly selected element
     * @throws IllegalArgumentException if the list is empty
     */
    public static <T> T pickOne(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Cannot pick from empty list");
        }
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Picks a random element from the given array.
     *
     * @param <T>   the type of elements in the array
     * @param array the array to pick from
     * @return a randomly selected element
     * @throws IllegalArgumentException if the array is empty
     */
    public static <T> T pickOne(T[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Cannot pick from empty array");
        }
        return array[random.nextInt(array.length)];
    }

    /**
     * Picks a random element from the given list that is not equal to the
     * forbidden value.
     *
     * @param <T>       the type of elements
     * @param forbidden the value to avoid
     * @param options   the list of available options
     * @return a randomly selected element different from {@code forbidden}
     * @throws IllegalArgumentException if the list is empty or contains only
     *                                  the forbidden value
     */
    public static <T> T pickOneNotEqual(T forbidden, List<T> options) {
        return pickOneNotEqualImpl(forbidden, options.size(), options::get);
    }

    /**
     * Picks a random element from the given array that is not equal to the
     * forbidden value.
     *
     * @param <T>       the type of elements
     * @param forbidden the value to avoid
     * @param options   the array of available options
     * @return a randomly selected element different from {@code forbidden}
     * @throws IllegalArgumentException if the array is empty or contains
     *                                  only the forbidden value
     */
    public static <T> T pickOneNotEqual(T forbidden, T[] options) {
        return pickOneNotEqualImpl(forbidden, options.length, i -> options[i]);
    }

    /**
     * Internal helper method to avoid code duplication between list and
     * array versions of pickOneNotEqual.
     */
    private static <T> T pickOneNotEqualImpl(T forbidden, int size,
                                             IntFunction<T> getter) {
        if (size == 0) {
            throw new IllegalArgumentException("Source is empty");
        }
        if (size == 1) {
            T only = getter.apply(0);
            if (only.equals(forbidden)) {
                throw new IllegalArgumentException("No alternative element " +
                        "available");
            }
            return only;
        }

        T candidate;
        do {
            candidate = getter.apply(random.nextInt(size));
        } while (candidate.equals(forbidden));
        return candidate;
    }
}
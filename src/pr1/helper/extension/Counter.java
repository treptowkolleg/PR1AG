/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pr1.helper.extension;

/**
 * A utility class to count oop-based.
 * <p>
 * This class is typically used with for-each or while loops to enhance
 * code readability or when final vars are needed like in
 * {@link java.util.function.Consumer} functions.
 * </p>
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @see java.util.function.Consumer
 * @since 2025
 */
public class Counter {
    private int counter;

    /**
     * Constructs a {@code Counter} initialized to {@code 1}.
     */
    public Counter() {
        counter = 1;
    }

    /**
     * Constructs a {@code Counter} with the specified start value.
     * <p>
     * If the provided {@code startValue} is less than or equal to zero,
     * the internal counter is automatically set to {@code 1} as a fallback.
     * </p>
     *
     * @param startValue the initial value for the counter
     */
    public Counter(int startValue) {
        counter = startValue;
    }

    /**
     * Returns the current value of the counter.
     *
     * @return the current counter value
     */
    public int get() {
        return counter;
    }

    /**
     * Sets the counter to the specified start value.
     * <p>
     * If the given {@code startValue} is less than or equal to zero,
     * the counter is reset to {@code 1}.
     * </p>
     *
     * @param startValue the new value for the counter
     */
    public void set(int startValue) {
        counter = (startValue > 0) ? startValue : 1;
    }

    /**
     * Resets the counter to its default initial value of {@code 1}.
     */
    public void reset() {
        this.counter = 1;
    }

    /**
     * Increments the counter by one.
     */
    public void increment() {
        counter++;
    }

    /**
     * Decrements the counter by one, but never below {@code 1}.
     * <p>
     * If the current value is already {@code 1} (or less, though this
     * should not occur under normal usage), the counter remains unchanged.
     * </p>
     */
    public void decrement() {
        if (counter > 1) {
            counter--;
        }
    }
}

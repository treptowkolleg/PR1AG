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
 * A generic accumulator for numeric values that internally stores the sum as
 * a {@code double}.
 * <p>
 * This class accepts any {@link Number} subtype (e.g., {@code Integer},
 * {@code Double}, {@code Long}, etc.)
 * and accumulates or sets their numeric values. Non-{@code double} numbers
 * are converted using
 * {@link Number#intValue()} for compatibility with legacy or integer-based
 * contexts, while {@code Double}
 * values are preserved with full precision.
 * </p>
 * <p>
 * {@code null} inputs are safely ignored in all operations.
 * </p>
 *
 * @param <T> the type of {@link Number} to be collected
 * @author Benjamin Wagner
 * @version 1.0
 * @since 2025
 */
public class Sum<T extends Number> {
    private Double sum = 0.0;

    /**
     * Adds the numeric value of the given {@code Number} to the internal sum.
     * <p>
     * If the argument is {@code null}, this method does nothing.
     * If the argument is an instance of {@code Double}, its {@code
     * doubleValue()} is used;
     * otherwise, {@code intValue()} is used for conversion.
     * </p>
     *
     * @param value the number to be added to the sum, or {@code null} to skip
     */
    public void add(T value) {
        if (null == value) {
            return;
        }
        sum += value instanceof Double ? value.doubleValue() : value.intValue();
    }

    /**
     * Sets the internal sum to the numeric value of the given {@code Number},
     * replacing any previous value.
     * <p>
     * If the argument is {@code null}, this method does nothing.
     * If the argument is an instance of {@code Double}, its {@code
     * doubleValue()} is used;
     * otherwise, {@code intValue()} is used for conversion.
     * </p>
     *
     * @param value the number to be set as the new sum, or {@code null} to skip
     */
    public void set(T value) {
        if (null == value) {
            return;
        }
        sum = value instanceof Double ? value.doubleValue() : value.intValue();
    }

    /**
     * Returns the current sum as a {@code double}.
     *
     * @return the accumulated sum with double precision
     */
    public double getSum() {
        return sum;
    }

    /**
     * Returns the current sum as an {@code int}, truncating any fractional
     * part.
     *
     * @return the accumulated sum converted to an integer
     */
    public int getSumAsInteger() {
        return sum.intValue();
    }
}
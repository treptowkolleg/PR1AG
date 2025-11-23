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
package pr1.helper.entity;

/**
 * An immutable representation of a numeric value paired with an associated precision.
 * <p>
 * A {@code MixedNumber} encapsulates a {@code double} amount and an {@code int} indicating
 * how many decimal places the value is intended to carry (its precision). This is useful
 * in contexts where both the numeric magnitude and formatting intent must be preserved,
 * such as in mixed-unit displays (e.g., "3.141 kg" with 3-digit precision).
 * </p>
 * <p>
 * Instances of this class are immutable by design: once created, the amount and precision
 * must not change. This is essential because {@code MixedNumber} objects are used in the
 * application model to represent fixed numeric values that are not allowed to be modified
 * after instantiation.
 * </p>
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @since 2025
 */
public class MixedNumber {
    private final double amount;
    private final int precision;

    /**
     * Constructs a {@code MixedNumber} with the specified amount and zero precision.
     *
     * @param amount the numeric value to store
     */
    public MixedNumber(double amount) {
        this.amount = amount;
        this.precision = 0;
    }

    /**
     * Constructs a {@code MixedNumber} with the specified amount and precision.
     * <p>
     * The {@code precision} parameter typically represents the number of decimal places
     * intended for display or rounding purposes. It may be zero or positive.
     * Negative values are accepted but discouraged, as they have no standard interpretation
     * in this context.
     * </p>
     *
     * @param amount    the numeric value to store
     * @param precision the number of decimal places associated with the amount
     */
    public MixedNumber(double amount, int precision) {
        this.amount = amount;
        this.precision = precision;
    }

    /**
     * Returns the numeric amount stored in this {@code MixedNumber}.
     *
     * @return the amount as a {@code double}
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the precision associated with this {@code MixedNumber}.
     * <p>
     * This value usually indicates how many digits after the decimal point are considered significant.
     * </p>
     *
     * @return the precision as an {@code int}
     */
    public int getPrecision() {
        return precision;
    }
}
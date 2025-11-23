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
package pr1.helper.repository;

import java.util.ArrayList;

import pr1.helper.entity.MixedNumber;

/**
 * A specialized list for storing and analyzing {@link MixedNumber} instances.
 * <p>
 * This class extends {@link ArrayList} to provide utility methods that operate
 * on the collection as a whole, such as determining the maximum numeric amount,
 * the number of digits in that maximum amount, or the highest precision among
 * all contained {@code MixedNumber} objects.
 * </p>
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @since 2025
 */
public class MixedNumberList extends ArrayList<MixedNumber> {

    /**
     * Returns the largest numeric amount among all {@code MixedNumber} elements
     * in this list.
     * <p>
     * If the list is empty, this method returns {@code 0.0}.
     * </p>
     *
     * @return the maximum amount as a {@code double}, or {@code 0.0} if the list is empty
     */
    public double getMaxAmount() {
        return this.stream()
                .mapToDouble(MixedNumber::getAmount)
                .max()
                .orElse(0.0);
    }

    /**
     * Returns the number of digits in the integer part of the largest amount
     * present in this list.
     * <p>
     * The value is derived by truncating the result of {@link #getMaxAmount()}
     * to an integer and converting it to a string to count its length.
     * If the maximum amount is less than 1 (e.g., 0.5), this method returns {@code 1},
     * since {@code (int) 0.5 == 0} and {@code "0".length() == 1}.
     * </p>
     *
     * @return the number of digits in the integer portion of the maximum amount
     */
    public int getMaxAmountLength() {
        return String.valueOf((int) getMaxAmount()).length();
    }

    /**
     * Returns the highest precision (i.e., number of decimal places) found among
     * all {@code MixedNumber} elements in this list.
     * <p>
     * If the list is empty, this method returns {@code 0}.
     * </p>
     *
     * @return the maximum precision as an {@code int}, or {@code 0} if the list is empty
     */
    public int getMaxPrecision() {
        return this.stream()
                .mapToInt(MixedNumber::getPrecision)
                .max()
                .orElse(0);
    }
}
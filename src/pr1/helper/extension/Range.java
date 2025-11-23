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

import java.util.Iterator;

/**
 * A utility class that represents an inclusive range of characters and enables
 * iteration over that range using a for-each loop.
 * <p>
 * This class supports construction from either {@code char} values or {@code int}
 * values (which are cast to {@code char}). The range is inclusive on both ends,
 * meaning both the start and end characters are part of the iteration.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * for (char c : new Range('a', 'z')) {
 *     System.out.println(c);
 * }
 * }</pre>
 * </p>
 *
 * @see java.util.Iterator
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @since 2025
 */
public class Range implements Iterable<Character> {
    private final char start;
    private final char endInclusive;

    /**
     * Constructs a {@code Range} from the specified start and end characters (inclusive).
     *
     * @param start          the first character in the range
     * @param endInclusive   the last character in the range (included in iteration)
     */
    public Range(char start, char endInclusive) {
        this.start = start;
        this.endInclusive = endInclusive;
    }

    /**
     * Constructs a {@code Range} from the specified integer values, which are
     * cast to {@code char}.
     * <p>
     * This is a convenience constructor for creating character ranges using
     * integer literals (e.g., ASCII codes). Note that values outside the
     * valid {@code char} range (0–65535) will be silently truncated due to
     * Java’s narrowing primitive conversion.
     * </p>
     *
     * @param start          the numeric representation of the first character
     * @param endInclusive   the numeric representation of the last character (inclusive)
     */
    public Range(int start, int endInclusive) {
        this((char) start, (char) endInclusive);
    }

    /**
     * Returns an iterator over the characters in this range, from {@code start}
     * to {@code endInclusive}, inclusive.
     * <p>
     * The iterator produces characters in ascending order. If {@code start}
     * is greater than {@code endInclusive}, the iterator will have no elements.
     * </p>
     *
     * @return an {@link Iterator} over the character range
     */
    @Override
    public Iterator<Character> iterator() {
        return new Iterator<>() {
            private char current = start;

            @Override
            public boolean hasNext() {
                return current <= endInclusive;
            }

            @Override
            public Character next() {
                return current++;
            }
        };
    }
}
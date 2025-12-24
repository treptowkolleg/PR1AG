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
package pr1.a09.more;

import pr1.a09.ObjectFactory;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for parsing structured text input into typed object lists.
 * <p>
 * This parser reads line-based input (e.g., from CSV-like formats), processes each line through
 * validation and transformation logic provided by an {@link ObjectFactory}, and returns a list
 * of successfully parsed objects. Lines are treated as records separated by line breaks.
 * <p>
 * <strong>Note:</strong> This class is intended for internal use and should be moved to the helper package.
 */
public class Parser {

    /**
     * Parses all lines from the given {@link Scanner} into a list of objects of type {@code T}.
     * <p>
     * The scanner's delimiter is reconfigured to recognize any line break ({@code \R}) as a record separator.
     * Each non-empty, non-comment line is:
     * <ul>
     *   <li>Trimmed of leading/trailing whitespace,</li>
     *   <li>Stripped of inline comments (starting with {@code #}),</li>
     *   <li>Split into tokens by whitespace,</li>
     *   <li>Validated using the factory's validator,</li>
     *   <li>Transformed into an object using the factory's mapper.</li>
     * </ul>
     * Invalid or malformed lines are silently discarded.
     *
     * @param <T>    the type of objects to produce
     * @param input  the {@code Scanner} providing input lines
     * @param factory the {@code ObjectFactory} supplying validation and mapping logic
     * @return a new {@code ArrayList} containing all successfully parsed objects
     * @throws NullPointerException if {@code input} or {@code factory} is {@code null}
     */
    public static <T> ArrayList<T> listOf(Scanner input,
                                          ObjectFactory<T> factory) {
        input.useDelimiter("\\R");
        return parseLines(input.tokens(), factory::validate, factory::map);
    }

    /**
     * Core parsing logic that transforms a stream of input lines into a list of typed objects.
     * <p>
     * Applies a sequence of stream operations to clean, filter, and convert raw input lines:
     * <ol>
     *   <li>Trims each line,</li>
     *   <li>Filters out empty lines,</li>
     *   <li>Filters out full-line comments (lines starting with {@code #}),</li>
     *   <li>Removes inline comments ({@code # ...} to end of line),</li>
     *   <li>Filters again for emptiness (in case inline comment removal left blank lines),</li>
     *   <li>Splits remaining lines into token arrays using whitespace,</li>
     *   <li>Validates token arrays via the provided {@code validator},</li>
     *   <li>Maps valid token arrays to objects via the provided {@code mapper}.</li>
     * </ol>
     *
     * @param <T>       the type of objects to produce
     * @param input     a stream of raw input lines
     * @param validator a predicate to test token arrays for validity
     * @param mapper    a function to transform valid token arrays into objects
     * @return a new {@code ArrayList} containing all successfully parsed objects
     * @throws NullPointerException if any argument is {@code null}
     */
    private static <T> ArrayList<T> parseLines(
            Stream<String> input,
            Predicate<String[]> validator,
            Function<String[], T> mapper
    ) {
        return input.map(String::trim)
                .filter(StreamUtil.IS_NOT_EMPTY)
                .filter(StreamUtil.IS_NO_LINE_COMMENT)
                .map(StreamUtil.REMOVE_INLINE_COMMENTS)
                .filter(StreamUtil.IS_NOT_EMPTY)
                .map(StreamUtil.SPLIT_AT_WHITESPACE)
                .filter(validator)
                .map(mapper)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
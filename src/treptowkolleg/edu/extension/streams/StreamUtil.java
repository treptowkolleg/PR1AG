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
package treptowkolleg.edu.extension.streams;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Utility class providing common {@link Predicate} and {@link Function}
 * instances for stream-based text processing.
 * <p>
 * All members are stateless, immutable, and safe for concurrent use.
 * Designed to be used as building blocks in stream pipelines for
 * line-by-line parsing.
 * <p>
 * <strong>Note:</strong> This class is intended for internal use and should be moved to the helper package.
 */
public abstract class StreamUtil {

    /**
     * A predicate that returns {@code true} if the given line does not start
     * with '#'.
     * Intended to filter out full-line comments in configuration or data files.
     * <p>
     * Example: {@code "name age # comment"} -> not filtered (inline
     * comments are handled separately).
     * {@code "# full comment"} -> filtered out.
     */
    public static final Predicate<String> IS_NO_LINE_COMMENT =
            line -> !line.startsWith("#");

    /**
     * A predicate that returns {@code true} if the given line is not empty.
     * Does not trim the input; only checks {@code !line.isEmpty()}.
     * <p>
     * To ignore lines containing only whitespace, use
     * {@link String#isBlank()} (Java 11+)
     * or combine with {@link #TRIM} and this predicate.
     */
    public static final Predicate<String> IS_NOT_EMPTY =
            line -> !line.isEmpty();

    /**
     * A predicate that returns {@code true} if the given line contains only
     * ASCII characters (code points < 128).
     * Useful for validating input in systems that require ASCII-only
     * identifiers or tokens.
     */
    public static final Predicate<String> IS_ASCII_ONLY =
            line -> line.chars().allMatch(ch -> ch < 128);

    /**
     * A function that removes any inline comment (everything from the first
     * '#' onward) and trims the resulting string.
     * <p>
     * Example: {@code "Anna 1985 # test"} -> {@code "Anna 1985"}
     * {@code "data #"} -> {@code "data"}
     * {@code "# only comment"} -> {@code ""} (empty string)
     */
    public static final Function<String, String> REMOVE_INLINE_COMMENTS =
            line -> line.split("#", 2)[0].trim();

    /**
     * A function that splits a line into tokens using one or more whitespace
     * characters (spaces, tabs, etc.) as delimiters.
     * <p>
     * Uses {@code String.split("\\s+")}, which discards empty trailing tokens.
     * Suitable for space-separated data formats.
     */
    public static final Function<String, String[]> SPLIT_AT_WHITESPACE =
            line -> line.split("\\s+");

    /**
     * A function that trims leading and trailing whitespace from a line.
     * Equivalent to {@code String::trim}.
     */
    public static final Function<String, String> TRIM = String::trim;

    /**
     * A function that splits a line into tokens using commas as delimiters,
     * ignoring surrounding whitespace around each token.
     * <p>
     * Uses regex {@code \\s*,\\s*}, making it suitable for clean CSV-like
     * input.
     * Example: {@code "a, b ,c"} -> {@code ["a", "b", "c"]}
     */
    public static final Function<String, String[]> SPLIT_AT_COMMA =
            line -> line.split("\\s*,\\s*");

    /**
     * A function that splits a line into tokens using semicolons as delimiters,
     * ignoring surrounding whitespace around each token.
     * <p>
     * Uses regex {@code \\s*;\\s*}, common in European CSV formats or
     * configuration files.
     * Example: {@code "a; b ;c"} -> {@code ["a", "b", "c"]}
     */
    public static final Function<String, String[]> SPLIT_AT_SEMIKOLON =
            line -> line.split("\\s*;\\s*");

    /**
     * A function that normalizes whitespace in a line:
     * trims leading/trailing whitespace and replaces any sequence of whitespace
     * characters with a single space.
     * <p>
     * Example: {@code "  a   \t\n  b  "} -> {@code "a b"}
     */
    public static final Function<String, String> NORMALIZE_WHITESPACE =
            line -> line.trim().replaceAll("\\s+", " ");

    /**
     * A function that converts a line to lower case using the default locale.
     * Equivalent to {@code String::toLowerCase}.
     * <p>
     * Useful for case-insensitive matching or normalization.
     */
    public static final Function<String, String> TO_LOWER_CASE =
            String::toLowerCase;
}
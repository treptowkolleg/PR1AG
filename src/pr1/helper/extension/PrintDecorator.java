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

import java.io.PrintWriter;

/**
 * A utility class to decorate text output with borders and headlines.
 * <p>
 * The {@code PrintDecorator} allows adding simple framed borders around text
 * and creating visually distinct headlines in console or file output.
 * </p>
 * <p>
 * This class is typically used with {@link java.io.PrintWriter} to enhance
 * console or file output formatting.
 * </p>
 *
 * @see java.io.PrintWriter
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @since 2025
 */
public class PrintDecorator {

	/** Character used to draw the frame. Default is '#' */
	public char frameChar = '#';

	/** The {@link PrintWriter} used for output */
	public PrintWriter out;

	/**
	 * Constructs a {@link PrintDecorator} using the given {@link PrintWriter}. The
	 * default frame character '#' is used.
	 *
	 * @param out the {@link PrintWriter} for output
	 */
	public PrintDecorator(PrintWriter out) {
		this.out = out;
	}

	/**
	 * Constructs a {@link PrintDecorator} using the given {@link PrintWriter} and
	 * frame character.
	 *
	 * @param out       the {@link PrintWriter} for output
	 * @param frameChar the character used for framing
	 */
	public PrintDecorator(PrintWriter out, char frameChar) {
		this.out = out;
		this.frameChar = frameChar;
	}

	/**
	 * Prints a border around the given text with default bottom margin of 1 line.
	 *
	 * @param text the text to determine the border width
	 */
	public void printBorder(String text) {
		printBorder(text, 1);
	}

	/**
	 * Prints a border around the given text with a specified bottom margin.
	 * <p>
	 * The width of the border matches the length of the input text plus padding.
	 * </p>
	 *
	 * @param text         the text to determine the border width
	 * @param marginBottom the number of line breaks to add after the border
	 */
	public void printBorder(String text, int marginBottom) {
		for (int i = -4; i < text.length(); i++) {
			out.print(frameChar);
		}
		out.printf("%n".repeat(marginBottom));
	}

	/**
	 * Prints a headline framed by borders above and below the text.
	 * <p>
	 * The headline consists of a top border, the text framed by the
	 * {@link #frameChar}, and a bottom border with a default bottom margin of 2
	 * lines.
	 * </p>
	 *
	 * @param text the headline text
	 */
	public void printHeadline(String text) {
        out.println();
		printBorder(text);
		out.printf("%s %s %s%n", frameChar, text, frameChar);
		printBorder(text);
	}
}

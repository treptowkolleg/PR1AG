package pr1.tests;

import java.io.PrintWriter;

public class Chars {

	public static void main(String[] args) {
		PrintWriter out = new PrintWriter(System.out);
		int charStart = 65;
		int cells = 8;

		for (char c = 'H'; c >= 'A'; c--) {
			out.printf("Das Char '%s' hat den Zahlenwert %d.%n", c, (int) c);
		}
		for (int i = 0; i < cells; i++) {
			// Normal
			out.printf("+\tC: %s und I: %d%n", (char) (i + charStart), i + 1);
			// Reverse
			out.printf("-\tC: %s und I: %d%n", (char) (cells - 1 - i + charStart), cells - i);
		}
		out.flush();
	}

}

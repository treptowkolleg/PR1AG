package pr1.a04;

import java.io.PrintWriter;
import java.util.Scanner;

import pr1.a03.SomeFormats;
import pr1.helper.core.AbstractApplication;
import pr1.helper.core.Delimiter;
import pr1.helper.core.MatchPattern;
import pr1.helper.extension.Collector;
import pr1.helper.extension.Counter;
import pr1.helper.extension.PrintDecorator;

public class FirstInput extends AbstractApplication {
	public static void main(String[] args) {
		new FirstInput();
	}

	@Override
	public void run() {
		PrintDecorator decorator = getConsolePrintDecorator();
		String filename = "../testfiles/zahlen01.txt";

		// Summe der Zahlen der Datei filename
		decorator.printHeadline("Ganze Zahlen (A03)");
		printf("Die Summe der Zahlen ist %10.4f%n", sumOfNumbersIn(filename));
		println();

		// Ganze Zahlen aus Aufgabe 3
		decorator.printHeadline("Ganze Zahlen (A03)");
		withFileScanner(SomeFormats.class, "write_numbers.txt",
				scanner -> prettyPrintNumbersFrom(scanner, 5, 0, getConsolePrintWriter()));
		println();

		// Gemischte Zahlen aus Aufgabe 3
		decorator.printHeadline("Gemischte Zahlen (A03)");
		withFileScanner(SomeFormats.class, "write_mixed_numbers_100.txt",
				scanner -> prettyPrintNumbersFrom(scanner, 5, 2, getConsolePrintWriter()));

		// Zahlen von A nach B mit neuer Breite kopieren
		copyNumberFile("../a03/write_mixed_numbers_100.txt", "new_write_mixed_numbers.txt", 10, 0);
	}

	/**
	 * Summiert alle gültigen Zahlen ({@link Integer} und {@link Double}) auf.
	 *
	 * @param scanner instance containing the input
	 * @return summed numbers as double
	 */
	public double sumOfNumbersIn(Scanner scanner) {
		Collector<Double> sum = new Collector<>();
		while (scanner.hasNext()) {
			if (scanner.hasNextInt() || scanner.hasNextDouble()) {
				sum.add(Double.parseDouble(scanner.next()));
				continue;
			}
			scanner.next();
		}
		return sum.getSum();
	}

	/**
	 * Summiert alle gültigen Zahlen ({@link Integer} und {@link Double}) auf.
	 *
	 * @param filename filename pointing to the input file
	 * @return summed numbers as double
	 */
	public double sumOfNumbersIn(String filename) {
		final Collector<Double> sum = new Collector<>();
		withFileScanner(filename, scanner -> sum.set(sumOfNumbersIn(scanner)));
		return sum.getSum();
	}

	/**
	 * Übergibt Zahlen vom Scanner an den PrintWriter. Breite und Präzision können
	 * manuell eingestellt werden.
	 * <p>
	 * Für eine automatische Breite sollten
	 * {@link SomeFormats#addNumber(double, int)} und
	 * {@link SomeFormats#printAllPretty(PrintWriter, int, int)} verwendet werden.
	 * <p>
	 * Wir benutzen aus Bequemlichkeit die Lösung aus Aufgabe 3, wo wir bereits die
	 * hübsche Formatierung umgesetzt haben.
	 *
	 * @param scanner   instance for reading data
	 * @param width     number width for printing
	 * @param precision float number precision
	 * @param out       instance for outputting data
	 *
	 * @see SomeFormats
	 */
	public void prettyPrintNumbersFrom(Scanner scanner, int width, int precision, PrintWriter out) {
		final Counter counter = new Counter();
		scanner.useDelimiter(Delimiter.WHITESPACE.getPattern());
		scanner.tokens().filter(f -> f.matches(MatchPattern.NUMBER.getRegex())).forEach(element -> {
			if (element.matches(MatchPattern.INTEGER.getRegex())) {
				SomeFormats.printPretty(out, Integer.parseInt(element), width + precision / 2 + precision, 0);
			} else {
				SomeFormats.printPretty(out, Double.parseDouble(element), width, precision);
			}
			if (counter.get() % 10 == 0) {
				out.println();
			}
			counter.increment();
		});
	}

	/**
	 * Kopiert alle Zahlen ({@link Integer} und {@link Double}) von der
	 * Ursprungsdatei in die Zieldatei und ändert dabei die Breite der Zahl sowie
	 * die Präzision der Nachkommastellen.
	 * <p>
	 * Aber eigentlich ist diese Methode nur ein Wrapper für
	 * {@link FirstInput#prettyPrintNumbersFrom(Scanner, int, int, PrintWriter)}.
	 *
	 * @param originFilename      filename that is reading from
	 * @param destinationFilename filename that is writing to
	 * @param width               number width
	 * @param precision           floating point precision
	 */
	public void copyNumberFile(String originFilename, String destinationFilename, int width, int precision) {
		createFileWriter(destinationFilename);
		withFileScanner(originFilename,
				scanner -> prettyPrintNumbersFrom(scanner, width, precision, getFilePrintWriter()));
	}
}

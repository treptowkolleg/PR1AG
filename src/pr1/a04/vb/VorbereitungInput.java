package pr1.a04.vb;

import java.util.Scanner;

import pr1.helper.core.AbstractApplication;
import pr1.helper.core.Delimiter;
import pr1.helper.core.MatchPattern;
import pr1.helper.extension.PrintDecorator;

public class VorbereitungInput extends AbstractApplication {
	public static void main(String[] args) {
		new VorbereitungInput();
	}

	@Override
	public void run() {
		scannerAusprobieren();
	}

	public void scannerAusprobieren() {
		PrintDecorator decorator = getConsolePrintDecorator();
		String intNumbers = "9 8 7 6 5 4 3 2 1";
		String doubleNumbers = "9.1 8.2 7.3 6.4 5.5 4.6 3.7 2.8 1.9";
		String mixedNumbers = "9 1.9 8 2.8 7 3.7";
		String intFormat = "Zahl: %7d%n";
		String doubleFormat = "Double: %10.4f%n";

		// nur ganze Zahlen
		decorator.printHeadline("Ganze Zahlen");
		createInputScanner(intNumbers).withInputScanner(s -> {
			s.useDelimiter(Delimiter.WHITESPACE.getPattern());
			s.tokens().filter(f -> f.matches(MatchPattern.INTEGER.getRegex())).mapToInt(Integer::parseInt)
					.forEach(i -> printf(intFormat, i));
		});
		println();

		// nur Gleitkommazahlen
		decorator.printHeadline("Gleitkommazahlen");
		createInputScanner(doubleNumbers).withInputScanner(s -> {
			s.useDelimiter(Delimiter.WHITESPACE.getPattern());
			s.tokens().filter(f -> f.matches(MatchPattern.DOUBLE.getRegex())).mapToDouble(Double::parseDouble)
					.forEach(i -> printf(doubleFormat, i));
		});
		println();

		// ganze und Gleitkommazahlen
		decorator.printHeadline("Gemischte Zahlen");
		createInputScanner(mixedNumbers).withInputScanner(s -> {
			s.useDelimiter(Delimiter.WHITESPACE.getPattern());
			print(formatScannedIntOrDouble(intFormat, doubleFormat, s));
		});
		println();

		// ganze und Gleitkommazahlen aus der Datei data/testfiles/zahlen01.txt
		// extrahieren
		decorator.printHeadline("Gemischte Zahlen aus Datei");
		// Standardmäßig wird als Pfad die Package-Hierarchie genutzt, daher zuerst zwei
		// Ebenen höher gehen.
		withFileScanner("../../testfiles/zahlen01.txt",
				s -> print(formatScannedIntOrDouble(intFormat, doubleFormat, s)));
	}

	private String formatScannedIntOrDouble(String intFormat, String doubleFormat, Scanner s) {
		StringBuilder output = new StringBuilder();
		while (s.hasNext()) {
			if (s.hasNextInt()) {
				output.append(String.format(intFormat, s.nextInt()));
				continue;
			}
			if (s.hasNextDouble()) {
				output.append(String.format(doubleFormat, s.nextDouble()));
				continue;
			}
			s.next();
		}
		return output.toString();
	}
}

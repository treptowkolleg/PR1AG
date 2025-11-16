package pr1.a04;

import pr1.helper.core.AbstractApplication;
import pr1.helper.core.Delimiter;
import pr1.helper.core.MatchPattern;

public class Demo extends AbstractApplication {
	public static void main(String[] args) {
		new Demo();
	}

	/**
	 * ConsolePrinter und FilePrinter werden ggf. automatisch instantiiert. Nach
	 * Ende von run() werden alle Streams geschlossen.
	 */
	public void run() {
		getFilePrintDecorator().printHeadline("Hallo Welt");
		getConsolePrintDecorator().printHeadline("Hallo Konsolen-Welt");
		println("Und das war's auch hier.");

		// benutzt Ordnerstruktur nach Package der angegebenen Klasse. Hier:
		// ./data/a03/write_numbers.txt
		// createFileReader(Aufgabe03IO.class, "write_numbers.txt");

		// benutzt Ordnerstruktur dieser Klasse.
		withFileScanner("test.txt", s -> {
			s.useDelimiter(Delimiter.WHITESPACE_OR_COMMA.getPattern()).tokens()
					.filter(token -> token.matches(MatchPattern.INTEGER.getRegex())).mapToInt(Integer::parseInt)
					.forEach(i -> this.printfToFile("Zahl %d gefunden.%n", i));
		});
	}
}

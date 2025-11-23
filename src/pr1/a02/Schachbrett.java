/**
 * Copyright (c) 2025 Benjamin Wagner.
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
package pr1.a02;

import pr1.helper.core.FileWriter;
import pr1.helper.extension.PrintDecorator;
import pr1.helper.core.FileTarget;
import pr1.helper.extension.Range;

import java.io.PrintWriter;

public class Schachbrett {
	public static PrintWriter out;

	public static void main(String[] args) {
        out = new PrintWriter(System.out);

		// Gibt schicke Überschriften aus.
		PrintDecorator printer = new PrintDecorator(out);

		// Gibt Output in Datei aus.
		FileWriter fOut = new FileWriter(FileTarget.DATA_DIR, "a02", "schach.txt");

		// Schicke Überschriften für die fOut-Instanz
		PrintDecorator fPrinter = new PrintDecorator(fOut.getWriter());

		// Konsolenausgabe
		printer.printHeadline("Schachbrett, weiß");
		printSchachbrett();
		out.println();
		printer.printHeadline("Schachbrett, schwarz");
		printSchachbrettReverse();
		out.println();
		out.flush();

		// Dateiausgabe
		fPrinter.printHeadline("Schachbrett, weiß");
		fOut.getWriter().print(schachbrett());
		fOut.getWriter().println();
		fPrinter.printHeadline("Schachbrett, schwarz");
		fOut.getWriter().print(schachbrettReverse());
		fOut.getWriter().println();
		fOut.close();
	}

	/**
	 * Gibt eine mehrzeilige Zeichenkette mit den Bezeichnungen der Felder eines
	 * Schachbretts (A1 - H8) aus.
	 */
	public static void printSchachbrett() {
		out.print(schachbrett());
	}

	/**
	 * Gibt eine mehrzeilige Zeichenkette mit den Bezeichnungen der Felder eines
	 * Schachbretts (A1 - H8) aus Sicht des Spielers mit den schwarzen Figuren aus.
	 */
	public static void printSchachbrettReverse() {
		out.print(schachbrettReverse());
	}

	/**
	 * Erzeugt eine mehrzeilige Zeichenkette mit den Bezeichnungen der Felder eines
	 * Schachbretts (A1 - H8).
	 *
	 * @return Ergebnis als Zeichenkette
	 */
	public static String schachbrett() {
		return schachbrett(true);
	}

	/**
	 * Erzeugt eine mehrzeilige Zeichenkette mit den Bezeichnungen der Felder eines
	 * Schachbretts (A1 - H8) aus Sicht des Spielers mit den schwarzen Figuren.
	 *
	 * @return Ergebnis als Zeichenkette
	 */
	public static String schachbrettReverse() {
		return schachbrett(false);
	}

	/**
	 * Erzeugt eine mehrzeilige Zeichenkette mit den Bezeichnungen der Felder eines
	 * Schachbretts (A1 - H8).
	 *
	 * @param isWhitePlayer Aus Sicht des Spielers mit den weißen Figuren
	 * @return Ergebnis
	 */
	public static String schachbrett(boolean isWhitePlayer) {
		StringBuilder result = new StringBuilder();
		StringBuilder rows = new StringBuilder();
		StringBuilder columns = new StringBuilder();
		char[] rowArray;
		char[] columnArray;

		// Werte vorbereiten
		for (char c : new Range('A', 'H')) {
			columns.append(c);
		}
		for (int i : new Range(1, 8)) {
			rows.append(i);
		}
		if (isWhitePlayer) {
			rows.reverse();
		} else {
			columns.reverse();
		}

		// Iterierbare Arrays erzeugen
		rowArray = rows.toString().toCharArray();
		columnArray = columns.toString().toCharArray();

		// Ausgabe zusammenbauen
		for (char row : rowArray) {
			for (char column : columnArray) {
				result.append(column).append(row).append(' ');
			}

			// letztes Zeichen entfernen und Zeilenvorschub anhängen.
			result.setLength(result.length() - 1);
			result.append("\n");
		}

		// Ergebnis ausliefern
		return result.toString();
	}

	/**
	 * Erzeugt eine Zeichenkette, die die Felder eines Schachbretts darstellt.
	 *
	 * @deprecated Funktioniert, ist aber kaum nachvollziehbar.
	 * @param isReverse Schachbrett drehen oder nicht
	 * @return Ergebnis
	 */
	public static String schachbrettAlt(boolean isReverse) {
		StringBuilder result = new StringBuilder();

		// 'A' hat den Unicode-Wert 65.
		int charStart = 65;

		// Ein Schachbrett hat 8x8 Felder
		int cells = 8;
		int cellNumber;
		char cellLetter;

		for (int k = 0; k < cells; k++) {
			for (int i = 0; i < cells; i++) {
				if (!isReverse) {

					// A1 ist unten links.
					cellNumber = cells - k;
					cellLetter = (char) (charStart + i);
					result.append(String.format("%s%d", cellLetter, cellNumber));
				} else {

					// A1 ist oben rechts.
					// oberste Zeile für k = 0: 0 + 1 == 1
					cellNumber = k + 1;

					// erste Spalte ist 'H' = 72 ==> für i = 0: 65 + 8 - 0 - 1 == 72
					cellLetter = (char) (charStart + cells - i - 1);

					// Für k = 0 und i = 0 ergibt sich oben links: H1
					result.append(String.format("%s%d", cellLetter, cellNumber));
				}
				if (cells - 1 > i) {
					result.append(" ");
				}
			}
			result.append("\n");
		}
		return result.toString();
	}
}

/**
 * Copyright (c) 2025 Benjamin Wagner.
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pr1.a01.ue;

import schimkat.berlin.lernhilfe2025ws.objectPlay.DoubleList;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class FirstMethods {

    // bequeme Anpassungsmöglichkeit für das Rahmen-Symbol
    public static String frameChar = "*"; // oder: [...] char frameChar = '*';

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        PrintWriter writer = new PrintWriter(System.out);

        // Aufgaben
        printDecorated(writer, "23.10.: Aufgabe 1b");
        printValue(writer, 4);
        printFlaecheSiebeneck(writer, 1);
        writer.println();

        // START: Überprüfung und Vergleich mit Taschenrechner
        for (Map.Entry<Double, Double> entry : getSiebeneckValues()) {
            try {
                writer.printf("a: %10.3f A_Sollwert: %10.3f A_Istwert: %10.3f%n", entry.getKey(), entry.getValue(),
                        flaecheAllgemein(entry.getKey(), 7));
            } catch (RuntimeException e) {
                writer.print("FEHLER:\t" + e.getMessage());
            }
        }
        // ENDE: Überprüfung und Vergleich mit Taschenrechner
        writer.flush();

        // Aufgabe 5.ue1.d)
        // Nein, es ändert sich nichts, da DoubleList von ArrayList<Double> erbt. Es ist also ein nützlicher Shortcut.
    }

    private static Set<Map.Entry<Double, Double>> getSiebeneckValues() {
        HashMap<Double, Double> allDimensions = new HashMap<>();
        DoubleList formDimensions = new DoubleList();
        DoubleList calculatorFormAreas = new DoubleList();

        // feste Werte, da wir andernfalls nicht parallel nachrechnen können.
        formDimensions.add(1);
        formDimensions.add(2.3);
        formDimensions.add(4);
        formDimensions.add(5.156);
        formDimensions.add(6.789);

        // mit Taschenrechner ermittelte Flächen
        calculatorFormAreas.add(3.634);
        calculatorFormAreas.add(19.223);
        calculatorFormAreas.add(58.143);
        calculatorFormAreas.add(96.605);
        calculatorFormAreas.add(167.489);
        for (int index = 0; index < formDimensions.size(); index++) {
            allDimensions.put(formDimensions.get(index), calculatorFormAreas.get(index));
        }
        return allDimensions.entrySet();
    }

    /**
     * Text mit Rahmen dekorieren und dem PrintWriter hinzufügen.
     *
     * @param out  Instanz des PrintWriters
     * @param text Eingabetext
     */
    public static void printDecorated(PrintWriter out, String text) {
        printHeadline(out, text);
    }

    /**
     * Berechne das Ergebnis mit a in der Methode value(); und gib das Ergebnis aus.
     *
     * @param out Instanz des PrintWriters
     * @param a   ganze Zahl
     */
    public static void printValue(PrintWriter out, int a) {
        out.printf("Die Berechnung mit %d ergibt %d%n", a, value(a));
    }

    /**
     * Formel mit Parameter a berechnen.
     *
     * @param a ganze Zahl
     * @return Ergebnis der Berechnung als ganze Zahl
     */
    public static int value(int a) {
        // (a + 13) * 9 - a wäre eigentlich sinnvoller.
        return (a + 1 + 5 + 7) * 9 - a;
    }

    /**
     * Berechne den Flächeninhalt eines regelmäßigen Siebenecks und runde das
     * Ergebnis auf 2 Nachkommastellen.
     *
     * @param out Instanz des PrintWriters
     * @param a   Seitenlänge
     */
    public static void printFlaecheSiebeneck(PrintWriter out, double a) {
        out.printf("Die Fläche des Siebenecks mit a=%.2f ergibt %.2f Flächeneinheiten.%n", a, flaecheSiebeneck(a));
    }

    /**
     * Berechne den Flächeninhalt eines regelmäßigen Siebenecks. Quelle: <a href=
     * "https://www.mathespass.at/formeln/regelm%C3%A4%C3%9Figes-siebeneck-formeln-und-eigenschaften">Mathespass.at</a>
     *
     * @param a Seitenlänge
     * @return Flächeninhalt
     */
    public static double flaecheSiebeneck(double a) {
        return flaecheAllgemein(a, 7);
    }

    /**
     *
     * @param a Seitenlänge
     * @return Innenradius
     */
    public static double innenRadiusSiebeneck(double a) {
        return innenRadiusAllgemein(a, 7);
    }

    /**
     * Allgemeine Formel zur Berechnung von regelmäßigen n-eckigen Flächen.
     *
     * @param a Seitenlänge
     * @param n Anzahl der Ecken
     * @return Flächeninhalt
     */
    public static double flaecheAllgemein(double a, int n) {
        if (n < 3) {
            throw new RuntimeException(String.format("%d-seitige Flächen sind nicht definiert!%n", n));
        }
        if (a <= 0) {
            throw new RuntimeException(
                    String.format("Flächen mit einer Seitenlänge von a=%.2f sind nicht definiert!%n", a));
        }
        return (n * Math.pow(a, 2)) / (4 * Math.tan(Math.PI / n));
    }

    /**
     *
     * @param a Seitenlänge
     * @param n Anzahl der Ecken
     * @return Innenradius
     */
    public static double innenRadiusAllgemein(double a, int n) {
        return a / (2 * Math.tan(Math.PI / n));
    }

    /**
     * Rahmen erzeugen, der so breit ist wie der Eingabetext (mit Padding und
     * bottom-Margin von 1).
     *
     * @param out  Instanz des PrintWriters
     * @param text Eingabetext
     */
    public static void printBorder(PrintWriter out, String text) {
        printBorder(out, text, 1);
    }

    /**
     * Rahmen erzeugen, der so breit ist wie der Eingabetext (mit Padding und
     * bottom-Margin von n).
     *
     * @param out          Instanz des PrintWriters
     * @param text         Eingabetext
     * @param marginBottom Anzahl der Zeilenumbrüche am Ende der Ausgabe
     */
    public static void printBorder(PrintWriter out, String text, int marginBottom) {
        for (int i = -4; i < text.length(); i++) {
            out.print(frameChar);
        }
        for (int i = 0; i < marginBottom; i++) {
            out.printf("%n");
        }
    }

    /**
     * Links und rechts mit Padding ein "Rahmen"-Symbol dem Eingabetext hinzufügen.
     *
     * @param out  Instanz des PrintWriters
     * @param text Eingabetext
     */
    public static void printHeadline(PrintWriter out, String text) {
        out.printf("%s %s %s%n", frameChar, text, frameChar);
    }

}

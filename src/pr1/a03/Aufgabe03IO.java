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
package pr1.a03;

import pr1.helper.core.StopWatch;
import schimkat.berlin.lernhilfe2025ws.io.ConsolePrinter;
import schimkat.berlin.lernhilfe2025ws.io.FunnyFirstFileWriter;

import java.io.PrintWriter;
import java.util.Locale;

public class Aufgabe03IO {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        ConsolePrinter consolePrinter = ConsolePrinter.createInstance();
        String result = "PASS";

        Locale.setDefault(Locale.US);
        writeIntNumberFile("./data/write_numbers.txt", 100);

        // Zeit für diese Datei stoppen
        for (int amount = 100; amount <= 1e7; amount *= 10) {
            consolePrinter.printf("Stoppe Zeit für %8d Elemente: ", amount);
            try {
                stopWatch.start();
                writeMixedNumberFile("./data/write_mixed_numbers_" + amount + ".txt", amount);
            } catch (OutOfMemoryError e) {
                result = "FAIL";
            } finally {
                stopWatch.stop();
            }
            consolePrinter.printf("%9.3fs\t%s%n", stopWatch.getElapsedSeconds(), result);
        }
        consolePrinter.print("Fertig!");
        consolePrinter.flush();
    }

    public static void writeIntNumberFile(String filename, int numberCount) {
        generateNumbers(filename, numberCount, 0, 0, 1000);
    }

    public static void writeMixedNumberFile(String filename, int numberCount) {
        generateNumbers(filename, numberCount, 4, 0, 1000);
    }

    public static void generateNumbers(String filename, int numberCount, int maxPrecision, int lowerBound, int upperBound) {
        FunnyFirstFileWriter fileWriter = new FunnyFirstFileWriter(filename);
        PrintWriter out = new PrintWriter(fileWriter);

        if (numberCount % 2 == 1) {
            numberCount++;
        }
        for (int i = 1; i <= numberCount; i++) {
            double randomNummer = randomNumber(lowerBound, upperBound);
            int randomPrecise = (int) randomNumber(0, maxPrecision);

            SomeFormats.addNumber(randomNummer, randomPrecise);
        }
        SomeFormats.printAllPretty(out, 3, 10);
        SomeFormats.clearNumbers();
        fileWriter.close();
    }

    /**
     * Erzeugt eine zufällige Gleitkommazahl innerhalb der unteren und oberen Grenzen (inklusiv),
     * die mit einer Wahrscheinlichkeit von 50 % als ganze Zahl formatiert ist <strong>(z. B. 34.00000)</strong>.
     *
     * @param lowerBound untere Grenze (inklusiv)
     * @param upperBound obere Grenze (inklusiv)
     * @return zufällige Zahl, die zwischen lowerBound und upperBound liegt.
     */
    public static double randomNumber(int lowerBound, int upperBound) {
        double randomNumber;

        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("lowerBound muss kleiner als upperBound sein!");
        }
        randomNumber = lowerBound + (Math.random() * (upperBound - lowerBound));
        return Math.random() < .5 ? (int) randomNumber : randomNumber;
    }
}

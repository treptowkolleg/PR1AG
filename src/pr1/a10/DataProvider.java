package pr1.a10;

import treptowkolleg.edu.extension.math.BetterRandom;
import treptowkolleg.edu.extension.streams.Validator;
import treptowkolleg.edu.text.IOApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProvider {

    /**
     * Erzeugt eine Textdatei mit Zahlenpaaren. Ist {@code numberCount}
     * ungerade, wird der Wert um eins erhöht. Deshalb Zahlenpaare, um
     * Punkt-Koordinaten erhalten zu können.
     *
     * @param filename Name der Datei
     * @param numberCount Menge der Zahlen
     */
    public static void writeIntNumberFile(String filename, int numberCount) {
        if (numberCount <= 0) {
            return;
        }
        if (numberCount % 2 != 0) {
            numberCount++;
        }
        final int count = numberCount;

        new IOApplication() {
            @Override
            public void run() {
                int currentNumber;

                createFileWriter(filename);
                for (int i = 1; i <= count; i++) {
                    currentNumber = BetterRandom.get(0, 1000 + 1);
                    printToFile(String.format("%d ", currentNumber));
                    if (i % 10 == 0 && i != count) {
                        printlnToFile();
                    }
                }
            }
        };
    }

    public static List<Integer> integerListFrom(String filename) {
        final List<Integer> list = new ArrayList<>();

        new IOApplication() {
            @Override
            public void run() {
                withFileScanner(filename, scanner -> {
                    list.addAll(scanner.tokens().map(String::trim)
                            .filter(Validator::isInteger)
                            .map(Integer::parseInt)
                            .toList());
                });
            }
        };
        return list;
    }
}

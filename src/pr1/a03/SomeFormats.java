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

import java.io.PrintWriter;

import pr1.helper.entity.MixedNumber;
import pr1.helper.repository.MixedNumberList;

public class SomeFormats {
    public static final MixedNumberList numberList = new MixedNumberList();
    private static int maxLength;
    private static int maxPrecision;

    public static void addNumber(double number, int precision) {
        numberList.add(new MixedNumber(number, precision));
    }

    public static void clearNumbers() {
        numberList.clear();
    }

    public static String prettyInt(int n, int width) {
        return prettyInt((double) n, width);
    }

    public static String prettyInt(double n, int width) {
        return prettyNumber(n, width, 0);
    }

    public static String prettyNumber(double n, int width, int precision) {
        StringBuilder sb = new StringBuilder();

        if (precision > 0) {
            width += 1 + precision;
        }
        sb.append(String.format("%" + width + "." + precision + "f", n));
        if (precision > 0) {
            precision = maxPrecision - precision;
        } else {
            precision = 1 + maxPrecision;
        }
        if (0 == maxPrecision) {
            precision = 0;
        }
        sb.append(" ".repeat(precision));
        return sb.toString();
    }

    public static void printPretty(PrintWriter out, double n, int width,
                                   int precision) {
        out.append(0 != precision ? prettyNumber(n, width, precision) :
                prettyInt(n, width));
    }

    public static void printAllPretty(PrintWriter out, int gap,
                                      int breakPoint) {
        final int[] counter = {1};

        // längste Zahl ermitteln und Länge abspeichern (vor dem Komma).
        maxLength = numberList.getMaxAmountLength();
        maxPrecision = numberList.getMaxPrecision();

        // Alle Zahlen ausgeben
        numberList.forEach(element -> {
            printPretty(out, element.getAmount(), maxLength,
                    element.getPrecision());
            if (counter[0] % breakPoint == 0) {
                out.println();
            } else {
                for (int i = 1; i <= gap; i++) {
                    out.append(" ");
                }
            }
            counter[0]++;
        });
    }
}

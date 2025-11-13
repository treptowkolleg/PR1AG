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
package pr1.tests;

public class Unicode {

    public static void main(String[] args) {
        System.out.println(unicodeTable('A', 7, 4));
        System.out.println(asciiTable());
    }

    public static String unicodeTable(char start, int lineCount, int colCount) {
        StringBuilder sb = new StringBuilder();
        char c = start;
        for (int lineNr = 0; lineNr < lineCount; lineNr++) {
            for (int colNr = 0; colNr < colCount; colNr++) {
                sb.append(String.format(" %s %04X", c, (int) c));
                c++;
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static String asciiTable() {
        return unicodeTable('\u0061', 7, 4);
    }

}

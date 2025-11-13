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

import pr1.a02.Schachbrett;
import schimkat.berlin.lernhilfe2025ws.io.ConsolePrinter;
import schimkat.berlin.lernhilfe2025ws.io.FunnyFirstFileWriter;

import java.util.HashMap;

public class TestSchachbrett {
    public static HashMap<String, Boolean> actions = new HashMap<>();
    public static ConsolePrinter out = ConsolePrinter.createInstance();

    public static void main(String[] args) {
        actions.put("./data/schach_normal.txt", false);
        actions.put("./data/schach_reverse.txt", true);
        out.append(String.format("Beginne Schreiben von %d Dateien.\n", actions.size()));
        actions.forEach(TestSchachbrett::printSchachbrett);
        out.flush();
    }

    public static void printSchachbrett(String filename, boolean isReverse) {
        FunnyFirstFileWriter fout = new FunnyFirstFileWriter(filename);
        fout.append(Schachbrett.schachbrett(!isReverse));
        fout.close();
        out.append(String.format("Datei '%s' erfolgreich geschrieben.%n", filename));
    }

}

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
package pr1.helper.core;

public enum FileTarget {

    PROJECT_DIR("."),
    DATA_DIR("./data"),
    USER_DIR(System.getProperty("user.home"));

    private final String baseDir;
    private final char separator = java.io.File.separatorChar;

    /**
     * @param property Base directory
     */
    FileTarget(String property) {
        baseDir = property;
    }

    public String getBaseDir() {
        return baseDir + separator;
    }

}

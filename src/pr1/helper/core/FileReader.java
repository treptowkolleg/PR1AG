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

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.util.Scanner;

/**
 * A simple file reader utility class for reading text files with
 * {@link java.util.Scanner}.
 * <p>
 * This class provides convenience constructors that automatically build file
 * paths based on {@link FileTarget} and optional subdirectories. It handles
 * file access errors gracefully by printing warnings to {@code System.err}.
 */
public class FileReader {
    private Scanner scanner;
    private File targetFile;

    /**
     * Creates a new FileReader instance for the specified file located under
     * the
     * given target path.
     *
     * @param targetPath the base directory defined by {@link FileTarget}
     * @param file       the name of the file to be read
     */
    public FileReader(FileTarget targetPath, String file) {
        setTargetFile(targetPath.getBaseDir() + file);
        createScanner();
    }

    /**
     * Creates a new FileReader instance for a file located in a subdirectory.
     *
     * @param targetPath the base directory defined by {@link FileTarget}
     * @param path       the relative subdirectory within the base directory
     * @param file       the name of the file to be read
     */
    public FileReader(FileTarget targetPath, String path, String file) {
        String separator = FileSystems.getDefault().getSeparator();
        setTargetFile(targetPath.getBaseDir() + path + separator + file);
        createScanner();
    }

    /**
     * Creates a {@link Scanner} instance for the target file.
     * <p>
     * If the file cannot be found, an error message is printed to
     * {@code System.err}.
     */
    public void createScanner() {
        if (null != targetFile) {
            try {
                this.scanner = new Scanner(targetFile);
            } catch (FileNotFoundException e) {
                System.err.printf("File '%s' not found!%n",
                        targetFile.getPath());
            }
        }
    }

    /**
     * Returns the current {@link Scanner} instance associated with this
     * FileReader.
     *
     * @return the active Scanner, or {@code null} if not initialized
     */
    public Scanner getScanner() {
        return this.scanner;
    }

    /**
     * Closes the current {@link Scanner} instance if it exists.
     * <p>
     * This method should be called to release system resources after reading is
     * complete.
     */
    public void close() {
        if (null != this.scanner) {
            this.scanner.close();
            this.scanner = null;
        }
    }

    /**
     * Sets the file that this FileReader will read from.
     *
     * @param file the full path of the target file
     */
    private void setTargetFile(String file) {
        this.targetFile = new File(file);
    }
}

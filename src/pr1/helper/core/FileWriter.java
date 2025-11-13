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
import java.io.PrintWriter;
import java.nio.file.FileSystems;

/**
 * Utility class for creating and managing {@link PrintWriter} instances
 * for file output.
 * <p>
 * This class handles the creation of writable text files within a base directory
 * specified by {@link FileTarget}. It also ensures that non-existing subdirectories
 * are created automatically.
 * </p>
 * <p>
 * Typical usage is through {@link pr1.helper.core.AbstractApplication}, which provides
 * higher-level lifecycle and error management for file output streams.
 * </p>
 *
 *
 * @see FileTarget
 * @see PrintWriter
 * @see AbstractApplication
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @since 2025
 */
public class FileWriter {

    /**
     * The active {@link PrintWriter} used for file output.
     */
    private PrintWriter fout;

    /**
     * The system-dependent file path separator.
     */
    private final String separator = FileSystems.getDefault().getSeparator();

    /**
     * The target file name to be created or written to.
     */
    private String targetFile;

    /**
     * Constructs a {@link FileWriter} using a {@link FileTarget} as base directory
     * and writes directly into the specified file.
     * <p>
     * Example: if {@code FileTarget.DATA_DIR} resolves to {@code ./data/} and
     * {@code file} equals {@code "output.txt"}, the resulting file will be
     * {@code ./data/output.txt}.
     * </p>
     *
     * @param targetPath the base directory defined by {@link FileTarget}.
     * @param file       the file name to be created within the base directory.
     * @see FileTarget
     * @see PrintWriter
     */
    public FileWriter(FileTarget targetPath, String file) {
        String baseDir = targetPath.getBaseDir();
        setTargetFile(file);
        try {
            this.fout = new PrintWriter(baseDir + targetFile);
        } catch (FileNotFoundException e) {
            System.err.printf("File '%s' could not be created!%n", baseDir + targetFile);
        }
    }

    /**
     * Constructs a {@link FileWriter} using a {@link FileTarget} as base directory
     * and an additional subdirectory path.
     * <p>
     * If the subdirectory does not exist, it will be created automatically using {@link File#mkdirs()}.
     * </p>
     * <p>
     * Example: if {@code FileTarget.DATA_DIR} resolves to {@code ./data/}, {@code path = "a02"}
     * and {@code file = "result.txt"}, the resulting file will be {@code ./data/a02/result.txt}.
     * </p>
     *
     * @param targetPath the base directory defined by {@link FileTarget}.
     * @param path       subdirectory path relative to {@code targetPath}.
     * @param file       file name to be created within the subdirectory.
     * @see FileTarget
     * @see File
     * @see PrintWriter
     */
    public FileWriter(FileTarget targetPath, String path, String file) {
        setTargetFile(file);
        File subPath = new File(targetPath.getBaseDir() + path);
        if (!subPath.exists()) {
            subPath.mkdirs();
        }
        String baseDir = subPath.getAbsolutePath();
        try {
            this.fout = new PrintWriter(baseDir + separator + targetFile);
        } catch (FileNotFoundException e) {
            System.err.printf("File '%s' could not be created!%n", baseDir + separator + targetFile);
        }
    }

    /**
     * Returns the active {@link PrintWriter} instance.
     * <p>
     * This writer can be used for direct write operations such as
     * {@link PrintWriter#print(String)}, {@link PrintWriter#printf(String, Object...)} or
     * {@link PrintWriter#append(CharSequence)}.
     * </p>
     *
     * @return the {@link PrintWriter} associated with this {@code FileWriter}.
     * @see PrintWriter
     */
    public PrintWriter getWriter() {
        return this.fout;
    }

    /**
     * Closes the underlying {@link PrintWriter} stream and releases all associated resources.
     * <p>
     * After calling this method, further write operations will throw a {@link java.io.IOException}.
     * </p>
     *
     * @see PrintWriter#close()
     */
    public void close() {
        if (this.fout != null) {
            this.fout.close();
        }
    }

    /**
     * Sets the target file name.
     * <p>
     * This method is used internally by the constructors to define the output file name.
     * </p>
     *
     * @param file name of the target file.
     */
    private void setTargetFile(String file) {
        this.targetFile = file;
    }
}

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

import pr1.helper.extension.PrintDecorator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Base class for console and file-based applications.
 * <p>
 * This abstract class provides automated setup and teardown of input/output
 * resources. It standardizes the {@link Locale} to {@code Locale.US},
 * automatically executes the subclass-defined {@link #run()} method after
 * construction, and ensures that all open {@link java.io.Closeable} resources
 * (e.g., {@link FileReader}, {@link FileWriter}) are properly closed after
 * execution.
 * </p>
 * <p>
 * Additionally, it provides convenient factory and wrapper methods for
 * {@link Scanner}, {@link PrintWriter}, and {@link PrintDecorator} instances
 * for both console and file output.
 * </p>
 *
 * @author Benjamin Wagner
 * @version 1.0
 * @see FileReader
 * @see FileWriter
 * @see FileTarget
 * @see PrintDecorator
 * @see Scanner
 * @see PrintWriter
 * @since 2025
 */
public abstract class IOApplication {
    private FileReader fileReader;
    private FileWriter fileWriter;
    private Scanner scanner;
    private PrintDecorator filePrintDecorator;
    private PrintWriter filePrintWriter;
    private PrintDecorator printDecorator;
    private PrintWriter printWriter;

    /**
     * Initializes the default locale ({@link Locale#US}), calls {@link #run()},
     * and ensures all open resources are closed afterward.
     */
    public IOApplication() {
        Locale.setDefault(Locale.US);
        try {
            run();
        } catch (Exception e) {
            System.err.printf("Error: %s%nStacktrace:%n", e.getMessage());
            for (StackTraceElement element : e.getStackTrace()) {
                System.err.println(element);
            }
        } finally {
            closeAll();
        }
    }

    /**
     * Creates a new {@link FileReader} instance using the current subclass
     * package hierarchy.
     *
     * @param fileName file name located within the {@code data} directory of
     *                 the subclass.
     * @return this instance for fluent method chaining.
     * @see FileReader
     */
    public IOApplication createFileReader(String fileName) {
        return createFileReader(getClass(), fileName);
    }

    /**
     * Creates a new {@link FileReader} instance based on another class package
     * hierarchy.
     * <p>
     * Example:
     * <ul>
     * <li>{@code pr1.a02.Schachbrett.class -> ./data/a02/}</li>
     * <li>{@code pr1.foobar.foo.bar.FooBar.class -> ./data/foobar/foo/bar/}
     * </li>
     * </ul>
     *
     * @param c    class whose package hierarchy determines the relative data
     *             path.
     * @param file file name located within the derived {@code data} path.
     * @return this instance for fluent method chaining.
     * @see FileReader
     * @see FileTarget
     */
    public IOApplication createFileReader(Class<?> c, String file) {
        if (null != fileReader) {
            fileReader.close();
        }
        this.fileReader = new FileReader(FileTarget.DATA_DIR, getDir(c), file);
        return this;
    }

    /**
     * Creates a new {@link Scanner} instance using the default
     * {@link System#in}.
     *
     * @return this instance for fluent method chaining.
     * @see Scanner
     */
    public IOApplication createInputScanner() {
        scanner = new Scanner(System.in);
        return this;
    }

    /**
     * Creates a new {@link Scanner} instance using the given input content.
     *
     * @param content input string to be scanned.
     * @return this instance for fluent method chaining.
     * @see Scanner
     */
    public IOApplication createInputScanner(String content) {
        scanner = new Scanner(content);
        return this;
    }

    /**
     * Creates a new {@link PrintWriter} instance for console output and a
     * corresponding {@link PrintDecorator}.
     *
     * @return this instance for fluent method chaining.
     * @see PrintDecorator
     */
    public IOApplication createConsoleWriter() {
        if (null != printWriter) {
            printWriter.close();
        }
        this.printWriter = new PrintWriter(System.out);
        this.printDecorator = new PrintDecorator(this.printWriter);
        return this;
    }

    /**
     * Creates a new {@link FileWriter} using the subclass name as base file
     * name, stored in the {@code data} directory.
     * <p>
     * Example: {@code pr1.a02.Schachbrett -> ./data/a02/schachbrett.txt}
     *
     * @return this instance for fluent method chaining.
     * @see FileWriter
     */
    public IOApplication createFileWriter() {
        String fileName = getClass().getSimpleName().toLowerCase() + ".txt";

        return createFileWriter(FileTarget.DATA_DIR, fileName);
    }

    /**
     * Creates a new {@link FileWriter} with a freely chosen file name,
     * stored in the {@code data} directory.
     *
     * @param fileName name of the file to be created.
     * @return this instance for fluent method chaining.
     * @see FileWriter
     * @see FileTarget
     */
    public IOApplication createFileWriter(String fileName) {
        return createFileWriter(FileTarget.DATA_DIR, fileName);
    }

    /**
     * Creates a new {@link FileWriter} with a custom {@link FileTarget} and
     * file name.
     * <p>
     * Example:
     * {@code FileTarget.DATA_DIR, pr1.a02.Schachbrett.class ->
     * ./data/a02/[filename]}
     *
     * @param t    base directory as defined in {@link FileTarget}.
     * @param file name of the file to be created.
     * @return this instance for fluent method chaining.
     * @see FileWriter
     * @see FileTarget
     */
    public IOApplication createFileWriter(FileTarget t, String file) {
        if (null != fileWriter) {
            fileWriter.close();
        }
        fileWriter = new FileWriter(t, getDir(), file);
        filePrintWriter = fileWriter.getWriter();
        filePrintDecorator = new PrintDecorator(filePrintWriter);
        return this;
    }

    /**
     * Appends content to the current console output.
     *
     * @param content char sequence to be appended to the console output.
     * @return this instance for fluent method chaining.
     */
    public IOApplication append(CharSequence content) {
        if (null == printWriter) {
            createConsoleWriter();
        }
        printWriter.append(content);
        return this;
    }

    /**
     * Appends content to the current file output.
     *
     * @param content char sequence to be appended to the file output.
     * @return this instance for fluent method chaining.
     */
    public IOApplication appendToFile(CharSequence content) {
        if (null == fileWriter) {
            createFileWriter();
        }
        filePrintWriter.append(content);
        return this;
    }

    /**
     * Prints content to the console output.
     *
     * @param content String to be printed to the console output.
     * @return this instance for fluent method chaining.
     */
    public IOApplication print(String content) {
        if (null == printWriter) {
            createConsoleWriter();
        }
        printWriter.print(content);
        return this;
    }

    /**
     * Prints content to the file output.
     *
     * @param content String to be printed to the file output.
     * @return this instance for fluent method chaining.
     */
    public IOApplication printToFile(String content) {
        if (null == fileWriter) {
            createFileWriter();
        }
        filePrintWriter.print(content);
        return this;
    }

    /**
     * Prints a linebreak to the console output.
     *
     * @return this instance for fluent method chaining.
     */
    public IOApplication println() {
        if (null == printWriter) {
            createConsoleWriter();
        }
        printWriter.println();
        return this;
    }

    /**
     * Prints a line to the console output appending a linebreak.
     *
     * @param content String to be printed to the console output.
     * @return this instance for fluent method chaining.
     */
    public IOApplication println(String content) {
        if (null == printWriter) {
            createConsoleWriter();
        }
        printWriter.println(content);
        return this;
    }

    /**
     * Prints a linebreak to the file output.
     *
     * @return this instance for fluent method chaining.
     */
    public IOApplication printlnToFile() {
        if (null == fileWriter) {
            createFileWriter();
        }
        filePrintWriter.println();
        return this;
    }

    /**
     * Prints a line to the file output appending a linebreak.
     *
     * @param content String to be printed to the file output.
     * @return this instance for fluent method chaining.
     */
    public IOApplication printlnToFile(String content) {
        if (null == fileWriter) {
            createFileWriter();
        }
        filePrintWriter.println(content);
        return this;
    }

    /**
     * Prints formatted text to the console output.
     *
     * @param format format string (as per {@link String#format}).
     * @param args   arguments referenced by the format specifiers.
     * @return this instance for fluent method chaining.
     */
    public IOApplication printf(String format, Object... args) {
        if (null == printWriter) {
            createConsoleWriter();
        }
        printWriter.printf(format, args);
        return this;
    }

    /**
     * Prints formatted text to the file output.
     *
     * @param format format string (as per {@link String#format}).
     * @param args   arguments referenced by the format specifiers.
     * @return this instance for fluent method chaining.
     */
    public IOApplication printfToFile(String format, Object... args) {
        if (null == fileWriter) {
            createFileWriter();
        }
        filePrintWriter.printf(format, args);
        return this;
    }

    /**
     * Returns the {@link PrintWriter} for console output.
     *
     * @return console {@link PrintWriter}.
     */
    public PrintWriter getConsolePrintWriter() {
        if (null == printWriter) {
            createConsoleWriter();
        }
        return printWriter;
    }

    /**
     * Executes a {@link Consumer} function with the current {@link Scanner}
     * with given input data.
     * <p>
     * The scanner must be initialized beforehand; otherwise, the closure
     * will not be executed.
     *
     * @param fnc lambda function that processes the {@link Scanner}.
     * @see Scanner
     */
    public void withInputScanner(Consumer<Scanner> fnc) {
        if (null == scanner) {
            return;
        }
        try (Scanner scanner = this.scanner) {
            fnc.accept(scanner);
        }
    }

    /**
     * Executes a {@link Consumer} function with a new instance of
     * {@link Scanner} and given input data.
     * <p>
     * The scanner will be instantiated and initialized automatically with given
     * input.
     *
     * @param input given input data.
     * @param fnc   lambda function that processes the {@link Scanner}.
     * @see Scanner
     */
    public void withInputScanner(String input, Consumer<Scanner> fnc) {
        scanner = new Scanner(input);
        withInputScanner(fnc);
    }

    /**
     * Executes a {@link Consumer} function with the current {@link Scanner}
     * from {@link FileReader}.
     * <p>
     * The file reader must be initialized beforehand; otherwise, the closure
     * will not be executed.
     *
     * @param fnc lambda function that processes the {@link Scanner}.
     * @see FileReader
     * @see Scanner
     */
    public void withFileScanner(Consumer<Scanner> fnc) {
        if (null == fileReader || null == fileReader.getScanner()) {
            return;
        }
        try (Scanner scanner = fileReader.getScanner()) {
            fnc.accept(scanner);
        }
    }

    /**
     * Overload: executes {@code closure} for a specific file name in the
     * subclass data path.
     *
     * @param f   name of the file to read from within the subclass data
     *            directory.
     * @param fnc lambda function that processes the {@link Scanner}.
     * @see FileReader
     */
    public void withFileScanner(String f, Consumer<Scanner> fnc) {
        createFileReader(f);
        withFileScanner(fnc);
    }

    /**
     * Overload: executes {@code closure} for a specific class and file path.
     *
     * @param c   the class used to resolve the package-based subdirectory.
     * @param f   name of the file to read from within the derived data path.
     * @param fnc lambda function that processes the {@link Scanner}.
     * @see FileReader
     */
    public void withFileScanner(Class<?> c, String f, Consumer<Scanner> fnc) {
        createFileReader(c, f);
        withFileScanner(fnc);
    }

    /**
     * Returns the {@link PrintWriter} for file output.
     *
     * @return file {@link PrintWriter}.
     */
    public PrintWriter getFilePrintWriter() {
        if (null == fileWriter) {
            createFileWriter();
        }
        return filePrintWriter;
    }

    /**
     * Returns the {@link PrintDecorator} for console output.
     *
     * @return console {@link PrintDecorator}.
     */
    public PrintDecorator getConsolePrintDecorator() {
        if (null == printDecorator) {
            createConsoleWriter();
        }
        return printDecorator;
    }

    /**
     * Returns the {@link PrintDecorator} for file output.
     *
     * @return file {@link PrintDecorator}.
     */
    public PrintDecorator getFilePrintDecorator() {
        if (null == filePrintDecorator) {
            createFileWriter();
        }
        return filePrintDecorator;
    }

    /**
     * Flushes the console output buffer.
     */
    public void flush() {
        if (printWriter != null) {
            printWriter.flush();
        }
    }

    /**
     * Closes the {@link Scanner} if open.
     */
    public void closeScanner() {
        if (null != fileReader) {
            fileReader.close();
            fileReader = null;
        }
    }

    /**
     * Closes the {@link FileWriter} if open.
     */
    public void closeFile() {
        if (null != fileWriter) {
            fileWriter.close();
            filePrintWriter = null;
        }
    }

    /**
     * Closes all open streams (console and file).
     */
    private void closeAll() {
        if (null != printWriter) {
            printWriter.close();
            printWriter = null;
        }
        closeFile();
        closeScanner();
    }

    /**
     * Returns the relative directory path based on the current subclass package
     * hierarchy.
     *
     * @return relative path derived from the current class’s package name.
     */
    private String getDir() {
        return getDir(getClass());
    }

    /**
     * Returns the relative directory path for the specified class package
     * hierarchy.
     *
     * @param c the class whose package name is used to derive the path.
     * @return relative path derived from the given class’s package name.
     */
    private String getDir(Class<?> c) {
        String pkg = c.getPackageName().toLowerCase();
        int di = pkg.indexOf('.');
        String subPkg = (di >= 0) ? pkg.substring(di + 1) : pkg;
        return subPkg.replace('.', java.io.File.separatorChar);
    }

    /**
     * Abstract entry point method that must be implemented by subclasses.
     * It is automatically called by the constructor after initialization.
     */
    public abstract void run() throws IOException;
}
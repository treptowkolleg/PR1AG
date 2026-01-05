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
package treptowkolleg.edu.swing.plot;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A utility class for reading numeric data from a serial port (e.g., an Arduino)
 * and appending it to a shared list in real time. Designed for use in live data
 * acquisition scenarios such as voltage measurements or sensor logging.
 *
 * <p>This class automatically detects the first available serial port on startup,
 * configures it for 9600 baud, 8N1 communication, and provides thread-safe
 * data ingestion. It implements {@link AutoCloseable} to support safe resource
 * cleanup and registers a JVM shutdown hook to ensure the serial port is closed
 * gracefully on application exit.
 *
 * <p>Usage is stateful: call {@link #startReading(ArrayList)} once to begin
 * streaming data, and optionally send a trigger command via {@link #sendStartCommand()}.
 * The class is not thread-safe for concurrent initialization but is safe for
 * concurrent reading and UI updates due to internal synchronization.
 *
 * <p>Requires the {@code jSerialComm} library (MIT licensed). If no serial port
 * is available, the instance remains inert and all operations become no-ops.
 */
public class SerialReader implements AutoCloseable {
    protected final SerialPort port;
    private final Thread readerThread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private boolean isRunning = false;
    private volatile ArrayList<Integer> targetList = null;

    /**
     * Constructs a new SerialReader and attempts to initialize communication with
     * the first available serial port. If no port is found, the instance remains
     * inactive but can be safely used (methods will behave as no-op or return false).
     * A shutdown hook is registered to ensure clean port closure on JVM exit.
     */
    public SerialReader() {
        this.port = Arrays.stream(SerialPort.getCommPorts())
                .findFirst()
                .orElse(null);
        configurePort();
        addShutdownHook();
        this.readerThread = new Thread(this::readLoop, "SerialReader-Thread");
    }

    /**
     * Starts reading serial data from the connected device and appends incoming
     * integer values to the provided list in real time.
     *
     * <p>This method must be called only once per instance. Attempting to start
     * an already running reader will throw an {@code IllegalStateException}.</p>
     *
     * @param yValues the list to which received integer values will be appended;
     *                must not be null
     * @throws IllegalStateException if the reader is already running
     * @throws IllegalArgumentException if {@code yValues} is null
     */
    public void startReading(ArrayList<Integer> yValues) {
        if (running.get()) {
            throw new IllegalStateException("Bereits gestartet");
        }
        if (yValues == null) {
            throw new IllegalArgumentException("Zielliste darf nicht null " +
                    "sein");
        }
        this.targetList = yValues;
        running.set(true);
        readerThread.start();
    }

    /**
     * Sends a "start" command to the connected serial device, instructing it to
     * begin transmitting measurement data. The command is sent as the ASCII string
     * "start\n".
     *
     * <p>This method has no effect if no serial port is open or if the reader is
     * already running.</p>
     */
    public void sendStartCommand() {
        if (port != null && port.isOpen() && !isRunning) {
            try {
                port.getOutputStream().write("start\n".getBytes());
                port.getOutputStream().flush();
                System.out.println("Start-Kommando gesendet.");
                setRunning(true);
            } catch (IOException e) {
                System.err.println("Fehler beim Senden: " + e.getMessage());
            }
        }
    }

    /**
     * Checks whether a serial port is available and successfully opened.
     *
     * @return {@code true} if a serial port is open and ready for communication,
     *         {@code false} otherwise
     */
    public boolean isAvailable() {
        return port != null && port.isOpen();
    }

    /**
     * Checks whether the serial reader is currently active and processing data.
     *
     * @return {@code true} if the reader is running, {@code false} otherwise
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Sets the internal running state flag. This method is typically called
     * by the reader itself or in response to external commands (e.g., after
     * sending a "start" signal).
     *
     * @param running {@code true} to mark the reader as active, {@code false} to mark it as idle
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * Gracefully shuts down the serial reader by stopping the reading thread
     * and closing the serial port. This method blocks for up to 1 second while
     * waiting for the reader thread to terminate.
     *
     * <p>Implements {@link AutoCloseable} for use in try-with-resources statements.</p>
     */
    @Override
    public void close() {
        running.set(false);
        try {
            readerThread.join(1000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        if (port != null && port.isOpen()) {
            port.closePort();
        }
    }

    private void configurePort() {
        if (port == null) {
            return;
        }
        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port.openPort();
    }

    private void addShutdownHook() {
        Runtime.getRuntime()
                .addShutdownHook(new Thread(this::close, "SerialReader" +
                        "-Cleanup"));
    }

    private void readLoop() {
        if (port == null || !port.isOpen()) {
            System.err.println("Port nicht verfügbar – Lese-Thread beendet.");
            running.set(false);
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(port.getInputStream()))) {
            String line;

            while (running.get()) {
                line = reader.readLine();

                if (line != null && !line.isBlank()) {
                    processLine(line.trim());
                }
            }
        } catch (IOException e) {
            if (isRunning) {
                System.err.println("Lese-Fehler: " + e.getMessage());
            }
        } finally {
            running.set(false);
        }
    }

    private void processLine(String line) {
        try {
            double raw = Double.parseDouble(line);
            int scaled = (int) Math.round(raw * 100);
            ArrayList<Integer> list = this.targetList;

            if (list != null) {
                synchronized (list) {
                    if (isRunning) {
                        list.add(scaled);
                    }
                }
            }
        } catch (NumberFormatException ignored) {
            // Ungültige Eingaben werden stillschweigend ignoriert
        }
    }
}
package pr1.a07.plot;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class SerialReader implements AutoCloseable {
    protected final SerialPort port;
    private final Thread readerThread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private boolean isRunning = false;
    private volatile ArrayList<Integer> targetList = null;

    public SerialReader() {
        this.port = Arrays.stream(SerialPort.getCommPorts())
                .findFirst()
                .orElse(null);
        configurePort();
        addShutdownHook();
        this.readerThread = new Thread(this::readLoop, "SerialReader-Thread");
    }

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

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    private void readLoop() {
        if (port == null || !port.isOpen()) {
            System.err.println("Port nicht verfügbar – Lese-Thread beendet.");
            running.set(false);
            return;
        }
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(port.getInputStream()))) {
            try {
                String line;
                while (running.get()) {

                    if (reader.ready()) {
                        line = reader.readLine();
                        if (line != null) {
                            line = line.trim();
                            if (!line.isEmpty()) {
                                try {
                                    double raw = Double.parseDouble(line);
                                    int scaled = (int) (raw * 100);
                                    ArrayList<Integer> list = this.targetList;
                                    if (list != null) {
                                        synchronized (list) {
                                            if (isRunning()) {
                                                list.add(scaled);
                                            }
                                        }
                                    }
                                } catch (NumberFormatException ignored) {
                                }
                            }
                        }
                    } else {
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }

                }
            } catch (IOException e) {
                if (running.get()) {
                    System.err.println("Lese-Fehler: " + e.getMessage());
                }
            }
        } catch (IOException ignored) {
        } finally {
            running.set(false);
        }
    }

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

    protected void configurePort() {
        if (port == null) {
            return;
        }
        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        port.openPort();
    }

    protected void addShutdownHook() {
        Runtime.getRuntime()
                .addShutdownHook(new Thread(this::close, "SerialReader" +
                        "-Cleanup"));
    }
}
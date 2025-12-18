package pr1.a07.plot.demo;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SerialDemo {
    private static volatile SerialPort activePort = null;

    public static void main(String[] args) {
        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort port = Arrays.stream(ports).findFirst().orElse(null);


        port.setComPortParameters(9600, 8, 1, 0);
        port.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        activePort = port;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (activePort != null && activePort.isOpen()) {
                activePort.closePort();
            }
        }, "Port-Cleanup-Hook"));
        port.openPort();
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(port.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    double voltage = Double.parseDouble(line.trim());

                    System.out.printf("⚡ Spannung: %.3f V%n", voltage);
                } catch (NumberFormatException ignored) {
                }
            }
        } catch (IOException e) {
            System.err.println("I/O-Fehler: " + e.getMessage());
        } finally {
            if (activePort != null && activePort.isOpen()) {
                activePort.closePort();
            }
        }
    }
}

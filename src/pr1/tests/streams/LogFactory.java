package pr1.tests.streams;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFactory {
    private static final DateTimeFormatter TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static pr1.tests.streams.java8.Log newLogClassInstance(String line) {
        String[] p = splitAndValidate(line);
        return new pr1.tests.streams.java8.Log(
                p[0], LocalDateTime.parse(p[1], TS_FORMAT), p[2], p[3], p[4],
                Integer.parseInt(p[5])
        );
    }

    public static pr1.tests.streams.java16.Log newLogRecordInstance(String line) {
        String[] p = splitAndValidate(line);
        return new pr1.tests.streams.java16.Log(
                p[0], LocalDateTime.parse(p[1], TS_FORMAT), p[2], p[3], p[4],
                Integer.parseInt(p[5])
        );
    }

    private static String[] splitAndValidate(String line) {
        String[] parts = line.split(";");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Ungültige Zeile: " + line);
        }
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

}

package pr1.tests.streams;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Neu gelernt: Ab Java 16 gibt es den Typ "record", vergleichbar mit "strut"
 * in C bzw. python? Eine record-Instanz ist ein gültiger Tupel.
 * <p>
 * Jede Spalte des Datensatzes entspricht einem Attribut dieses Konstruktes.
 * </p>
 *
 * @param user
 * @param timestamp
 * @param referer
 * @param target
 * @param method
 * @param status
 */
public record Log(
        String user,
        LocalDateTime timestamp,
        String referer,
        String target,
        String method,
        int status
) {
    /**
     * DateTime-Format, dass wir hier definieren
     */
    private static final DateTimeFormatter TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Ein neues Objekt vom Typ Log erzeugen.
     *
     * @param line input string, den wir zerlegen.
     * @return neue Instanz von Log.
     */
    public static Log parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Ungültige Zeile: " + line);
        }
        return new Log(
                parts[0].trim(),
                LocalDateTime.parse(parts[1].trim(), TS_FORMAT),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                Integer.parseInt(parts[5].trim())
        );
    }
}

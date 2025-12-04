package pr1.tests.streams.java16;

import java.time.LocalDateTime;

/**
 * Neu gelernt: Ab Java 16 gibt es den Typ "record", vergleichbar mit "struct"
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
public record Log(String user, LocalDateTime timestamp, String referer,
                  String target, String method, int status) {
}

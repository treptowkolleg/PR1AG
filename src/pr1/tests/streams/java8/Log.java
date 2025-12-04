package pr1.tests.streams.java8;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private final String user;
    private final LocalDateTime timestamp;
    private final String referer;
    private final String target;
    private final String method;
    private final int status;

    private static final DateTimeFormatter TS_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Log(String user, LocalDateTime timestamp, String referer,
               String target, String method, int status) {
        this.user = user;
        this.timestamp = timestamp;
        this.referer = referer;
        this.target = target;
        this.method = method;
        this.status = status;
    }

    // Getter (wie bei record automatisch)
    public String user() { return user; }
    public LocalDateTime timestamp() { return timestamp; }
    public String referer() { return referer; }
    public String target() { return target; }
    public String method() { return method; }
    public int status() { return status; }
}

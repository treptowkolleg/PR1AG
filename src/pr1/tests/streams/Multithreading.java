package pr1.tests.streams;

import pr1.helper.core.AbstractApplication;
import pr1.helper.core.StopWatch;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Multithreading extends AbstractApplication {

    public static void main(String[] args) {
        new Multithreading();
    }

    public Map<Integer, Long> countResponseByDayOfMonth(File logFile,
                                                        int responseCode) throws IOException {
        try (Stream<String> lines = Files.lines(logFile.toPath(),
                StandardCharsets.UTF_8)) {
            return lines
                    .skip(1)
                    .parallel()
                    .map(LogFactory::newLogClassInstance)
                    .filter(log -> log.status() == responseCode)
                    .collect(groupingBy(
                            log -> log.timestamp().getDayOfMonth(),
                            counting()
                    ));
        }
    }

    @Override
    public void run() {
        File f = new File("./data/testfiles/url_tracking_2025-11.csv");
        try {
            int responseCode = 200;
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Map<Integer, Long> result = countResponseByDayOfMonth(f,
                    responseCode);

            // Anzahl der Response-Codes je Tag ausgeben.
            result.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> printf("%2d.: %3d x %d-Response.%n",
                            e.getKey(), e.getValue(), responseCode));
            stopWatch.stop();
            printf("Dauer: %d ms%n", stopWatch.getElapsedMillis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

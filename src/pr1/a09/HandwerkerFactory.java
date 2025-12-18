package pr1.a09;

import java.time.LocalDate;
import java.util.ArrayList;

public class HandwerkerFactory extends AbstractPersonFactory<Handwerker> {
    private static final HandwerkerFactory INSTANCE = new HandwerkerFactory();

    public static Handwerker createHandwerker(String firstName, String lastName,
                                              String birthYear, String gewerk,
                                              double stundenlohn) {
        return INSTANCE.create(firstName, lastName, birthYear, gewerk,
                stundenlohn);
    }

    public static Handwerker createHandwerker(String firstName, String lastName,
                                              LocalDate birthDate,
                                              String gewerk,
                                              double stundenlohn) {
        return INSTANCE.create(firstName, lastName, birthDate, gewerk,
                stundenlohn);
    }

    public static ArrayList<Handwerker> createTestHandwerker() {
        ArrayList<Handwerker> handwerkerList = new ArrayList<>();
        Object[][] data = createTestHandwerkerRaw();

        for (Object[] row : data) {
            handwerkerList.add(createHandwerker(
                    (String) row[0],
                    (String) row[1],
                    (String) row[2],
                    (String) row[3],
                    (Double) row[4]
            ));
        }
        return handwerkerList;
    }

    public static Object[][] createTestHandwerkerRaw() {
        return new Object[][]{
                {"Anna", "Schmidt", "1985", "Tischlerei", 28.50},
                {"Klaus", "Müller", "1972", "Elektroinstallation", 32.00},
                {"Fatima", "Öztürk", "1990", "Sanitär", 29.75},
                {"Jens", "Becker", "1968", "Maurer", 26.00},
                {"Leonie", "Wagner", "1988", "Dachdeckerei", 31.25},
                {"Mehmet", "Yilmaz", "1975", "Kfz-Mechanik", 27.80},
                {"Sven", "Krause", "1982", "Fliesenlegen", 25.50},
                {"Nina", "Hoffmann", "1993", "Schlosserei", 30.00},
                {"Thorsten", "Schulz", "1965", "Lackierung", 24.90},
                {"Petra", "Lang", "1979", "Garten- und Landschaftsbau", 23.40},
                {"David", "Koch", "1987", "Heizungsbau", 33.10},
                {"Ayla", "Demir", "1991", "Bäckerei", 22.00},
                {"Ralf", "Bauer", "1970", "Schweißtechnik", 35.00},
                {"Claudia", "Fuchs", "1984", "Friseurhandwerk", 21.50},
                {"Oskar", "Schreiner", "1958", "Restaurierung", 38.75}
        };
    }

    @Override
    protected Handwerker createInstance(String firstName, String lastName,
                                        LocalDate birthDate,
                                        Object... extraArgs) {
        if (extraArgs.length < 2) {
            throw new IllegalArgumentException(
                    "Handwerker requires exactly two extra arguments: " +
                            "(String) gewerk, (Double) stundenlohn");
        }
        String gewerk = (String) extraArgs[0];
        Double stundenlohn = (Double) extraArgs[1];
        return new Handwerker(firstName, lastName, birthDate, gewerk,
                stundenlohn);
    }
}

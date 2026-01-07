package pr1.a09;

import java.time.LocalDate;
import java.util.ArrayList;

import static pr1.a09.PersonFactory.createPerson;
import static treptowkolleg.edu.extension.streams.Validator.isDouble;
import static treptowkolleg.edu.extension.streams.Validator.isInteger;

public class HandwerkerFactory implements ObjectFactory<Handwerker> {

    public static Handwerker createHandwerker(String firstName, String lastName,
                                              String birthYear, String gewerk,
                                              double stundenlohn) {
        Person person = createPerson(firstName, lastName, birthYear);

        return new Handwerker(person, gewerk, stundenlohn);
    }

    public static Handwerker createHandwerker(String firstName, String lastName,
                                              LocalDate birthDate,
                                              String gewerk,
                                              double stundenlohn) {
        Person person = createPerson(firstName, lastName, birthDate);

        return new Handwerker(person, gewerk, stundenlohn);
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
    public boolean validate(String[] parts) {
        return parts.length == 5
                && isInteger(parts[2])
                && isDouble(parts[4]);
    }

    @Override
    public Handwerker map(String[] parts) {
        return createHandwerker(parts[0], parts[1], parts[2], parts[3],
                Double.parseDouble(parts[4]));
    }
}

package pr1.a06;

import pr1.helper.extension.WesternReplacementRule;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonFactory {

    public static Person createPerson(String firstName, String lastName,
                                      String birthYear) {
        return createPerson(firstName, lastName, Integer.parseInt(birthYear));
    }

    public static Person createPerson(String firstName, String lastName,
                                      int birthYear) {
        return createPerson(firstName, lastName, birthYear, 1, 1);
    }

    public static Person createPerson(String firstName, String lastName,
                                      int birthYear, int birthMonth,
                                      int birthDay) {
        return createPerson(firstName, lastName, LocalDate.of(birthYear,
                birthMonth, birthDay));
    }

    public static Person createPerson(String firstName, String lastName,
                                      LocalDate birthDate) {
        return new Person(firstName, lastName, birthDate);
    }

    public static ArrayList<Person> createTestPersons() {
        ArrayList<Person> personList = new ArrayList<>();
        String[][] persons = createTestPersonsRaw();

        for (String[] person : persons) {
            personList.add(createPerson(person[0], person[1], person[2]));
        }
        return personList;
    }

    /**
     * Erzeugt Rohdaten für Personenobjekte. Testweise werden Namen mit
     * Sonderzeichen erstellt.
     * <p>
     * {@link WesternReplacementRule} für weitere
     * Informationen.
     * </p>
     *
     * @return Mehrdimensionales Array mit Vorname, Name und Geburtsjahr.
     */
    public static String[][] createTestPersonsRaw() {
        return new String[][]{
                {"Ada", "Lovelace", "1815"},
                {"Alan", "Turing", "1912"},
                {"Grace", "Hopper", "1906"},
                {"John", "von Neumann", "1903"},
                {"Donald", "Knuth", "1938"},
                {"Linus", "Torvalds", "1969"},
                {"Tim", "Berners-Lee", "1955"},
                {"Barbara", "Liskov", "1939"},
                {"Vint", "Cerf", "1943"},
                {"Margaret", "Hamilton", "1936"},
                {"Günther", "Maßfühler von Petermann", "1987"},
                {"François", "Bry", "1953"},
                {"Jiří", "Matoušek", "1963"},
                {"Ľubomír", "Balko", "1970"},
                {"Åsmund", "Tveit", "1975"},
        };
    }
}

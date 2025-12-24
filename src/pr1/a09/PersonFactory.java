package pr1.a09;

import java.time.LocalDate;
import java.util.ArrayList;

import static pr1.helper.extension.Validator.isInteger;

public class PersonFactory implements ObjectFactory<Person> {

    public static Person createPerson(String firstName, String lastName,
                                      String birthYear) {
        return createPerson(firstName, lastName, Integer.parseInt(birthYear));
    }

    public static Person createPerson(String firstName, String lastName,
                                      int birthYear) {
        return new Person(firstName, lastName, birthYear);
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

    @Override
    public boolean validate(String[] parts) {
        return parts.length == 3
                && isInteger(parts[2]);
    }

    @Override
    public Person map(String[] parts) {
        return createPerson(parts[0], parts[1], parts[2]);
    }
}

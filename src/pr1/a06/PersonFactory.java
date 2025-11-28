package pr1.a06;

import java.util.ArrayList;

public class PersonFactory {

    public static Person createPerson(String firstName, String lastName,
                                      String birthYear) {
        return createPerson(firstName, lastName, Integer.parseInt(birthYear));
    }

    public static Person createPerson(String firstName, String lastName,
                                      int birthYear) {
        return new Person(firstName, lastName, birthYear);
    }

    public static ArrayList<Person> createTestPersons() {
        ArrayList<Person> personList = new ArrayList<>();
        String[][] persons = getPersonsRaw();

        for (String[] person : persons) {
            personList.add(createPerson(person[0], person[1], person[2]));
        }
        return personList;
    }

    public static String[][] getPersonsRaw() {
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
                {"Margaret", "Hamilton", "1936"}
        };
    }
}

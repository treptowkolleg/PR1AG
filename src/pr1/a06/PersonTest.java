package pr1.a06;

import pr1.helper.core.AbstractApplication;
import pr1.helper.extension.PrintDecorator;
import pr1.helper.extension.StringTransformer;
import pr1.helper.extension.WesternReplacementRule;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PersonTest extends AbstractApplication {
    public static int TEST_PERSON_AMOUNT = 7;

    public static void main(String[] args) {
        new PersonTest();
    }

    public static void printPersons(PrintWriter out,
                                    ArrayList<Person> persons) {
        persons.forEach(out::println);
    }

    public static void printPersonsSlugified(PrintWriter out,
                                             ArrayList<Person> persons) {
        persons.forEach(p -> out.printf("%s %s %s%n",
                StringTransformer.slugify(p.getFirstName(),
                        WesternReplacementRule.class),
                StringTransformer.slugify(p.getLastName(),
                        WesternReplacementRule.class),
                p.getBirthYear()));
    }

    public static void test(PrintWriter out) {
        String[][] persons = PersonFactory.createTestPersonsRaw();
        ArrayList<Person> personsList = new ArrayList<>();

        while (personsList.size() < TEST_PERSON_AMOUNT) {
            int index = personsList.size();

            personsList.add(PersonFactory.createPerson(persons[index][0],
                    persons[index][1], persons[index][2]));
//          out.println(personsList.get(index));
//          out.printf("%s %s %d%n", personsList.get(index).getFirstName(),
//                  personsList.get(index).getLastName(),
//                  personsList.get(index).getBirthYear().getValue());
        }
        printPersons(out, personsList);
    }

    public static void printFactoryPersons(PrintWriter out) {
        printPersons(out, PersonFactory.createTestPersons());
    }

    @Override
    public void run() {
        PrintDecorator decorator = getConsolePrintDecorator();

        decorator.printHeadline("printPersons()");
        printPersons(getConsolePrintWriter(),
                PersonFactory.createTestPersons());
        decorator.printHeadline("test()");
        test(getConsolePrintWriter());
        createFileWriter("factory_persons.txt");
        test(getFilePrintWriter());
        createFileWriter("demo_slugified_persons.txt");
        printlnToFile("Demo, Slugified Strings:");
        printPersonsSlugified(getFilePrintWriter(),
                PersonFactory.createTestPersons());

        // Test: Maschinentext aus Datei dekodieren und auf Konsole ausgeben.
        withFileScanner("import_person_List.txt", s -> {
            // Tokens an Zeilenumbruch trennen
            s.useDelimiter("\\n");
            ArrayList<Person> personList = s.tokens()
                    // Stream<String>
                    .map(String::trim)
                    // nur nicht leere Zeilen
                    .filter(line -> !line.isEmpty())
                    // Stream<String[]> (Sub-Tokens an WS trennen)
                    .map(line -> line.split("\\s+"))
                    // nur Zeilen mit 3 Tokens
                    .filter(parts -> parts.length == 3)
                    // Stream<Person>
                    .map(parts -> new Person(
                            StringTransformer.humanize(parts[0],
                                    WesternReplacementRule.class),
                            StringTransformer.humanize(parts[1],
                                    WesternReplacementRule.class),
                            Integer.parseInt(parts[2])
                    ))
                    // Sammeln und zu ArrayList hinzufügen
                    .collect(Collectors.toCollection(ArrayList::new));
            decorator.printHeadline("dekodierte Personen");
            printPersons(getConsolePrintWriter(), personList);
        });
    }
}

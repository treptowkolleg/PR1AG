package pr1.a06;

import pr1.helper.core.AbstractApplication;
import pr1.helper.extension.PrintDecorator;

import java.io.PrintWriter;
import java.util.ArrayList;

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
        persons.forEach(p -> out.println(p.toStringAsSlug()));
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
    }
}

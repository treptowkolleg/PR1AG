package pr1.a09;

import treptowkolleg.edu.text.IOApplication;
import treptowkolleg.edu.extension.math.BetterRandom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TestThePersons extends IOApplication {

    public static void main(String[] args) {
        new TestThePersons();
    }

    public static void testDifferentTypes(PrintWriter out) {
        List<Student> students;
        String[] subjects = StudentFactory.createTestSubjects();
        ArrayList<Person> people = new ArrayList<>();

        people.addAll(PersonFactory.createTestPersons());
        people.addAll(StudentFactory.createTestStudents());
        people.addAll(HandwerkerFactory.createTestHandwerker());
        printAll(out, people, "Alle Personen");
        students = filterPeople(people, Student.class);
        printAll(out, students, "Altes Studienfach");
        students.forEach(student ->
                student.studienfachWechseln(
                        pickOneNotEqual(student.getStudienfach(), subjects)
                )
        );
        printAll(out, students, "Neues Studienfach");
    }

    public static void testRechnungen(PrintWriter out) {
        List<Handwerker> handwerker =
                HandwerkerFactory.createTestHandwerker().subList(0, 3);

        out.println("Rechnungen".toUpperCase());
        handwerker.forEach(h -> {
            for (int stunden : new int[]{7, 11, 17}) {
                out.printf("%s berechnet für %2d h: %.2f € %n",
                        h.toStringReadable(), stunden, h.rechnung(stunden));
            }
        });
    }

    public static void printAll(PrintWriter out, List<?> list) {
        printAll(out, list, null);
    }

    public static void printAll(PrintWriter out, List<?> list, String title) {
        if (null != title) {
            out.println(title.toUpperCase());
        }
        list.forEach(out::println);
        out.println();
    }

    public static <T extends Person> List<T> filterPeople(List<Person> people,
                                                          Class<T> targetType) {
        return people.stream()
                .filter(targetType::isInstance)
                .map(targetType::cast)
                .toList();
    }

    public static String pickOneNotEqual(String current, String[] elements) {
        String candidate;

        if (elements.length <= 1) {
            throw new IllegalArgumentException("Mindestens zwei Fächer benötigt.");
        }
        do {
            candidate = BetterRandom.pickOne(elements);
        } while (candidate.equals(current));
        return candidate;
    }

    @Override
    public void run() throws IOException {
        testDifferentTypes(getConsolePrintWriter());
        testRechnungen(getConsolePrintWriter());
    }
}

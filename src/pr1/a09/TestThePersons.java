package pr1.a09;

import pr1.helper.core.IOApplication;
import pr1.helper.extension.BetterRandom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TestThePersons extends IOApplication {

    public static void main(String[] args) {
        new TestThePersons();
    }

    public static void testDifferentTypes(PrintWriter out) {
        ArrayList<Person> people = new ArrayList<>();
        List<Student> students;
        String[] subjects = StudentFactory.createTestSubjects();

        people.addAll(PersonFactory.createTestPersons());
        people.addAll(StudentFactory.createTestStudents());
        people.addAll(HandwerkerFactory.createTestHandwerker());
        printAll(out, people, "Alle Personen");
        students = filterPeople(people, Student.class);
        printAll(out, students, "Altes Studienfach");
        students.forEach(student ->
                student.studienfachWechseln(
                        getAnotherElement(student.getStudienfach(), subjects)
                )
        );
        printAll(out, students, "Neues Studienfach");
    }

    public static void testRechnungen(PrintWriter out) {
        List<Handwerker> handwerker =
                HandwerkerFactory.createTestHandwerker().subList(0, 3);

        out.println("Rechnungen".toUpperCase());
        handwerker.forEach(h -> {
            for (int arbeitszeit : new int[]{7, 11, 17}) {
                out.printf("%s berechnet für %2d h: %.2f € %n",
                        h.toStringReadable(), arbeitszeit,
                        h.rechnung(arbeitszeit));
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

    public static String getAnotherElement(String current, String[] elements) {
        String candidate;

        do {
            candidate = elements[BetterRandom.get(0, elements.length)];
        } while (candidate.equals(current));
        return candidate;
    }

    @Override
    public void run() throws IOException {
        testDifferentTypes(getConsolePrintWriter());
        testRechnungen(getConsolePrintWriter());
    }
}

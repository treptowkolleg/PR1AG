package pr1.a09;

import pr1.helper.core.IOApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TestThePersons extends IOApplication {

    public static void main(String[] args) {
        new TestThePersons();
    }

    public static void testDifferentTypes(PrintWriter out) {
        ArrayList<Person> people = new ArrayList<>();

        people.addAll(PersonFactory.createTestPersons());
        people.addAll(StudentFactory.createTestStudents());
        people.addAll(HandwerkerFactory.createTestHandwerker());
        people.forEach(out::println);
    }

    @Override
    public void run() throws IOException {
        testDifferentTypes(getConsolePrintWriter());
    }
}

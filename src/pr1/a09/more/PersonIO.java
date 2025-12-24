package pr1.a09.more;

import pr1.a09.Handwerker;
import pr1.a09.HandwerkerFactory;
import pr1.a09.Person;
import pr1.a09.PersonFactory;
import pr1.a09.Student;
import pr1.a09.StudentFactory;
import pr1.helper.core.IOApplication;
import pr1.helper.extension.PrintDecorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PersonIO extends IOApplication {

    public static void main(String[] args) {
        new PersonIO();
    }


    public static ArrayList<Person> generatePeople(Stream<String> input) {
        return Factory.listFromInput(input, new PersonFactory());
    }

    public static ArrayList<Student> generateStudents(Stream<String> input) {
        return Factory.listFromInput(input, new StudentFactory());
    }

    public static ArrayList<Handwerker> generateHandwerker(Stream<String> input) {
        return Factory.listFromInput(input, new HandwerkerFactory());
    }

    @Override
    public void run() throws IOException {
        PrintDecorator decorator = getConsolePrintDecorator();

        withFileScanner("people.txt", s -> {
            s.useDelimiter("\\R");
            decorator.printHeadline("Handwerker");
            generateHandwerker(s.tokens()).forEach(this::println);
        });
        withFileScanner("people.txt", s -> {
            s.useDelimiter("\\R");
            decorator.printHeadline("Studierende");
            generateStudents(s.tokens()).forEach(this::println);
        });
        withFileScanner("people.txt", s -> {
            s.useDelimiter("\\R");
            decorator.printHeadline("Leute");
            generatePeople(s.tokens()).forEach(this::println);
        });
    }
}

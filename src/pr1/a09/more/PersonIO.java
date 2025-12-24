package pr1.a09.more;

import pr1.a09.HandwerkerFactory;
import pr1.a09.ObjectFactory;
import pr1.a09.PersonFactory;
import pr1.a09.StudentFactory;
import pr1.helper.core.IOApplication;
import pr1.helper.extension.PrintDecorator;

import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

public class PersonIO extends IOApplication {
    public static final Map<String, ObjectFactory<?>> PEOPLE = Map.ofEntries(
            entry("Handwerker", new HandwerkerFactory()),
            entry("Studierende", new StudentFactory()),
            entry("Leute", new PersonFactory())
    );

    public static void main(String[] args) {
        new PersonIO();
    }

    @Override
    public void run() throws IOException {
        PrintDecorator decorator = getConsolePrintDecorator();

        for (Map.Entry<String, ObjectFactory<?>> entry : PEOPLE.entrySet()) {
            decorator.printHeadline(entry.getKey());
            withFileScanner("people.txt", scanner -> {
                Factory.listOf(scanner, entry.getValue())
                        .forEach(this::println);
            });
        }
    }
}

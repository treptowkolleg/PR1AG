package pr1.a09.more;

import pr1.a09.ObjectFactory;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Factory {

    private static <T> ArrayList<T> parseLines(
            Stream<String> input,
            Predicate<String[]> validator,
            Function<String[], T> mapper
    ) {
        return input.map(String::trim)
                .filter(line -> !line.isEmpty())
                .filter(line -> !line.startsWith("#"))
                .map(line -> line.split("\\s+"))
                .filter(validator)
                .map(mapper)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static <T> ArrayList<T> listOfInput(Scanner input,
                                               ObjectFactory<T> factory) {
        input.useDelimiter("\\R");
        return parseLines(input.tokens(), factory::validate, factory::map);
    }
}

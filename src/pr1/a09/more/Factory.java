package pr1.a09.more;

import pr1.a09.ObjectFactory;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Factory {

    public static <T> ArrayList<T> map(
            Stream<String> input,
            Predicate<String[]> validator,
            Function<String[], T> mapper
    ) {
        return input.map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> line.split("\\s+"))
                .filter(validator)
                .map(mapper)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static <T> ArrayList<T> listFromInput(Stream<String> input,
                                                 ObjectFactory<T> factory) {
        return map(input, factory::validate, factory::map);
    }
}

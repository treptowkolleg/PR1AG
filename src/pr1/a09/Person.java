package pr1.a09;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Person {
    protected final String firstName;
    protected final String lastName;
    protected final LocalDate birthDate;

    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Person(String firstName, String lastName, int birthYear) {
        this(firstName, lastName, birthYear, 1, 1);
    }

    public Person(String firstName, String lastName, int birthYear,
                  int birthMonth, int birthDayOfMonth) {
        this(firstName, lastName, LocalDate.of(birthYear, birthMonth,
                birthDayOfMonth));
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return String.join(" ", firstName, lastName);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getBirthYear() {
        return birthDate.getYear();
    }

    public Month getBirthMonth() {
        return birthDate.getMonth();
    }

    public int getBirthDayOfMonth() {
        return birthDate.getDayOfMonth();
    }

    @Override
    public String toString() {
        return toString(" ", getFullName(), getBirthYear());
    }

    public String toStringReadable() {
        return toString(", ", getFullName(), getBirthYear());
    }

    protected String toString(Object... elements) {
        return toString(" ", elements);
    }

    protected String toString(String delimiter, Object... elements) {
        return Arrays.stream(elements)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
    }
}

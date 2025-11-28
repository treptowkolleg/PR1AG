package pr1.a06;

import java.time.LocalDate;
import java.time.Month;

public class Person {
    private final String firstName;
    private final String lastName;
    private final LocalDate birthYear;

    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthDate;
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

    public LocalDate getBirthDate() {
        return birthYear;
    }

    public int getBirthYear() {
        return birthYear.getYear();
    }

    public Month getBirthMonth() {
        return birthYear.getMonth();
    }

    public int getBirthDayOfMonth() {
        return birthYear.getDayOfMonth();
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", firstName, lastName,
                birthYear.getYear());
    }

    public String toStringFullBirthDate() {
        return String.format("%s %s %s", firstName, lastName, birthYear);
    }
}

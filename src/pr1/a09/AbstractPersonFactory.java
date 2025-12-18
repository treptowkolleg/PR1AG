package pr1.a09;

import java.time.LocalDate;

public abstract class AbstractPersonFactory<T extends Person> {

    protected abstract T createInstance(String firstName, String lastName,
                                        LocalDate birthDate,
                                        Object... extraArgs);

    protected LocalDate dateFromYear(int birthYear) {
        return LocalDate.of(birthYear, 1, 1);
    }

    protected LocalDate dateFromParts(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public T create(String firstName, String lastName, String birthYear,
                    Object... extraArgs) {
        return create(firstName, lastName, Integer.parseInt(birthYear),
                extraArgs);
    }

    public T create(String firstName, String lastName, int birthYear,
                    Object... extraArgs) {
        return createInstance(firstName, lastName, dateFromYear(birthYear),
                extraArgs);
    }

    public T create(String firstName, String lastName,
                    int birthYear, int birthMonth, int birthDay,
                    Object... extraArgs) {
        return createInstance(firstName, lastName,
                dateFromParts(birthYear, birthMonth, birthDay), extraArgs);
    }

    public T create(String firstName, String lastName, LocalDate birthDate,
                    Object... extraArgs) {
        return createInstance(firstName, lastName, birthDate, extraArgs);
    }
}

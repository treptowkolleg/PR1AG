package pr1.a09;

import java.time.LocalDate;

public class Student extends Person {
    protected String studienfach;
    protected int matrikelNr;

    public Student(Person person, String studienfach, int matrikelNr) {
        this(person.getFirstName(), person.getLastName(),
                person.getBirthDate(), studienfach, matrikelNr);
    }

    public Student(String firstName, String lastName, LocalDate birthDate,
                   String studienfach, int matrikelNr) {
        super(firstName, lastName, birthDate);
        initFields(studienfach, matrikelNr);
    }

    public Student(String firstName, String lastName, int birthYear,
                   String studienfach, int matrikelNr) {
        super(firstName, lastName, birthYear);
        initFields(studienfach, matrikelNr);
    }

    public Student(String firstName, String lastName, int birthYear,
                   int birthMonth, int birthDayOfMonth, String studienfach,
                   int matrikelNr) {
        super(firstName, lastName, birthYear, birthMonth, birthDayOfMonth);
        initFields(studienfach, matrikelNr);
    }

    public String getStudienfach() {
        return studienfach;
    }

    public int getMatrikelNr() {
        return matrikelNr;
    }

    public void studienfachWechseln(String studienfach) {
        this.studienfach = studienfach;
    }

    @Override
    public String toString() {
        return toString(" ", getFullName(), studienfach, matrikelNr);
    }

    @Override
    public String toStringReadable() {
        return toString(", ", getFullName(), studienfach, matrikelNr);
    }

    private void initFields(String studienfach, int matrikelNr) {
        studienfachWechseln(studienfach);
        this.matrikelNr = matrikelNr;
    }
}

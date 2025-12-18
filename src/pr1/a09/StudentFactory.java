package pr1.a09;

import java.time.LocalDate;
import java.util.ArrayList;

public class StudentFactory extends AbstractPersonFactory<Student> {
    private static final StudentFactory INSTANCE = new StudentFactory();

    public static Student createStudent(String firstName, String lastName,
                                        String birthYear, String studienfach,
                                        int matrikelNr) {
        return INSTANCE.create(firstName, lastName, birthYear, studienfach,
                matrikelNr);
    }

    public static Student createStudent(String firstName, String lastName,
                                        LocalDate birthDate, String studienfach,
                                        int matrikelNr) {
        return INSTANCE.create(firstName, lastName, birthDate, studienfach,
                matrikelNr);
    }

    public static ArrayList<Student> createTestStudents() {
        ArrayList<Student> students = new ArrayList<>();
        Object[][] data = createTestStudentsRaw();

        for (Object[] row : data) {
            students.add(createStudent(
                    (String) row[0],
                    (String) row[1],
                    (String) row[2],
                    (String) row[3],
                    (Integer) row[4]
            ));
        }
        return students;
    }

    public static Object[][] createTestStudentsRaw() {
        return new Object[][]{
                {"Bill", "Gates", "1955", "Informatik", 200001},
                {"Mark", "Zuckerberg", "1984", "Informatik", 200002},
                {"Steve", "Jobs", "1955", "Philosophie", 200003},
                {"Travis", "Kalanick", "1976", "Informatik", 200004},
                {"Edwin", "Catmull", "1945", "Informatik", 200005},
                {"Richard", "Branson", "1950", "Wirtschaft", 200006},
                {"David", "Karp", "1986", "Informatik", 200007},
                {"Peter", "Thiel", "1967", "Jura", 200008},
                {"Ellen", "Pao", "1970", "Informatik", 200009},
                {"Kanye", "West", "1977", "Kunst", 200010},
                {"Troy", "Hunt", "1976", "Informatik", 200011},
                {"Sophie", "Wilson", "1957", "Informatik", 200012},
                {"Jack", "Dorsey", "1976", "Informatik", 200013},
                {"Jan", "Koum", "1976", "Informatik", 200014},
                {"Rihanna", "Fenty", "1988", "Musik", 200015}
        };
    }

    @Override
    protected Student createInstance(String firstName, String lastName,
                                     LocalDate birthDate, Object... extraArgs) {
        if (extraArgs.length < 2) {
            throw new IllegalArgumentException(
                    "Student requires exactly two extra arguments: (String) " +
                            "studienfach, (Integer) matrikelNr");
        }
        String studienfach = (String) extraArgs[0];
        Integer matrikelNr = (Integer) extraArgs[1];
        return new Student(firstName, lastName, birthDate, studienfach,
                matrikelNr);
    }
}

package pr1.a09;

import java.time.LocalDate;
import java.util.ArrayList;

import static pr1.a09.PersonFactory.createPerson;
import static pr1.helper.extension.Validator.isInteger;

public class StudentFactory implements ObjectFactory<Student> {

    public static Student createStudent(String firstName, String lastName,
                                        String birthYear, String studienfach,
                                        int matrikelNr) {
        Person person = createPerson(firstName, lastName, birthYear);

        return new Student(person, studienfach, matrikelNr);
    }

    public static Student createStudent(String firstName, String lastName,
                                        LocalDate birthDate, String studienfach,
                                        int matrikelNr) {
        Person person = createPerson(firstName, lastName, birthDate);

        return new Student(person, studienfach, matrikelNr);
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

    public static String[] createTestSubjects() {
        return new String[]{
                "Informatik",
                "Mathematik",
                "Physik",
                "Biologie",
                "Chemie",
                "Elektrotechnik",
                "Maschinenbau",
                "Wirtschaftsinformatik",
                "Psychologie",
                "Geschichte",
                "Philosophie",
                "Medizin",
                "Rechtswissenschaft",
                "Soziologie",
                "Kunstgeschichte"
        };
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
    public boolean validate(String[] parts) {
        return parts.length == 5
                && isInteger(parts[2])
                && isInteger(parts[4]);
    }

    @Override
    public Student map(String[] parts) {
        return createStudent(parts[0], parts[1], parts[2], parts[3],
                Integer.parseInt(parts[4]));
    }


}

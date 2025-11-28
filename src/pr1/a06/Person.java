package pr1.a06;

import pr1.helper.core.Delimiter;

public class Person {
    private final String firstName;
    private final String lastName;
    private final BirthYear birthYear;

    public Person(String firstName, String lastName, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = new BirthYear(birthYear);
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * replaces whitespace with underscore for scanner friendly readability.
     *
     * @return slugified firstname
     */
    public String getFirstNameAsSlug() {
        return slugify(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * replaces whitespace with underscore for scanner friendly readability.
     *
     * @return slugified lastname
     */
    public String getLastNameAsSlug() {
        return slugify(lastName);
    }

    public BirthYear getBirthYear() {
        return birthYear;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", firstName, lastName, birthYear);
    }

    public String toStringAsSlug() {
        return String.format("%s %s %s", getFirstNameAsSlug(),
                getLastNameAsSlug(), birthYear);
    }

    private String slugify(String string) {
        return string.replaceAll(Delimiter.WHITESPACE.getRegex(), "_");
    }
}

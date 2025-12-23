package pr1.a09;

import java.time.LocalDate;

public class Handwerker extends Person {
    protected String gewerk;
    protected double stundenlohn;

    public Handwerker(Person person, String gewerk, double stundenlohn) {
        this(person.getFirstName(), person.getLastName(),
                person.getBirthDate(), gewerk, stundenlohn);
    }

    public Handwerker(String firstName, String lastName, LocalDate birthDate,
                      String gewerk, double stundenlohn) {
        super(firstName, lastName, birthDate);
        initFields(gewerk, stundenlohn);
    }

    public Handwerker(String firstName, String lastName, int birthYear,
                      String gewerk, double stundenlohn) {
        super(firstName, lastName, birthYear);
        initFields(gewerk, stundenlohn);
    }

    public Handwerker(String firstName, String lastName, int birthYear,
                      int birthMonth, int birthDayOfMonth, String gewerk,
                      double stundenlohn) {
        super(firstName, lastName, birthYear, birthMonth, birthDayOfMonth);
        initFields(gewerk, stundenlohn);
    }

    public String getGewerk() {
        return gewerk;
    }

    public double getStundenlohn() {
        return stundenlohn;
    }

    public String getStundenlohnFormatted() {
        return String.format("%.2f €/h", stundenlohn);
    }

    public double rechnung(double arbeitszeit) {
        if (arbeitszeit <= 0) {
            return 0;
        }
        return arbeitszeit * stundenlohn + 72;
    }

    @Override
    public String toString() {
        return toString(" ", getFullName(), getGewerk(), getStundenlohnFormatted());
    }

    @Override
    public String toStringReadable() {
        return toString(", ", getFullName(), getGewerk(), getStundenlohnFormatted());
    }

    private void initFields(String gewerk, double stundenlohn) {
        this.gewerk = gewerk;
        this.stundenlohn = stundenlohn;
    }
}

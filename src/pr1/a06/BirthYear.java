package pr1.a06;

public class BirthYear {
    private final int value;

    public BirthYear(int birthYear) {
        if (String.valueOf(birthYear).length() < 4) {
            throw new IllegalArgumentException("Year should have 4 digits.");
        }
        this.value = birthYear;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

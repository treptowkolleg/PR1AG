package pr1.training;

public class Schuh {
    protected double groesse;

    public Schuh(double groesse) {
        this.groesse = groesse;
    }

    @Override
    public String toString() {
        return "Schuh der Groesse " + groesse;
    }
}

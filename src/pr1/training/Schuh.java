package pr1.training;

public class Schuh extends AutoToString {

    @ToStringInclude(prefix = "der Größe")
    protected double groesse;

    public Schuh(double groesse) {
        this.groesse = groesse;
    }
}

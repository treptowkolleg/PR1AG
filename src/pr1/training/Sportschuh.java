package pr1.training;

public class Sportschuh extends Schuh {
    @ToStringInclude(prefix = "für")
    private final String sportart;

    public Sportschuh(Sportschuh schuh) {
        this(schuh.groesse, schuh.sportart);
    }

    public Sportschuh(Schuh schuh, String sportart) {
        this(schuh.groesse, sportart);
    }

    public Sportschuh(double groesse, String sportart) {
        super(groesse);
        this.sportart = sportart;
    }
}

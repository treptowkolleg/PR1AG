package pr1.training;

public class AutoStringTest {

    public static void main(String[] args) {
        Schuh schuh = new Schuh(44);
        Sportschuh sportschuh = new Sportschuh(schuh, "Fußball");


        System.out.println(schuh);
        System.out.println(sportschuh);
    }
}

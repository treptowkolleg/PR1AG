package pr1.training;

import schimkat.berlin.lernhilfe2025ws.graphics.FunnyFirstPainter;

public class Radikant {

    public static void main(String[] args) {
        System.out.printf("Radikant A: %f%n", radikant(2,3,4));
        System.out.printf("Radikant B: %f%n", radikantNeu(2,3,4));
        Ringe ro = new Ringe(25, 50);
        FunnyFirstPainter painter = new FunnyFirstPainter();
        painter.add(ro);
        painter.showDrawing();
    }

    public static double radikant(double a, double b, double c) {
        double ergebnis;
        ergebnis = 0;
        double temp = Math.pow(b, 2);
        ergebnis = temp - 4 * a * c;
        return ergebnis;
    }

    public static double radikantNeu(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

}

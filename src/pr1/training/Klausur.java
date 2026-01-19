package pr1.training;

import java.util.Scanner;

public class Klausur {

    public static void main(String[] args) {
        String result = aufgabe01("O~mma~");
        System.out.println(result);
    }

    public static String aufgabe01(String text) {
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (i % 2 == 0) {
                b.append(text.charAt(i));
            }
        }
        return b.toString();
    }

    public static Sportschuh createSchuh(Scanner in) {
        in.useDelimiter("\\R");
        return in.tokens()
                .map(line -> line.split("\\s+"))
                .map(parts ->
                        new Sportschuh(Double.parseDouble(parts[0]), parts[1]))
                .findFirst()
                .orElse(null);
    }
}

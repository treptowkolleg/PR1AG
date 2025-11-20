package pr1.a04;

import java.io.PrintWriter;
import java.util.Scanner;

public class ScannerDemo {

    public static void askFor(Scanner scanner, PrintWriter printWriter) {
        System.out.print("Sag was: ");
        printWriter.println(scanner.nextLine());
    }
}

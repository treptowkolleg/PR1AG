package pr1.a05;

import pr1.helper.core.AbstractApplication;
import pr1.helper.core.Delimiter;
import pr1.helper.extension.PrintDecorator;
import pr1.helper.extension.Range;
import schimkat.berlin.lernhilfe2025ws.io.FunnyFirstFileReader;
import schimkat.berlin.lernhilfe2025ws.objectPlay.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AddressPlay extends AbstractApplication {
    public static PrintWriter printWriter;
    public static PrintDecorator decorator;

    public static void main(String[] args) {
        new AddressPlay();
    }

    @Override
    public void run() {
        printWriter = getConsolePrintWriter();
        decorator = getConsolePrintDecorator();
        String myAddresses = "12356 Berlin Baumstraße 4 56789 Hamburg Freiheit 15";

        decorator.printHeadline("Erste Testausgabe:");
        test(printWriter);
        someInhabitants();

        // nur eine Adresse erzeugen (weitere Token werden ignoriert)
        decorator.printHeadline("Erste Adresse aus String:");
        withInputScanner(myAddresses, scanner -> {
            AdresseList aList = new AdresseList();

            aList.add(createAdresse(scanner));
            printListObjects(printWriter, aList);
        });

        // Alle enthaltenen Adressen erzeugen
        decorator.printHeadline("Alle Adressen aus String:");
        withInputScanner(myAddresses, scanner -> {
            AdresseList adresseList = createAdressen(scanner);

            printListObjects(printWriter, adresseList);
        });

        // Adressen aus Datei importieren und auf der Konsole ausgeben
        decorator.printHeadline("Adressen aus Datei (A):");
        withFileScanner("addresses.txt",
                scanner -> printListObjects(printWriter, createAdressen(scanner)));
        decorator.printHeadline("Adressen aus Datei (B):");
        printListObjects(printWriter, createAdressen("./data/a05/addresses.txt"));

        // Einwohner umziehen lassen
        umzuege(printWriter, "./data/a05/addresses.txt",
                "./data/a05/addresses_new.txt");
    }

    public static void test(PrintWriter out) {
        printListObjects(out, createTestAddresses());
    }

    public static AdresseList createTestAddresses() {
        AdresseList addresses = new AdresseList();

        addresses.add(new Adresse(13353, "Berlin", "Luxemburger Straße", 8));
        for (int i : new Range(1, 4)) {
            Adresse a0 = addresses.get(0);

            addresses.add(new Adresse(a0, a0.getHausNr() + i));
        }
        for (int i : new Range(1, 5)) {
            addresses.add(new Adresse(12437, "Berlin", "Baumschulenstraße", i * 2));
        }
        return addresses;
    }

    public static void someInhabitants() {
        PersonList persons = Factory.createTestPersonliste();
        AdresseList addresses = createTestAddresses();
        EinwohnerList inhabitants = new EinwohnerList();

        for (Person person : persons) {
            int index = (int) (Math.random() * addresses.size() - 1);

            inhabitants.add(new Einwohner(person, addresses.get(index)));
        }
        decorator.printHeadline("VOR UMZUG:");
        printListObjects(printWriter, inhabitants);

        // Die ersten drei Personen umziehen lassen
        for (int inhabitantIndex : new Range(3)) {
            Einwohner inhabitant = inhabitants.get(inhabitantIndex);

            while (true) {
                int index = (int) (Math.random() * addresses.size() - 1);

                if (!inhabitant.getAdresse().equals(addresses.get(index))) {
                    inhabitants.set(inhabitantIndex, new Einwohner(inhabitant, addresses.get(index)));
                    break;
                }
            }
        }
        decorator.printHeadline("NACH UMZUG:");
        printListObjects(printWriter, inhabitants);
    }

    public static Adresse createAdresse(Scanner in) {
        in.useDelimiter(Delimiter.WHITESPACE.getPattern());
        /*
        Nur rudimentäre Fehlerbehandlung. Wir wissen eigentlich, dass jede Adresse aus vier Komponenten besteht, davon
        die erste und letzte aus einem Integer, der Rest aus Strings. Aktuell tun wir aber "fast" so, dass alle Eingaben
        korrekt sind.
         */
        try {
            int plz = in.nextInt();
            String city = in.next();
            String street = in.next().replaceAll("_", " ");
            int streetNumber = in.nextInt();

            return new Adresse(plz, city, street, streetNumber);
        } catch (Exception ignored) {
            return new Adresse(12345, "Generischer Ort", "Dorfstraße", 12);
        }
    }

    public static AdresseList createAdressen(Scanner in) {
        AdresseList addressList = new AdresseList();

        while (in.hasNext()) {
            addressList.add(createAdresse(in));
        }
        return addressList;
    }

    public static AdresseList createAdressen(String filename) {
        FunnyFirstFileReader fileReader = new FunnyFirstFileReader(filename);

        return createAdressen(new Scanner(fileReader));
    }

    public static void umzuege(PrintWriter out, String addressFileStartAdressen, String addressFileZielAdressen) {
        PersonList personList = Factory.createTestPersonliste();
        EinwohnerList einwohnerList = new EinwohnerList();
        AdresseList oldAddressList = createAdressen(addressFileStartAdressen);
        AdresseList newAddressList = createAdressen(addressFileZielAdressen);

        for (int i : new Range(oldAddressList)) {
            einwohnerList.add(new Einwohner(personList.get(i), oldAddressList.get(i)));
        }

        // Ausgabe der Einwohner
        out.println();
        out.println("Einwohner (alte Adresse):");
        printListObjects(out, einwohnerList);

        // nun alle umziehen lassen
        for (int i : new Range(newAddressList)) {
            einwohnerList.set(i, new Einwohner(personList.get(i), newAddressList.get(i)));
        }

        // Ausgabe der Einwohner
        out.println();
        out.println("Einwohner (neue Adresse):");
        printListObjects(out, einwohnerList);
    }

    public static void printListObjects(PrintWriter out, ArrayList<?> list) {
        list.forEach(out::println);
    }
}

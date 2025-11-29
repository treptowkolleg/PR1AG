package pr1.a05.ue;

import pr1.helper.core.AbstractApplication;
import pr1.helper.extension.BetterRandom;
import pr1.helper.extension.PrintDecorator;
import pr1.helper.extension.Range;
import schimkat.berlin.lernhilfe2025ws.io.FunnyFirstFileReader;
import schimkat.berlin.lernhilfe2025ws.objectPlay.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddressPlay extends AbstractApplication {
    public static PrintWriter printWriter;
    public static PrintDecorator decorator;

    public static void main(String[] args) {
        new AddressPlay();
    }

    public static void test(PrintWriter out) {
        printListObjects(out, createTestAddresses());
    }

    public static ArrayList<Adresse> createTestAddresses() {
        ArrayList<Adresse> addresses = new ArrayList<>();

        addresses.add(new Adresse(13353, "Berlin", "Luxemburger Straße", 8));
        for (int i : new Range(1, 4)) {
            Adresse a0 = addresses.get(0);

            addresses.add(new Adresse(a0, a0.getHausNr() + i));
        }
        for (int i : new Range(1, 5)) {
            addresses.add(new Adresse(12437, "Berlin", "Baumschulenstraße",
                    i * 2));
        }
        return addresses;
    }

    public static void someInhabitants() {
        PersonList persons = Factory.createTestPersonliste();
        ArrayList<Adresse> addresses = createTestAddresses();
        EinwohnerList inhabitants = new EinwohnerList();

        for (Person person : persons) {
            int index = BetterRandom.indexOf(addresses);

            inhabitants.add(new Einwohner(person, addresses.get(index)));
        }
        decorator.printHeadline("VOR UMZUG:");
        printListObjects(printWriter, inhabitants);

        // Die ersten drei Personen umziehen lassen
        for (int inhabitantIndex : new Range(3)) {
            Einwohner inhabitant = inhabitants.get(inhabitantIndex);

            while (true) {
                int index = BetterRandom.indexOf(addresses);

                if (!inhabitant.getAdresse().equals(addresses.get(index))) {
                    inhabitants.set(inhabitantIndex, new Einwohner(inhabitant
                            , addresses.get(index)));
                    break;
                }
            }
        }
        decorator.printHeadline("NACH UMZUG:");
        printListObjects(printWriter, inhabitants);
    }

    public static Stream<Adresse> transformToAdresseStream(Scanner in, int parts) {
        return in.useDelimiter("\\n").tokens()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> line.split("\\s+"))
                .filter(sections -> sections.length == parts)
                .map(AddressPlay::createAdresse);
    }

    public static Adresse createAdresse(Scanner in) {
        return transformToAdresseStream(in, 4)
                .findFirst()
                .orElse(null);
    }

    public static Adresse createAdresse(String[] parts) {
        return new Adresse(Integer.parseInt(parts[0]),
                parts[1].replaceAll("_", " "),
                parts[2].replaceAll("_", " "),
                Integer.parseInt(parts[3]));
    }

    public static ArrayList<Adresse> createAdressen(Scanner in) {
        return transformToAdresseStream(in, 4)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Adresse> createAdressen(String filename) {
        return createAdressen(new Scanner(new FunnyFirstFileReader(filename)));
    }

    public static void umzuege(PrintWriter out,
                               String addressFileStartAdressen,
                               String addressFileZielAdressen) {
        PersonList personList = Factory.createTestPersonliste();
        EinwohnerList einwohnerList = new EinwohnerList();
        ArrayList<Adresse> oldAddressList =
                createAdressen(addressFileStartAdressen);
        ArrayList<Adresse> newAddressList =
                createAdressen(addressFileZielAdressen);

        for (int i : new Range(oldAddressList)) {
            einwohnerList.add(new Einwohner(personList.get(i),
                    oldAddressList.get(i)));
        }

        // Ausgabe der Einwohner
        out.println();
        out.println("Einwohner (alte Adresse):");
        printListObjects(out, einwohnerList);

        // nun alle umziehen lassen
        for (int i : new Range(newAddressList)) {
            einwohnerList.set(i, new Einwohner(personList.get(i),
                    newAddressList.get(i)));
        }

        // Ausgabe der Einwohner
        out.println();
        out.println("Einwohner (neue Adresse):");
        printListObjects(out, einwohnerList);
    }

    public static void printListObjects(PrintWriter out, ArrayList<?> list) {
        list.forEach(out::println);
    }

    /**
     * {@link AdresseList} wurde in {@link ArrayList<Adresse>} geändert. Es
     * ändert sich auch hier nichts, weil
     * hier dieselbe Situation stattfindet wie bei {@link DoubleList} und
     * {@link ArrayList<Double>}.
     * <p>
     * Die für die Ausgaben relevanten Objektmethoden werden gleichermaßen
     * durch {@link Adresse} bzw. {@link Double}
     * umgesetzt. Gleichzeitig kann {@link AdresseList} aber auch
     * <i>polymorph</i> zu {@link ArrayList<Adresse>}
     * betrachtet werden. Aus diesem Grund funktioniert die Methode
     * {@link #printListObjects(PrintWriter, ArrayList)}
     * auch für sämtliche ArrayList-Container.
     * </p>
     */
    @Override
    public void run() {
        printWriter = getConsolePrintWriter();
        decorator = getConsolePrintDecorator();
        // wir benötigen nun Zeilenumbrüche zur Trennung der Tupel.
        String myAddresses = "12356 Berlin Baumstraße 4\n56789 Hamburg " +
                "Freiheit 15";

        decorator.printHeadline("Erste Testausgabe:");
        test(printWriter);
        someInhabitants();

        // nur eine Adresse erzeugen (weitere Token werden ignoriert)
        decorator.printHeadline("Erste Adresse aus String:");
        withInputScanner(myAddresses, scanner -> {
            ArrayList<Adresse> aList = new ArrayList<>();

            aList.add(createAdresse(scanner));
            printListObjects(printWriter, aList);
        });

        // Alle enthaltenen Adressen erzeugen
        decorator.printHeadline("Alle Adressen aus String:");
        withInputScanner(myAddresses, scanner -> {
            ArrayList<Adresse> addressList = createAdressen(scanner);

            printListObjects(printWriter, addressList);
        });

        // Adressen aus Datei importieren und auf der Konsole ausgeben
        decorator.printHeadline("Adressen aus Datei (A):");
        withFileScanner("../addresses.txt",
                scanner -> printListObjects(printWriter,
                        createAdressen(scanner)));
        decorator.printHeadline("Adressen aus Datei (B):");
        printListObjects(printWriter, createAdressen("./data/a05/addresses" +
                ".txt"));

        // Einwohner umziehen lassen
        umzuege(printWriter, "./data/a05/addresses.txt", "./data/a05" +
                "/addresses_new.txt");
    }
}

package pr1.a05.vb;

import pr1.helper.core.IOApplication;
import pr1.helper.extension.PrintDecorator;
import schimkat.berlin.lernhilfe2025ws.objectPlay.Adresse;
import schimkat.berlin.lernhilfe2025ws.objectPlay.AdresseList;
import schimkat.berlin.lernhilfe2025ws.objectPlay.Person;
import schimkat.berlin.lernhilfe2025ws.objectPlay.PersonList;

import java.io.PrintWriter;

public class VorbereitungOOP extends IOApplication {
    public static void main(String[] args) {
        new VorbereitungOOP();
    }

    @Override
    public void run() {
        test(getConsolePrintWriter());
    }

    public static void test(PrintWriter out) {
        PrintDecorator decorator = new PrintDecorator(out);

        // Erzeugung und Initialisierung der Personen- und Adressobjekte
        Person p1 = new Person("Alan", "Turing", 1912);
        Person p2 = new Person("Grace", "Hopper", 1906);
        Person p3 = new Person("Donald", "Knuth", 1938);
        Person p4 = new Person("Tim", "Berners-Lee", 1955);
        Person p5 = new Person("Margaret", "Hamilton", 1938);
        PersonList persons = PersonList.of(p1, p2, p3, p4, p5);
        Adresse a1 = new Adresse(12437, "Berlin", "Kiefholzstraße", 274);
        Adresse a2 = new Adresse(13353, "Berlin", "Luxemburger Straße", 9);
        Adresse a3 = new Adresse(a2, 10);
        Adresse a4 = new Adresse(a2, 11);
        Adresse a5 = new Adresse(a2, 12);
        AdresseList addresses = AdresseList.of(a1, a2, a3, a4, a5);

        // Ausgabe der Personen
        decorator.printHeadline("Personen");
        for (Person p : persons) {
            out.println(p);
        }

        // Ausgabe der Adressen
        out.println();
        decorator.printHeadline("Adressen");
        for (Adresse adresse : addresses) {
            out.println(adresse);
        }
    }
}

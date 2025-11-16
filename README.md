# Programmierung 1

## Hilfsklassen verwenden
### Setup

Um bequem auf nützliche Helfer zugreifen zu können, erben wir von ``AbstractApplication``.

````java
package pr1.a04;

import pr1.helper.core.AbstractApplication;

public class Demo extends AbstractApplication {
    public static void main(String[] args) {
        // neue Instanz dieser Klasse erzeugen.
        new Demo();
    }
}
````

In der IDE werden wir zunächst einen Fehler angezeigt bekommen, denn die Oberklasse ``AbstractApplication`` verlangt,
dass wir die Methode ``public void run()`` implementieren. Das tun wir also:

````java
package pr1.a04;

import pr1.helper.core.AbstractApplication;

public class Demo extends AbstractApplication {
    public static void main(String[] args) {
        // neue Instanz dieser Klasse erzeugen.
        new Demo();
    }
    
    @override
    public void run() {
        // hier erfolgen unsere Anweisungen.
    }
}
````

In dieser Methode führen wir nun unsere Anweisungen aus. ``AbstractApplication`` sorgt dafür, dass diese
Methode automatisch ausgeführt wird, sobald wir eine Instanz unserer Klasse mit ``new`` erzeugt haben.

Wir können nun auf hilfreiche Methoden der Oberklasse zugreifen.

### Console Printer

Wir haben direkt Zugriff auf einen KonsolenPrinter. Das macht unseren **Client Code** wesentlich übersichtlicher.
Mit Ende des Programms wird übrigens automatisch ``flush()`` bzw. ``close()`` aufgerufen.

````java
public void run() {
    // erzeugt neuen PrintWriter an System.out
    createConsoleWriter();
    println("Hallo Welt");
    printf("Die Zahl %d ist toll.%n", 5);
    append("Ich ").append("lerne ").append("Programmieren.\n");
}
````

Die Methode ``createConsoleWriter()`` ist zudem optional, da mit jeder Ausgabe geprüft wird, ob wir schon
eine PrintWriter-Instanz haben oder nicht. Wenn nicht, wird automatisch eine Instanz erzeugt. Daher können
wir auch schreiben:

````java
public void run() {
    println("Hallo Welt");
}
````

Für Überschriften und ähnliches haben wir ebenfalls eine nützliche Klasse. Den sogenannten ``PrintDecorator``.

````java
public void run() {
    getConsolePrintDecorator().printHeadline("Meine Überschrift");
    println("Mein Inhalt");
}

/* erzeugte Ausgabe:
#####################
# Meine Überschrift #
#####################

Mein Inhalt
 */
````

### File Printer

Der File Printer funktioniert analog zum Console Printer.

````java
public void run() {
    getFilePrintDecorator().printHeadline("Meine Überschrift");
    printlnToFile("Mein Datei-Inhalt");
}

/* erzeugte Ausgabe:
#####################
# Meine Überschrift #
#####################

Mein Datei-Inhalt
 */
````

#### Zieldatei bestimmen

Anders als beim Console Printer, können wir hier optional festlegen, wo die zu erzeugende Datei gespeichert
werden soll und welchen Namen sie trägt. Wenn wir keine Angaben machen, wird automatisch eine Datei erzeugt, die
zum aktuellen Klassen-Namen passt. Als Standardpfad wird die Package-Struktur genutzt.

Diese Klasse hat den vollqualifizierten Namen ``pr1.a04.Demo``. Die neue Datei liegt daher dann unter
``./data/a04/demo.txt``.

````java
import pr1.helper.core.FileTarget;

// ...

public void run() {
    // erzeugt die Datei ./data/a04/demo.txt
    createFileWriter();
    // erzeugt die Datei ./data/a04/my_file.html
    createFileWriter("my_file.html");
    // erzeugt die Datei './a04/my_file.txt' im Benutzerordner (je nach Betriebssystem)
    createFileWriter(FileTarget.USER_DIR, "my_file.txt");
}
````

### File Reader

Wie beim Schreiben von Dateien können wir auch beim Einlesen verschiedene Möglichkeiten nutzen.
Sollte eine Datei nicht existieren, wird ein kurzer Fehler ausgegeben und die Anweisungen zum
Abarbeiten des Dateiinhalts werden übersprungen.

````java
import pr1.a03.Aufgabe03IO;

// ...

public void run() {
    // liest die Datei ./data/a04/demo.txt
    withFileScanner(scanner -> {
        // Dateiinhalt abarbeiten
    });
    // liest die Datei ./data/a04/my_file.html
    withFileScanner("my_file.html", scanner -> {
        // Dateiinhalt abarbeiten
    });
    // liest die Datei ./data/a03/test.txt
    withFileScanner(Aufgabe03IO.class, "test.txt", scanner -> {
        // Dateiinhalt abarbeiten
    });
}
````

#### Beispiel

Wir haben folgenden Dateiinhalt:

````
40, 20, 50 43 4.23, 7.0
````

```java
import pr1.helper.core.Delimiter;
import pr1.helper.core.MatchPattern;

import java.util.stream.IntStream;
import java.util.stream.Stream;

// ...

public void run() {
    // liest die Datei ./data/a04/demo.txt
    withFileScanner(scanner -> {
        // Als Token-Trenner Leerzeichen und Komma erlauben
        scanner.useDelimiter(Delimiter.WHITESPACE_OR_COMMA.getPattern());
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                // auf der Konsole ausgeben (oder mit printfToFile() in Datei schreiben)
                printf("Zahl %d gefunden.%n", scanner.nextInt());
            } else {
                scanner.next();
            }
        }
    });

    // oder alternativ:
    withFileScanner(scanner -> {
        Stream<String> stream = scanner.useDelimiter(Delimiter.WHITESPACE_OR_COMMA.getPattern()).tokens();
        Stream<String> filteredStream = stream.filter(s -> s.matches(MatchPattern.INTEGER.getRegex()));
        IntStream intStream = filteredStream.mapToInt(Integer::parseInt);
        intStream.forEach(i -> this.printf("Zahl %d gefunden.%n", i));
    });
    
    // und kürzer:
    withFileScanner(scanner -> scanner.useDelimiter(Delimiter.WHITESPACE_OR_COMMA.getPattern()).tokens()
            .filter(s -> s.matches(MatchPattern.INTEGER.getRegex())).mapToInt(Integer::parseInt).forEach(
                    element -> this.printf("Zahl %d gefunden.%n", element))
    );
}

/* ergibt die Ausgabe
Zahl 40 gefunden.
Zahl 20 gefunden.
Zahl 50 gefunden.
Zahl 43 gefunden.
 */
```

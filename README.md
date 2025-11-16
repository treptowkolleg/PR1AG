# Programmierung 1

## Hilfsklassen verwenden
### AbstractApplication

Um bequem auf nützliche Helfer zugreifen zu können, erben wir von AbstractApplication.

````php
package pr1.a04;

import pr1.helper.core.AbstractApplication;

public Class Demo extends AbstractApplication {
    public static void main(String[] args) {
        // neue Instanz dieser Klasse erzeugen.
        new Demo();
    }
}
````

In der IDE werden wir zunächst einen Fehler angezeigt bekommen, denn die Oberklasse ``AbstractApplication`` verlangt,
dass wir die Methode ``public void run()`` implementieren. Das tun wir also:

````php
package pr1.a04;

import pr1.helper.core.AbstractApplication;

public Class Demo extends AbstractApplication {
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

````php
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

````php
public void run() {
    println("Hallo Welt");
}
````

Für Überschriften und ähnliches haben wir ebenfalls eine nützliche Klasse. Den sogenannten ``PrintDecorator``.

````php
public void run() {
    getConsolePrintDecorator().printHeadline("Meine Überschrift");
}

// Ausgabe
#####################
# Meine Überschrift #
#####################
````

## Übungsaufgaben

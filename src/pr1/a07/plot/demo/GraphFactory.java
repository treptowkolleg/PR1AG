/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package pr1.a07.plot.demo;

import pr1.a07.Colors;

/**
 * Fabrikklassen zur Erzeugung vordefinierter trigonometrischer Funktionsgraphen.
 * Jede Methode liefert eine instanziierte, vorkonfigurierte {@link TrigonometrieGraph}-Instanz
 * mit sinnvollen Standardparametern für Demonstrationen und Übungen.
 *
 * <p>Alle Methoden bieten eine überladene Variante mit {@code scale}-Parameter,
 * um die Pixeldichte (Pixel pro Einheit) des Koordinatensystems anzupassen.</p>
 */
public class GraphFactory {

    /**
     * Erzeugt einen standardmäßigen Sinus-Graphen mit {@code scale = 50}.
     *
     * @return vorkonfigurierter Sinus-Graph (rot, Auflösung 0.1, Intervall [-9π, 9π])
     */
    public static TrigonometrieGraph sinus() {
        return sinus(50);
    }

    /**
     * Erzeugt einen Sinus-Graphen mit benutzerdefinierter Skalierung.
     *
     * @param scale Anzahl Pixel pro mathematischer Einheit (empfohlen: 30–70)
     * @return vorkonfigurierter Sinus-Graph
     */
    public static TrigonometrieGraph sinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Sinus-Funktion (rot)")
                .setResolution(.1)
                .setScale(scale)
                .setColor(Colors.RED)
                .build();
    }

    /**
     * Erzeugt einen standardmäßigen Kosinus-Graphen mit {@code scale = 50}.
     * Der Kosinus wird als um π/2 nach links verschobener Sinus dargestellt.
     *
     * @return vorkonfigurierter Kosinus-Graph (blau, mit Phasenverschiebung dx = -0.5)
     */
    public static TrigonometrieGraph cosinus() {
        return cosinus(50);
    }

    /**
     * Erzeugt einen Kosinus-Graphen mit benutzerdefinierter Skalierung.
     *
     * @param scale Anzahl Pixel pro mathematischer Einheit
     * @return vorkonfigurierter Kosinus-Graph
     */
    public static TrigonometrieGraph cosinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Kosinus-Funktion (blau)")
                .setResolution(.1)
                .setScale(scale)
                .setColor(Colors.BLUE)
                .setDx(-.5)
                .build();
    }

    /**
     * Erzeugt den Graphen der Funktion f(x) = sin²(x).
     * Nutzt die Identität sin²(x) = (1 - cos(2x)) / 2 = 0.5·sin(2x - π/2) + 0.5.
     * Dargestellt als Sinuswelle mit Amplitude 0.5, Frequenz 2, vertikalem Offset +0.5
     * und Phasenverschiebung um +π/4 (dx = +0.25).
     *
     * @return vorkonfigurierter Sinus-Quadrat-Graph (lila, Wertebereich [0, 1])
     */
    public static TrigonometrieGraph sinusSquared() {
        return sinusSquared(50);
    }

    /**
     * Erzeugt den Sinus-Quadrat-Graphen mit benutzerdefinierter Skalierung.
     *
     * @param scale Anzahl Pixel pro mathematischer Einheit
     * @return vorkonfigurierter Sinus-Quadrat-Graph
     */
    public static TrigonometrieGraph sinusSquared(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("Sinus-Quadrat (lila)")
                .setResolution(.1)
                .setAmplitude(0.5)
                .setFrequency(2.0)
                .setDy(0.5)
                .setDx(0.25)
                .setScale(scale)
                .setColor(Colors.PURPLE)
                .build();
    }

    /**
     * Erzeugt den Graphen der Funktion f(x) = sin(x)·cos(x).
     * Nutzt die Identität sin(x)·cos(x) = ½·sin(2x).
     * Ergebnis: Sinuswelle mit Amplitude 0.5, Frequenz 2, keine Phasenverschiebung.
     *
     * @return vorkonfigurierter Produkt-Graph (orange, Wertebereich [-0.5, 0.5])
     */
    public static TrigonometrieGraph sinusMalCosinus() {
        return sinusMalCosinus(50);
    }

    /**
     * Erzeugt den sin·cos-Graphen mit benutzerdefinierter Skalierung.
     *
     * @param scale Anzahl Pixel pro mathematischer Einheit
     * @return vorkonfigurierter sin·cos-Graph
     */
    public static TrigonometrieGraph sinusMalCosinus(int scale) {
        return new TrigonometrieGraphBuilder()
                .setTitle("sin(x)·cos(x) (orange)")
                .setResolution(.1)
                .setAmplitude(0.5)
                .setFrequency(2.0)
                .setScale(scale)
                .setColor(Colors.ORANGE)
                .build();
    }
}
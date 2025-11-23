/**
 * Copyright (c) 2025 Benjamin Wagner.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pr1.a02;

import java.io.PrintWriter;
import java.util.Random;

public class SomeFunctions {

	public static void main(String[] args) {
		PrintWriter out = new PrintWriter(System.out);
		Random random = new Random();
		
		// Anzahl der Durchläufe
		int i = 2;
		
		// Flächenparameter
		double a;
		double b;
		double r;

		while (i > 0) {
			a = random.nextDouble();
			b = random.nextDouble();
			r = random.nextDouble();
			out.printf("U (Kreis) mit r = %.2f = %.2f.%n", r, kreisUmfang(r));
			out.printf("U (Rechteck) mit a = %.2f und b = %.2f = %.2f.%n", a, b, rechteckUmfang(a, b));
			out.printf("A (Rechteck) mit a = %.2f und b = %.2f = %.2f.%n", a, b, rechteckFlaeche(a, b));
			out.println();
			i--;
		}
		out.flush();
		
		/*
		 * Beispielausgabe:
		 * 
		 * U (Kreis) mit r = 4,08 = 25,66.
		 * U (Rechteck) mit a = 3,05 und b = 9,97 = 26,03.
		 * A (Rechteck) mit a = 3,05 und b = 9,97 = 30,37.
		 * 
		 * U (Kreis) mit r = 1,59 = 10,02.
		 * U (Rechteck) mit a = 2,79 und b = 10,00 = 25,57.
		 * A (Rechteck) mit a = 2,79 und b = 10,00 = 27,85.
		 * 
		 */
	}

	public static double kreisUmfang(double radius) {
		return 2 * Math.PI * radius;
	}

	public static double rechteckUmfang(double a, double b) {
		return 2 * (a + b);
	}

	public static double rechteckFlaeche(double a, double b) {
		return a * b;
	}
}

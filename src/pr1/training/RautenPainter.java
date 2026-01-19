package pr1.training;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList; // diesen Import hatte ich in der Klausur vergessen.
import java.util.List;

public class RautenPainter {
    private final Color color;
    private final List<Raute> rauten;

    /**
     * Aufgabe 4a): erzeugen Sie im Constructor die Rauten mit den gegebenen
     * Funktionen für y und r.
     */
    public RautenPainter(Color color) {
        double r;
        double y;

        this.color = color;
        rauten = new ArrayList<>();
        for (double x = -300; x <= 0; x += 50) {
            y = .056 * x * x;
            r = .078 * Math.pow((x + 100.1), 2) / (Math.PI * 3);
            rauten.add(new Raute(x, y, r));
        }
    }

    /**
     * Zeichnen Sie auf möglichst einfache Weise die Rauten
     */
    public void drawTo(Graphics2D g) {
        g.setColor(color);
        rauten.forEach(g::draw);
    }
}

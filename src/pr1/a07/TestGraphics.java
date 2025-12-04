package pr1.a07;

import pr1.helper.core.GraphicsApplication;

import java.awt.Color;

public class TestGraphics {
    public static void main(String[] args) {
        GraphicsApplication g = new GraphicsApplication();

        g.setTitle("Übungsaufgabe A07");
        g.add(new Gitter(Color.GRAY, 25));
        g.add(new Muster(Color.BLUE));
        g.showDrawing();
    }
}

package pr1.a07.test;

import treptowkolleg.edu.swing.graphics.GraphicsApplication;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        GraphicsApplication app = new GraphicsApplication();

        app.setTitle("Test");
        app.setResizable(false);
        app.addPlotList(List.of(new FirstGraphics()));
        app.showDrawing();
    }
}

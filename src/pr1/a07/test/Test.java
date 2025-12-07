package pr1.a07.test;

import pr1.helper.core.GraphicsApplication;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        GraphicsApplication app = new GraphicsApplication();

        app.setTitle("Test");
        app.addPlotList(List.of(new FirstGraphics()));
        app.showDrawing();
    }
}

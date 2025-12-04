package pr1.a07.test;

import pr1.helper.core.GraphicsApplication;

public class Test extends GraphicsApplication {

    public static void main(String[] args) {
        GraphicsApplication app = new Test();

        app.setTitle("Test");
        app.add(new FirstGraphics());
        app.showDrawing();
    }
}

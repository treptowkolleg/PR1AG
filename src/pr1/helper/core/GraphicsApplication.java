package pr1.helper.core;

import javax.swing.JFrame;
import java.awt.Dimension;

public class GraphicsApplication extends JFrame {
    protected final DrawingPanel panel;

    public GraphicsApplication() {
        this(800, 600);
    }

    public GraphicsApplication(int width, int height) {
        panel = new DrawingPanel();
        setTitle("GraphicsApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        add(panel);
    }
    
    public void add(Drawable object) {
        panel.add(object);
    }

    public void showDrawing() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

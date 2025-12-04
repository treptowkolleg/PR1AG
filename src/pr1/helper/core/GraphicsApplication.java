package pr1.helper.core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class GraphicsApplication extends JFrame {
    private final ArrayList<Drawable> drawings;

    public GraphicsApplication() {
        drawings = new ArrayList<>();
        setTitle("GraphicsApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        add(new DrawingPanel());
    }

    public void add(Drawable object) {
        drawings.add(object);
    }

    public void showDrawing() {
        setVisible(true);
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawings.forEach(drawable -> drawable.draw(g));
        }
    }
}

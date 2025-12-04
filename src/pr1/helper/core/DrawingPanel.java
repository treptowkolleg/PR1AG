package pr1.helper.core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    protected ArrayList<Drawable> drawings;

    public DrawingPanel() {
        drawings = new ArrayList<>();
    }

    public void add(Drawable drawable) {
        drawings.add(drawable);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawings.forEach(drawable -> drawable.draw(g));
    }
}

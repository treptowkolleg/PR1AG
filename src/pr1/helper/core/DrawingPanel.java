package pr1.helper.core;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    protected List<Drawable> drawings;

    public DrawingPanel() {
        drawings = new ArrayList<>();
    }

    public void add(Drawable drawable) {
        drawings.add(drawable);
    }

    public void setDrawings(List<Drawable> drawings) {
        clear();
        this.drawings.addAll(drawings);
    }

    public void clear() {
        drawings.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawings.forEach(drawable -> drawable.draw(g));
    }
}

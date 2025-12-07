package pr1.helper.core;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GraphicsApplication extends JFrame {
    protected final DrawingPanel panel;
    protected final JPanel controlPanel;
    protected final JButton nextBtn;
    protected final JButton prevBtn;
    protected ArrayList<List<Drawable>> plotLists;
    protected int currentPlot = 0;

    public GraphicsApplication() {
        this(800, 600);
    }

    public GraphicsApplication(int width, int height) {
        plotLists = new ArrayList<>();
        panel = new DrawingPanel();
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.LIGHT_GRAY);
        nextBtn = new JButton("Nächster Plot");
        nextBtn.setPreferredSize(new Dimension(200, 30));
        nextBtn.addActionListener(this::nextPlot);
        prevBtn = new JButton("Vorheriger Plot");
        prevBtn.setPreferredSize(new Dimension(200, 30));
        prevBtn.addActionListener(this::prevPlot);
        controlPanel.add(prevBtn);
        controlPanel.add(nextBtn);
        setTitle("GraphicsApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(600, 300));
        add(panel);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void addPlotList(List<Drawable> drawings) {
        plotLists.add(drawings);
    }

    public void nextPlot(ActionEvent e) {
        if (currentPlot < plotLists.size() - 1) {
            currentPlot++;
        } else {
            currentPlot = 0;
        }
        setDrawings(plotLists.get(currentPlot));
        repaint();
    }

    public void prevPlot(ActionEvent e) {
        if (currentPlot > 0) {
            currentPlot--;
        } else {
            currentPlot = plotLists.size() - 1;
        }
        setDrawings(plotLists.get(currentPlot));
        repaint();
    }

    private void add(Drawable object) {
        panel.add(object);
    }

    public void setDrawings(List<Drawable> drawings) {
        panel.setDrawings(drawings);
    }

    public void showDrawing() {
        plotLists.get(currentPlot).forEach(this::add);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

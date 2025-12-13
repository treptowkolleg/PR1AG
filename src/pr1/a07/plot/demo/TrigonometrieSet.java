package pr1.a07.plot.demo;

import pr1.a07.Colors;
import pr1.a07.plot.PlotApplication;
import pr1.a07.plot.PlotControl;
import pr1.a07.plot.PlotSet;

public class TrigonometrieSet extends PlotSet<TrigonometrieGraph> {

    public TrigonometrieSet() {
        int scale = 50;
        TrigonometrieGraph g2 = new TrigonometrieGraph(Colors.DARKER_GREEN,
                .1, -12, 12, .5, 2., .0, 2., scale);

        g2.setTitle("Grüner Graph");
        setTitle("Trigonometrie-Funktionen");
        setGrid(new TrigonometrieGrid(scale));
        addGraph(new TrigonometrieGraph(null, .1, -4, 4, 1., 1., .0, .0,
                scale));
        addGraph(g2);
    }

    @Override
    public PlotControl<TrigonometrieGraph> createControl(PlotApplication app) {
        return new TrigonometrieControl(app, this);
    }

}

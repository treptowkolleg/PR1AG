/*
 * Copyright (C) 2025 Benjamin Wagner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package pr1.a07;

import treptowkolleg.edu.swing.graphics.Drawable;
import treptowkolleg.edu.swing.graphics.GraphicsApplication;
import treptowkolleg.edu.swing.plot.PlotApplication;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.List;

/**
 * A demonstration entry point for the legacy plotting system based on
 * {@link GraphicsApplication} and manual control panels.
 *
 * <p>This class illustrates the older approach to interactive plotting,
 * which is being superseded by the new, more structured framework located in
 * the {@code pr1.a07.plot} package. The new framework centers around
 * {@link PlotApplication}, which provides:
 * </p>
 * <ul>
 *   <li>Type-safe management of multiple {@code PlotSet} instances</li>
 *   <li>Automatic rendering coordination via a shared {@code DrawablePanel}</li>
 *   <li>Dynamic UI controls created through a fluent {@code ControlBuilder}</li>
 *   <li>Graph selection and real-time parameter synchronization</li>
 * </ul>
 *
 * <p>For examples of the new architecture in action, see the classes in the
 * {@code pr1.a07.plot.demo} package, which demonstrate how to create,
 * configure, and interact with plot sets using my new API.</p>
 *
 * <p>This class remains for comparison and transition purposes only. The new
 * approach is work in progress, though.</p>
 */
public class TestGraphics {
    public static double scaleX = 50;
    public static double scaleY = 50;

    public static void main(String[] args) {
        GraphicsApplication g = new GraphicsApplication(1440, 920);
        TrigoPlotBuilder builder = new TrigoPlotBuilder();
        TrigoPlot plot = builder
                .setResolution(.1)
                .setIntervalStart(-2)
                .setIntervalEnd(4)
                .setColor(Color.BLUE)
                .setScaleX(scaleX)
                .setScaleY(scaleY)
                .setWidth(1.5)
                .setDx(1.0)
                .setDy(1.5)
                .createTrigoPlot();
        List<Drawable> plotSet1 = List.of(new Gitter(), new Muster(Color.RED));
        List<Drawable> plotSet2 = List.of(new TrigoGrid(scaleX, scaleY), plot);

        g.setTitle("Übungsaufgabe A07 + Zusatz");
        g.addPlotList(plotSet1);
        g.addPlotList(plotSet2);
        g.showDrawing();
        SwingUtilities.invokeLater(() -> new TrigoControlPanel(plot, g));
    }
}
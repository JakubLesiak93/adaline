import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;

public class ChartOne extends ApplicationFrame  {

    private Net net;

    ChartOne(Net net) {
        super("Wykresy");
        this.net = net;

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Error dla 1",
                "Error","Wartość",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);

        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new Dimension( 1400 , 367 ) );
        setContentPane( chartPanel );

        setSize(520, 300);

        setVisible(true);
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int step = 1;
        for (double d: net.getOneError()) {

            dataset.addValue(d, "Krok", Integer.toString(step));
            step++;
        }
        return dataset;
    }
}

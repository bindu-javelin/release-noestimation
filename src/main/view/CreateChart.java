package main.view;

/**
 * Created by ferhaty on 11/25/2016.
 */

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;

public class CreateChart {
    public ChartPanel createChartPanel(double[] probability, double[] delivery,
                            double[] cdf,String issueType,int listID) {

        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        for(int i=0;i<delivery.length;i++){
            dataset1.addValue(probability[i], "Delivery time (days)",String.valueOf(delivery[i]));
        }

        final CategoryItemRenderer renderer = new BarRenderer();
        if(listID==2)
            renderer.setSeriesPaint(0, Color.GREEN);

        renderer.setItemLabelsVisible(true);

        final CategoryPlot plot = new CategoryPlot();
        plot.setDataset(dataset1);
        plot.setRenderer(renderer);

        plot.setDomainAxis(new CategoryAxis("Delivery time (days)"));
        plot.setRangeAxis(new NumberAxis("PDF"));

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);


        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        for(int i=0;i<delivery.length;i++){
            dataset2.addValue(cdf[i], "CDF", String.valueOf(delivery[i]));

        }
        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);

        final CategoryItemRenderer renderer3 = new LineAndShapeRenderer();
        plot.setRenderer(2, renderer3);

        plot.mapDatasetToRangeAxis(2, 1);

        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        final JFreeChart chart = new JFreeChart(plot);

        chart.setTitle(issueType);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 350));
        return chartPanel;
    }






}

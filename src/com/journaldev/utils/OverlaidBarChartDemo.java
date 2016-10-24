package com.journaldev.utils;



import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/** 
 * @author Ferhat Yalçın
 */  
public class OverlaidBarChartDemo extends ApplicationFrame {


    public OverlaidBarChartDemo(String a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	public void createchart(double[] probability, double[] delivery,
			double[] cdf) {
		// TODO Auto-generated method stub


      
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        for(int i=0;i<delivery.length;i++){
        	dataset1.addValue(probability[i], "Delivery time (days)",String.valueOf(delivery[i]));
       	
       }
   



        // create the first renderer...
  //      final CategoryLabelGenerator generator = new StandardCategoryLabelGenerator();
        final CategoryItemRenderer renderer = new BarRenderer();
    //    renderer.setLabelGenerator(generator);
        renderer.setItemLabelsVisible(true);
        
        final CategoryPlot plot = new CategoryPlot();
        plot.setDataset(dataset1);
        plot.setRenderer(renderer);
        
        plot.setDomainAxis(new CategoryAxis("Delivery time (days)"));
        plot.setRangeAxis(new NumberAxis("Probability"));

        plot.setOrientation(PlotOrientation.VERTICAL);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainGridlinesVisible(true);

        // now create the second dataset and renderer...
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        for(int i=0;i<delivery.length;i++){
        	dataset2.addValue(cdf[i], "T1", String.valueOf(delivery[i]));
       	
       }
     

        final CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        plot.setDataset(1, dataset2);
        plot.setRenderer(1, renderer2);

     

       
        final CategoryItemRenderer renderer3 = new LineAndShapeRenderer();
        plot.setRenderer(2, renderer3);
        plot.mapDatasetToRangeAxis(2, 1);

        // change the rendering order so the primary dataset appears "behind" the 
        // other datasets...
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle("Overlaid Bar Chart");
      //  chart.setLegend(new StandardLegend());

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }





 

}

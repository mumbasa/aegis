package com.component;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;
import com.vaadin.ui.Component;

public class AgentG {
	
	
	static public Component getChart() {
	        Chart chart = new Chart(ChartType.COLUMN);

	        Configuration conf = chart.getConfiguration();
	        conf.getChart().setMargin(50, 80, 100, 50);

	        conf.setTitle(new Title("World's largest cities per 2008"));

	        XAxis xAxis = new XAxis();
	        xAxis.setCategories(new String[] { "Tokyo", "Jakarta", "New York",
	                "Seoul", "Manila", "Mumbai", "Sao Paulo", "Mexico City",
	                "Dehli", "Osaka", "Cairo", "Kolkata", "Los Angeles",
	                "Shanghai", "Moscow", "Beijing", "Buenos Aires", "Guangzhou",
	                "Shenzhen", "Istanbul" });
	        Labels labels = new Labels();
	        labels.setRotation(-45);
	        labels.setAlign(HorizontalAlign.RIGHT);
	        Style style = new Style();
	        style.setFontSize("13px");
	        style.setFontFamily("Verdana, sans-serif");
	        labels.setStyle(style);
	        xAxis.setLabels(labels);
	        conf.addxAxis(xAxis);

	        YAxis yAxis = new YAxis();
	        yAxis.setMin(0);
	        yAxis.setTitle(new Title("Population (millions)"));
	        conf.addyAxis(yAxis);

	        Legend legend = new Legend();
	        legend.setEnabled(false);
	        conf.setLegend(legend);

	        Tooltip tooltip = new Tooltip();
	        tooltip.setFormatter("''+ this.x +''+'Population in 2008: '"
	                + "+ Highcharts.numberFormat(this.y, 1) +' millions'");
	        conf.setTooltip(tooltip);

	        ListSeries serie = new ListSeries("Population", new Number[] { 34.4,
	                21.8, 20.1, 20, 19.6, 19.5, 19.1, 18.4, 18, 17.3, 16.8, 15,
	                14.7, 14.5, 13.3, 12.8, 12.4, 11.8, 11.7, 11.2 });
	        Labels dataLabels = new Labels();
	        dataLabels.setEnabled(true);
	        dataLabels.setRotation(-90);
	        dataLabels.setColor(new SolidColor(255, 255, 255));
	        dataLabels.setAlign(HorizontalAlign.RIGHT);
	        dataLabels.setX(4);
	        dataLabels.setY(10);
	        dataLabels.setFormatter("this.y");
	        PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
	        plotOptionsColumn.setDataLabels(dataLabels);
	        serie.setPlotOptions(plotOptionsColumn);
	        conf.addSeries(serie);

	        chart.drawChart(conf);

	        return chart;
	    }

	
	
	  public static Component getChartLine() {
	        Chart chart = new Chart();
	        chart.setHeight("450px");
	        chart.setWidth("100%");

	        Configuration configuration = chart.getConfiguration();
	        configuration.getChart().setType(ChartType.LINE);

	        configuration.getTitle().setText("Monthly Average Temperature");
	        configuration.getSubTitle().setText("Source: WorldClimate.com");

	        configuration.getxAxis().setCategories("Jan", "Feb", "Mar", "Apr",
	                "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

	        Axis yAxis = configuration.getyAxis();
	        yAxis.setTitle(new Title("Temperature (°C)"));

	        configuration
	                .getTooltip()
	                .setFormatter(
	                        "''+ this.series.name +''+this.x +': '+ this.y +'°C'");

	        PlotOptionsLine plotOptions = new PlotOptionsLine();
	        plotOptions.setDataLabels(new Labels(true));
	        plotOptions.setEnableMouseTracking(false);
	        configuration.setPlotOptions(plotOptions);

	        ListSeries ls = new ListSeries();
	        ls.setName("Tokyo");
	        ls.setData(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
	                13.9, 9.6);
	        configuration.addSeries(ls);

	        ls = new ListSeries();
	        ls.setName("London");
	        ls.setData(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6,
	                4.8);
	        configuration.addSeries(ls);

	        chart.drawChart(configuration);
	        return chart;
	    }

}

package com.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.themes.VaadinTheme;
import com.vaadin.ui.Component;

public class Charter {

	private static Random rand = new Random(0);
	private static Color[] colors = new VaadinTheme().getColors();

	public static Component getChart() {
		Chart chart = new Chart(ChartType.COLUMN);

		Configuration conf = chart.getConfiguration();

		conf.setTitle("Total Staff Registered");

		XAxis x = new XAxis();
		x.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec");
		conf.addxAxis(x);

		YAxis y = new YAxis();
		y.setMin(0);
		y.setTitle("Cedis (Thousand)");
		conf.addyAxis(y);

		Legend legend = new Legend();
		legend.setLayout(LayoutDirection.VERTICAL);
		legend.setBackgroundColor("#FFFFFF");
		legend.setHorizontalAlign(HorizontalAlign.LEFT);
		legend.setVerticalAlign(VerticalAlign.TOP);
		legend.setX(100);
		legend.setY(70);
		legend.setFloating(true);
		legend.setShadow(true);
		conf.setLegend(legend);

		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("this.x +': '+ this.y +' mm'");
		conf.setTooltip(tooltip);

		PlotOptionsColumn plot = new PlotOptionsColumn();
		plot.setPointPadding(0.2);
		plot.setBorderWidth(0);

		conf.addSeries(new ListSeries("MMP", 49.9, 71.5, 106.4, 129.2, 144.0,
				176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4));
		conf.addSeries(new ListSeries("ETP", 83.6, 78.8, 98.5, 93.4, 106.0,
				84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3));
		conf.addSeries(new ListSeries("IMP", 48.9, 38.8, 39.3, 41.4, 47.0,
				48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2));
		conf.addSeries(new ListSeries("EMP", 42.4, 33.2, 34.5, 39.7, 52.6,
				75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1));
		conf.disableCredits();
		chart.drawChart(conf);
		return chart;
	}

	public static Component getChart2(HashMap<String, Number> stuff) {
		Chart chart = new Chart(ChartType.COLUMN);
		chart.setHeight("300px");
		Configuration conf = chart.getConfiguration();

		conf.setTitle("Total Staff Registered");

		XAxis x = new XAxis();
		x.setCategories((stuff.keySet().toArray(new String[stuff.size()])));
		conf.addxAxis(x);

		YAxis y = new YAxis();
		y.setMin(0);
		y.setTitle("People");
		conf.addyAxis(y);

		Legend legend = new Legend();
		legend.setLayout(LayoutDirection.VERTICAL);
		legend.setBackgroundColor("#FFFFFF");
		legend.setHorizontalAlign(HorizontalAlign.LEFT);
		legend.setVerticalAlign(VerticalAlign.TOP);
		legend.setX(100);
		legend.setY(70);
		legend.setFloating(true);
		legend.setShadow(true);
		conf.setLegend(legend);

		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("this.x +': '+ this.y +' mm'");
		conf.setTooltip(tooltip);

		PlotOptionsColumn plot = new PlotOptionsColumn();
		plot.setPointPadding(1);
		plot.setBorderWidth(0);

		conf.addSeries(new ListSeries("YEARS", stuff.values().toArray(
				new Number[stuff.size()])));

		conf.disableCredits();
		chart.drawChart(conf);
		return chart;
	}

	protected Component getDoe() {
		Component ret = createChart();
		ret.setWidth("100%");
		ret.setHeight("450px");
		return ret;
	}

	public static Chart createChart() {
		rand = new Random(0);

		Chart chart = new Chart(ChartType.PIE);

		Configuration conf = chart.getConfiguration();

		conf.setTitle("Staff Contribution share, April, 2011");

		YAxis yaxis = new YAxis();
		yaxis.setTitle("Total percent  share");

		PlotOptionsPie pie = new PlotOptionsPie();
		pie.setShadow(false);
		conf.setPlotOptions(pie);

		conf.getTooltip().setValueSuffix("%");

		DataSeries innerSeries = new DataSeries();
		innerSeries.setName("Browsers");
		PlotOptionsPie innerPieOptions = new PlotOptionsPie();
		innerSeries.setPlotOptions(innerPieOptions);
		innerPieOptions.setSize(237);
		innerPieOptions.setDataLabels(new Labels());
		innerPieOptions.getDataLabels().setFormatter(
				"this.y > 5 ? this.point.name : null");
		innerPieOptions.getDataLabels().setColor(new SolidColor(255, 255, 255));
		innerPieOptions.getDataLabels().setDistance(-30);

		Color[] innerColors = Arrays.copyOf(colors, 5);
		innerSeries.setData(new String[] { "MSIE", "Firefox", "Chrome",
				"Safari", "Opera" }, new Number[] { 55.11, 21.63, 11.94, 7.15,
				2.14 }, innerColors);

		DataSeries outerSeries = new DataSeries();
		outerSeries.setName("Versions");
		PlotOptionsPie outerSeriesOptions = new PlotOptionsPie();
		outerSeries.setPlotOptions(outerSeriesOptions);
		outerSeriesOptions.setInnerSize(237);
		outerSeriesOptions.setSize(318);
		outerSeriesOptions.setDataLabels(new Labels());
		outerSeriesOptions.getDataLabels().setFormatter(
				"this.y > 1 ? ''+ this.point.name +': '+ this.y +'%' : null");

		DataSeriesItem[] outerItems = new DataSeriesItem[] {
		/* @formatter:off */
		new DataSeriesItem("MSIE 6.0", 10.85, color(0)),
				new DataSeriesItem("MSIE 7.0", 7.35, color(0)),
				new DataSeriesItem("MSIE 8.0", 33.06, color(0)),
				new DataSeriesItem("MSIE 9.0", 2.81, color(0)),
				new DataSeriesItem("Firefox 2.0", 0.20, color(1)),
				new DataSeriesItem("Firefox 3.0", 0.83, color(1)),
				new DataSeriesItem("Firefox 3.5", 1.58, color(1)),
				new DataSeriesItem("Firefox 3.6", 13.12, color(1)),
				new DataSeriesItem("Firefox 4.0", 5.43, color(1)),
				new DataSeriesItem("Chrome 5.0", 0.12, color(2)),
				new DataSeriesItem("Chrome 6.0", 0.19, color(2)),
				new DataSeriesItem("Chrome 7.0", 0.12, color(2)),
				new DataSeriesItem("Chrome 8.0", 0.36, color(2)),
				new DataSeriesItem("Chrome 9.0", 0.32, color(2)),
				new DataSeriesItem("Chrome 10.0", 9.91, color(2)),
				new DataSeriesItem("Chrome 11.0", 0.50, color(2)),
				new DataSeriesItem("Chrome 12.0", 0.22, color(2)),
				new DataSeriesItem("Safari 5.0", 4.55, color(3)),
				new DataSeriesItem("Safari 4.0", 1.42, color(3)),
				new DataSeriesItem("Safari Win 5.0", 0.23, color(3)),
				new DataSeriesItem("Safari 4.1", 0.21, color(3)),
				new DataSeriesItem("Safari/Maxthon", 0.20, color(3)),
				new DataSeriesItem("Safari 3.1", 0.19, color(3)),
				new DataSeriesItem("Safari 4.1", 0.14, color(3)),
				new DataSeriesItem("Opera 9.x", 0.12, color(4)),
				new DataSeriesItem("Opera 10.x", 0.37, color(4)),
				new DataSeriesItem("Opera 11.x", 1.65, color(4))
		/* @formatter:on */
		};

		outerSeries.setData(Arrays.asList(outerItems));
		conf.setSeries(innerSeries, outerSeries);
		chart.drawChart(conf);
		conf.disableCredits();
		return chart;
	}

	public static Chart createPieChart(String caption,
			HashMap<String, Number> stuffn) {
		rand = new Random(0);

		Chart chart = new Chart(ChartType.PIE);

		Configuration conf = chart.getConfiguration();

		conf.setTitle(caption);

		YAxis yaxis = new YAxis();
		yaxis.setTitle("Total percent  share " + stuffn.size());

		PlotOptionsPie pie = new PlotOptionsPie();
		pie.setShadow(false);
		conf.setPlotOptions(pie);

		conf.getTooltip().setValueSuffix("%");

		

		DataSeries outerSeries = new DataSeries();
		outerSeries.setName("Percentage");
		PlotOptionsPie outerSeriesOptions = new PlotOptionsPie();
		outerSeries.setPlotOptions(outerSeriesOptions);
		outerSeriesOptions.setInnerSize(150);
		outerSeriesOptions.setSize(200);
		outerSeriesOptions.setDataLabels(new Labels());
		outerSeriesOptions.getDataLabels().setFormatter(
				"this.y > 1 ? ''+ this.point.name +': '+ this.y +'%' : null");

		DataSeriesItem[] outerItems = new DataSeriesItem[stuffn.size()];

		int count = 0;
		for (String a : stuffn.keySet()) {

			outerItems[count] = new DataSeriesItem(a, stuffn.get(a),
					color(count));
			count++;
		}

		outerSeries.setData(Arrays.asList(outerItems));
		conf.setSeries(outerSeries);
		chart.drawChart(conf);
		conf.disableCredits();
		return chart;
	}

	/**
	 * Add a small random factor to a color form the vaadin theme.
	 *
	 * @param colorIndex
	 *            the index of the color in the colors array.
	 * @return the new color
	 */
	private static SolidColor color(int colorIndex) {
		SolidColor c = (SolidColor) colors[colorIndex];
		String cStr = c.toString().substring(1);

		int r = Integer.parseInt(cStr.substring(0, 2), 16);
		int g = Integer.parseInt(cStr.substring(2, 4), 16);
		int b = Integer.parseInt(cStr.substring(4, 6), 16);

		double opacity = (50 + rand.nextInt(95 - 50)) / 100.0;

		return new SolidColor(r, g, b, opacity);
	}

}

package com.vobi.team.presentation.backingBeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class VtGraficaSprintArtefactoView implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(VtGraficaSprintArtefactoView.class);
	
	private LineChartModel model;

	public LineChartModel getModel() {
		return model;
	}
	public void setModel(LineChartModel model) {
		this.model = model;
	}

		public void ChartBean() {
			model = new LineChartModel();
			BarChartSeries boys = new BarChartSeries();
			boys.setLabel("Boys");
			boys.set("2004", 120);
			boys.set("2005", 100);
			boys.set("2006", 44);
			boys.set("2007", 150);
			boys.set("2008", 25);
			LineChartSeries girls = new LineChartSeries();
			girls.setLabel("Girls");
			girls.setXaxis(AxisType.X2);
			girls.setYaxis(AxisType.Y2);
			girls.set("A", 52);
			girls.set("B", 60);
			girls.set("C", 110);
			girls.set("D", 135);
			girls.set("E", 120);
			model.addSeries(boys);
			model.addSeries(girls);
			model.setTitle("Multi Axis Chart");
			model.setMouseoverHighlight(false);
			model.getAxes().put(AxisType.X, new CategoryAxis("Years"));
			model.getAxes().put(AxisType.X2, new CategoryAxis("Period"));
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setLabel("Birth");
			yAxis.setMin(0);
			yAxis.setMax(200);
			Axis y2Axis = new LinearAxis("Number");
			y2Axis.setMin(0);
			y2Axis.setMax(200);
			model.getAxes().put(AxisType.Y2, y2Axis);
			
		}

}

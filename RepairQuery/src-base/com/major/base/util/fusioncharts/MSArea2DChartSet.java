package com.major.base.util.fusioncharts;

import java.io.Serializable;
import java.util.List;

public class MSArea2DChartSet implements Serializable {

	private static final long serialVersionUID = -3929537920773095596L;

	private MSArea2DChart chart;

	private List<MSArea2DCategories> categories;

	private List<MSData2DataSet> dataset;

	public MSArea2DChart getChart() {
		return chart;
	}

	public void setChart(MSArea2DChart chart) {
		this.chart = chart;
	}

	public List<MSArea2DCategories> getCategories() {
		return categories;
	}

	public void setCategories(List<MSArea2DCategories> categories) {
		this.categories = categories;
	}

	public List<MSData2DataSet> getDataset() {
		return dataset;
	}

	public void setDataset(List<MSData2DataSet> dataset) {
		this.dataset = dataset;
	}

}

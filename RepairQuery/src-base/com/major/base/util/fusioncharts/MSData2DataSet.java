package com.major.base.util.fusioncharts;

import java.io.Serializable;
import java.util.List;

public class MSData2DataSet implements Serializable {

	private static final long serialVersionUID = 4398179836566273878L;

	private String seriesName = "";

	private List<MSData2Data> data;

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public List<MSData2Data> getData() {
		return data;
	}

	public void setData(List<MSData2Data> data) {
		this.data = data;
	}

}

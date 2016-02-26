package com.major.base.util.fusioncharts;

import java.io.Serializable;

public class MSArea2DChart implements Serializable {

	private static final long serialVersionUID = 1125240097337401195L;

	private String bgColor = "E9E9E9";

	private String outCnvBaseFontColor = "666666";

	private String caption = "兑现率统计";

	private String xaxisname = "";

	private String yaxisname = "兑现率";

	private String numberPrefix = "";

	private String showNames = "1";

	private String showValues = "1";

	private String baseFontColor = "666666";

	private String canvasBorderThickness = "1";

	private String showPlotBorder = "1";

	private String plotBorderThickness = "1";

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getOutCnvBaseFontColor() {
		return outCnvBaseFontColor;
	}

	public void setOutCnvBaseFontColor(String outCnvBaseFontColor) {
		this.outCnvBaseFontColor = outCnvBaseFontColor;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getXaxisname() {
		return xaxisname;
	}

	public void setXaxisname(String xaxisname) {
		this.xaxisname = xaxisname;
	}

	public String getYaxisname() {
		return yaxisname;
	}

	public void setYaxisname(String yaxisname) {
		this.yaxisname = yaxisname;
	}

	public String getNumberPrefix() {
		return numberPrefix;
	}

	public void setNumberPrefix(String numberPrefix) {
		this.numberPrefix = numberPrefix;
	}

	public String getShowNames() {
		return showNames;
	}

	public void setShowNames(String showNames) {
		this.showNames = showNames;
	}

	public String getShowValues() {
		return showValues;
	}

	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}

	public String getBaseFontColor() {
		return baseFontColor;
	}

	public void setBaseFontColor(String baseFontColor) {
		this.baseFontColor = baseFontColor;
	}

	public String getCanvasBorderThickness() {
		return canvasBorderThickness;
	}

	public void setCanvasBorderThickness(String canvasBorderThickness) {
		this.canvasBorderThickness = canvasBorderThickness;
	}

	public String getShowPlotBorder() {
		return showPlotBorder;
	}

	public void setShowPlotBorder(String showPlotBorder) {
		this.showPlotBorder = showPlotBorder;
	}

	public String getPlotBorderThickness() {
		return plotBorderThickness;
	}

	public void setPlotBorderThickness(String plotBorderThickness) {
		this.plotBorderThickness = plotBorderThickness;
	}

}

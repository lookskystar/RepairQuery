package com.major.base.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 各段报活统计model
 * 
 * @author eleven
 * 
 */
public class CountReportModel implements Serializable {

	private static final long serialVersionUID = -6395903803618622989L;
	/** 星期 */
	private String dayOfWeek = "";
	/** 各段统计信息 map:key-机务段名称, value-统计数量 */
	private Map<String, String> countMap = new HashMap<String, String>();
	/** 每天各段合计 */
	private String total = "";

	public CountReportModel() {

	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Map<String, String> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String, String> countMap) {
		this.countMap = countMap;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "CountReportModel [dayOfWeek=" + dayOfWeek + ", countMap=" + countMap + ", total=" + total + "]";
	}

}

package com.major.base.vo;

import java.io.Serializable;

/**
 * 机车油水化验项目表
 * 
 * @author zx
 * 
 */
public class OilAssayItem implements Serializable {
	private static final long serialVersionUID = -4051560147957458103L;
	// 化验项目ID
	private Integer reportItemId;
	// 化验项目拼音简写
	private String reportItemPy;
	// 化验项目名称
	private String reportItemDefin;
	// 顺序号
	private Integer series;
	// 是否启用：0-不启用，1-启用
	private Short isused;
	// DF4、SS3、SS3B..
	private String jcValue;

	private String jwdCode;

	public OilAssayItem() {

	}

	public Integer getReportItemId() {
		return reportItemId;
	}

	public void setReportItemId(Integer reportItemId) {
		this.reportItemId = reportItemId;
	}

	public String getReportItemPy() {
		return reportItemPy;
	}

	public void setReportItemPy(String reportItemPy) {
		this.reportItemPy = reportItemPy;
	}

	public String getReportItemDefin() {
		return reportItemDefin;
	}

	public void setReportItemDefin(String reportItemDefin) {
		this.reportItemDefin = reportItemDefin;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}

	public Short getIsused() {
		return isused;
	}

	public void setIsused(Short isused) {
		this.isused = isused;
	}

	public String getJcValue() {
		return jcValue;
	}

	public void setJcValue(String jcValue) {
		this.jcValue = jcValue;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reportItemId == null) ? 0 : reportItemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OilAssayItem other = (OilAssayItem) obj;
		if (reportItemId == null) {
			if (other.reportItemId != null)
				return false;
		} else if (!reportItemId.equals(other.reportItemId))
			return false;
		return true;
	}
}

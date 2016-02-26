package com.major.base.vo;

import java.io.Serializable;

/**
 * 机车油水化记录主表 -- 按机车油水化验记录主表存放
 * 
 * @author zx
 * 
 */
public class OilAssayPriRecorder implements Serializable {
	private static final long serialVersionUID = 3900412099294569723L;
	// 油水化验记录主表ID
	private Long recPriId;
	// 机务段代码
	private String jwdCode;
	// 区域代码
	private String areaId;
	// 检修周计划记录ID
	//private WeekPlanPri wekPrecId;
	//日计划ID
	private Integer dpId;
	// 送样人
	private String sentsamPeo;
	// 接收人
	private String receiptPeo;
	// 接收待化验样品时间
	private String recesamTime;
	// 化验完成时间
	private String finTime;
	// 化验状态：0-待化验，1-化验完成，-1-作废
	private Short detecteStatus;
	// 机车型号
	private String jcsTypeVal;
	// 机车编号
	private String jcNum;
	// 处理意见 （（其他油0更换）（变压器油：1滤油））
	private Short dealAdvice;
	// 质量评定
	private Short quasituation;
	
	public OilAssayPriRecorder() {
		
	}

	public Long getRecPriId() {
		return recPriId;
	}

	public void setRecPriId(Long recPriId) {
		this.recPriId = recPriId;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getDpId() {
		return dpId;
	}

	public void setDpId(Integer dpId) {
		this.dpId = dpId;
	}

	public String getSentsamPeo() {
		return sentsamPeo;
	}

	public void setSentsamPeo(String sentsamPeo) {
		this.sentsamPeo = sentsamPeo;
	}

	public String getReceiptPeo() {
		return receiptPeo;
	}

	public void setReceiptPeo(String receiptPeo) {
		this.receiptPeo = receiptPeo;
	}

	public String getRecesamTime() {
		return recesamTime;
	}

	public void setRecesamTime(String recesamTime) {
		this.recesamTime = recesamTime;
	}

	public String getFinTime() {
		return finTime;
	}

	public void setFinTime(String finTime) {
		this.finTime = finTime;
	}

	public Short getDetecteStatus() {
		return detecteStatus;
	}

	public void setDetecteStatus(Short detecteStatus) {
		this.detecteStatus = detecteStatus;
	}

	public String getJcsTypeVal() {
		return jcsTypeVal;
	}

	public void setJcsTypeVal(String jcsTypeVal) {
		this.jcsTypeVal = jcsTypeVal;
	}

	public String getJcNum() {
		return jcNum;
	}

	public void setJcNum(String jcNum) {
		this.jcNum = jcNum;
	}

	public Short getDealAdvice() {
		return dealAdvice;
	}

	public void setDealAdvice(Short dealAdvice) {
		this.dealAdvice = dealAdvice;
	}

	public Short getQuasituation() {
		return quasituation;
	}

	public void setQuasituation(Short quasituation) {
		this.quasituation = quasituation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((recPriId == null) ? 0 : recPriId.hashCode());
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
		OilAssayPriRecorder other = (OilAssayPriRecorder) obj;
		if (recPriId == null) {
			if (other.recPriId != null)
				return false;
		} else if (!recPriId.equals(other.recPriId))
			return false;
		return true;
	}
}

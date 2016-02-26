package com.major.base.vo;

import java.io.Serializable;

public class JCQZFixRec implements Serializable {

	private static final long serialVersionUID = 8572084870117233900L;

	/**
	 * 表主键:检修记录ID
	 */
	private long jcRecId;
	/**
	 * 段代码
	 */
	private String jwdCode;
	/**
	 * 
	 */
	private Integer wekprecid;
	/**
	 * 配件ID
	 */
	private Long pjStaticInfo;
	/**
	 * 机车ID
	 */
	private Integer jcid;
	/**
	 * 下车配件条码
	 */
	private String doPjBarcode;
	/**
	 * 下车配件编号
	 */
	private String downPjNum;
	/**
	 * 上车配件条码
	 */
	private String upPjBarcode;
	/**
	 * 上车配件编号
	 */
	private String upPjNum;
	/**
	 * 检修项目
	 */
	private Long items;
	/**
	 * 项目名称
	 */
	private String itemName;
	/**
	 * 配件名称
	 */
	private String pjName;
	/**
	 * 检修I端(左端)
	 */
	private String leftMargin;
	/**
	 * 检修II端(右端)
	 */
	private String rightMargin;
	/**
	 * 检修情况 逗号分割填报值
	 */
	private String fixSituation;
	/**
	 * 派工-检修人ＩＤ
	 */
	private String workerId;
	/**
	 * 派工-检修人名字
	 */
	private String workerName;
	/**
	 * 检修人签字
	 */
	private String fixEmp;
	/**
	 * 检修人签名时间
	 */
	private String empAfformTime;
	/**
	 * 工长ID
	 */
	private String lead;
	/**
	 * 工长名字
	 */
	private String leadName;
	/**
	 * 工长签名时间
	 */
	private String ldAffirmTime;
	/**
	 * 交车工长
	 */
	private String commitLead;
	/**
	 * 交车工长签名时间
	 */
	private String comLdAffiTime;
	/**
	 * 质检员
	 */
	private String qi;
	/**
	 * 质检员签名时间
	 */
	private String qiAffiTime;
	/**
	 * 技术员
	 */
	private String tech;
	/**
	 * 技术员签名时间
	 */
	private String techAffiTime;
	/**
	 * 验收员
	 */
	private String accepter;
	/**
	 * 验收员签名时间
	 */
	private String acceAffiTime;
	/**
	 * 记录状态 0 - 新建、1 - 检修人签认、 2 - 工长签字、3 - 技术员+质检员、4 - 交车工长签字、5-验收员签字
	 */
	private Short recStas;
	/**
	 * 日计划关联
	 */
	private Integer jcRecmId;
	/**
	 * 班组ID
	 */
	private Long bzId;
	/**
	 * 默认检修情况
	 */
	private String moren;
	/**
	 * 专业包保人员
	 */
	private String bbrw;
	/**
	 * 专业包保人员签名时间
	 */
	private String bbrwAffiTime;
	/**
	 * 检修主任
	 */
	private String jxzr;
	/**
	 * 检修主任签名时间
	 */
	private String jxzrAffiTime;
	/**
	 * 质检科长
	 */
	private String zjkz;
	/**
	 * 质检科长签名时间
	 */
	private String zjkzAffiTime;
	/**
	 * 技术科长
	 */
	private String jskz;
	/**
	 * 技术科长签名时间
	 */
	private String jskzAffiTime;
	/**
	 * 段领导
	 */
	private String dld;
	/**
	 * 段领导签名时间
	 */
	private String dldAffiTime;
	/**
	 * 项目类型 0：检查 1：检测
	 */
	private Short itemType;

	private String unitName;

	private String secUnitName;

	public long getJcRecId() {
		return jcRecId;
	}

	public void setJcRecId(long jcRecId) {
		this.jcRecId = jcRecId;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public Integer getWekprecid() {
		return wekprecid;
	}

	public void setWekprecid(Integer wekprecid) {
		this.wekprecid = wekprecid;
	}

	public Long getPjStaticInfo() {
		return pjStaticInfo;
	}

	public void setPjStaticInfo(Long pjStaticInfo) {
		this.pjStaticInfo = pjStaticInfo;
	}

	public Integer getJcid() {
		return jcid;
	}

	public void setJcid(Integer jcid) {
		this.jcid = jcid;
	}

	public String getDoPjBarcode() {
		return doPjBarcode;
	}

	public void setDoPjBarcode(String doPjBarcode) {
		this.doPjBarcode = doPjBarcode;
	}

	public String getDownPjNum() {
		return downPjNum;
	}

	public void setDownPjNum(String downPjNum) {
		this.downPjNum = downPjNum;
	}

	public String getUpPjBarcode() {
		return upPjBarcode;
	}

	public void setUpPjBarcode(String upPjBarcode) {
		this.upPjBarcode = upPjBarcode;
	}

	public String getUpPjNum() {
		return upPjNum;
	}

	public void setUpPjNum(String upPjNum) {
		this.upPjNum = upPjNum;
	}

	public Long getItems() {
		return items;
	}

	public void setItems(Long items) {
		this.items = items;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPjName() {
		return pjName;
	}

	public void setPjName(String pjName) {
		this.pjName = pjName;
	}

	public String getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(String leftMargin) {
		this.leftMargin = leftMargin;
	}

	public String getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(String rightMargin) {
		this.rightMargin = rightMargin;
	}

	public String getFixSituation() {
		return fixSituation;
	}

	public void setFixSituation(String fixSituation) {
		this.fixSituation = fixSituation;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getFixEmp() {
		return fixEmp;
	}

	public void setFixEmp(String fixEmp) {
		this.fixEmp = fixEmp;
	}

	public String getEmpAfformTime() {
		return empAfformTime;
	}

	public void setEmpAfformTime(String empAfformTime) {
		this.empAfformTime = empAfformTime;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public String getLdAffirmTime() {
		return ldAffirmTime;
	}

	public void setLdAffirmTime(String ldAffirmTime) {
		this.ldAffirmTime = ldAffirmTime;
	}

	public String getCommitLead() {
		return commitLead;
	}

	public void setCommitLead(String commitLead) {
		this.commitLead = commitLead;
	}

	public String getComLdAffiTime() {
		return comLdAffiTime;
	}

	public void setComLdAffiTime(String comLdAffiTime) {
		this.comLdAffiTime = comLdAffiTime;
	}

	public String getQi() {
		return qi;
	}

	public void setQi(String qi) {
		this.qi = qi;
	}

	public String getQiAffiTime() {
		return qiAffiTime;
	}

	public void setQiAffiTime(String qiAffiTime) {
		this.qiAffiTime = qiAffiTime;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public String getTechAffiTime() {
		return techAffiTime;
	}

	public void setTechAffiTime(String techAffiTime) {
		this.techAffiTime = techAffiTime;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public String getAcceAffiTime() {
		return acceAffiTime;
	}

	public void setAcceAffiTime(String acceAffiTime) {
		this.acceAffiTime = acceAffiTime;
	}

	public Short getRecStas() {
		return recStas;
	}

	public void setRecStas(Short recStas) {
		this.recStas = recStas;
	}

	public Integer getJcRecmId() {
		return jcRecmId;
	}

	public void setJcRecmId(Integer jcRecmId) {
		this.jcRecmId = jcRecmId;
	}

	public Long getBzId() {
		return bzId;
	}

	public void setBzId(Long bzId) {
		this.bzId = bzId;
	}

	public String getMoren() {
		return moren;
	}

	public void setMoren(String moren) {
		this.moren = moren;
	}

	public String getBbrw() {
		return bbrw;
	}

	public void setBbrw(String bbrw) {
		this.bbrw = bbrw;
	}

	public String getBbrwAffiTime() {
		return bbrwAffiTime;
	}

	public void setBbrwAffiTime(String bbrwAffiTime) {
		this.bbrwAffiTime = bbrwAffiTime;
	}

	public String getJxzr() {
		return jxzr;
	}

	public void setJxzr(String jxzr) {
		this.jxzr = jxzr;
	}

	public String getJxzrAffiTime() {
		return jxzrAffiTime;
	}

	public void setJxzrAffiTime(String jxzrAffiTime) {
		this.jxzrAffiTime = jxzrAffiTime;
	}

	public String getZjkz() {
		return zjkz;
	}

	public void setZjkz(String zjkz) {
		this.zjkz = zjkz;
	}

	public String getZjkzAffiTime() {
		return zjkzAffiTime;
	}

	public void setZjkzAffiTime(String zjkzAffiTime) {
		this.zjkzAffiTime = zjkzAffiTime;
	}

	public String getJskz() {
		return jskz;
	}

	public void setJskz(String jskz) {
		this.jskz = jskz;
	}

	public String getJskzAffiTime() {
		return jskzAffiTime;
	}

	public void setJskzAffiTime(String jskzAffiTime) {
		this.jskzAffiTime = jskzAffiTime;
	}

	public String getDld() {
		return dld;
	}

	public void setDld(String dld) {
		this.dld = dld;
	}

	public String getDldAffiTime() {
		return dldAffiTime;
	}

	public void setDldAffiTime(String dldAffiTime) {
		this.dldAffiTime = dldAffiTime;
	}

	public Short getItemType() {
		return itemType;
	}

	public void setItemType(Short itemType) {
		this.itemType = itemType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getSecUnitName() {
		return secUnitName;
	}

	public void setSecUnitName(String secUnitName) {
		this.secUnitName = secUnitName;
	}

	@Override
	public String toString() {
		return "JCQZFixRec [jcRecId=" + jcRecId + ", jwdCode=" + jwdCode + ", pjStaticInfo=" + pjStaticInfo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (jcRecId ^ (jcRecId >>> 32));
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
		JCQZFixRec other = (JCQZFixRec) obj;
		if (jcRecId != other.jcRecId)
			return false;
		return true;
	}

}

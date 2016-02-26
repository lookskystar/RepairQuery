package com.major.base.vo;

import java.io.Serializable;
import java.util.Date;

public class PJFixRecord implements Serializable {

	private static final long serialVersionUID = 6075492397877013349L;

	/**
	 * 主键
	 */
	private Long pjRecId;
	/**
	 * 段代码
	 */
	private String jwdCode;
	/**
	 * 检修配件项目
	 */
	private String pjFixItem;
	/**
	 * 配件检修流程节点记录（作用：用于查询当前节点已经分配的 检修项目时不需要查询当前配件所有的检修项目，还有后期修改流程节点记录状态）
	 */
	private Long pjFixFlowRecord;
	/**
	 * 配件动态信息实体类（检修配件）
	 */
	private Long pjDynamicInfo;
	/**
	 * 子配件（动态配件）id
	 */
	private Long childPJId;
	/**
	 * 下车配件编号
	 */
	private String downpjnum;
	/**
	 * 上车配件编号
	 */
	private String uppjnum;
	/**
	 * 配件名称
	 */
	private String pjname;
	/**
	 * 检修I端(左端)
	 */
	private String leftmargin;
	/**
	 * 检修II端(右端)
	 */
	private String rightmargin;
	/**
	 * 检修情况
	 */
	private String fixsituation;
	/**
	 * 工人接活时间
	 */
	private Date accepttime;
	/**
	 * 检修人id
	 */
	private Long fixempid;
	/**
	 * 检修人名字
	 */
	private String fixemp;
	/**
	 * 检修人签认时间
	 */
	private Date empaffirmtime;
	/*
	 * 工长id
	 */
	private Long leadid;
	/**
	 * 工长名字
	 */
	private String lead;
	/**
	 * 工长验收时间
	 */
	private Date ldaffirmtime;
	/**
	 * 交车工长id
	 */
	private Long commitleadid;
	/**
	 * 交车工长名字
	 */
	private String commitlead;
	/**
	 * 交车工长验收时间
	 */
	private Date comldaffitime;
	/**
	 * 技术员id
	 */
	private Long techId;
	/**
	 * 技术员名字
	 */
	private String techName;
	/**
	 * 技术员签认时间
	 */
	private Date techTime;
	/**
	 * 质检员id
	 */
	private Long qiid;
	/**
	 * 质检员名字
	 */
	private String qi;
	/**
	 * 质检员验收时间
	 */
	private Date qiaffitime;
	/**
	 * 验收员id
	 */
	private Long accepterid;
	/**
	 * 验收员名字
	 */
	private String accepter;
	/**
	 * 验收员验收时间
	 */
	private Date acceaffitime;
	/**
	 * 配件检修记录的id（便于报活时查找当前配件检修记录修改它的报活状态）
	 */
	private Long pjFixRecSid;
	/**
	 * 配件报活id
	 */
	private Long pjPredictId;
	/**
	 * 报活状态：1已报活，2报活完成
	 */
	private Integer preStatus;
	/**
	 * 检修对象类型（0表示配件，1表示检修项目;默认为1） 2:委外检修 3:新增良好
	 */
	private Integer type;
	/**
	 * 组装配件编码
	 */
	private String pjNum;
	/**
	 * 负责检修的班组id；格式如：type=0时为id集合例如：,5,18,1,10,2,； type=1是为负责检修项目的班组id。
	 */
	private String teams;
	/**
	 * 检修项目记录： 0-新建、1-已派工、2 - 检修人签认 3-工长验收、4-技术员验收、5-质检员验收、6-交车工长验收、7-完工 (验收员验收)
	 * 配件记录：0-新建、1-新建且有子配件、2-完成子配件选择、7-完成检修
	 */
	private Integer recstas;
	/**
	 * 检修项目关联配件ID
	 */
	private Long parentId;
	/**
	 * 查询关联日计划ID
	 */
	private Integer rjhmId;

	/**
	 * 处理情况
	 */
	private String dealSituaton;
	/**
	 * 复探id
	 */
	private Long reptId;
	/**
	 * 复探员名字
	 */
	private String rept;
	/**
	 * 复探员验收时间
	 */
	private String reptAffirmTime;

	// 新增与配件项目关联字段
	/**
	 * 部位名称
	 */
	private String posiName;

	/**
	 * 检修项目名
	 */
	private String fixItem;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 项目卡控人员：0-工长不控；1-工长卡控
	 */
	private Integer itemctrllead;
	/**
	 * 项目卡控人员：0-交车工长不控；1-交车工长卡控
	 */
	private Integer itemctrlcomld;
	/**
	 * 项目卡控人员：0-质检员不控；1-质检员卡控
	 */
	private Integer itemctrlqi;
	/**
	 * 项目卡控人员：0-技术员不控；1-技术员卡控
	 */
	private Integer itemctrltech;
	/**
	 * 项目卡控人员：0-验收员不控；1-验收员卡控
	 */
	private Integer itemctrlacce;
	/**
	 * 复探卡控 0-不卡控 1-卡控
	 */
	private Integer itemctrlrept;

	public Long getPjRecId() {
		return pjRecId;
	}

	public void setPjRecId(Long pjRecId) {
		this.pjRecId = pjRecId;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getPjFixItem() {
		return pjFixItem;
	}

	public void setPjFixItem(String pjFixItem) {
		this.pjFixItem = pjFixItem;
	}

	public Long getPjFixFlowRecord() {
		return pjFixFlowRecord;
	}

	public void setPjFixFlowRecord(Long pjFixFlowRecord) {
		this.pjFixFlowRecord = pjFixFlowRecord;
	}

	public Long getPjDynamicInfo() {
		return pjDynamicInfo;
	}

	public void setPjDynamicInfo(Long pjDynamicInfo) {
		this.pjDynamicInfo = pjDynamicInfo;
	}

	public Long getChildPJId() {
		return childPJId;
	}

	public void setChildPJId(Long childPJId) {
		this.childPJId = childPJId;
	}

	public String getDownpjnum() {
		return downpjnum;
	}

	public void setDownpjnum(String downpjnum) {
		this.downpjnum = downpjnum;
	}

	public String getUppjnum() {
		return uppjnum;
	}

	public void setUppjnum(String uppjnum) {
		this.uppjnum = uppjnum;
	}

	public String getPjname() {
		return pjname;
	}

	public void setPjname(String pjname) {
		this.pjname = pjname;
	}

	public String getLeftmargin() {
		return leftmargin;
	}

	public void setLeftmargin(String leftmargin) {
		this.leftmargin = leftmargin;
	}

	public String getRightmargin() {
		return rightmargin;
	}

	public void setRightmargin(String rightmargin) {
		this.rightmargin = rightmargin;
	}

	public String getFixsituation() {
		return fixsituation;
	}

	public void setFixsituation(String fixsituation) {
		this.fixsituation = fixsituation;
	}

	public Date getAccepttime() {
		return accepttime;
	}

	public void setAccepttime(Date accepttime) {
		this.accepttime = accepttime;
	}

	public Long getFixempid() {
		return fixempid;
	}

	public void setFixempid(Long fixempid) {
		this.fixempid = fixempid;
	}

	public String getFixemp() {
		return fixemp;
	}

	public void setFixemp(String fixemp) {
		this.fixemp = fixemp;
	}

	public Date getEmpaffirmtime() {
		return empaffirmtime;
	}

	public void setEmpaffirmtime(Date empaffirmtime) {
		this.empaffirmtime = empaffirmtime;
	}

	public Long getLeadid() {
		return leadid;
	}

	public void setLeadid(Long leadid) {
		this.leadid = leadid;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public Date getLdaffirmtime() {
		return ldaffirmtime;
	}

	public void setLdaffirmtime(Date ldaffirmtime) {
		this.ldaffirmtime = ldaffirmtime;
	}

	public Long getCommitleadid() {
		return commitleadid;
	}

	public void setCommitleadid(Long commitleadid) {
		this.commitleadid = commitleadid;
	}

	public String getCommitlead() {
		return commitlead;
	}

	public void setCommitlead(String commitlead) {
		this.commitlead = commitlead;
	}

	public Date getComldaffitime() {
		return comldaffitime;
	}

	public void setComldaffitime(Date comldaffitime) {
		this.comldaffitime = comldaffitime;
	}

	public Long getTechId() {
		return techId;
	}

	public void setTechId(Long techId) {
		this.techId = techId;
	}

	public String getTechName() {
		return techName;
	}

	public void setTechName(String techName) {
		this.techName = techName;
	}

	public Date getTechTime() {
		return techTime;
	}

	public void setTechTime(Date techTime) {
		this.techTime = techTime;
	}

	public Long getQiid() {
		return qiid;
	}

	public void setQiid(Long qiid) {
		this.qiid = qiid;
	}

	public String getQi() {
		return qi;
	}

	public void setQi(String qi) {
		this.qi = qi;
	}

	public Date getQiaffitime() {
		return qiaffitime;
	}

	public void setQiaffitime(Date qiaffitime) {
		this.qiaffitime = qiaffitime;
	}

	public Long getAccepterid() {
		return accepterid;
	}

	public void setAccepterid(Long accepterid) {
		this.accepterid = accepterid;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public Date getAcceaffitime() {
		return acceaffitime;
	}

	public void setAcceaffitime(Date acceaffitime) {
		this.acceaffitime = acceaffitime;
	}

	public Long getPjFixRecSid() {
		return pjFixRecSid;
	}

	public void setPjFixRecSid(Long pjFixRecSid) {
		this.pjFixRecSid = pjFixRecSid;
	}

	public Long getPjPredictId() {
		return pjPredictId;
	}

	public void setPjPredictId(Long pjPredictId) {
		this.pjPredictId = pjPredictId;
	}

	public Integer getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(Integer preStatus) {
		this.preStatus = preStatus;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPjNum() {
		return pjNum;
	}

	public void setPjNum(String pjNum) {
		this.pjNum = pjNum;
	}

	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public Integer getRecstas() {
		return recstas;
	}

	public void setRecstas(Integer recstas) {
		this.recstas = recstas;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getRjhmId() {
		return rjhmId;
	}

	public void setRjhmId(Integer rjhmId) {
		this.rjhmId = rjhmId;
	}

	public String getDealSituaton() {
		return dealSituaton;
	}

	public void setDealSituaton(String dealSituaton) {
		this.dealSituaton = dealSituaton;
	}

	public Long getReptId() {
		return reptId;
	}

	public void setReptId(Long reptId) {
		this.reptId = reptId;
	}

	public String getRept() {
		return rept;
	}

	public void setRept(String rept) {
		this.rept = rept;
	}

	public String getReptAffirmTime() {
		return reptAffirmTime;
	}

	public void setReptAffirmTime(String reptAffirmTime) {
		this.reptAffirmTime = reptAffirmTime;
	}

	public String getPosiName() {
		return posiName;
	}

	public void setPosiName(String posiName) {
		this.posiName = posiName;
	}

	public String getFixItem() {
		return fixItem;
	}

	public void setFixItem(String fixItem) {
		this.fixItem = fixItem;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getItemctrllead() {
		return itemctrllead;
	}

	public void setItemctrllead(Integer itemctrllead) {
		this.itemctrllead = itemctrllead;
	}

	public Integer getItemctrlcomld() {
		return itemctrlcomld;
	}

	public void setItemctrlcomld(Integer itemctrlcomld) {
		this.itemctrlcomld = itemctrlcomld;
	}

	public Integer getItemctrlqi() {
		return itemctrlqi;
	}

	public void setItemctrlqi(Integer itemctrlqi) {
		this.itemctrlqi = itemctrlqi;
	}

	public Integer getItemctrltech() {
		return itemctrltech;
	}

	public void setItemctrltech(Integer itemctrltech) {
		this.itemctrltech = itemctrltech;
	}

	public Integer getItemctrlacce() {
		return itemctrlacce;
	}

	public void setItemctrlacce(Integer itemctrlacce) {
		this.itemctrlacce = itemctrlacce;
	}

	public Integer getItemctrlrept() {
		return itemctrlrept;
	}

	public void setItemctrlrept(Integer itemctrlrept) {
		this.itemctrlrept = itemctrlrept;
	}

	@Override
	public String toString() {
		return "PJFixRecord [pjRecId=" + pjRecId + ", jwdCode=" + jwdCode + ", pjFixItem=" + pjFixItem + ", pjname="
				+ pjname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pjRecId == null) ? 0 : pjRecId.hashCode());
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
		PJFixRecord other = (PJFixRecord) obj;
		if (pjRecId == null) {
			if (other.pjRecId != null)
				return false;
		} else if (!pjRecId.equals(other.pjRecId))
			return false;
		return true;
	}

}

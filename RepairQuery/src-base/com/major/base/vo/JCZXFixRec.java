package com.major.base.vo;

import java.io.Serializable;

public class JCZXFixRec implements Serializable {

	private static final long serialVersionUID = 3796415349138033611L;

	// 记录ID标识
	private Long id;
	// 段代码
	private String jwdCode;
	// 机车类型
	private String jcType;
	// 机车编号
	private String jcNum;
	// 日计划记录ID
	private Integer dyPrecId;
	// 下车配件条码
	private String downPjBarCode;
	// 下车配件编号
	private String downPjNum;
	// 上车配件条码
	private String upPjBarCode;
	// 上车配件编号
	private String upPjNum;
	// 分解(组装)项目ID
	private Long itemId;
	// 项目名
	private String itemName;
	// 检修情况
	private String fixSituation;
	// 单位
	private String unit;
	// 检修人ID(多个ID，用逗号隔开)
	private String fixEmpId;
	// 检修人姓名
	private String fixEmp;
	// 检修人签名时间
	private String fixEmpTime;
	// 工长ID
	private Long leadId;
	// 工长姓名
	private String lead;
	// 工长签名时间
	private String ldAffirmTime;
	// 技术员ID
	private Long teachId;
	// 技术员姓名
	private String teachName;
	// 技术员签认时间
	private String teachAffiTime;
	// 质检员ID
	private Long qiId;
	// 质检员名字
	private String qi;
	// 质检员签认时间
	private String qiAffiTime;
	// 交车工长ID
	private Long commitLeadId;
	// 交车工长姓名
	private String commitLead;
	// 交车工长签名时间
	private String comLdAffiTime;
	// 验收员ID
	private Long acceptErId;
	// 验收员姓名
	private String acceptEr;
	// 验收员签名时间
	private String acceAffiTime;
	// 记录状态 1：检修人待签字 2：工长待签字 3技术员待签字、质检员待签字 4交车工长待签字 5验收员待签字 6完成
	private Short recStas;
	// 检查项目或检测项目 (0:检查项目 1：检测项目)
	private Short itemType;
	// 接活时间
	private String jhTime;
	// 班组ID
	private long bzId;
	// 项目节点ID
	private Integer nodeId;
	// 项目检修时长
	private Integer duration;

	// 复探ID
	private Long reptId;
	// 复探姓名
	private String rept;
	// 复探签名时间
	private String reptAffirmTime;
	// 探伤处理情况
	private String dealSituation;

	// 添加中修项目关联字段
	// 部位名称
	private String posiName;
	// 专业ID
	private Integer firstUnitId;
	// 部件名称
	private String unitName;
	// 项目卡控人员：0-工长不控；1-工长卡控
	private Short itemCtrlLead;
	// 项目卡控人员：0-交车工长不控；1-交车工长卡控
	private Short itemCtrlComld;
	// 项目卡控人员：0-质检员不控；1-质检员卡控
	private Short itemCtrlQi;
	// 项目卡控人员：0-技术员不控；1-技术员卡控
	private Short itemCtrlTech;
	// 项目卡控人员：0-验收员不控；1-验收员卡控
	private Short itemCtrlAcce;
	// 复探卡控
	private Short itemCtrlRept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getJcType() {
		return jcType;
	}

	public void setJcType(String jcType) {
		this.jcType = jcType;
	}

	public String getJcNum() {
		return jcNum;
	}

	public void setJcNum(String jcNum) {
		this.jcNum = jcNum;
	}

	public Integer getDyPrecId() {
		return dyPrecId;
	}

	public void setDyPrecId(Integer dyPrecId) {
		this.dyPrecId = dyPrecId;
	}

	public String getDownPjBarCode() {
		return downPjBarCode;
	}

	public void setDownPjBarCode(String downPjBarCode) {
		this.downPjBarCode = downPjBarCode;
	}

	public String getDownPjNum() {
		return downPjNum;
	}

	public void setDownPjNum(String downPjNum) {
		this.downPjNum = downPjNum;
	}

	public String getUpPjBarCode() {
		return upPjBarCode;
	}

	public void setUpPjBarCode(String upPjBarCode) {
		this.upPjBarCode = upPjBarCode;
	}

	public String getUpPjNum() {
		return upPjNum;
	}

	public void setUpPjNum(String upPjNum) {
		this.upPjNum = upPjNum;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFixSituation() {
		return fixSituation;
	}

	public void setFixSituation(String fixSituation) {
		this.fixSituation = fixSituation;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFixEmpId() {
		return fixEmpId;
	}

	public void setFixEmpId(String fixEmpId) {
		this.fixEmpId = fixEmpId;
	}

	public String getFixEmp() {
		return fixEmp;
	}

	public void setFixEmp(String fixEmp) {
		this.fixEmp = fixEmp;
	}

	public String getFixEmpTime() {
		return fixEmpTime;
	}

	public void setFixEmpTime(String fixEmpTime) {
		this.fixEmpTime = fixEmpTime;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getLdAffirmTime() {
		return ldAffirmTime;
	}

	public void setLdAffirmTime(String ldAffirmTime) {
		this.ldAffirmTime = ldAffirmTime;
	}

	public Long getTeachId() {
		return teachId;
	}

	public void setTeachId(Long teachId) {
		this.teachId = teachId;
	}

	public String getTeachName() {
		return teachName;
	}

	public void setTeachName(String teachName) {
		this.teachName = teachName;
	}

	public String getTeachAffiTime() {
		return teachAffiTime;
	}

	public void setTeachAffiTime(String teachAffiTime) {
		this.teachAffiTime = teachAffiTime;
	}

	public Long getQiId() {
		return qiId;
	}

	public void setQiId(Long qiId) {
		this.qiId = qiId;
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

	public Long getCommitLeadId() {
		return commitLeadId;
	}

	public void setCommitLeadId(Long commitLeadId) {
		this.commitLeadId = commitLeadId;
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

	public Long getAcceptErId() {
		return acceptErId;
	}

	public void setAcceptErId(Long acceptErId) {
		this.acceptErId = acceptErId;
	}

	public String getAcceptEr() {
		return acceptEr;
	}

	public void setAcceptEr(String acceptEr) {
		this.acceptEr = acceptEr;
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

	public Short getItemType() {
		return itemType;
	}

	public void setItemType(Short itemType) {
		this.itemType = itemType;
	}

	public String getJhTime() {
		return jhTime;
	}

	public void setJhTime(String jhTime) {
		this.jhTime = jhTime;
	}

	public long getBzId() {
		return bzId;
	}

	public void setBzId(long bzId) {
		this.bzId = bzId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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

	public String getDealSituation() {
		return dealSituation;
	}

	public void setDealSituation(String dealSituation) {
		this.dealSituation = dealSituation;
	}

	public String getPosiName() {
		return posiName;
	}

	public void setPosiName(String posiName) {
		this.posiName = posiName;
	}

	public Integer getFirstUnitId() {
		return firstUnitId;
	}

	public void setFirstUnitId(Integer firstUnitId) {
		this.firstUnitId = firstUnitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Short getItemCtrlLead() {
		return itemCtrlLead;
	}

	public void setItemCtrlLead(Short itemCtrlLead) {
		this.itemCtrlLead = itemCtrlLead;
	}

	public Short getItemCtrlComld() {
		return itemCtrlComld;
	}

	public void setItemCtrlComld(Short itemCtrlComld) {
		this.itemCtrlComld = itemCtrlComld;
	}

	public Short getItemCtrlQi() {
		return itemCtrlQi;
	}

	public void setItemCtrlQi(Short itemCtrlQi) {
		this.itemCtrlQi = itemCtrlQi;
	}

	public Short getItemCtrlTech() {
		return itemCtrlTech;
	}

	public void setItemCtrlTech(Short itemCtrlTech) {
		this.itemCtrlTech = itemCtrlTech;
	}

	public Short getItemCtrlAcce() {
		return itemCtrlAcce;
	}

	public void setItemCtrlAcce(Short itemCtrlAcce) {
		this.itemCtrlAcce = itemCtrlAcce;
	}

	public Short getItemCtrlRept() {
		return itemCtrlRept;
	}

	public void setItemCtrlRept(Short itemCtrlRept) {
		this.itemCtrlRept = itemCtrlRept;
	}



	@Override
	public String toString() {
		return "JCZXFixRec [id=" + id + ", jwdCode=" + jwdCode + ", jcType=" + jcType + ", jcNum=" + jcNum
				+ ", unitName=" + unitName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		JCZXFixRec other = (JCZXFixRec) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

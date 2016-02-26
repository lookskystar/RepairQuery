package com.major.base.vo;

import java.io.Serializable;

public class JtPreDict implements Serializable {

	private static final long serialVersionUID = -1797642423537805851L;

	// 报活记录ID
	private Integer preDictId;
	// 机务段代码
	private String jwdCode;
	// 报活日期(yyyy-mm-dd)
	private String repDate;
	// 机车类型
	private String jcType;
	// 机车编号
	private String jcNum;
	// 项目ID
	private Long thirdUnitId;
	// 详细报活部位、处所
	private String repPosi;
	// 报活情况
	private String repsituation;
	// 故障情况编号
	private String failNum;
	// 故障备注
	private String failNote;
	// 报活人工号
	private String repempNo;
	// 报活人
	private String repemp;
	// 报活时间
	private String repTime;
	// 审批人
	private String verifier;
	// 审批时间
	private String verifyTime;
	// 接收人
	private String receiptPeo;
	// 接收时间
	private String receTime;
	// 检修人ID(,3,4,5,)
	private String fixEmpId;
	// 检修人(工长派工)(小米,小花)
	private String fixEmp;
	// 检修人处理签字(,小米,小花,)
	private String dealFixEmp;
	// 处理情况
	private String dealSituation;
	// 检修结束时间
	private String fixEndTime;
	// 工长
	private String lead;
	// 工长验收时间
	private String ldAffirmTime;
	// 交车工长
	private String commitLd;
	// 交车工长验收时间
	private String comLdAffiTime;
	// 质检员
	private String qi;
	// 质检验收时间
	private String qiAffiTime;
	// 验收员
	private String accepter;
	// 验收员验收时间
	private String acceTime;
	// 技术员验收
	private String technician;
	// 技术员验收时间
	private String techAffiTime;
	// 核验级别
	private Short affirmGrade;
	// 类型 0-地检预报，1-复检，2-检修过程报，3-临修加改,5-不良状态通知书，6-零公里检查
	private Short type;
	// 零公里记录ID
	private Integer zeroKiloRecId;
	// 记录状态0-新报，1-审批，2-接收，3-处理完成，4-工长核验,5-质检员、验收员等核验，9-完成存档，-1-作废货审批不通过
	private Short recStas;
	// 图片地址

	private String imgUrl;
	// 班组Id
	private Long proTeamId;

	/**
	 * 项目卡控人员：0-交车工长不控；1-交车工长卡控
	 */
	private Integer itemCtrlComLd;
	/**
	 * 项目卡控人员：0-质检员不控；1-质检员卡控
	 */
	private Integer itemCtrlQI;
	/**
	 * 项目卡控人员：0-技术员不控；1-技术员卡控
	 */
	private Integer itemCtrlTech;
	/**
	 * 项目卡控人员：0-验收员不控；1-验收员卡控
	 */
	private Integer itemCtrlAcce;
	/**
	 * 关联日计划
	 */
	private Integer datePlanPri;

	/**
	 * 预设分工信息
	 */
	private Integer divisionId;

	/**
	 * 评分
	 */
	private Integer score;

	/**
	 * 捆绑预设类ID 分给多个班组时，产生相同的记录（班组不同其他相同）
	 * 
	 * @param obj
	 * @return
	 */
	public Integer smpPreDictId;

	/**
	 * 多个班组 班组名字 仅用于显示
	 * 
	 * @param obj
	 * @return
	 */
	public String smBzNames;
	// 上车配件编码
	public String upPjNum;

	public Integer getPreDictId() {
		return preDictId;
	}

	public void setPreDictId(Integer preDictId) {
		this.preDictId = preDictId;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getRepDate() {
		return repDate;
	}

	public void setRepDate(String repDate) {
		this.repDate = repDate;
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

	public Long getThirdUnitId() {
		return thirdUnitId;
	}

	public void setThirdUnitId(Long thirdUnitId) {
		this.thirdUnitId = thirdUnitId;
	}

	public String getRepPosi() {
		return repPosi;
	}

	public void setRepPosi(String repPosi) {
		this.repPosi = repPosi;
	}

	public String getRepsituation() {
		return repsituation;
	}

	public void setRepsituation(String repsituation) {
		this.repsituation = repsituation;
	}

	public String getFailNum() {
		return failNum;
	}

	public void setFailNum(String failNum) {
		this.failNum = failNum;
	}

	public String getFailNote() {
		return failNote;
	}

	public void setFailNote(String failNote) {
		this.failNote = failNote;
	}

	public String getRepempNo() {
		return repempNo;
	}

	public void setRepempNo(String repempNo) {
		this.repempNo = repempNo;
	}

	public String getRepemp() {
		return repemp;
	}

	public void setRepemp(String repemp) {
		this.repemp = repemp;
	}

	public String getRepTime() {
		return repTime;
	}

	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getReceiptPeo() {
		return receiptPeo;
	}

	public void setReceiptPeo(String receiptPeo) {
		this.receiptPeo = receiptPeo;
	}

	public String getReceTime() {
		return receTime;
	}

	public void setReceTime(String receTime) {
		this.receTime = receTime;
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

	public String getDealFixEmp() {
		return dealFixEmp;
	}

	public void setDealFixEmp(String dealFixEmp) {
		this.dealFixEmp = dealFixEmp;
	}

	public String getDealSituation() {
		return dealSituation;
	}

	public void setDealSituation(String dealSituation) {
		this.dealSituation = dealSituation;
	}

	public String getFixEndTime() {
		return fixEndTime;
	}

	public void setFixEndTime(String fixEndTime) {
		this.fixEndTime = fixEndTime;
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

	public String getCommitLd() {
		return commitLd;
	}

	public void setCommitLd(String commitLd) {
		this.commitLd = commitLd;
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

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

	public String getAcceTime() {
		return acceTime;
	}

	public void setAcceTime(String acceTime) {
		this.acceTime = acceTime;
	}

	public String getTechnician() {
		return technician;
	}

	public void setTechnician(String technician) {
		this.technician = technician;
	}

	public String getTechAffiTime() {
		return techAffiTime;
	}

	public void setTechAffiTime(String techAffiTime) {
		this.techAffiTime = techAffiTime;
	}

	public Short getAffirmGrade() {
		return affirmGrade;
	}

	public void setAffirmGrade(Short affirmGrade) {
		this.affirmGrade = affirmGrade;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getZeroKiloRecId() {
		return zeroKiloRecId;
	}

	public void setZeroKiloRecId(Integer zeroKiloRecId) {
		this.zeroKiloRecId = zeroKiloRecId;
	}

	public Short getRecStas() {
		return recStas;
	}

	public void setRecStas(Short recStas) {
		this.recStas = recStas;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getProTeamId() {
		return proTeamId;
	}

	public void setProTeamId(Long proTeamId) {
		this.proTeamId = proTeamId;
	}

	public Integer getItemCtrlComLd() {
		return itemCtrlComLd;
	}

	public void setItemCtrlComLd(Integer itemCtrlComLd) {
		this.itemCtrlComLd = itemCtrlComLd;
	}

	public Integer getItemCtrlQI() {
		return itemCtrlQI;
	}

	public void setItemCtrlQI(Integer itemCtrlQI) {
		this.itemCtrlQI = itemCtrlQI;
	}

	public Integer getItemCtrlTech() {
		return itemCtrlTech;
	}

	public void setItemCtrlTech(Integer itemCtrlTech) {
		this.itemCtrlTech = itemCtrlTech;
	}

	public Integer getItemCtrlAcce() {
		return itemCtrlAcce;
	}

	public void setItemCtrlAcce(Integer itemCtrlAcce) {
		this.itemCtrlAcce = itemCtrlAcce;
	}

	public Integer getDatePlanPri() {
		return datePlanPri;
	}

	public void setDatePlanPri(Integer datePlanPri) {
		this.datePlanPri = datePlanPri;
	}

	public Integer getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getSmpPreDictId() {
		return smpPreDictId;
	}

	public void setSmpPreDictId(Integer smpPreDictId) {
		this.smpPreDictId = smpPreDictId;
	}

	public String getSmBzNames() {
		return smBzNames;
	}

	public void setSmBzNames(String smBzNames) {
		this.smBzNames = smBzNames;
	}

	public String getUpPjNum() {
		return upPjNum;
	}

	public void setUpPjNum(String upPjNum) {
		this.upPjNum = upPjNum;
	}

	@Override
	public String toString() {
		return "JtPreDict [preDictId=" + preDictId + ", jwdCode=" + jwdCode + ", jcType=" + jcType + ", jcNum=" + jcNum
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((preDictId == null) ? 0 : preDictId.hashCode());
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
		JtPreDict other = (JtPreDict) obj;
		if (preDictId == null) {
			if (other.preDictId != null)
				return false;
		} else if (!preDictId.equals(other.preDictId))
			return false;
		return true;
	}

}

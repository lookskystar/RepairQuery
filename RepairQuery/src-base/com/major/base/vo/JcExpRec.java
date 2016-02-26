package com.major.base.vo;

import java.io.Serializable;

/**
 * 机车试验记录
 * 
 * @author Administrator
 * 
 */
public class JcExpRec implements Serializable {

	private static final long serialVersionUID = -4316871967462207758L;
	// 表主键
	private Long jceRId;
	// 段代码
	private String jwdCode;
	// 机车Id
	private String jcnum;
	// 日计划记录ID
	private Integer dypRecId;
	// 修程
	private String xc;
	// 下车配件条码
	private String doPjBarCode;
	// 下车配件编号
	private String downPjNum;
	// 上车配件条码
	private String upPjBarCode;
	// 上车配件编号
	private String upPjNum;
	// 试验项目ID
	private Long itemId;
	// 项目名称
	private String itemName;
	// 试验情况
	private String expStatus;
	// 单位
	private String unit;
	// 检修人ID集合
	private String fixEmpId;
	// 检修人名字集合
	private String fixEmp;
	// 试验项目签字的检修人id
	private Integer fixSigneeId;
	// 试验项目签字的检修人名字
	private String fixSignee;
	// 检修人签名时间(若为实验记录时，此时为试验时间)
	private String empAffirmTime;
	// 工长ID
	private Integer leadId;
	// 工长名字
	private String leader;
	// 工长签名时间
	private String ldAffirmTime;
	// 技术员ID
	private Integer teachId;
	// 技术员名字
	private String teachName;
	// 技术员签认时间
	private String teachFiTime;
	// 质检员ID
	private Integer qiId;
	// 质检员名字
	private String qi;
	// 质检员签认时间
	private String qiAffiTime;
	// 交车工长ID
	private Integer commitLeadId;
	// 交车工长名字
	private String commitLead;
	// 交车工长签名时间
	private String comLdAffiTime;
	// 验收员ID
	private Integer accepterId;
	// 验收员名字
	private String accepter;
	// 验收员签名时间
	private String acceAffiTime;
	// 试验记录类别：0：试验检修项目记录，1试验记录；默认0
	private Integer expType;
	// 记录状态:0新建、1工人签认、2工长签认、3质检签认、4技术签认、5交车工长签认、6完成（包含验收员签认）
	private Short recStas;

	public Long getJceRId() {
		return jceRId;
	}

	public void setJceRId(Long jceRId) {
		this.jceRId = jceRId;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getJcnum() {
		return jcnum;
	}

	public void setJcnum(String jcnum) {
		this.jcnum = jcnum;
	}

	public Integer getDypRecId() {
		return dypRecId;
	}

	public void setDypRecId(Integer dypRecId) {
		this.dypRecId = dypRecId;
	}

	public String getXc() {
		return xc;
	}

	public void setXc(String xc) {
		this.xc = xc;
	}

	public String getDoPjBarCode() {
		return doPjBarCode;
	}

	public void setDoPjBarCode(String doPjBarCode) {
		this.doPjBarCode = doPjBarCode;
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

	public String getExpStatus() {
		return expStatus;
	}

	public void setExpStatus(String expStatus) {
		this.expStatus = expStatus;
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

	public Integer getFixSigneeId() {
		return fixSigneeId;
	}

	public void setFixSigneeId(Integer fixSigneeId) {
		this.fixSigneeId = fixSigneeId;
	}

	public String getFixSignee() {
		return fixSignee;
	}

	public void setFixSignee(String fixSignee) {
		this.fixSignee = fixSignee;
	}

	public String getFixEmp() {
		return fixEmp;
	}

	public void setFixEmp(String fixEmp) {
		this.fixEmp = fixEmp;
	}

	public String getEmpAffirmTime() {
		return empAffirmTime;
	}

	public void setEmpAffirmTime(String empAffirmTime) {
		this.empAffirmTime = empAffirmTime;
	}

	public Integer getLeadId() {
		return leadId;
	}

	public void setLeadId(Integer leadId) {
		this.leadId = leadId;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getLdAffirmTime() {
		return ldAffirmTime;
	}

	public void setLdAffirmTime(String ldAffirmTime) {
		this.ldAffirmTime = ldAffirmTime;
	}

	public Integer getTeachId() {
		return teachId;
	}

	public void setTeachId(Integer teachId) {
		this.teachId = teachId;
	}

	public String getTeachName() {
		return teachName;
	}

	public void setTeachName(String teachName) {
		this.teachName = teachName;
	}

	public String getTeachFiTime() {
		return teachFiTime;
	}

	public void setTeachFiTime(String teachFiTime) {
		this.teachFiTime = teachFiTime;
	}

	public Integer getQiId() {
		return qiId;
	}

	public void setQiId(Integer qiId) {
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

	public Integer getCommitLeadId() {
		return commitLeadId;
	}

	public void setCommitLeadId(Integer commitLeadId) {
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

	public Integer getAccepterId() {
		return accepterId;
	}

	public void setAccepterId(Integer accepterId) {
		this.accepterId = accepterId;
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

	public Integer getExpType() {
		return expType;
	}

	public void setExpType(Integer expType) {
		this.expType = expType;
	}

	public Short getRecStas() {
		return recStas;
	}

	public void setRecStas(Short recStas) {
		this.recStas = recStas;
	}

}

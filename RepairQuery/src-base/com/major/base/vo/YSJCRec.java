package com.major.base.vo;

import java.io.Serializable;

/**
 * 验收交车记录
 * 
 * @author Administrator
 * 
 */
public class YSJCRec implements Serializable {

	private static final long serialVersionUID = 2172200782130218892L;

	/**
	 * 记录ID
	 */
	private int recId;
	/**
	 * 日计划
	 */
	private Integer datePlanPri;
	/**
	 * 项目
	 */
	private Integer item;
	/**
	 * 项目分类(冗余,防止项目改变导致记录丢失)
	 */
	private String classify;
	/**
	 * 项目内容
	 */
	private String itemName;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 顺序
	 */
	private Integer orderNo;
	/**
	 * 检修情况
	 */
	private String fixSituation;
	/**
	 * 检修人
	 */
	private String fixemp;
	/**
	 * 检修时间
	 */
	private String fixEmpTime;
	/**
	 * 技术签字
	 */
	private String tech;
	/**
	 * 技术签字时间
	 */
	private String techAffiTime;
	/**
	 * 验收签字
	 */
	private String accept;
	/**
	 * 验收签字时间
	 */
	private String acceptAffiTime;
	/**
	 * 交车工长签字
	 */
	private String commitLead;
	/**
	 * 交车工长签字时间
	 */
	private String commAffiTime;
	/**
	 * 机务段编码
	 */
	private String jwdCode;

	public int getRecId() {
		return recId;
	}

	public void setRecId(int recId) {
		this.recId = recId;
	}

	public Integer getDatePlanPri() {
		return datePlanPri;
	}

	public void setDatePlanPri(Integer datePlanPri) {
		this.datePlanPri = datePlanPri;
	}

	public Integer getItem() {
		return item;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public String getFixSituation() {
		return fixSituation;
	}

	public void setFixSituation(String fixSituation) {
		this.fixSituation = fixSituation;
	}

	public String getFixemp() {
		return fixemp;
	}

	public void setFixemp(String fixemp) {
		this.fixemp = fixemp;
	}

	public String getFixEmpTime() {
		return fixEmpTime;
	}

	public void setFixEmpTime(String fixEmpTime) {
		this.fixEmpTime = fixEmpTime;
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

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptAffiTime() {
		return acceptAffiTime;
	}

	public void setAcceptAffiTime(String acceptAffiTime) {
		this.acceptAffiTime = acceptAffiTime;
	}

	public String getCommitLead() {
		return commitLead;
	}

	public void setCommitLead(String commitLead) {
		this.commitLead = commitLead;
	}

	public String getCommAffiTime() {
		return commAffiTime;
	}

	public void setCommAffiTime(String commAffiTime) {
		this.commAffiTime = commAffiTime;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}

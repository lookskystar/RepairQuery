package com.major.base.vo;

import java.io.Serializable;

public class PJFixItem implements Serializable {

	private static final long serialVersionUID = -2782222378373862861L;

	/**
	 * 主键
	 */
	private Long pjItemId;
	/**
	 * 对应的检修流程
	 */
	private Long pjFixFlow;
	/**
	 * 对应的配件
	 */
	private Long pjStaticInfo;
	/**
	 * 配件名
	 */
	private String pjName;
	/**
	 * 部位名称
	 */
	private String posiName;
	/**
	 * 检修配件的子配件id
	 */
	private Long childPJId;
	/**
	 * 检修配件的子配件名
	 */
	private String childPJName;
	/**
	 * 检修项目名
	 */
	private String fixItem;
	/**
	 * 负责检修当前项目的班组
	 */
	private Long team;
	/**
	 * 项目顺序号
	 */
	private Integer itemOrder;
	/**
	 * 技术要求及标准
	 */
	private String techStandard;
	/**
	 * 项目填报默认值
	 */
	private String itemFillDefault;
	/**
	 * 最小值
	 */
	private Double minVal;
	/**
	 * 最大值
	 */
	private Double maxVal;
	/**
	 * 项目在查询报表显示状态：0-不显示；1-显示
	 */
	private Integer queryshwstas;
	/**
	 * 检修项目拼音简写
	 */
	private String itempy;
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
	 * 单位
	 */
	private String unit;

	/**
	 * 添加配件数量
	 */
	private Integer amount;
	/**
	 * 复探卡控 0-不卡控 1-卡控
	 */
	private Integer itemctrlrept;

	private String jwdCode;

	public Long getPjItemId() {
		return pjItemId;
	}

	public void setPjItemId(Long pjItemId) {
		this.pjItemId = pjItemId;
	}

	public Long getPjFixFlow() {
		return pjFixFlow;
	}

	public void setPjFixFlow(Long pjFixFlow) {
		this.pjFixFlow = pjFixFlow;
	}

	public Long getPjStaticInfo() {
		return pjStaticInfo;
	}

	public void setPjStaticInfo(Long pjStaticInfo) {
		this.pjStaticInfo = pjStaticInfo;
	}

	public String getPjName() {
		return pjName;
	}

	public void setPjName(String pjName) {
		this.pjName = pjName;
	}

	public String getPosiName() {
		return posiName;
	}

	public void setPosiName(String posiName) {
		this.posiName = posiName;
	}

	public Long getChildPJId() {
		return childPJId;
	}

	public void setChildPJId(Long childPJId) {
		this.childPJId = childPJId;
	}

	public String getChildPJName() {
		return childPJName;
	}

	public void setChildPJName(String childPJName) {
		this.childPJName = childPJName;
	}

	public String getFixItem() {
		return fixItem;
	}

	public void setFixItem(String fixItem) {
		this.fixItem = fixItem;
	}

	public Long getTeam() {
		return team;
	}

	public void setTeam(Long team) {
		this.team = team;
	}

	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	public String getTechStandard() {
		return techStandard;
	}

	public void setTechStandard(String techStandard) {
		this.techStandard = techStandard;
	}

	public String getItemFillDefault() {
		return itemFillDefault;
	}

	public void setItemFillDefault(String itemFillDefault) {
		this.itemFillDefault = itemFillDefault;
	}

	public Double getMinVal() {
		return minVal;
	}

	public void setMinVal(Double minVal) {
		this.minVal = minVal;
	}

	public Double getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(Double maxVal) {
		this.maxVal = maxVal;
	}

	public Integer getQueryshwstas() {
		return queryshwstas;
	}

	public void setQueryshwstas(Integer queryshwstas) {
		this.queryshwstas = queryshwstas;
	}

	public String getItempy() {
		return itempy;
	}

	public void setItempy(String itempy) {
		this.itempy = itempy;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getItemctrlrept() {
		return itemctrlrept;
	}

	public void setItemctrlrept(Integer itemctrlrept) {
		this.itemctrlrept = itemctrlrept;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

}

package com.major.base.vo;

import java.io.Serializable;

/**
 * 项目关联映射(机务处项目与段项目关联关系，用于报表查询)
 * @author Administrator
 *
 */
public class ItemRelation implements Serializable{
	
	private static final long serialVersionUID = 4622325337651825423L;

	private int id;
	/**
	 * 机车类型  只能填写一个车型 如DF4
	 */
	private String jcType;
	/**
	 * 大部件ID
	 */
	private int firstUnitId;
	/**
	 * 大部件名字
	 */
	private String firstUnitName;
	/**
	 * 检修项目
	 */
	private String fixItem;
	/**
	 * 检修内容
	 */
	private String fixContent;
	/**
	 * 技术要求
	 */
	private String techStanderd;
	/**
	 * 修程修次：填写说明: F1,F2,F3,F4,X1
	 */
	private String xcxc;
	/**
	 * 位置
	 */
	private String position;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 关联项目表   用,隔开 如 1,2,3,
	 */
	private String itemIds;
	/**
	 * 检修情况(不映射数据库,用于查询显示)
	 */
	private String fixSituation;
	/**
	 * 检修人(不映射数据库,用于查询显示)
	 */
	private String fixEmp;
	/**
	 * 工长(不映射数据库,用于查询显示)
	 */
	private String lead;
	/**
	 * 交车工长(不映射数据库,用于查询显示)
	 */
	private String commitLead;
	/**
	 * 质检员(不映射数据库,用于查询显示)
	 */
	private String qi;
	/**
	 * 技术员(不映射数据库,用于查询显示)
	 */
	private String tech;
	/**
	 * 验收员(不映射数据库,用于查询显示)
	 */
	private String accepter;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJcType() {
		return jcType;
	}
	public void setJcType(String jcType) {
		this.jcType = jcType;
	}
	public long getFirstUnitId() {
		return firstUnitId;
	}
	public void setFirstUnitId(int firstUnitId) {
		this.firstUnitId = firstUnitId;
	}
	public String getFirstUnitName() {
		return firstUnitName;
	}
	public void setFirstUnitName(String firstUnitName) {
		this.firstUnitName = firstUnitName;
	}
	public String getFixItem() {
		return fixItem;
	}
	public void setFixItem(String fixItem) {
		this.fixItem = fixItem;
	}
	public String getFixContent() {
		return fixContent;
	}
	public void setFixContent(String fixContent) {
		this.fixContent = fixContent;
	}
	public String getTechStanderd() {
		return techStanderd;
	}
	public void setTechStanderd(String techStanderd) {
		this.techStanderd = techStanderd;
	}
	public String getXcxc() {
		return xcxc;
	}
	public void setXcxc(String xcxc) {
		this.xcxc = xcxc;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	public String getFixSituation() {
		return fixSituation;
	}
	public void setFixSituation(String fixSituation) {
		this.fixSituation = fixSituation;
	}
	public String getFixEmp() {
		return fixEmp;
	}
	public void setFixEmp(String fixEmp) {
		this.fixEmp = fixEmp;
	}
	public String getLead() {
		return lead;
	}
	public void setLead(String lead) {
		this.lead = lead;
	}
	public String getCommitLead() {
		return commitLead;
	}
	public void setCommitLead(String commitLead) {
		this.commitLead = commitLead;
	}
	public String getQi() {
		return qi;
	}
	public void setQi(String qi) {
		this.qi = qi;
	}
	public String getTech() {
		return tech;
	}
	public void setTech(String tech) {
		this.tech = tech;
	}
	public String getAccepter() {
		return accepter;
	}
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
}

package com.major.base.vo;

import java.io.Serializable;

public class PJStaticInfo implements Serializable {

	private static final long serialVersionUID = -1587315952549821433L;

	/** 主键 */
	private Long pjsid;
	/** 机务段代码 */
	private String jwdCode;
	/** 机务段区域编号 */
	private String areaId;
	/** 配件名 */
	private String pjName;
	/** 配件父级id */
	private Long parent;
	/** 最小库存量 */
	private Integer lowestStore;
	/** 最大库存量 */
	private Integer mostStore;
	/** 配件拼音 */
	private String py;
	/** 所属大部件/专业名（例如：电机、电器、走行等） */
	private Long firstUnit;
	/** 机车类型（例如：SS3B、DF4） */
	private String jcType;
	/** 配件检修流程类型 */
	private Long pjFixFlowType;
	/** 是否需要查看检修记录：0不要，1要(默认0) */
	private Integer visitRecord;
	/** 车上组装数量 */
	private Integer amount;
	/** 配件类型：0：组装到车上(默认) 1：组装到配件 */
	private Integer type;
	/** 检修的班组IDS 如: ,1,2, */
	private String bzIds;

	public PJStaticInfo() {

	}

	public PJStaticInfo(Long pjsid, String jwdCode, String areaId, String pjName, Long parent,
			Integer lowestStore, Integer mostStore, String py, Long firstUnit, String jcType,
			Long pjFixFlowType, Integer visitRecord, Integer amount, Integer type, String bzIds) {
		this.pjsid = pjsid;
		this.jwdCode = jwdCode;
		this.areaId = areaId;
		this.pjName = pjName;
		this.parent = parent;
		this.lowestStore = lowestStore;
		this.mostStore = mostStore;
		this.py = py;
		this.firstUnit = firstUnit;
		this.jcType = jcType;
		this.pjFixFlowType = pjFixFlowType;
		this.visitRecord = visitRecord;
		this.amount = amount;
		this.type = type;
		this.bzIds = bzIds;
	}

	public Long getPjsid() {
		return pjsid;
	}

	public void setPjsid(Long pjsid) {
		this.pjsid = pjsid;
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

	public String getPjName() {
		return pjName;
	}

	public void setPjName(String pjName) {
		this.pjName = pjName;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Integer getLowestStore() {
		return lowestStore;
	}

	public void setLowestStore(Integer lowestStore) {
		this.lowestStore = lowestStore;
	}

	public Integer getMostStore() {
		return mostStore;
	}

	public void setMostStore(Integer mostStore) {
		this.mostStore = mostStore;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public Long getFirstUnit() {
		return firstUnit;
	}

	public void setFirstUnit(Long firstUnit) {
		this.firstUnit = firstUnit;
	}

	public String getJcType() {
		return jcType;
	}

	public void setJcType(String jcType) {
		this.jcType = jcType;
	}

	public Long getPjFixFlowType() {
		return pjFixFlowType;
	}

	public void setPjFixFlowType(Long pjFixFlowType) {
		this.pjFixFlowType = pjFixFlowType;
	}

	public Integer getVisitRecord() {
		return visitRecord;
	}

	public void setVisitRecord(Integer visitRecord) {
		this.visitRecord = visitRecord;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBzIds() {
		return bzIds;
	}

	public void setBzIds(String bzIds) {
		this.bzIds = bzIds;
	}

}

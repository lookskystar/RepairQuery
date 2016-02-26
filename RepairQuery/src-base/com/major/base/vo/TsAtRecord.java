package com.major.base.vo;

import java.io.Serializable;

public class TsAtRecord implements Serializable {

	private static final long serialVersionUID = 4794817877467118950L;

	/**
	 * 主键id
	 * */
	private Integer id;
	/**
	 * 地区
	 */
	private Long areaId;
	/**
	 * 车型
	 * */
	private String jcType;
	/**
	 * 车号
	 * */
	private String jcNum;
	/**
	 * 修程
	 * */
	private String xc;
	/**
	 * 修次
	 * */
	private String jcFixNum;
	/**
	 * 部件
	 * */
	private String unitName;
	/**
	 * 数量
	 * */
	private String unitCount;
	/**
	 * 编号
	 * */
	private String unitNum;
	/**
	 * 报活时间
	 * */
	private String atTime;
	/**
	 * 接活时间
	 * */
	private String fixTime;
	/**
	 * 报活人
	 * */
	private String atWorker;
	/**
	 * 主探者
	 * */
	private String firstWorker;
	/**
	 * 复探者
	 * */
	private String secondWorker;
	/**
	 * 状态 0 新建 1 已派工 2 已完成
	 * */
	private String status;
	/**
	 * 探伤结果
	 * */
	private String tsResult;
	/**
	 * 处理结果
	 * */
	private String tsDeal;
	/**
	 * 探伤方法
	 * */
	private String tsMethod;
	/**
	 * 探伤时间
	 * */
	private String tsTime;

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getXc() {
		return xc;
	}

	public void setXc(String xc) {
		this.xc = xc;
	}

	public String getJcFixNum() {
		return jcFixNum;
	}

	public void setJcFixNum(String jcFixNum) {
		this.jcFixNum = jcFixNum;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(String unitCount) {
		this.unitCount = unitCount;
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public String getAtTime() {
		return atTime;
	}

	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}

	public String getFixTime() {
		return fixTime;
	}

	public void setFixTime(String fixTime) {
		this.fixTime = fixTime;
	}

	public String getAtWorker() {
		return atWorker;
	}

	public void setAtWorker(String atWorker) {
		this.atWorker = atWorker;
	}

	public String getFirstWorker() {
		return firstWorker;
	}

	public void setFirstWorker(String firstWorker) {
		this.firstWorker = firstWorker;
	}

	public String getSecondWorker() {
		return secondWorker;
	}

	public void setSecondWorker(String secondWorker) {
		this.secondWorker = secondWorker;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTsResult() {
		return tsResult;
	}

	public void setTsResult(String tsResult) {
		this.tsResult = tsResult;
	}

	public String getTsDeal() {
		return tsDeal;
	}

	public void setTsDeal(String tsDeal) {
		this.tsDeal = tsDeal;
	}

	public String getTsMethod() {
		return tsMethod;
	}

	public void setTsMethod(String tsMethod) {
		this.tsMethod = tsMethod;
	}

	public String getTsTime() {
		return tsTime;
	}

	public void setTsTime(String tsTime) {
		this.tsTime = tsTime;
	}

	@Override
	public String toString() {
		return "TsAtRecord [id=" + id + ", jcType=" + jcType + ", jcNum=" + jcNum + ", xc=" + xc + "]";
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
		TsAtRecord other = (TsAtRecord) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

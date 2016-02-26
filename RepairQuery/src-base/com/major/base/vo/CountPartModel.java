package com.major.base.vo;

import java.io.Serializable;

/**
 * 各段配件统计model
 * @author eleven
 *
 */
public class CountPartModel implements Serializable {

	private static final long serialVersionUID = -140764186174873854L;

	/** 专业(配件ID) */
	private Long id = 0L;
	/** 专业名称(配件名称) */
	private String name = "";
	/** 合格配件 */
	private Integer passCount = 0;
	/** 在修配件 */
	private Integer fixCount = 0;
	/** 待修配件 */
	private Integer waitCount = 0;
	/** 合格率 */
	private String passRate = "0.0";
	/** 已装车 */
	private Integer onTrain = 0;
	/** 总计 */
	private Integer totalCount = 0;

	public CountPartModel() {

	}

	public CountPartModel(Long id, String name, Integer passCount, Integer fixCount, Integer waitCount,
			String passRate, Integer onTrain, Integer totalCount) {
		this.id = id;
		this.name = name;
		this.passCount = passCount;
		this.fixCount = fixCount;
		this.waitCount = waitCount;
		this.passRate = passRate;
		this.onTrain = onTrain;
		this.totalCount = totalCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPassCount() {
		return passCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	public Integer getFixCount() {
		return fixCount;
	}

	public void setFixCount(Integer fixCount) {
		this.fixCount = fixCount;
	}

	public Integer getWaitCount() {
		return waitCount;
	}

	public void setWaitCount(Integer waitCount) {
		this.waitCount = waitCount;
	}

	public String getPassRate() {
		return passRate;
	}

	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}

	public Integer getOnTrain() {
		return onTrain;
	}

	public void setOnTrain(Integer onTrain) {
		this.onTrain = onTrain;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "CountPartModel [id=" + id + ", name=" + name + ", passCount=" + passCount + ", fixCount=" + fixCount
				+ ", waitCount=" + waitCount + ", passRate=" + passRate + ", onTrain=" + onTrain + ", totalCount="
				+ totalCount + "]";
	}

}

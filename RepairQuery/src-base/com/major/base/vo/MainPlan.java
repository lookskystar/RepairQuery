package com.major.base.vo;

import java.io.Serializable;

public class MainPlan implements Serializable {

	private static final long serialVersionUID = -2000422163047763750L;

	// 主键ID
	private Long id;
	// 地区
	private Long areaId;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
	// 标题
	private String title;
	// 编制人
	private String makePeople;
	// 编制时间
	private String makeTime;
	// 备注
	private String comments;
	// 状态 0:计划可变 1：计划已经发布，不能进行相应操作
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMakePeople() {
		return makePeople;
	}

	public void setMakePeople(String makePeople) {
		this.makePeople = makePeople;
	}

	public String getMakeTime() {
		return makeTime;
	}

	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	@Override
	public String toString() {
		return "MainPlan [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", title=" + title
				+ ", makePeople=" + makePeople + ", makeTime=" + makeTime + ", comments=" + comments + ", status="
				+ status + "]";
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
		MainPlan other = (MainPlan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

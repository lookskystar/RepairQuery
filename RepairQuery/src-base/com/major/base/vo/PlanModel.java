package com.major.base.vo;

import java.util.ArrayList;
import java.util.List;

public class PlanModel {

	/** 制表日期 */
	private String makePlanTime;
	/** 制表人 */
	private String makePlanPerson;
	/** 每天对应的计划 */
	private List<MainPlanDetail> detailList = new ArrayList<MainPlanDetail>();

	public String getMakePlanTime() {
		return makePlanTime;
	}

	public void setMakePlanTime(String makePlanTime) {
		this.makePlanTime = makePlanTime;
	}

	public String getMakePlanPerson() {
		return makePlanPerson;
	}

	public void setMakePlanPerson(String makePlanPerson) {
		this.makePlanPerson = makePlanPerson;
	}

	public List<MainPlanDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<MainPlanDetail> detailList) {
		this.detailList = detailList;
	}

}

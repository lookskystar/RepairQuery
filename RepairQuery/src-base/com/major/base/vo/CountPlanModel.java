package com.major.base.vo;

import java.io.Serializable;

/**
 * 各段在修机车数量统计model
 * 
 * @author eleven
 * 
 */
public class CountPlanModel implements Serializable {

	private static final long serialVersionUID = 3049074231691220624L;
	// 机务段
	private String areaName = "";
	// 机务段编码
	private String jwdCode = "";
	// 大修计划
	private int oh_plan = 0;
	// 大修实际
	private int oh_fact = 0;
	// 大修未兑现
	private int oh_unfill = 0;
	// 中修计划
	private int mid_plan = 0;
	// 中修实际
	private int mid_fact = 0;
	// 中修未兑现
	private int mid_unfill = 0;
	// 小辅修计划
	private int sm_plan = 0;
	// 小辅修实际
	private int sm_fact = 0;
	// 小辅修未兑现
	private int sm_unfill = 0;
	// 临修计划
	private int temp_plan = 0;
	// 临修实际
	private int temp_fact = 0;
	// 临修未兑现
	private int temp_unfill = 0;
	// 其他计划
	private int ot_plan = 0;
	// 其他实际
	private int ot_fact = 0;
	// 其他未兑现
	private int ot_unfill = 0;
	// 合计计划
	private int to_plan = 0;
	// 合计实际
	private int to_fact = 0;
	// 合计未兑现
	private int to_unfill = 0;

	public CountPlanModel() {
		// TODO Auto-generated constructor stub
	}

	public CountPlanModel(String areaName, int oh_plan, int oh_fact, int oh_unfill, int mid_plan, int mid_fact,
			int mid_unfill, int sm_plan, int sm_fact, int sm_unfill, int temp_plan, int temp_fact, int temp_unfill,
			int ot_plan, int ot_fact, int ot_unfill, int to_plan, int to_fact, int to_unfill) {
		this.areaName = areaName;
		this.oh_plan = oh_plan;
		this.oh_fact = oh_fact;
		this.oh_unfill = oh_unfill;
		this.mid_plan = mid_plan;
		this.mid_fact = mid_fact;
		this.mid_unfill = mid_unfill;
		this.sm_plan = sm_plan;
		this.sm_fact = sm_fact;
		this.sm_unfill = sm_unfill;
		this.temp_plan = temp_plan;
		this.temp_fact = temp_fact;
		this.temp_unfill = temp_unfill;
		this.ot_plan = ot_plan;
		this.ot_fact = ot_fact;
		this.ot_unfill = ot_unfill;
		this.to_plan = to_plan;
		this.to_fact = to_fact;
		this.to_unfill = to_unfill;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getOh_plan() {
		return oh_plan;
	}

	public void setOh_plan(int oh_plan) {
		this.oh_plan = oh_plan;
	}

	public int getOh_fact() {
		return oh_fact;
	}

	public void setOh_fact(int oh_fact) {
		this.oh_fact = oh_fact;
	}

	public int getOh_unfill() {
		return oh_unfill;
	}

	public void setOh_unfill(int oh_unfill) {
		this.oh_unfill = oh_unfill;
	}

	public int getMid_plan() {
		return mid_plan;
	}

	public void setMid_plan(int mid_plan) {
		this.mid_plan = mid_plan;
	}

	public int getMid_fact() {
		return mid_fact;
	}

	public void setMid_fact(int mid_fact) {
		this.mid_fact = mid_fact;
	}

	public int getMid_unfill() {
		return mid_unfill;
	}

	public void setMid_unfill(int mid_unfill) {
		this.mid_unfill = mid_unfill;
	}

	public int getSm_plan() {
		return sm_plan;
	}

	public void setSm_plan(int sm_plan) {
		this.sm_plan = sm_plan;
	}

	public int getSm_fact() {
		return sm_fact;
	}

	public void setSm_fact(int sm_fact) {
		this.sm_fact = sm_fact;
	}

	public int getSm_unfill() {
		return sm_unfill;
	}

	public void setSm_unfill(int sm_unfill) {
		this.sm_unfill = sm_unfill;
	}

	public int getOt_plan() {
		return ot_plan;
	}

	public void setOt_plan(int ot_plan) {
		this.ot_plan = ot_plan;
	}

	public int getOt_fact() {
		return ot_fact;
	}

	public void setOt_fact(int ot_fact) {
		this.ot_fact = ot_fact;
	}

	public int getOt_unfill() {
		return ot_unfill;
	}

	public void setOt_unfill(int ot_unfill) {
		this.ot_unfill = ot_unfill;
	}

	public int getTo_plan() {
		return to_plan;
	}

	public void setTo_plan(int to_plan) {
		this.to_plan = to_plan;
	}

	public int getTo_fact() {
		return to_fact;
	}

	public void setTo_fact(int to_fact) {
		this.to_fact = to_fact;
	}

	public int getTo_unfill() {
		return to_unfill;
	}

	public void setTo_unfill(int to_unfill) {
		this.to_unfill = to_unfill;
	}

	public int getTemp_plan() {
		return temp_plan;
	}

	public void setTemp_plan(int temp_plan) {
		this.temp_plan = temp_plan;
	}

	public int getTemp_fact() {
		return temp_fact;
	}

	public void setTemp_fact(int temp_fact) {
		this.temp_fact = temp_fact;
	}

	public int getTemp_unfill() {
		return temp_unfill;
	}

	public void setTemp_unfill(int temp_unfill) {
		this.temp_unfill = temp_unfill;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	@Override
	public String toString() {
		return "CountPlanModel [areaName=" + areaName + ", oh_plan=" + oh_plan + ", oh_fact=" + oh_fact
				+ ", oh_unfill=" + oh_unfill + ", mid_plan=" + mid_plan + ", mid_fact=" + mid_fact + ", mid_unfill="
				+ mid_unfill + ", sm_plan=" + sm_plan + ", sm_fact=" + sm_fact + ", sm_unfill=" + sm_unfill
				+ ", temp_plan=" + temp_plan + ", temp_fact=" + temp_fact + ", temp_unfill=" + temp_unfill
				+ ", ot_plan=" + ot_plan + ", ot_fact=" + ot_fact + ", ot_unfill=" + ot_unfill + ", to_plan=" + to_plan
				+ ", to_fact=" + to_fact + ", to_unfill=" + to_unfill + "]";
	}
}

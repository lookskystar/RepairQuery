package com.major.loco.service;

import java.util.List;
import java.util.Map;

import com.major.base.vo.CountPlanModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.JCFlowRec;

public interface LocoServiceI {
	
	/**
	 * 统计各段在修机车
	 * @return
	 */
	public List<CountPlanModel> CountLocoOnFix(String st, String et);
	
	/**
	 * 查询各段在修机车信息
	 * @param jwdCode
	 * @return
	 */
	public List<DatePlanPri> findDatePlanPri(String jwdCode);
	
	public List<JCFlowRec> findJCFlowRecByDatePlan(String jwdCode, String rjhmId);

	public Map<String, Object> findExpSituation(String rjhmId) throws Exception;
}

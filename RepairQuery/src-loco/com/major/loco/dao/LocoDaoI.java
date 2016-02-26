package com.major.loco.dao;

import java.util.List;

import com.major.base.vo.CountPlanModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.JCFlowRec;

public interface LocoDaoI {

	public List<CountPlanModel> CountLocoOnFix(String st, String et);

	public List<DatePlanPri> findDatePlanPri(String jwdCode);

	public List<JCFlowRec> findJCFlowRecByDatePlan(String jwdCode, String rjhmId);

	public Object findExpSituation(String rjhmId, int i) throws Exception;
}

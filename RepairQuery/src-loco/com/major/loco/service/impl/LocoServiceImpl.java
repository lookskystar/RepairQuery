package com.major.loco.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.major.base.vo.CountPlanModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.JCFlowRec;
import com.major.loco.dao.LocoDaoI;
import com.major.loco.service.LocoServiceI;

public class LocoServiceImpl implements LocoServiceI {

	private LocoDaoI locoDao;

	@Resource
	public void setLocoDao(LocoDaoI locoDao) {
		this.locoDao = locoDao;
	}

	@Override
	public List<CountPlanModel> CountLocoOnFix(String st, String et) {
		return locoDao.CountLocoOnFix(st, et);
	}

	@Override
	public List<DatePlanPri> findDatePlanPri(String jwdCode) {
		return locoDao.findDatePlanPri(jwdCode);
	}

	@Override
	public List<JCFlowRec> findJCFlowRecByDatePlan(String jwdCode, String rjhmId) {
		return locoDao.findJCFlowRecByDatePlan(jwdCode, rjhmId);
	}

	@Override
	public Map<String, Object> findExpSituation(String rjhmId) throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("item","机车试验");
		map.put("grFinished", locoDao.findExpSituation(rjhmId, 0));
		map.put("gzFinished", locoDao.findExpSituation(rjhmId, 1));
		map.put("zjFinished", locoDao.findExpSituation(rjhmId, 2));
		map.put("jsFinished", locoDao.findExpSituation(rjhmId, 3));
		map.put("jcgzFinished", locoDao.findExpSituation(rjhmId, 4));
		map.put("ysFinished", locoDao.findExpSituation(rjhmId, 5));
		return map;
	}

}

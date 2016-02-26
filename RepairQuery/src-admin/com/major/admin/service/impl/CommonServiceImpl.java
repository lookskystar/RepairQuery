package com.major.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.major.admin.dao.CommonDaoI;
import com.major.admin.service.CommonServiceI;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.DictArea;
import com.major.base.vo.DictFirstUnit;
import com.major.base.vo.DictJcStype;
import com.major.base.vo.DictProTeam;
import com.major.base.vo.ItemRelation;
import com.major.base.vo.JCFixrec;
import com.major.base.vo.JcExpRec;
import com.major.base.vo.JcType;
import com.major.base.vo.JtPreDict;
import com.major.base.vo.PJFixRecord;
import com.major.base.vo.SignedForFinish;
import com.major.base.vo.StringUtil;

public class CommonServiceImpl implements CommonServiceI {

	private CommonDaoI commonDao;

	@Resource
	public void setCommonDao(CommonDaoI commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public List<DictArea> queryDictAreaList() {
		return commonDao.queryDictAreaList();
	}

	@Override
	public List<JcType> queryDictJcTypeList() {
		return commonDao.queryDictJcTypeList();
	}

	@Override
	public DictArea queryDictAreaByCode(String jwdCode) {
		return commonDao.queryDictAreaByCode(jwdCode);
	}

	@Override
	public DatePlanPri findDatePlanPriById(String jwdCode, String rjhmId) {
		return commonDao.findDatePlanPriById(jwdCode, rjhmId);
	}

	@Override
	public String findXcxc(String xcxc, String jwdCode) {
		return commonDao.findXcxc(xcxc, jwdCode);
	}

	@Override
	public List<JtPreDict> findJtPreDictPre(String jwdCode, Integer datePlanPri) {
		return commonDao.findJtPreDictPre(jwdCode, datePlanPri);
	}

	@Override
	public SignedForFinish findSignedForFinishByPlan(String jwdCode, Integer datePlanPri) {
		return commonDao.findSignedForFinishByPlan(jwdCode, datePlanPri);
	}

	@Override
	public String getParameterValueById(int id) {
		return commonDao.getParameterValueById(id);
	}

	@Override
	public Long countItemRelation(String jctype) {
		return commonDao.countItemRelation(jctype);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DictFirstUnit> listFirstUnitsOfTemplate(String jctype) {
		List<DictFirstUnit> units = new ArrayList<DictFirstUnit>();
		DictFirstUnit firstUnit = null;
		List list = commonDao.listFirstUnitsOfTemplate(jctype);
		for (int i = 0; i < list.size(); i++) {
			Object[] it = (Object[]) list.get(i);
			firstUnit = new DictFirstUnit();
			firstUnit.setFirstunitid(Long.parseLong(it[0] + ""));
			firstUnit.setFirstunitname(it[1] + "");
			units.add(firstUnit);
		}
		return units;
	}

	@Override
	public List<ItemRelation> listItemRelation(String jwdCode, String jctype, String xcxc, Integer firstUnitId,
			Integer rjhmId) {
		List<ItemRelation> itemRelations = commonDao.listItemRelation(jctype, xcxc, firstUnitId);
		String ids = "";
		for (ItemRelation itr : itemRelations) {
			try {
				ids = itr.getItemIds();
				if (ids != null && !"".equals(ids) && ids.split(",").length > 0) {
					String[] result = dealString(commonDao.listJCFixrec(jwdCode, rjhmId, ids));
					itr.setFixSituation(result[0]);
					itr.setFixEmp(result[1]);
					itr.setLead(result[2]);
					itr.setQi(result[3]);
					itr.setTech(result[4]);
					itr.setCommitLead(result[5]);
					itr.setAccepter(result[6]);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return itemRelations;
	}

	/**
	 * 处理字符串
	 */
	private String[] dealString(List<JCFixrec> fixrecs) {
		String[] result = new String[7];
		String fixSituation = "", fixEmp = "", lead = "", qi = "", tech = "", commitLead = "", accepter = "";
		for (JCFixrec rec : fixrecs) {
			fixSituation = StringUtil.addString(fixSituation, rec.getFixSituation());
			fixEmp = StringUtil.addString(fixEmp, rec.getFixEmp());
			lead = StringUtil.addString(lead, rec.getLead());
			qi = StringUtil.addString(qi, rec.getQi());
			tech = StringUtil.addString(tech, rec.getTech());
			commitLead = StringUtil.addString(commitLead, rec.getCommitLead());
			accepter = StringUtil.addString(accepter, rec.getAccepter());
		}
		result[0] = fixSituation;
		result[1] = fixEmp;
		result[2] = lead;
		result[3] = qi;
		result[4] = tech;
		result[5] = commitLead;
		result[6] = accepter;
		return result;
	}

	@Override
	public DictProTeam findDictProTeamByPY(String jwdCode, String tSZ_PY) {
		return commonDao.findDictProTeamByPY(jwdCode, tSZ_PY);
	}

	@Override
	public List<JcExpRec> findJcExpRecs(String jwdCode, int rjhmId, long jceiId) {
		return commonDao.findJcExpRecs(jwdCode, rjhmId, jceiId);
	}

	@Override
	public JcExpRec findJcExperimentByDatePlanAndExpId(String jwdCode, int rjhmId, String expId) {
		return commonDao.findJcExperimentByDatePlanAndExpId(jwdCode, rjhmId, expId);
	}

	@Override
	public List<DictFirstUnit> findDictFistUnitForDatePlan(String jwdCode, int rjhmId) {
		List<String> fittingNums = commonDao.findFittingNumberForFixRec(jwdCode, rjhmId);
		if (null != fittingNums && !fittingNums.isEmpty()) {
			return commonDao.forFittingNumbers(jwdCode, fittingNums);
		}
		return null;
	}

	@Override
	public List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid, int rjhmId) {
		List<String> fittingNums = commonDao.findFittingNumberForFixRec(jwdCode, rjhmId);
		return commonDao.forFirstUnit(jwdCode, firstunitid, fittingNums);
	}

	@Override
	public DictProTeam findDictProTeamById(String jwdCode, Long bzId) {
		return commonDao.findDictProTeamById(jwdCode, bzId);
	}

	@Override
	public List<DictJcStype> findDictJcStype() {
		return commonDao.findDictJcStype();
	}

	@Override
	public String getFirstunitname(String jwdCode, Long firstUnitId) {
		return commonDao.getFirstunitname(jwdCode, firstUnitId);
	}
}

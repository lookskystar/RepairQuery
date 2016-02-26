package com.major.admin.dao;

import java.util.List;

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

public interface CommonDaoI {
	
	/**
	 * 查询所有地区信息
	 * @return 返回所有地区信息
	 */
	public List<DictArea> queryDictAreaList();
	
	/**
	 * 查询所有机车类型信息
	 * @return
	 */
	public List<JcType> queryDictJcTypeList();
	
	/**
	 * 根据机务段编码查询地区信息
	 * @param jwdCode
	 * @return
	 */
	public DictArea queryDictAreaByCode(String jwdCode);
	
	/**
	 * 查询日计划
	 * @param rjhmId
	 * @return
	 */
	public DatePlanPri findDatePlanPriById(String jwdCode, String rjhmId);
	
	/**
	 * 查询修程修次
	 * @param xcxc
	 * @return
	 */
	public String findXcxc(String xcxc, String jwdCode);
	
	/**
	 * 报活记录
	 * @param datePlanPri
	 * @return
	 */
	public List<JtPreDict> findJtPreDictPre(String jwdCode, Integer datePlanPri);
	
	/**
	 * 根据日计划，查找签名
	 * @param datePlanPri
	 * @return
	 */
	public SignedForFinish findSignedForFinishByPlan(String jwdCode, Integer datePlanPri);

	public String getParameterValueById(int id);

	public Long countItemRelation(String jctype);

	public List<DictFirstUnit> listFirstUnitsOfTemplate(String jctype);

	public List<ItemRelation> listItemRelation(String jctype, String xcxc, Integer firstUnitId);

	/**
	 * 根据日计划ID以及关联的项目ID查询检修记录
	 * @param rjhmId 日计划ID
	 * @param itemIds 关联项目ID
	 */
	public List<JCFixrec> listJCFixrec(String jwdCode, Integer rjhmId,String itemIds);

	public DictProTeam findDictProTeamByPY(String jwdCode, String tSZ_PY);

	public List<JcExpRec> findJcExpRecs(String jwdCode, int rjhmId, long jceiId);

	public JcExpRec findJcExperimentByDatePlanAndExpId(String jwdCode, int rjhmId, String expId);

	public List<String> findFittingNumberForFixRec(String jwdCode, int rjhmId);

	public List<DictFirstUnit> forFittingNumbers(String jwdCode, List<String> fittingNums);

	public List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid, List<String> fittingNums);

	public DictProTeam findDictProTeamById(String jwdCode, Long bzId);

	public List<DictJcStype> findDictJcStype();

	public String getFirstunitname(String jwdCode, Long firstUnitId);
}

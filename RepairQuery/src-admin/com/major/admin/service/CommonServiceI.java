package com.major.admin.service;

import java.util.List;

import com.major.base.vo.DatePlanPri;
import com.major.base.vo.DictArea;
import com.major.base.vo.DictFirstUnit;
import com.major.base.vo.DictJcStype;
import com.major.base.vo.DictProTeam;
import com.major.base.vo.ItemRelation;
import com.major.base.vo.JcExpRec;
import com.major.base.vo.JcType;
import com.major.base.vo.JtPreDict;
import com.major.base.vo.PJFixRecord;
import com.major.base.vo.SignedForFinish;

public interface CommonServiceI {

	/**
	 * 查询所有地区信息
	 * 
	 * @return 返回所有地区信息
	 */
	public List<DictArea> queryDictAreaList();

	/**
	 * 查询所有机车类型信息
	 * 
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
	 * 查找日计划
	 * @param rjhmId
	 * @return
	 */
	public DatePlanPri findDatePlanPriById(String jwdCode, String rjhmId);
	
	/**
	 * 查询修程修次
	 */
	public String findXcxc(String xcxc, String jwdCode);
	
	/**
	 * 报活记录
	 * @param datePlanPri
	 * @return
	 */
	List<JtPreDict> findJtPreDictPre(String jwdCode, Integer datePlanPri);
	
	/**
	 * 根据日计划，查找签名
	 * @param datePlanPri
	 * @return
	 */
	SignedForFinish findSignedForFinishByPlan(String jwdCode, Integer datePlanPri);
	
	/**
	 * 根据参数ID查询结果值
	 */
	public String getParameterValueById(int id);
	
	/**
	 * 判断项目是否关联模板项目
	 */
	public Long countItemRelation(String jctype);
	
	/**
	 * 查询模板项目中的一级部件
	 */
	public List<DictFirstUnit> listFirstUnitsOfTemplate(String jctype);
	
	/**
	 * 查询报表
	 * @param jctype 机车类型
	 * @param xcxc 修程修次
	 * @param firstUnitId 大部件ID
	 * @param rjhmId 日计划ID
	 */
	public List<ItemRelation> listItemRelation(String jwdCode, String jctype,String xcxc,Integer firstUnitId,Integer rjhmId);

	public DictProTeam findDictProTeamByPY(String jwdCode, String tSZ_PY);
	
	public DictProTeam findDictProTeamById(String jwdCode, Long bzId);
	
	
	/**
	 * 查询机车实验记录
	 * 
	 * @param rjhmId
	 *            日计划ID
	 * @param jceiId
	 *            实验主项目ID
	 * @return
	 */
	List<JcExpRec> findJcExpRecs(String jwdCode, int rjhmId, long jceiId);
	
	/**
	 * 根据日计划和试验id查询试验的试验记录
	 * @param rjhmId 日记id
	 * @param expId 试验id
	 * @return
	 */
	JcExpRec findJcExperimentByDatePlanAndExpId(String jwdCode, int rjhmId, String expId);
	
	/**
	 * 根据日计划查找配件专业
	 * @param rjhmId 日计划
	 * @return
	 */
	List<DictFirstUnit> findDictFistUnitForDatePlan(String jwdCode, int rjhmId);
	
	/**
	 * 根据专业查找检修记录
	 * @param firstunitid 专业ID
	 * @return 检修记录
	 */
	List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid,int rjhmId);
	
	/**
	 * 机车类型信息
	 * @return
	 */
	public List<DictJcStype> findDictJcStype();

	public String getFirstunitname(String jwdCode, Long firstUnitId);
}

package com.major.recorder.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.major.base.page.PageModel;
import com.major.base.util.fusioncharts.MSArea2DChartSet;
import com.major.base.vo.CountPartModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.DictFirstUnit;
import com.major.base.vo.DictProTeam;
import com.major.base.vo.JCFixrec;
import com.major.base.vo.JCQZFixRec;
import com.major.base.vo.JCZXFixItem;
import com.major.base.vo.JCZXFixRec;
import com.major.base.vo.JtPreDict;
import com.major.base.vo.MainPlanDetail;
import com.major.base.vo.OilAssayItem;
import com.major.base.vo.OilAssayPriRecorder;
import com.major.base.vo.PJDynamicInfo;
import com.major.base.vo.PJFixItem;
import com.major.base.vo.PJFixRecord;
import com.major.base.vo.PJStaticInfo;
import com.major.base.vo.PlanModel;
import com.major.base.vo.SignedForFinish;
import com.major.base.vo.YSJCRec;

public interface RecorderServiceI {

	/**
	 * 查询机车检修记录
	 * 
	 * @param xcxc
	 *            修程修次
	 * @return
	 */
	public List<DatePlanPri> queryLocoFixRec(String itemName, String xcxc, String jcNum, String st,
			String et, String jwdCode);

	/**
	 * 统计检修配件
	 * 
	 * @param jwdCode
	 *            机务段编号
	 * @param jcType
	 *            机车类型
	 * @return
	 */
	public Map<CountPartModel, List<CountPartModel>> countPartRate(String jwdCode, String jcType);

	/**
	 * 统计检修配件
	 * 
	 * @param jwdCode
	 *            机务段编号
	 * @param jcType
	 *            机车类型
	 * @return
	 */
	public Map<CountPartModel, List<CountPartModel>> countPartRate2(String jwdCode, String jcType);

	/**
	 * 统计报活
	 * 
	 * @param st
	 * @param et
	 * @return
	 */
	public List<Map<String, Object>> countReportInfo(String st, String et) throws ParseException;

	/**
	 * 计划查询
	 * 
	 * @param yearMonth
	 * @param weekCount
	 * @param jwdCode
	 * @return
	 */
	public PlanModel countPlan(String yearMonth, String weekCount, String jwdCode) throws Exception;

	public List<Map<String, Object>> findJgAcounts(String jwdCode);

	public Map<String, List<Map<String, Object>>> findJgItemCount(String jwdCode);

	/**
	 * 查找对应班组
	 * 
	 * @param jctype
	 * @param workflag
	 * @return
	 */
	public List<DictProTeam> findAllProTeam(String jwdcode, String jctype, Integer workflag);

	/**
	 * 临修检修记录
	 * 
	 * @param datePlanPri
	 * @return
	 */
	List<JtPreDict> findJtPreDict(String jwdCode, Integer datePlanPri);

	/**
	 * 秋整检修记录
	 * 
	 * @param datePlanPri
	 * @return
	 */
	List<JCQZFixRec> findJCQZFixRec(String jwdCode, Integer datePlanPri);

	/**
	 * 从记录表中获取一级部件 0：小辅修 1：中修
	 * 
	 * @param rjhmId
	 * @return
	 */
	public List<DictFirstUnit> listFirstUnitsOfJCFixRec(String jwdCode, Integer rjhmId, int type);

	/**
	 * 小辅修检修记录
	 * 
	 * @return
	 */
	List<JCFixrec> findJCFixrec(String jwdCode, Integer datePlanPri, String unit);

	/**
	 * 根据班组查找秋整检修记录
	 * 
	 * @param rjhmId
	 * @param unit
	 * @return
	 */
	public List<JCQZFixRec> findJCQZFixrecByTeam(String jwdCode, Integer rjhmId, Long teamId);

	/**
	 * 根据班组、专业查询报活记录
	 * 
	 * @param datePlanPri
	 * @param type
	 * @return
	 */
	List<JtPreDict> findJtPreDictByFlag(String jwdCode, Integer datePlanPri, Object flag, int type);

	/**
	 * 根据班组、专业查询报活记录
	 * 
	 * @param datePlanPri
	 * @param type
	 * @return
	 */
	List<JtPreDict> findJtPreDictByFlag(String jwdCode, Integer datePlanPri, Object flag);

	/**
	 * 根据班组查询检修记录
	 * 
	 * @param rjhmId
	 * @param teamId
	 * @return
	 */
	public List<JCFixrec> findRecByProTeam(String jwdCode, Integer rjhmId, Long teamId);

	public List<DictProTeam> findAllZxProTeam(String jwdCode, String jcType, int workFlag);

	/**
	 * 查询中修检修项目
	 * 
	 * @param nodeid
	 * @param xcxc
	 * @param jcsType
	 * @param bzid
	 * @param protemid
	 * @return
	 */
	public List<JCZXFixItem> findZXItem(String jwdCode, Integer nodeid, String xcxc,
			String jcsType, Long bzid, Integer firstUnitId);

	/**
	 * 根据班组查询检修记录
	 * 
	 * @param rjhmId
	 * @param teamId
	 * @return
	 */
	public List<JCZXFixRec> findZxRecByProTeam(String jwdCode, Integer rjhmId, Long teamId,
			Integer nodeId);

	public PageModel findJCFixrecLimited(String jwdCode, Integer datePlanPri, String unit);

	public PageModel findJCZXFixRecLimited(String jwdCode, Integer datePlanPri);

	public List<DatePlanPri> findJCHistory(String jwdCode, String jcNum, String jcStype);

	/**
	 * 获取竣工记录
	 */
	public SignedForFinish getJCjungong(String jwdCode, int rjhmId);

	/**
	 * 查询记录
	 */
	public List<YSJCRec> listYSJCRec(String jwdCode, int rjhmId);

	/**
	 * 根据日计划ID查找油水化验记录
	 * 
	 * @param rjhmId
	 * @return
	 */
	public OilAssayPriRecorder findOilAssayRecByDailyId(String jwdCode, Integer rjhmId);

	/**
	 * 根据机车类型查询机车油水化验项目
	 * 
	 * @param jcType
	 * @return
	 */
	public List<OilAssayItem> findOilAssayItemByJcType(String jwdCode, String jcType);

	/**
	 * 根据记录主表ID查询相应的油水化验记录明细信息
	 * 
	 * @param recId
	 * @param itemId
	 * @return
	 */
	public List<Map<String, Object>> findOilDetailRecorderByRecId(String jwdCode, int itemId,
			long recId, String jcType);

	public Map<String, List<JCFixrec>> listLeftWorkRecord(String jwdCode, Integer valueOf);

	public Map<String, List<JCZXFixRec>> listZXLeftWorkRecord(String jwdCode, Integer valueOf);

	/**
	 * 查询配件动态信息
	 * 
	 * @return
	 */
	public PageModel findPJDynamicInfo(String jwdCode, String jcsType, String firstUnitId,
			String pjName, String pjNum, String pjStatus, String storePosition, String getOnNum,
			String bzId);

	/**
	 * 根据配件编号查找配件信息
	 * 
	 * @param pjNum
	 * @return
	 */
	public PJDynamicInfo findPJDynamicInfoByPJNum(String jwdCode, String pjNum);

	/**
	 * 根据配件ID查询配件动态信息
	 * 
	 * @param pjdId
	 * @return
	 */
	public PJDynamicInfo findPJDynamicInfoById(String jwdCode, Long pjdId);

	/**
	 * 根据动态配件ID查询配件检修记录
	 * 
	 * @param pjdid
	 * @return
	 */
	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid);

	/**
	 * 根据动态配件ID和日计划ID查询配件检修记录
	 * 
	 * @param pjdid
	 * @return
	 */
	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid, Long recId);

	/**
	 * 根据日计划查询小辅修日计划机车下所有填写的配件编号
	 * 
	 * @param rjhmId
	 * @param type
	 *            0:小辅修 1：临修 2：春整
	 * @return
	 */
	List<String> findXXPJNums(String jwdCode, int rjhmId, int type);

	/**
	 * 通过小辅修配件编号查询配件大类信息
	 * 
	 * @param pjNums
	 * @return
	 */
	public List<PJStaticInfo> findPJStaticInfoByXXPJNums(String jwdCode, List<String> pjNums);

	/**
	 * 根据配件编号查询所有的配件动态信息
	 * 
	 * @param pjsid
	 * @param pjNums
	 * @return
	 */
	public List<PJDynamicInfo> findPJDynamicInfoByPjNums(String jwdCode, Long pjsid,
			List<String> pjNums);

	/**
	 * 根据机车类型查询配件静态信息
	 * 
	 * @param jcType
	 * @param bzId
	 *            对应的班组 null 表示全部
	 * @return
	 */
	public List<PJStaticInfo> findPJStaticInfo(String jwdCode, String jcType, Long bzId);

	/**
	 * 根据日计划查询日计划机车下所有填写的配件编号
	 * 
	 * @param rjhmId
	 * @return
	 */
	List<String> findPjNums(String jwdCode, int rjhmId);

	/**
	 * 查询动态配件在中修上车的数量
	 * 
	 * @param pjsid
	 * @param pjNums
	 * @return
	 */
	public int findDynamicInZxFixItem(String jwdCode, Long pjsid, List<String> pjNums);

	/**
	 * 根据日计划查找配件专业
	 * 
	 * @param rjhmId
	 *            日计划
	 * @return
	 */
	List<DictFirstUnit> findDictFistUnitForDatePlan(String jwdCode, int rjhmId);

	/**
	 * 查询中修记录
	 */
	public List<JCZXFixRec> listJCZXFixRec(String jwdCode, int rjhmId, Long bzId);

	/**
	 * 根据配件编号查找动态配件信息
	 * 
	 * @param pjNum
	 * @return
	 */
	PJDynamicInfo findDynamicInfoByPjNum(String jwdCode, String pjNum);

	/**
	 * 根据配件动态ID查询配件信息
	 * 
	 * @param pjdid
	 * @return
	 */
	PJDynamicInfo findPJDynamicInfoByDID(String jwdCode, Long pjdid);

	/**
	 * 根据日计划ID查询配件记录
	 * 
	 * @param rjhmId
	 * @return
	 */
	public PJFixRecord findPJFixRecordByRjhmId(String jwdCode, Integer rjhmId, Long pjdid);

	/**
	 * 根据配件动态ID和配件记录ID查询配件检修记录信息
	 * 
	 * @param pjdid
	 * @param pjRecId
	 * @param bzId
	 *            指定班组 如为null，表示全部
	 * @return
	 */
	List<PJFixRecord> findPJFixRecordByDid(String jwdCode, Long pjdid, Long pjRecId, Long bzId);

	/**
	 * 根据机车型号查找对应的部件
	 * 
	 * @param jcStype
	 * @return
	 */
	List<DictFirstUnit> findDictFirstUnitByType(String jwdCode, String jcStype);

	/**
	 * 统计记录条数
	 */
	public Long countJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit);

	/**
	 * 中修检修记录
	 * 
	 * @return
	 */
	List<JCZXFixRec> findJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit);

	public String findFirstUnitName(String jwdCode, Long staticInfo);

	/**
	 * 根据日计划与配件编码查找检修记录
	 * 
	 * @param pjNum
	 * @return
	 */
	List<PJFixRecord> forNumAndDataPlan(String jwdCode, String pjNum);

	/**
	 * 根据专业查找检修记录
	 * 
	 * @param firstunitid
	 *            专业ID
	 * @return 检修记录
	 */
	List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid, int rjhmId);

	/**
	 * 根据配件静态ID查询配件静态信息
	 * 
	 * @param pjsid
	 * @return
	 */
	PJStaticInfo findPJStaticInfoById(String jwdCode, Long pjsid);

	/**
	 * 根据静态配件信息查找检修项目信息
	 * 
	 * @param staticId
	 * @return
	 */
	List<PJFixItem> findPjFixItemByStaticId(String jwdCode, long staticId);

	public List<JtPreDict> countReportDetailInfo(String jwdCode, String st, String et, String type, String jcType, String jcNum, String emp, String fixEmp);

	public List<DatePlanPri> queryLocoFixRecOnNum(String xcxc, String st, String et,
			String jwdCode, String type);

	public List<MainPlanDetail> queryLocoFixRecOnPlan(String xcxc, String st, String et,
			String jwdCode);

	public List<Map<String, Object>> countReportCategoryType(String st, String et, String jwdCode)
			throws Exception;

	public List<Map<String, Object>> countReportCategoryJctype(String st, String et, String jwdCode)
			throws Exception;

	public List<Map<String, Object>> countPlans(String st, String et) throws Exception;

	public List<MainPlanDetail> countPlansDetail(String st, String et, String jwdCode, String isCash, String jcType, String jcNum);

	public MSArea2DChartSet createMonthMSArea2D(String st, String et);

}

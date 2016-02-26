package com.major.recorder.dao;

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

public interface RecorderDaoI {

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
	 * 查询计划
	 * 
	 * @param st
	 * @param et
	 * @param jwdcode
	 * @return
	 */
	public PlanModel countPlan(String st, String et, String jwdCode) throws Exception;

	public List<Map<String, Object>> findJgAcounts(String jwdCode);

	public List<Map<String, Object>> findJgItemCount(String jwdCode);

	public List<DictProTeam> findAllProTeam(String jwdcode, String jctype, Integer workflag);

	/**
	 * 临修检修记录
	 * 
	 * @param datePlanPri
	 * @return
	 */
	List<JtPreDict> findJtPreDict(String jwdCode, Integer datePlanPri);

	public List<JCQZFixRec> findJCQZFixRec(String jwdCode, Integer datePlanPri);

	public List<DictFirstUnit> listFirstUnitsOfJCFixRec(String jwdCode, Integer rjhmId, int type);

	public List<JCFixrec> findJCFixrec(String jwdCode, Integer datePlanPri, String unit);

	public List<JCQZFixRec> findJCQZFixrecByTeam(String jwdCode, Integer rjhmId, Long teamId);

	public List<JtPreDict> findJtPreDictByFlag(String jwdCode, Integer datePlanPri, Object flag,
			int type);

	public List<JCFixrec> findRecByProTeam(String jwdCode, Integer rjhmId, Long teamId);

	public List<DictProTeam> findAllZxProTeam(String jwdCode, String jcType, int parseInt);

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

	public List<JCZXFixRec> findZxRecByProTeam(String jwdCode, Integer rjhmId, Long teamId,
			Integer nodeId);

	public PageModel findJCFixrecLimited(String jwdCode, Integer datePlanPri, String unit);

	public PageModel findJCZXFixRecLimited(String jwdCode, Integer datePlanPri);

	public List<DatePlanPri> findJCHistory(String jwdCode, String jcNum, String jcStype);

	public SignedForFinish getJCjungong(String jwdCode, int rjhmId);

	public List<YSJCRec> listYSJCRec(String jwdCode, int rjhmId);

	public OilAssayPriRecorder findOilAssayRecByDailyId(String jwdCode, Integer rjhmId);

	public List<OilAssayItem> findOilAssayItemByJcType(String jwdCode, String jcType);

	public int findOilDetailRecorderCount(String jwdCode, int itemId, long recId);

	/**
	 * 查询化验记录明细信息
	 * 
	 * @param itemId
	 * @param recId
	 * @return
	 */
	public List<Object[]> findOilDetailRecorder(String jwdCode, int itemId, long recId);

	public List<JCFixrec> listLeftWorkRecord(String jwdCode, Integer rjhmId);

	public List<JCZXFixRec> listZXLeftWorkRecord(String jwdCode, Integer rjhmId);

	public PageModel findPJDynamicInfo(String jwdCode, String jcsType, String firstUnitId,
			String pjName, String pjNum, String pjStatus, String storePosition, String getOnNum,
			String bzId);

	public PJDynamicInfo findPJDynamicInfoByPJNum(String jwdCode, String pjNum);

	public PJDynamicInfo findPJDynamicInfoById(String jwdCode, Long pjdId);

	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid);

	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid, Long recId);

	public List<String> findXXPJNums(String jwdCode, int rjhmId, int type);

	public List<PJStaticInfo> findPJStaticInfoByXXPJNums(String jwdCode, List<String> pjNums);

	public List<PJDynamicInfo> findPJDynamicInfoByPjNums(String jwdCode, Long pjsid,
			List<String> pjNums);

	public List<PJStaticInfo> findPJStaticInfo(String jwdCode, String jcType, Long bzId);

	public List<String> findPjNums(String jwdCode, int rjhmId);

	public int findDynamicInZxFixItem(String jwdCode, Long pjsid, List<String> pjNums);

	/**
	 * 根据检修组装记录查询配件编号
	 * 
	 * @param rjhmId
	 *            日计划
	 * @return 日计划组装填入编号集合
	 */
	List<String> findFittingNumberForFixRec(String jwdCode, int rjhmId);

	/**
	 * 根据配件编号集合查找配件专业
	 * 
	 * @param fittingNums
	 *            配件编号集合
	 * @return 配件动态信息集合
	 */
	List<DictFirstUnit> forFittingNumbers(String jwdCode, List<String> fittingNums);

	public List<JCZXFixRec> listJCZXFixRec(String jwdCode, int rjhmId, Long bzId);

	public PJDynamicInfo findDynamicInfoByPjNum(String jwdCode, String pjNum);

	public PJDynamicInfo findPJDynamicInfoByDID(String jwdCode, Long pjdid);

	public PJFixRecord findPJFixRecordByRjhmId(String jwdCode, Integer rjhmId, Long pjdid);

	public List<PJFixRecord> findPJFixRecordByDid(String jwdCode, Long pjdid, Long pjRecId,
			Long bzId);

	public List<DictFirstUnit> findDictFirstUnitByType(String jwdCode, String jcStype);

	public Long countJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit);

	public List<JCZXFixRec> findJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit);

	/**
	 * 保存油水化验记录明细信息
	 * 
	 * @param itemId
	 * @param recId
	 */
	public void saveOilDetailRecorder(String jwdCode, int itemId, long recId, String jcType);

	public String findFirstUnitName(String jwdCode, Long staticInfo);

	public List<PJFixRecord> forNumAndDataPlan(String jwdCode, String pjNum);

	public List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid, List<String> fittingNums);

	public PJStaticInfo findPJStaticInfoById(String jwdCode, Long pjsid);

	public List<PJFixItem> findPjFixItemByStaticId(String jwdCode, long staticId);

	public List<JtPreDict> countReportDetailInfo(String jwdCode, String st, String et, String type, String jcType, String jcNum, String emp, String fixEmp);

	public List<DatePlanPri> queryLocoFixRecOnNum(String xcxc, String st, String et,
			String jwdCode, String type);

	public List<MainPlanDetail> queryLocoFixRecOnPlan(String xcxc, String st, String et,
			String jwdCode);

	public List<Map<String, Object>> countReportCategoryType(String st, String et, String jwdCode);

	public List<Map<String, Object>> countReportCategoryJctype(String st, String et, String jwdCode);

	public List<Map<String, Object>> countPlans(String st, String et);

	public List<MainPlanDetail> countPlansDetail(String st, String et, String jwdCode, String isCash, String jcType, String jcNum);

	public List<Map<String, Object>> countMonthRateData(String st, String et);

}

package com.major.recorder.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.major.admin.dao.CommonDaoI;
import com.major.base.page.PageModel;
import com.major.base.util.SimplDateFormatUtils;
import com.major.base.util.fusioncharts.MSArea2DCategories;
import com.major.base.util.fusioncharts.MSArea2DCategory;
import com.major.base.util.fusioncharts.MSArea2DChart;
import com.major.base.util.fusioncharts.MSArea2DChartSet;
import com.major.base.util.fusioncharts.MSData2Data;
import com.major.base.util.fusioncharts.MSData2DataSet;
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
import com.major.recorder.dao.RecorderDaoI;
import com.major.recorder.service.RecorderServiceI;

public class RecorderServiceImpl implements RecorderServiceI {

	private RecorderDaoI recorderDao;

	private CommonDaoI commonDao;

	@Resource
	public void setRecorderDao(RecorderDaoI recorderDao) {
		this.recorderDao = recorderDao;
	}

	@Resource
	public void setCommonDao(CommonDaoI commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public List<DatePlanPri> queryLocoFixRec(String itemName, String xcxc, String jcNum, String st,
			String et, String jwdCode) {
		return recorderDao.queryLocoFixRec(itemName, xcxc, jcNum, st, et, jwdCode);
	}

	@Override
	public Map<CountPartModel, List<CountPartModel>> countPartRate(String jwdCode, String jcType) {
		return recorderDao.countPartRate(jwdCode, jcType);
	}

	@Override
	public List<Map<String, Object>> countReportInfo(String st, String et) throws ParseException {
		SimpleDateFormat YMD = SimplDateFormatUtils.createYMDFormat();
		List<Map<String, Object>> reportList = recorderDao.countReportInfo(st, et);
		for (Map<String, Object> map : reportList) {
			String date = (String) map.get("date");
			String dayOfWeek = this.getTodayWeekNick(YMD.parse(date));
			map.put("dayOfWeek", date + " " + dayOfWeek);
		}
		return reportList;
	}

	@Override
	public PlanModel countPlan(String yearMonth, String weekCount, String jwdCode) throws Exception {
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		Date tagerDate = ym.parse(yearMonth);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tagerDate);
		Integer weekCountInt = Integer.parseInt(weekCount);
		calendar.set(Calendar.WEEK_OF_MONTH, weekCountInt);
		// 获取该周第一天 "yyyy-MM-dd"
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		Date firstDayOfWeekDate = calendar.getTime();
		String st = ymdhms.format(firstDayOfWeekDate);
		// 获取该周的最后一天 "yyyy-MM-dd"
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		Date lastDayOfWeekDate = calendar.getTime();
		String et = ymdhms.format(lastDayOfWeekDate);
		return recorderDao.countPlan(st, et, jwdCode);
	}

	@Override
	public List<Map<String, Object>> findJgAcounts(String jwdCode) {
		return recorderDao.findJgAcounts(jwdCode);
	}

	@Override
	public Map<String, List<Map<String, Object>>> findJgItemCount(String jwdCode) {
		Map<String, List<Map<String, Object>>> map = new LinkedHashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> acounts = recorderDao.findJgItemCount(jwdCode);
		// Object[] array = null;
		// String item = null;
		// Map<String, String> temp = null;
		// for (int i = 0; i < acounts.size(); i++) {
		// array = (Object[]) acounts.get(i);
		// item = (String) array[0];
		// if (map.get(item) == null) {
		// map.put(item, new ArrayList<Map<String, String>>());
		// }
		// temp = new LinkedHashMap<String, String>();
		// temp.put("jctype", array[1] + "");
		// temp.put("planNum", array[2] + "");
		// temp.put("sjNum", array[3] + "");
		// temp.put("jgid", array[4] + "");
		// map.get(item).add(temp);
		// }
		String content = null;
		for (int i = 0; i < acounts.size(); i++) {
			Map<String, Object> tempMap = acounts.get(i);
			content = tempMap.get("jg_content").toString();
			if (null == map.get(content)) {
				map.put(content, new ArrayList<Map<String, Object>>());
			}
			tempMap.put("jctype", tempMap.get("jc_type") + "");
			tempMap.put("planNum", tempMap.get("plan_num") + "");
			tempMap.put("sjNum", tempMap.get("sjcount") + "");
			tempMap.put("jgid", tempMap.get("jgid") + "");
			map.get(content).add(tempMap);
		}
		return map;
	}

	@Override
	public List<DictProTeam> findAllProTeam(String jwdcode, String jctype, Integer workflag) {
		return recorderDao.findAllProTeam(jwdcode, jctype, workflag);
	}

	@Override
	public List<JtPreDict> findJtPreDict(String jwdCode, Integer datePlanPri) {
		return recorderDao.findJtPreDict(jwdCode, datePlanPri);
	}

	@Override
	public List<JCQZFixRec> findJCQZFixRec(String jwdCode, Integer datePlanPri) {
		return recorderDao.findJCQZFixRec(jwdCode, datePlanPri);
	}

	@Override
	public List<DictFirstUnit> listFirstUnitsOfJCFixRec(String jwdCode, Integer rjhmId, int type) {
		return recorderDao.listFirstUnitsOfJCFixRec(jwdCode, rjhmId, type);
	}

	@Override
	public List<JCFixrec> findJCFixrec(String jwdCode, Integer datePlanPri, String unit) {
		return recorderDao.findJCFixrec(jwdCode, datePlanPri, unit);
	}

	@Override
	public List<JCQZFixRec> findJCQZFixrecByTeam(String jwdCode, Integer rjhmId, Long teamId) {
		return recorderDao.findJCQZFixrecByTeam(jwdCode, rjhmId, teamId);
	}

	@Override
	public List<JtPreDict> findJtPreDictByFlag(String jwdCode, Integer datePlanPri, Object flag,
			int type) {
		return recorderDao.findJtPreDictByFlag(jwdCode, datePlanPri, flag, type);
	}

	@Override
	public List<JtPreDict> findJtPreDictByFlag(String jwdCode, Integer datePlanPri, Object flag) {
		return recorderDao.findJtPreDictByFlag(jwdCode, datePlanPri, flag, datePlanPri);
	}

	@Override
	public List<JCFixrec> findRecByProTeam(String jwdCode, Integer rjhmId, Long teamId) {
		return recorderDao.findRecByProTeam(jwdCode, rjhmId, teamId);
	}

	@Override
	public List<DictProTeam> findAllZxProTeam(String jwdCode, String jcType, int workFlag) {
		return recorderDao.findAllZxProTeam(jwdCode, jcType, workFlag);
	}

	@Override
	public List<JCZXFixItem> findZXItem(String jwdCode, Integer nodeid, String xcxc,
			String jcsType, Long bzid, Integer firstUnitId) {
		return recorderDao.findZXItem(jwdCode, nodeid, xcxc, jcsType, bzid, firstUnitId);
	}

	@Override
	public List<JCZXFixRec> findZxRecByProTeam(String jwdCode, Integer rjhmId, Long teamId,
			Integer nodeId) {
		return recorderDao.findZxRecByProTeam(jwdCode, rjhmId, teamId, nodeId);
	}

	@Override
	public PageModel findJCFixrecLimited(String jwdCode, Integer datePlanPri, String unit) {
		return recorderDao.findJCFixrecLimited(jwdCode, datePlanPri, unit);
	}

	@Override
	public PageModel findJCZXFixRecLimited(String jwdCode, Integer datePlanPri) {
		return recorderDao.findJCZXFixRecLimited(jwdCode, datePlanPri);
	}

	@Override
	public List<DatePlanPri> findJCHistory(String jwdCode, String jcNum, String jcStype) {
		return recorderDao.findJCHistory(jwdCode, jcNum, jcStype);
	}

	@Override
	public SignedForFinish getJCjungong(String jwdCode, int rjhmId) {
		return recorderDao.getJCjungong(jwdCode, rjhmId);
	}

	@Override
	public List<YSJCRec> listYSJCRec(String jwdCode, int rjhmId) {
		return recorderDao.listYSJCRec(jwdCode, rjhmId);
	}

	@Override
	public OilAssayPriRecorder findOilAssayRecByDailyId(String jwdCode, Integer rjhmId) {
		return recorderDao.findOilAssayRecByDailyId(jwdCode, rjhmId);
	}

	@Override
	public List<OilAssayItem> findOilAssayItemByJcType(String jwdCode, String jcType) {
		return recorderDao.findOilAssayItemByJcType(jwdCode, jcType);
	}

	@Override
	public List<Map<String, Object>> findOilDetailRecorderByRecId(String jwdCode, int itemId,
			long recId, String jcType) {
		if (recorderDao.findOilDetailRecorderCount(jwdCode, itemId, recId) == 0) {
			recorderDao.saveOilDetailRecorder(jwdCode, itemId, recId, jcType);// 将油水化验记录信息插入到记录明细表中
		}
		List<Object[]> list = recorderDao.findOilDetailRecorder(jwdCode, itemId, recId);
		List<Map<String, Object>> details = new ArrayList<Map<String, Object>>();

		for (Object[] obj : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("recdetailid", obj[0]);
			map.put("subitemtitle", obj[1]);
			map.put("minVal", obj[2]);
			map.put("maxVal", obj[3]);
			map.put("realdeteval", obj[4]);
			map.put("quagrade", obj[5]);
			map.put("receiptpeo", obj[6]);
			map.put("fintime", obj[7]);
			details.add(map);
		}
		return details;
	}

	@Override
	public Map<String, List<JCFixrec>> listLeftWorkRecord(String jwdCode, Integer rjhmId) {
		Map<String, List<JCFixrec>> leftWorkRecordMap = new HashMap<String, List<JCFixrec>>();
		List<JCFixrec> leftWorkRecordList = recorderDao.listLeftWorkRecord(jwdCode, rjhmId);
		List<JCFixrec> leftWorkRecordListOfPro = null;
		for (JCFixrec jcFixrec : leftWorkRecordList) {
			Long bzId = jcFixrec.getBanzuId();
			DictProTeam dicProTeam = commonDao.findDictProTeamById(jwdCode, bzId);
			String proTeamName = dicProTeam.getProteamname();
			if (jcFixrec.getFixEmp() == null || jcFixrec.getLead() == null
					|| (jcFixrec.getItemCtrlQI() == 1 && jcFixrec.getQi() == null)
					|| (jcFixrec.getItemCtrlTech() == 1 && jcFixrec.getTech() == null)
					|| (jcFixrec.getItemCtrlComLd() == 1 && jcFixrec.getCommitLead() == null)
					|| (jcFixrec.getItemCtrlAcce() == 1 && jcFixrec.getAccepter() == null)) {
				if (leftWorkRecordMap.get(proTeamName) == null) {
					leftWorkRecordListOfPro = new ArrayList<JCFixrec>();
					leftWorkRecordMap.put(proTeamName, leftWorkRecordListOfPro);
				} else {
					leftWorkRecordListOfPro = leftWorkRecordMap.get(proTeamName);
				}
				leftWorkRecordListOfPro.add(jcFixrec);
			}
		}
		return leftWorkRecordMap;
	}

	@Override
	public Map<String, List<JCZXFixRec>> listZXLeftWorkRecord(String jwdCode, Integer rjhmId) {
		Map<String, List<JCZXFixRec>> leftWorkRecordMap = new HashMap<String, List<JCZXFixRec>>();
		List<JCZXFixRec> leftWorkRecordList = recorderDao.listZXLeftWorkRecord(jwdCode, rjhmId);
		List<JCZXFixRec> leftWorkRecordListOfPro = null;
		for (JCZXFixRec jCZXFixRec : leftWorkRecordList) {
			Long bzId = jCZXFixRec.getBzId();
			DictProTeam dicProTeam = commonDao.findDictProTeamById(jwdCode, bzId);
			String proTeamName = dicProTeam.getProteamname();
			if (jCZXFixRec.getFixEmp() == null || jCZXFixRec.getLead() == null
					|| (jCZXFixRec.getItemCtrlQi() == 1 && jCZXFixRec.getQi() == null)
					|| (jCZXFixRec.getItemCtrlTech() == 1 && jCZXFixRec.getTeachName() == null)
					|| (jCZXFixRec.getItemCtrlComld() == 1 && jCZXFixRec.getCommitLead() == null)
					|| (jCZXFixRec.getItemCtrlAcce() == 1 && jCZXFixRec.getAcceptEr() == null)) {
				if (leftWorkRecordMap.get(proTeamName) == null) {
					leftWorkRecordListOfPro = new ArrayList<JCZXFixRec>();
					leftWorkRecordMap.put(proTeamName, leftWorkRecordListOfPro);
				} else {
					leftWorkRecordListOfPro = leftWorkRecordMap.get(proTeamName);
				}
				leftWorkRecordListOfPro.add(jCZXFixRec);
			}
		}
		return leftWorkRecordMap;
	}

	@Override
	public PageModel findPJDynamicInfo(String jwdCode, String jcsType, String firstUnitId,
			String pjName, String pjNum, String pjStatus, String storePosition, String getOnNum,
			String bzId) {
		return recorderDao.findPJDynamicInfo(jwdCode, jcsType, firstUnitId, pjName, pjNum,
				pjStatus, storePosition, getOnNum, bzId);
	}

	@Override
	public PJDynamicInfo findPJDynamicInfoByPJNum(String jwdCode, String pjNum) {
		return recorderDao.findPJDynamicInfoByPJNum(jwdCode, pjNum);
	}

	@Override
	public PJDynamicInfo findPJDynamicInfoById(String jwdCode, Long pjdId) {
		return recorderDao.findPJDynamicInfoById(jwdCode, pjdId);
	}

	@Override
	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid) {
		return recorderDao.findPJFixRecord(jwdCode, pjdid);
	}

	@Override
	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid, Long recId) {
		return recorderDao.findPJFixRecord(jwdCode, pjdid, recId);
	}

	@Override
	public List<String> findXXPJNums(String jwdCode, int rjhmId, int type) {
		return recorderDao.findXXPJNums(jwdCode, rjhmId, type);
	}

	@Override
	public List<PJStaticInfo> findPJStaticInfoByXXPJNums(String jwdCode, List<String> pjNums) {
		return recorderDao.findPJStaticInfoByXXPJNums(jwdCode, pjNums);
	}

	@Override
	public List<PJDynamicInfo> findPJDynamicInfoByPjNums(String jwdCode, Long pjsid,
			List<String> pjNums) {
		return recorderDao.findPJDynamicInfoByPjNums(jwdCode, pjsid, pjNums);
	}

	@Override
	public List<PJStaticInfo> findPJStaticInfo(String jwdCode, String jcType, Long bzId) {
		return recorderDao.findPJStaticInfo(jwdCode, jcType, bzId);
	}

	@Override
	public List<String> findPjNums(String jwdCode, int rjhmId) {
		return recorderDao.findPjNums(jwdCode, rjhmId);
	}

	@Override
	public int findDynamicInZxFixItem(String jwdCode, Long pjsid, List<String> pjNums) {
		return recorderDao.findDynamicInZxFixItem(jwdCode, pjsid, pjNums);
	}

	@Override
	public List<DictFirstUnit> findDictFistUnitForDatePlan(String jwdCode, int rjhmId) {
		List<String> fittingNums = recorderDao.findFittingNumberForFixRec(jwdCode, rjhmId);
		if (null != fittingNums && !fittingNums.isEmpty()) {
			return recorderDao.forFittingNumbers(jwdCode, fittingNums);
		}
		return null;
	}

	@Override
	public List<JCZXFixRec> listJCZXFixRec(String jwdCode, int rjhmId, Long bzId) {
		return recorderDao.listJCZXFixRec(jwdCode, rjhmId, bzId);
	}

	@Override
	public PJDynamicInfo findDynamicInfoByPjNum(String jwdCode, String pjNum) {
		return recorderDao.findDynamicInfoByPjNum(jwdCode, pjNum);
	}

	@Override
	public PJDynamicInfo findPJDynamicInfoByDID(String jwdCode, Long pjdid) {
		return recorderDao.findPJDynamicInfoByDID(jwdCode, pjdid);
	}

	@Override
	public PJFixRecord findPJFixRecordByRjhmId(String jwdCode, Integer rjhmId, Long pjdid) {
		return recorderDao.findPJFixRecordByRjhmId(jwdCode, rjhmId, pjdid);
	}

	@Override
	public List<PJFixRecord> findPJFixRecordByDid(String jwdCode, Long pjdid, Long pjRecId,
			Long bzId) {
		return recorderDao.findPJFixRecordByDid(jwdCode, pjdid, pjRecId, bzId);
	}

	@Override
	public List<DictFirstUnit> findDictFirstUnitByType(String jwdCode, String jcStype) {
		return recorderDao.findDictFirstUnitByType(jwdCode, jcStype);
	}

	@Override
	public Long countJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit) {
		return recorderDao.countJCZXFixRec(jwdCode, datePlanPri, fristUnit);
	}

	@Override
	public List<JCZXFixRec> findJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit) {
		return recorderDao.findJCZXFixRec(jwdCode, datePlanPri, fristUnit);
	}

	@Override
	public String findFirstUnitName(String jwdCode, Long staticInfo) {
		return recorderDao.findFirstUnitName(jwdCode, staticInfo);
	}

	@Override
	public List<PJFixRecord> forNumAndDataPlan(String jwdCode, String pjNum) {
		return recorderDao.forNumAndDataPlan(jwdCode, pjNum);
	}

	@Override
	public List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid, int rjhmId) {
		List<String> fittingNums = recorderDao.findFittingNumberForFixRec(jwdCode, rjhmId);
		return recorderDao.forFirstUnit(jwdCode, firstunitid, fittingNums);
	}

	@Override
	public PJStaticInfo findPJStaticInfoById(String jwdCode, Long pjsid) {
		return recorderDao.findPJStaticInfoById(jwdCode, pjsid);
	}

	@Override
	public List<PJFixItem> findPjFixItemByStaticId(String jwdCode, long staticId) {
		return recorderDao.findPjFixItemByStaticId(jwdCode, staticId);
	}

	@Override
	public List<JtPreDict> countReportDetailInfo(String jwdCode, String st, String et, String type,
			String jcType, String jcNum, String emp, String fixEmp) {
		return recorderDao.countReportDetailInfo(jwdCode, st, et, type, jcType, jcNum, emp, fixEmp);
	}

	@Override
	public Map<CountPartModel, List<CountPartModel>> countPartRate2(String jwdCode, String jcType) {
		return recorderDao.countPartRate2(jwdCode, jcType);
	}

	@Override
	public List<DatePlanPri> queryLocoFixRecOnNum(String xcxc, String st, String et,
			String jwdCode, String type) {
		return recorderDao.queryLocoFixRecOnNum(xcxc, st, et, jwdCode, type);
	}

	@Override
	public List<MainPlanDetail> queryLocoFixRecOnPlan(String xcxc, String st, String et,
			String jwdCode) {
		return recorderDao.queryLocoFixRecOnPlan(xcxc, st, et, jwdCode);
	}

	@Override
	public List<Map<String, Object>> countReportCategoryType(String st, String et, String jwdCode)
			throws Exception {
		SimpleDateFormat YMD = SimplDateFormatUtils.createYMDFormat();
		List<Map<String, Object>> reportList = recorderDao.countReportCategoryType(st, et, jwdCode);
		for (Map<String, Object> map : reportList) {
			String date = (String) map.get("date");
			String dayOfWeek = this.getTodayWeekNick(YMD.parse(date));
			map.put("dayOfWeek", date + " " + dayOfWeek);
			map.put("st", date);
			map.put("et", date);
		}
		return reportList;
	}

	private String getTodayWeekNick(Date date) {
		String todayWeekNick = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
		switch (dayNum) {
		case 1:
			todayWeekNick = "星期日";
			break;
		case 2:
			todayWeekNick = "星期一";
			break;
		case 3:
			todayWeekNick = "星期二";
			break;
		case 4:
			todayWeekNick = "星期三";
			break;
		case 5:
			todayWeekNick = "星期四";
			break;
		case 6:
			todayWeekNick = "星期五";
			break;
		case 7:
			todayWeekNick = "星期六";
			break;
		default:
			todayWeekNick = "";
		}
		return todayWeekNick;
	}

	@Override
	public List<Map<String, Object>> countReportCategoryJctype(String st, String et, String jwdCode)
			throws Exception {
		SimpleDateFormat YMD = SimplDateFormatUtils.createYMDFormat();
		List<Map<String, Object>> reportList = recorderDao.countReportCategoryJctype(st, et,
				jwdCode);
		for (Map<String, Object> map : reportList) {
			String date = (String) map.get("date");
			String dayOfWeek = this.getTodayWeekNick(YMD.parse(date));
			map.put("dayOfWeek", date + " " + dayOfWeek);
			map.put("st", date);
			map.put("et", date);
		}
		return reportList;
	}

	@Override
	public List<Map<String, Object>> countPlans(String st, String et) throws Exception {
		SimpleDateFormat YMD = SimplDateFormatUtils.createYMDFormat();
		List<Map<String, Object>> planList = recorderDao.countPlans(st, et);
		for (Map<String, Object> map : planList) {
			String date = (String) map.get("date");
			String dayOfWeek = this.getTodayWeekNick(YMD.parse(date));
			map.put("dayOfWeek", date + " " + dayOfWeek);
			map.put("st", date);
			map.put("et", date);
		}
		return planList;
	}

	@Override
	public List<MainPlanDetail> countPlansDetail(String st, String et, String jwdCode,
			String isCash, String jcType, String jcNum) {
		return recorderDao.countPlansDetail(st, et, jwdCode, isCash, jcType, jcNum);
	}

	@Override
	public MSArea2DChartSet createMonthMSArea2D(String st, String et) {
		// 创建chartSet
		MSArea2DChartSet msArea2DChartSet = new MSArea2DChartSet();
		// 创建chart
		MSArea2DChart msArea2DChart = new MSArea2DChart();
		// 创建categoriesList
		List<MSArea2DCategories> categoriesList = new ArrayList<MSArea2DCategories>();
		MSArea2DCategories categories = new MSArea2DCategories();
		// 创建categoryList
		List<MSArea2DCategory> categoryList = new ArrayList<MSArea2DCategory>();
		
		// 创建dataSetList
		List<MSData2DataSet> dataSetList = new ArrayList<MSData2DataSet>();
		MSData2DataSet gzDataSet = new MSData2DataSet();
		gzDataSet.setSeriesName("广州");
		List<MSData2Data> gzDataList = new ArrayList<MSData2Data>();
		dataSetList.add(gzDataSet);
		MSData2DataSet lcDataSet = new MSData2DataSet();
		lcDataSet.setSeriesName("龙川");
		List<MSData2Data> lcDataList = new ArrayList<MSData2Data>();
		dataSetList.add(lcDataSet);
		MSData2DataSet ssDataSet = new MSData2DataSet();
		ssDataSet.setSeriesName("三水");
		List<MSData2Data> ssDataList = new ArrayList<MSData2Data>();
		dataSetList.add(ssDataSet);
		MSData2DataSet zzDataSet = new MSData2DataSet();
		zzDataSet.setSeriesName("株洲");
		List<MSData2Data> zzDataList = new ArrayList<MSData2Data>();
		dataSetList.add(zzDataSet);
		MSData2DataSet csDataSet = new MSData2DataSet();
		csDataSet.setSeriesName("长沙");
		List<MSData2Data> csDataList = new ArrayList<MSData2Data>();
		dataSetList.add(csDataSet);
		MSData2DataSet hhDataSet = new MSData2DataSet();
		hhDataSet.setSeriesName("怀化");
		List<MSData2Data> hhDataList = new ArrayList<MSData2Data>();
		dataSetList.add(hhDataSet);
		
		
		List<Map<String, Object>> dataList = recorderDao.countMonthRateData(st, et);
		for(int i = 0; i < dataList.size(); i++) {
			Map<String, Object> dataMap = dataList.get(i);
			String month = (String) dataMap.get("month");
			String gz = (String) dataMap.get("gz");
			String lc = (String) dataMap.get("lc");
			String ss = (String) dataMap.get("ss");
			String zz = (String) dataMap.get("zz");
			String cs = (String) dataMap.get("cs");
			String hh = (String) dataMap.get("hh");
			// 创建category
			MSArea2DCategory category = new MSArea2DCategory();
			category.setLabel(month);
			categoryList.add(category);
			// 创建data
			MSData2Data gzData = new MSData2Data();
			gzData.setValue(gz);
			gzDataList.add(gzData);
			MSData2Data lcData = new MSData2Data();
			lcData.setValue(lc);
			lcDataList.add(lcData);
			MSData2Data ssData = new MSData2Data();
			ssData.setValue(ss);
			ssDataList.add(ssData);
			MSData2Data zzData = new MSData2Data();
			zzData.setValue(zz);
			zzDataList.add(zzData);
			MSData2Data csData = new MSData2Data();
			csData.setValue(cs);
			csDataList.add(csData);
			MSData2Data hhData = new MSData2Data();
			hhData.setValue(hh);
			hhDataList.add(hhData);
		}
		// 设置chart
		msArea2DChartSet.setChart(msArea2DChart);
		// 设置categories
		categories.setCategory(categoryList);
		categoriesList.add(categories);
		msArea2DChartSet.setCategories(categoriesList);
		
		gzDataSet.setData(gzDataList);
		lcDataSet.setData(lcDataList);
		ssDataSet.setData(ssDataList);
		zzDataSet.setData(zzDataList);
		csDataSet.setData(csDataList);
		hhDataSet.setData(hhDataList);
		msArea2DChartSet.setDataset(dataSetList);
		
		return msArea2DChartSet;
	}
}

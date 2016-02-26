package com.major.recorder.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.major.admin.service.CommonServiceI;
import com.major.base.action.BaseAction;
import com.major.base.page.PageModel;
import com.major.base.util.SimplDateFormatUtils;
import com.major.base.util.fusioncharts.MSArea2DChartSet;
import com.major.base.vo.Contains;
import com.major.base.vo.CountPartModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.DictArea;
import com.major.base.vo.DictFirstUnit;
import com.major.base.vo.DictJcStype;
import com.major.base.vo.DictProTeam;
import com.major.base.vo.ItemRelation;
import com.major.base.vo.JCFixrec;
import com.major.base.vo.JCQZFixRec;
import com.major.base.vo.JCZXFixItem;
import com.major.base.vo.JCZXFixRec;
import com.major.base.vo.JcExpRec;
import com.major.base.vo.JcType;
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
import com.major.recorder.service.RecorderServiceI;

public class RecorderAction extends BaseAction {

	private static final long serialVersionUID = -653567514234623952L;

	/** request */
	private HttpServletRequest request = ServletActionContext.getRequest();

	private RecorderServiceI recorderService;

	private CommonServiceI commonService;

	@Resource
	public void setRecorderService(RecorderServiceI recorderService) {
		this.recorderService = recorderService;
	}

	@Resource
	public void setCommonService(CommonServiceI commonService) {
		this.commonService = commonService;
	}

	public String countFixRec() throws Exception {
		String now = YMD.format(new Date());
		String xcxc = request.getParameter("xcxc");
		String st = (null == request.getParameter("st")) ? now : request.getParameter("st");
		String et = (null == request.getParameter("et")) ? now : request.getParameter("et");
		String jwdCode = (null == request.getParameter("jwdCode")) ? "0801" : request.getParameter("jwdCode");
		String jcNum = request.getParameter("jcNum");
		String itemName = request.getParameter("itemName");
		List<DatePlanPri> planPris = recorderService.queryLocoFixRec(itemName, xcxc, jcNum, st, et, jwdCode);
		List<DictArea> areas = commonService.queryDictAreaList();
		request.setAttribute("planPris", planPris);
		request.setAttribute("areas", areas);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("xcxc", xcxc);
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		request.setAttribute("jcNum", jcNum);
		request.setAttribute("itemName", itemName);
		return "countFixRec";
	}
	
	public String findFixRecOnNum() throws Exception {
		String now = YMD.format(new Date());
		String xcxc = request.getParameter("xcxc");
		String st = (null == request.getParameter("st")) ? now : request.getParameter("st");
		String et = (null == request.getParameter("et")) ? now : request.getParameter("et");
		String jwdCode = (null == request.getParameter("jwdCode")) ? "0801" : request.getParameter("jwdCode");
		String type = request.getParameter("type");//1：实际 2：未兑现
		List<DatePlanPri> planPris = recorderService.queryLocoFixRecOnNum(xcxc, st, et, jwdCode, type);
		request.setAttribute("planPris", planPris);
		return "findRecOnNum";
	} 
	
	public String findFixRecOnPlan() throws Exception {
		String now = YMD.format(new Date());
		String xcxc = request.getParameter("xcxc");
		String st = (null == request.getParameter("st")) ? now : request.getParameter("st");
		String et = (null == request.getParameter("et")) ? now : request.getParameter("et");
		String jwdCode = (null == request.getParameter("jwdCode")) ? "0801" : request.getParameter("jwdCode");
		List<MainPlanDetail> planPris = recorderService.queryLocoFixRecOnPlan(xcxc, st, et, jwdCode);
		request.setAttribute("planPris", planPris);
		return "fixRecOnPlan";
	}

	public String countPartRate() throws Exception {
		String jwdCode = (null == request.getParameter("jwdCode")) ? "0801" : request.getParameter("jwdCode");
		String jcType = (null == request.getParameter("jcType")) ? "SS3B" : request.getParameter("jcType");
		List<DictArea> areas = commonService.queryDictAreaList();
		List<JcType> types = commonService.queryDictJcTypeList();
		Map<CountPartModel, List<CountPartModel>> pjCountMap = recorderService.countPartRate2(jwdCode, jcType);
		request.setAttribute("pjCountMap", pjCountMap);
		request.setAttribute("areas", areas);
		request.setAttribute("types", types);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("jcType", jcType);
		return "countPartRate";
	}

	public String countReport() throws Exception {
		Date dateTimeNow = new Date();
		Calendar calendar = Calendar.getInstance();
		String timeNow = YMD.format(dateTimeNow);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		Date dateWeekAgo = calendar.getTime();
		String weekAgo = YMD.format(dateWeekAgo);
		String st = (null == request.getParameter("st") ? weekAgo : request.getParameter("st"));
		String et = (null == request.getParameter("et") ? timeNow : request.getParameter("et"));

		List<DictArea> areas = commonService.queryDictAreaList();
		List<Map<String, Object>> reports = recorderService.countReportInfo(st, et);
		request.setAttribute("reports", reports);
		request.setAttribute("areas", areas);
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		return "countReport";
	}
	
	public String countReportCategoryShow() throws Exception {
		Date dateTimeNow = new Date();
		Calendar calendar = Calendar.getInstance();
		String timeNow = YMD.format(dateTimeNow);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		Date dateWeekAgo = calendar.getTime();
		String weekAgo = YMD.format(dateWeekAgo);
		String st = (null == request.getParameter("st") ? weekAgo : request.getParameter("st"));
		String et = (null == request.getParameter("et") ? timeNow : request.getParameter("et"));
		String jwdCode = request.getParameter("jwdCode");
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		request.setAttribute("jwdCode", jwdCode);
		return "countReportCategoryShow";
	}
	
	public String countReportCategoryType() throws Exception {
		Date dateTimeNow = new Date();
		Calendar calendar = Calendar.getInstance();
		String timeNow = YMD.format(dateTimeNow);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		Date dateWeekAgo = calendar.getTime();
		String weekAgo = YMD.format(dateWeekAgo);
		String st = (null == request.getParameter("st") ? weekAgo : request.getParameter("st"));
		String et = (null == request.getParameter("et") ? timeNow : request.getParameter("et"));
		String jwdCode = request.getParameter("jwdCode");
		List<Map<String, Object>> reports = recorderService.countReportCategoryType(st, et, jwdCode);
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("reports", reports);
		return "countReportCategoryType";
	}
	
	public String countReportCategoryJctype() throws Exception {
		Date dateTimeNow = new Date();
		Calendar calendar = Calendar.getInstance();
		String timeNow = YMD.format(dateTimeNow);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		Date dateWeekAgo = calendar.getTime();
		String weekAgo = YMD.format(dateWeekAgo);
		String st = (null == request.getParameter("st") ? weekAgo : request.getParameter("st"));
		String et = (null == request.getParameter("et") ? timeNow : request.getParameter("et"));
		String jwdCode = request.getParameter("jwdCode");
		List<Map<String, Object>> reports = recorderService.countReportCategoryJctype(st, et, jwdCode);
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("reports", reports);
		return "countReportCategoryJctype";
	}

	public String countReportDetail() throws Exception {
		Date dateTimeNow = new Date();
		Calendar calendar = Calendar.getInstance();
		String timeNow = YMD.format(dateTimeNow);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		Date dateWeekAgo = calendar.getTime();
		String weekAgo = YMD.format(dateWeekAgo);
		String st = (null == request.getParameter("st") ? weekAgo : request.getParameter("st").substring(0, 10));
		String et = (null == request.getParameter("et") ? timeNow : request.getParameter("et").substring(0, 10));
		String jwdCode = request.getParameter("jwdCode");
		String type = request.getParameter("type");
		String jcType = request.getParameter("jcType");
		String emp = request.getParameter("emp");
		String fixEmp = request.getParameter("fixEmp");
		String jcNum = request.getParameter("jcNum");
		List<DictJcStype> jcTypes = commonService.findDictJcStype();
		List<DictArea> areas = commonService.queryDictAreaList();
		List<JtPreDict> preDictRecs = recorderService.countReportDetailInfo(jwdCode, st, et, type, jcType, jcNum, emp, fixEmp);
		request.setAttribute("preDictRecs", preDictRecs);
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("areas", areas);
		request.setAttribute("jcType", jcType);
		request.setAttribute("jcNum", jcNum);
		request.setAttribute("jcTypes", jcTypes);
		request.setAttribute("emp", emp);
		request.setAttribute("fixEmp", fixEmp);
		return "countReportDetail";
	}

	public String countPlan() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String jwdCode = (null == request.getParameter("jwdCode")) ? "0801" : request.getParameter("jwdCode");
		String yearMonth = (null == request.getParameter("yeatMonth")) ? format.format(new Date()) : request
				.getParameter("yeatMonth");
		String weekCount = (null == request.getParameter("weekCount")) ? "1" : request.getParameter("weekCount");
		List<DictArea> areas = commonService.queryDictAreaList();
		PlanModel planModel = recorderService.countPlan(yearMonth, weekCount, jwdCode);
		request.setAttribute("planModel", planModel);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("yearMonth", yearMonth);
		request.setAttribute("weekCount", weekCount);
		request.setAttribute("areas", areas);
		return "countPlan";
	}
	
	public String countPlans() throws Exception {
		Date dateTimeNow = new Date();
		Calendar calendar = Calendar.getInstance();
		String timeNow = YMD.format(dateTimeNow);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		Date dateWeekAgo = calendar.getTime();
		String weekAgo = YMD.format(dateWeekAgo);
		String st = (null == request.getParameter("st") ? weekAgo : request.getParameter("st"));
		String et = (null == request.getParameter("et") ? timeNow : request.getParameter("et"));
		List<Map<String, Object>> plans = recorderService.countPlans(st, et);
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		request.setAttribute("plans", plans);
		return "countPlans";
	}
	
	/**
	 * 创建MSArea2D
	 */
	public String createMonthMSArea2DCharts() throws Exception {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 6);
		Date threeMonthBefore = calendar.getTime();
		SimpleDateFormat dateFormatUtils = SimplDateFormatUtils.createYMFormat();
		String st = (null == request.getParameter("st") || request.getParameter("st").length() > 7 ? dateFormatUtils.format(threeMonthBefore) : request.getParameter("st").substring(0, 7));
		String et = (null == request.getParameter("et") || request.getParameter("et").length() > 7? dateFormatUtils.format(now) : request.getParameter("et").substring(0, 7));
		MSArea2DChartSet msArea2DChart = recorderService.createMonthMSArea2D(st, et);
		String jsonData = JSON.toJSONStringWithDateFormat(msArea2DChart, "yyyy-MM-dd HH:mm:ss");
		request.setAttribute("jsonData", jsonData);
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		return "createMonthMSArea2DCharts";
	}
	
	public String countPlansDetail() throws Exception {
		Date dateTimeNow = new Date();
		Calendar calendar = Calendar.getInstance();
		String timeNow = YMD.format(dateTimeNow);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		Date dateWeekAgo = calendar.getTime();
		String weekAgo = YMD.format(dateWeekAgo);
		String st = (null == request.getParameter("st") ? weekAgo : request.getParameter("st"));
		String et = (null == request.getParameter("et") ? timeNow : request.getParameter("et"));
		String jwdCode = request.getParameter("jwdCode");
		String isCash = request.getParameter("isCash");
		String jcType = request.getParameter("jcType");
		String jcNum = request.getParameter("jcNum");
		List<MainPlanDetail> plans = recorderService.countPlansDetail(st, et, jwdCode, isCash, jcType, jcNum);
		List<DictArea> areas = commonService.queryDictAreaList();
		List<DictJcStype> jcTypes = commonService.findDictJcStype();
		request.setAttribute("st", st);
		request.setAttribute("et", et);
		request.setAttribute("plans", plans);
		request.setAttribute("areas", areas);
		request.setAttribute("jcType", jcType);
		request.setAttribute("jcTypes", jcTypes);
		request.setAttribute("jcNum", jcNum);
		request.setAttribute("jwdCode", jwdCode);
		return "countPlansDetail";
	}

	public String countJgPredict() throws Exception {
		String jwdCode = (null == request.getParameter("jwdCode")) ? "0801" : request.getParameter("jwdCode");
		request.setAttribute("jcAcounts", recorderService.findJgAcounts(jwdCode));
		request.setAttribute("result", recorderService.findJgItemCount(jwdCode));
		List<DictArea> areas = commonService.queryDictAreaList();
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("areas", areas);
		return "countJgPredict";
	}

	public String queryRecorder() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		Integer fristUnit = (null == request.getParameter("fristUnit")) ? 0 : Integer.parseInt(request
				.getParameter("fristUnit"));
		String xcxc = datePlan.getFixFreque();
		String jcStype = datePlan.getJcType();
		String flowval = this.commonService.findXcxc(xcxc, jwdCode);
		// 报活检修
		List<JtPreDict> preDictRecs = commonService.findJtPreDictPre(jwdCode, datePlan.getRjhmId());
		List<DictProTeam> bzs = null;
		SignedForFinish signed = null;
		List<JCQZFixRec> qzFixRecs = null;
		List<DictFirstUnit> units = null;
		String unitType = "";
		List<JCFixrec> fixRecs = null;
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		if (xcxc.startsWith("LX") || xcxc.startsWith("JG") || xcxc.startsWith("ZZ")) {// 临修加改整治
			bzs = recorderService.findAllProTeam(jwdCode, jcStype, 1);
			preDictRecs = recorderService.findJtPreDict(jwdCode, datePlan.getRjhmId());
			request.setAttribute("flowval", flowval);
			request.setAttribute("bzs", bzs);
			request.setAttribute("preDictRecs", preDictRecs);
			return "recorderLx";
		} else if (xcxc.startsWith("QZ") || xcxc.startsWith("CJ")) {// 秋整
			signed = commonService.findSignedForFinishByPlan(jwdCode, datePlan.getRjhmId());
			qzFixRecs = recorderService.findJCQZFixRec(jwdCode, datePlan.getRjhmId());
			request.setAttribute("signed", signed);
			request.setAttribute("qzFixRecs", qzFixRecs);
			request.setAttribute("flowval", flowval);
			request.setAttribute("bzs", bzs);
			return "recorderQz";
		} else {// 小辅修
			bzs = recorderService.findAllProTeam(jwdCode, jcStype, 1);
			String flag = commonService.getParameterValueById(Contains.IS_USE_REPORT_TEMPLATE);
			// 表示使用报表模板
			// 1、启用了报表模板 2、存在当前车型关联模板项目
			if (flag.equals("1") && commonService.countItemRelation(jcStype) > 0) {
				units = commonService.listFirstUnitsOfTemplate(jcStype);
				if (fristUnit != null && fristUnit != 0) {// 包含部件
					for (int i = 0; i < units.size(); i++) {
						if (units.get(i).getFirstunitid().intValue() == fristUnit) {
							unitType = units.get(i).getFirstunitname();
							break;
						}
					}
				} else {
					if (units.size() > 0) {
						fristUnit = units.get(0).getFirstunitid().intValue();
						unitType = units.get(0).getFirstunitname();
					}
				}
				List<ItemRelation> relations = commonService.listItemRelation(jwdCode, jcStype, xcxc, fristUnit,
						Integer.parseInt(rjhmId));
				request.setAttribute("itemRelation", relations);
				request.setAttribute("bzs", bzs);
				request.setAttribute("flowval", flowval);
				request.setAttribute("unitType", unitType);
				return "recorderXfStander";
			} else {
				units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.parseInt(rjhmId), 0);
				if (fristUnit != null && fristUnit != 0) {// 包含部件
					for (int i = 0; i < units.size(); i++) {
						if (units.get(i).getFirstunitid().intValue() == fristUnit) {
							unitType = units.get(i).getFirstunitname();
							break;
						}
					}
				} else {
					if (units.size() > 0) {
						fristUnit = units.get(0).getFirstunitid().intValue();
						unitType = units.get(0).getFirstunitname();
					}
				}
				fixRecs = recorderService.findJCFixrec(jwdCode, datePlan.getRjhmId(), unitType);
				request.setAttribute("units", units);
				request.setAttribute("fixRecs", fixRecs);
				request.setAttribute("flowval", flowval);
				request.setAttribute("bzs", bzs);
				request.setAttribute("unitType", unitType);
				return "recorderXf";
			}
		}
	}

	/**
	 * 中修记录展现
	 * 
	 * @return
	 */
	public String queryZxRecorder() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		Integer nodeId = datePlan.getNodeid();
		List<DictFirstUnit> temp = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.parseInt(rjhmId), 1);
		List<DictProTeam> bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = new ArrayList<DictFirstUnit>();
		for (int i = 0; i < temp.size(); i++) {
			if (recorderService.countJCZXFixRec(jwdCode, datePlan.getRjhmId(),
					Integer.valueOf(temp.get(i).getFirstunitid() + "")) > 0) {
				units.add(temp.get(i));
			}
		}
		String fristUnitStr = request.getParameter("fristUnit");
		Integer fristUnit = 0;
		if (StringUtils.isNotEmpty(fristUnitStr)) {
			fristUnit = Integer.parseInt(fristUnitStr);
		}
		String unitType = "";
		if (0 != fristUnit && null != fristUnit) {// 包含部件
			for (int i = 0; i < units.size(); i++) {
				if (units.get(i).getFirstunitid().intValue() == fristUnit) {
					unitType = units.get(i).getFirstunitname();
					break;
				}
			}
		} else {
			if (units.size() > 0) {
				fristUnit = units.get(0).getFirstunitid().intValue();
				unitType = units.get(0).getFirstunitname();
			}
		}
		List<JCZXFixItem> zxItemList = null;
		if (nodeId == Contains.ZX_FG_NODEID) {
			zxItemList = recorderService.findZXItem(jwdCode, Contains.ZX_CSZZ_NODEID, datePlan.getFixFreque(),
					datePlan.getJcType(), null, fristUnit);
		}
		List<JCZXFixRec> jczxFixRecs = recorderService.findJCZXFixRec(jwdCode, datePlan.getRjhmId(), fristUnit);
		request.setAttribute("zxItemList", zxItemList);
		request.setAttribute("pjzy", recorderService.findDictFistUnitForDatePlan(jwdCode, datePlan.getRjhmId()));
		request.setAttribute("tsbzid", commonService.findDictProTeamByPY(jwdCode, Contains.TSZ_PY).getProteamid());
		request.setAttribute("jczxFixRecs", jczxFixRecs);
		request.setAttribute("unitType", unitType);
		request.setAttribute("bzs", bzs);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("units", units);
		return "recorderZx";
	}

	public String queryInfoByBz() throws Exception {
		String rjhmId = request.getParameter("rjhmId");
		String type = request.getParameter("type");
		String jwdCode = request.getParameter("jwdCode");
		Long teamId = (null == request.getParameter("teamId")) ? 0 : Long.parseLong(request.getParameter("teamId"));
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		String xcxc = datePlan.getFixFreque();
		String jcStype = datePlan.getJcType();
		String flowval = this.commonService.findXcxc(xcxc, jwdCode);
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, jcStype, 1);
		DictProTeam currentTeam = null;
		List<DictFirstUnit> units = null;
		List<JtPreDict> preDictRecs = null;
		List<JCQZFixRec> qzFixRecs = null;
		List<JCFixrec> fixRecs = null;
		if (teamId == 0) {
			if (bzs.size() > 0) {// 默认选择第一个班组
				currentTeam = bzs.get(0);
				teamId = currentTeam.getProteamid();
			}
		} else {
			for (int i = 0; i < bzs.size(); i++) {
				if (bzs.get(i).getProteamid().equals(teamId)) {
					currentTeam = bzs.get(i);
					break;
				}
			}
		}
		request.setAttribute("flowval", flowval);
		request.setAttribute("type", type);
		request.setAttribute("bzs", bzs);
		request.setAttribute("currentTeam", currentTeam);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		if ("QZ".equalsIgnoreCase(xcxc) || "CJ".equalsIgnoreCase(xcxc)) {
			qzFixRecs = recorderService.findJCQZFixrecByTeam(jwdCode, datePlan.getRjhmId(), teamId);
			request.setAttribute("qzFixRecs", qzFixRecs);
			return "recorderBzQz";
		} else if ("LX".equalsIgnoreCase(xcxc) || "JG".equalsIgnoreCase(xcxc) || "ZZ".equalsIgnoreCase(xcxc)) {
			preDictRecs = recorderService.findJtPreDictByFlag(jwdCode, datePlan.getRjhmId(), teamId, 3);
			request.setAttribute("preDictRecs", preDictRecs);
			return "recorderBzLx";
		} else {
			units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 0);
			preDictRecs = recorderService
					.findJtPreDictByFlag(jwdCode, datePlan.getRjhmId(), currentTeam.getProteamid());
			fixRecs = recorderService.findRecByProTeam(jwdCode, datePlan.getRjhmId(), teamId);
			request.setAttribute("units", units);
			request.setAttribute("preDictRecs", preDictRecs);
			request.setAttribute("fixRecs", fixRecs);
			return "recorderBz";
		}
	}

	public String getZxInfoByBZ() {
		String type = request.getParameter("type");
		Long teamId = (null == request.getParameter("teamId")) ? 0 : Long.parseLong(request.getParameter("teamId"));
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		String workFlag = request.getParameter("workFlag");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		Integer nodeId = datePlan.getNodeid();
		List<DictProTeam> bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(),
				Integer.parseInt(workFlag));
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 1);
		DictProTeam currentTeam = null;
		List<JCZXFixItem> zxItemList = null;
		List<JtPreDict> preDictRecs = null;
		List<JCZXFixRec> zxFixRecs = null;
		if (teamId == 0) {
			if (bzs.size() > 0) {// 默认选择第一个班组
				currentTeam = bzs.get(0);
				teamId = currentTeam.getProteamid();
			}
		} else {
			for (int i = 0; i < bzs.size(); i++) {
				if (bzs.get(i).getProteamid().equals(teamId)) {
					currentTeam = bzs.get(i);
					break;
				}
			}
		}
		if (nodeId == Contains.ZX_FG_NODEID) {
			zxItemList = recorderService.findZXItem(jwdCode, Contains.ZX_CSZZ_NODEID, datePlan.getFixFreque(),
					datePlan.getJcType(), teamId, null);
		}
		preDictRecs = recorderService.findJtPreDictByFlag(jwdCode, datePlan.getRjhmId(), currentTeam.getProteamid());
		zxFixRecs = recorderService.findZxRecByProTeam(jwdCode, Integer.parseInt(rjhmId), teamId, null);
		request.setAttribute("zxItemList", zxItemList);
		request.setAttribute("tsbzid", commonService.findDictProTeamByPY(jwdCode, Contains.TSZ_PY).getProteamid());
		request.setAttribute("units", units);
		request.setAttribute("preDictRecs", preDictRecs);
		request.setAttribute("zxFixRecs", zxFixRecs);
		request.setAttribute("type", type);
		request.setAttribute("currentTeam", currentTeam);
		request.setAttribute("bzs", bzs);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		return "recorderZxBz";
	}

	public String getInfoByJC() throws Exception {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		String unitType = request.getParameter("unitType");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		String xcxc = datePlan.getFixFreque();
		String flowval = this.commonService.findXcxc(xcxc, jwdCode);
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 0);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 0);
		List<JtPreDict> preDictRecs = null;
		List<JCQZFixRec> qzFixRecs = null;
		request.setAttribute("flowval", flowval);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		if (xcxc.startsWith("LX") || xcxc.startsWith("JG") || xcxc.startsWith("ZZ")) {// 临修加改
			preDictRecs = recorderService.findJtPreDict(jwdCode, datePlan.getRjhmId());
			request.setAttribute("preDictRecs", preDictRecs);
			return "info_jc_lx";
		} else if (xcxc.startsWith("QZ") || xcxc.startsWith("CJ")) {
			qzFixRecs = recorderService.findJCQZFixRec(jwdCode, datePlan.getRjhmId());
			request.setAttribute("qzFixRecs", qzFixRecs);
			return "info_jc_qz";
		} else {
			preDictRecs = commonService.findJtPreDictPre(jwdCode, datePlan.getRjhmId());
			PageModel fixRecs = recorderService.findJCFixrecLimited(jwdCode, datePlan.getRjhmId(), unitType);
			request.setAttribute("preDictRecs", preDictRecs);
			request.setAttribute("fixRecs", fixRecs);
			return "recorderJc";
		}
	}

	public String getZxInfoByJC() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		String xcxc = datePlan.getFixFreque();
		String flowval = this.commonService.findXcxc(xcxc, jwdCode);
		Integer nodeid = datePlan.getNodeid();
		List<DictProTeam> bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 1);
		List<JtPreDict> preDictRecs = commonService.findJtPreDictPre(jwdCode, datePlan.getRjhmId());
		PageModel zxFixRecs = recorderService.findJCZXFixRecLimited(jwdCode, Integer.parseInt(rjhmId));
		List<JCZXFixItem> zxItemList = null;
		if (nodeid == Contains.ZX_FG_NODEID) {
			zxItemList = recorderService.findZXItem(jwdCode, Contains.ZX_CSZZ_NODEID, datePlan.getFixFreque(),
					datePlan.getJcType(), null, null);
		}
		request.setAttribute("flowval", flowval);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		request.setAttribute("zxFixRecs", zxFixRecs);
		request.setAttribute("zxItemList", zxItemList);
		request.setAttribute("flowval", flowval);
		request.setAttribute("preDictRecs", preDictRecs);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("datePlan", datePlan);
		return "recorderZxJc";
	}

	/**
	 * 机车检修记录
	 * 
	 * @return
	 */
	public String showHistory() {
		String jcStype = request.getParameter("jcStype");
		String jwdCode = request.getParameter("jwdCode");
		String jcNum = request.getParameter("jcNum");
		List<DatePlanPri> list = recorderService.findJCHistory(jwdCode, jcNum, jcStype);
		request.setAttribute("list", list);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("jcNum", jcNum);
		request.setAttribute("jcStype", jcStype);
		return "recorderHis";
	}

	/**
	 * 查询机车所有的报活检修信息
	 * 
	 * @return
	 */
	public String getAllInfoPre() {
		int JTNnum = 0, FJNnum = 0, JGNnum = 0, LGNnum = 0;
		String type = request.getParameter("type");
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		String xcxc = datePlan.getFixFreque();
		List<DictProTeam> bzs = null;
		List<DictFirstUnit> units = null;
		if (datePlan.getProjectType() == 0) {// 小辅修
			bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(), 0);
			units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 0);
		} else {
			bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(), 1);
			units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 1);
		}
		// 查询修成修次
		String flowval = this.commonService.findXcxc(xcxc, jwdCode);
		// 报活检修
		List<JtPreDict> preDictRecs = null;
		DictProTeam currentTeam = null;
		Long teamId = 0L;
		if (teamId == 0) {
			if (bzs.size() > 0) {// 默认选择第一个班组
				currentTeam = bzs.get(0);
				teamId = currentTeam.getProteamid();
			}
		} else {
			for (int i = 0; i < bzs.size(); i++) {
				if (bzs.get(i).getProteamid().equals(teamId)) {
					currentTeam = bzs.get(i);
					break;
				}
			}
		}
		if ("work".equalsIgnoreCase(type)) {
			preDictRecs = recorderService
					.findJtPreDictByFlag(jwdCode, datePlan.getRjhmId(), currentTeam.getProteamid());

		} else {
			preDictRecs = commonService.findJtPreDictPre(jwdCode, datePlan.getRjhmId());
		}
		for (JtPreDict jtPreDict : preDictRecs) {
			if (jtPreDict.getType() == 0) {
				JTNnum++;
			} else if (jtPreDict.getType() == 1) {
				FJNnum++;
			} else if (jtPreDict.getType() == 2) {
				JGNnum++;
			} else if (jtPreDict.getType() == 6) {
				LGNnum++;
			}
		}
		request.setAttribute("JTNum", JTNnum);
		request.setAttribute("FJNum", FJNnum);
		request.setAttribute("JGNum", JGNnum);
		request.setAttribute("LGNum", LGNnum);
		request.setAttribute("flowval", flowval);
		request.setAttribute("preDictRecs", preDictRecs);
		request.setAttribute("units", units);
		request.setAttribute("bzs", bzs);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		return "recorderAllPre";
	}

	/**
	 * 交车竣工查询
	 * 
	 * @return
	 */
	public String searchJCjungong() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		String xcxc = datePlan.getFixFreque();
		String flowval = this.commonService.findXcxc(xcxc, jwdCode);
		List<DictProTeam> bzs = null;
		List<DictFirstUnit> units = null;
		if (datePlan.getProjectType() == 0) {// 小辅修
			bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(), 0);
			units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 0);
		} else {
			bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(), 1);
			units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 1);
		}
		request.setAttribute("rec", recorderService.getJCjungong(jwdCode, Integer.parseInt(rjhmId)));
		request.setAttribute("flowval", flowval);
		request.setAttribute("units", units);
		request.setAttribute("bzs", bzs);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		return "recorderFinish";
	}

	/**
	 * 交车记录查询
	 * 
	 * @return
	 */
	public String searchJcRec() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		String xcxc = datePlan.getFixFreque();
		String flowval = this.commonService.findXcxc(xcxc, jwdCode);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 0);
		List<YSJCRec> itemList = recorderService.listYSJCRec(jwdCode, Integer.parseInt(rjhmId));
		Map<String, List<YSJCRec>> itemListMap = new LinkedHashMap<String, List<YSJCRec>>();
		String classify = null;
		for (int i = 0; i < itemList.size(); i++) {
			classify = itemList.get(i).getClassify();
			if (itemListMap.get(classify) == null) {
				itemListMap.put(classify, new ArrayList<YSJCRec>());
			}
			itemListMap.get(classify).add(itemList.get(i));
		}
		request.setAttribute("itemListMap", itemListMap);
		request.setAttribute("rjhmId", rjhmId);
		request.setAttribute("flowval", flowval);
		request.setAttribute("units", units);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		return "recorderYsjc";
	}

	public String viewExperiment() {
		String rjhmId = request.getParameter("id");
		String jceiId = request.getParameter("jceiId");
		if (StringUtils.isNotEmpty(rjhmId) && StringUtils.isNotEmpty(jceiId)) {
			String jwdCode = request.getParameter("jwdCode");
			DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
			String xcxc = datePlan.getFixFreque();
			String flowval = this.commonService.findXcxc(xcxc, jwdCode);
			List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 1);
			List<DictProTeam> bzs = recorderService.findAllZxProTeam(jwdCode, datePlan.getJcType(), 1);
			List<JcExpRec> jcExpRecs = commonService.findJcExpRecs(jwdCode, Integer.parseInt(rjhmId),
					Integer.parseInt(jceiId));
			Map<String, Object> maps = new HashMap<String, Object>();
			for (JcExpRec jcExpRec : jcExpRecs) {
				maps.put(jcExpRec.getItemName(), jcExpRec.getExpStatus());
			}
			request.setAttribute("jceis", maps);
			request.setAttribute("experiment",
					commonService.findJcExperimentByDatePlanAndExpId(jwdCode, Integer.parseInt(rjhmId), jceiId));
			request.setAttribute("pjzy", commonService.findDictFistUnitForDatePlan(jwdCode, datePlan.getRjhmId()));
			request.setAttribute("flowval", flowval);
			request.setAttribute("units", units);
			request.setAttribute("bzs", bzs);
			request.setAttribute("datePlan", datePlan);
			Integer fristUnit = 0;
			if (null != fristUnit && fristUnit > 0) {
				request.setAttribute("pjFixRecs", commonService.forFirstUnit(jwdCode, fristUnit, datePlan.getRjhmId()));
			}
			if ("2".equals(jceiId)) {
				return "experimentWaterCooking";
			}
			if ("3".equals(jceiId)) {
				if (datePlan.getJcType().contains("DF")) {
					return "experimentDfTrialRun";
				} else {
					return "experimentSsTrialRun";
				}
			}
			if ("4".equals(jceiId)) {
				return "experimentLowHighPress";
			}
			if ("5".equals(jceiId)) {
				return "experimentCrownChakra";
			}
		}
		return null;
	}

	/**
	 * 查询油水化验记录
	 * 
	 * @return
	 */
	public String searchOilAssayRecorderDaily() {
		String jwdCode = request.getParameter("jwdCode");
		String rjhmId = request.getParameter("rjhmId");
		DatePlanPri datePlanPri = commonService.findDatePlanPriById(jwdCode, rjhmId);
		OilAssayPriRecorder recorder = recorderService.findOilAssayRecByDailyId(jwdCode, Integer.parseInt(rjhmId));

		Map<String, List<Map<String, Object>>> datas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> subItems = null;
		String jcType = datePlanPri.getJcType();

		List<OilAssayItem> items = recorderService.findOilAssayItemByJcType(jwdCode, jcType);
		for (OilAssayItem item : items) {
			int itemId = item.getReportItemId();
			String itemName = item.getReportItemDefin();
			Long recPriId = recorder.getRecPriId();
			if (null != recPriId) {
				subItems = recorderService.findOilDetailRecorderByRecId(jwdCode, itemId, recPriId,
						recorder.getJcsTypeVal());
			}
			datas.put(itemName, subItems);
		}
		request.setAttribute("datas", datas);
		request.setAttribute("recorder", recorder);
		request.setAttribute("datePlanPri", datePlanPri);
		return "recorderOil";
	}

	/**
	 * 小辅修各班组未完成记录单
	 * 
	 * @return
	 */
	public String listLeftWorkRecord() {
		String jwdCode = request.getParameter("jwdCode");
		String rjhmId = request.getParameter("rjhmId");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		Map<String, List<JCFixrec>> leftWorkRecordMap = recorderService.listLeftWorkRecord(jwdCode,
				Integer.valueOf(rjhmId));
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 0);
		request.setAttribute("leftWorkRecordMap", leftWorkRecordMap);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		request.setAttribute("jwdCode", jwdCode);
		return "leftWorkRecord";
	}

	/**
	 * 中修各班组未完成记录单
	 * 
	 * @return
	 */
	public String listZXLeftWorkRecord() {
		String jwdCode = request.getParameter("jwdCode");
		String rjhmId = request.getParameter("rjhmId");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		Map<String, List<JCZXFixRec>> leftWorkRecordMap = recorderService.listZXLeftWorkRecord(jwdCode,
				Integer.valueOf(rjhmId));
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.valueOf(rjhmId), 1);
		request.setAttribute("leftWorkRecordMap", leftWorkRecordMap);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		request.setAttribute("jwdCode", jwdCode);
		return "leftZxWorkRecord";
	}

	/**
	 * 进入动态配件信息列表
	 * 
	 * @return
	 */
	public String dyPJListInput() {
		String urlName = request.getParameter("urlName");
		String pjName = request.getParameter("pjName");
		String pjNum = request.getParameter("pjNum");
		String jcsType = request.getParameter("jcsType");
		String firstUnitId = request.getParameter("firstUnitId");
		String pjStatus = request.getParameter("pjStatus");
		String storePosition = request.getParameter("storePosition");
		String getOnNum = request.getParameter("getOnNum");
		String jwdCode = request.getParameter("jwdCode");
		if (StringUtils.isNotEmpty(pjName)) {
			try {
				pjName = URLDecoder.decode(pjName, "UTF-8").trim();// 十六制转为中文
				urlName = URLEncoder.encode(pjName, "UTF-8");// 中文转为十六制编码
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(pjNum)) {
			pjNum = pjNum.trim();
		}

		if (StringUtils.isNotEmpty(getOnNum)) {
			getOnNum = getOnNum.toUpperCase().trim();
		}
		PageModel pm = recorderService.findPJDynamicInfo(jwdCode, jcsType, firstUnitId, pjName, pjNum, pjStatus,
				storePosition, getOnNum, null);
		request.setAttribute("jcsTypes", commonService.findDictJcStype());
		request.setAttribute("pm", pm);
		request.setAttribute("urlName", urlName);
		request.setAttribute("getOnNum", getOnNum);
		request.setAttribute("pjName", pjName);
		request.setAttribute("pjNum", pjNum);
		request.setAttribute("jcsType", jcsType);
		request.setAttribute("storePosition", storePosition);
		request.setAttribute("pjStatus", pjStatus);
		request.setAttribute("jwdCode", jwdCode);
		return "recorderDynamic";
	}

	/**
	 * 查询配件的检修记录
	 * 
	 * @return
	 */
	public String findPJRecorder() {
		String pjnum = request.getParameter("pjnum");
		String pjId = request.getParameter("pjId");
		String jwdCode = request.getParameter("jwdCode");
		PJDynamicInfo pjDynamic = null;
		if (StringUtils.isNotEmpty(pjnum)) {
			pjDynamic = recorderService.findPJDynamicInfoByPJNum(jwdCode, pjnum);
		} else {
			pjDynamic = recorderService.findPJDynamicInfoById(jwdCode, Long.parseLong(pjId));
		}
		request.setAttribute("pjDy", pjDynamic);
		request.setAttribute("pjRecs", recorderService.findPJFixRecord(jwdCode, pjDynamic.getPjdid()));
		request.setAttribute("jwdCode", jwdCode);
		return "recorderPart";
	}

	/**
	 * 查看配件详细信息
	 * 
	 * @return
	 */
	public String findPjDetailRecorder() {
		Long recId = Long.parseLong(request.getParameter("recId"));
		String pjId = request.getParameter("pjId");
		String jwdCode = request.getParameter("jwdCode");
		request.setAttribute("tsbzid", commonService.findDictProTeamByPY(jwdCode, Contains.TSZ_PY).getProteamid());
		request.setAttribute("pjDy", recorderService.findPJDynamicInfoById(jwdCode, Long.parseLong(pjId)));
		request.setAttribute("pjDetailRecs", recorderService.findPJFixRecord(jwdCode, Long.parseLong(pjId), recId));
		request.setAttribute("jwdCode", jwdCode);
		return "recorderPartDetail";
	}

	/**
	 * 查询小辅修机车配件信息
	 * 
	 * @return
	 */
	public String findXXJCPJ() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		String fixFreque = datePlan.getFixFreque();
		List<String> xxPJNums = null;
		if (fixFreque.startsWith("LX") || fixFreque.startsWith("JG") || fixFreque.startsWith("ZZ")) {
			xxPJNums = recorderService.findXXPJNums(jwdCode, Integer.parseInt(rjhmId), 1);
		} else if (fixFreque.startsWith("QZ") || fixFreque.startsWith("CJ")) {
			xxPJNums = recorderService.findXXPJNums(jwdCode, Integer.parseInt(rjhmId), 2);
		} else {
			xxPJNums = recorderService.findXXPJNums(jwdCode, Integer.parseInt(rjhmId), 0);
		}

		Map<String, List<Map<String, Object>>> map = null;
		Map<String, Object> staticMap = null;
		if (xxPJNums != null && xxPJNums.size() > 0) {
			map = new HashMap<String, List<Map<String, Object>>>();
			List<PJStaticInfo> staticInfos = recorderService.findPJStaticInfoByXXPJNums(jwdCode, xxPJNums);
			for (PJStaticInfo staticInfo : staticInfos) {
				staticMap = new HashMap<String, Object>();
				staticMap.put("pjsid", staticInfo.getPjsid());
				staticMap.put("pjName", staticInfo.getPjName());
				staticMap.put("dynamicInfos",
						recorderService.findPJDynamicInfoByPjNums(jwdCode, staticInfo.getPjsid(), xxPJNums));
				Long firstUnitId = staticInfo.getFirstUnit();
				String firstUnitName = commonService.getFirstunitname(jwdCode, firstUnitId);
				if (map.get(firstUnitName) == null) {
					map.put(firstUnitName, new ArrayList<Map<String, Object>>());
				}
				map.get(firstUnitName).add(staticMap);
			}
		}
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.parseInt(rjhmId), 0);
		request.setAttribute("map", map);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		if (fixFreque.startsWith("LX") || fixFreque.startsWith("JG") || fixFreque.startsWith("QZ")
				|| fixFreque.startsWith("CJ") || fixFreque.startsWith("ZZ")) {
			return "recorderOtPj";
		}
		return "recorderXxPj";
	}

	/**
	 * 查询中修整车配件信息
	 * 
	 * @return
	 */
	public String findJCPJ() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		List<PJStaticInfo> staticInfos = recorderService.findPJStaticInfo(jwdCode, datePlan.getJcType(), null);
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		List<String> jcPjNums = recorderService.findPjNums(jwdCode, Integer.parseInt(rjhmId));// 查询机车所有填写的编号
		Map<String, Object> staticMap = null;
		for (PJStaticInfo staticInfo : staticInfos) {
			staticMap = new HashMap<String, Object>();
			staticMap.put("pjsid", staticInfo.getPjsid());
			staticMap.put("pjName", staticInfo.getPjName());
			// 需要上车的总动态配件数
			staticMap.put("amount", staticInfo.getAmount());
			// 中修上车的动态配件
			staticMap.put("zxAmount", recorderService.findDynamicInZxFixItem(jwdCode, staticInfo.getPjsid(), jcPjNums));
			staticMap.put("dynamicInfos",
					recorderService.findPJDynamicInfoByPjNums(jwdCode, staticInfo.getPjsid(), jcPjNums));
			Long firstUnitId = staticInfo.getFirstUnit();
			String firstUnitName = commonService.getFirstunitname(jwdCode, firstUnitId);
			if (map.get(firstUnitName) == null) {
				map.put(firstUnitName, new ArrayList<Map<String, Object>>());
			}
			map.get(firstUnitName).add(staticMap);
		}
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.parseInt(rjhmId), 1);
		request.setAttribute("pjzy", recorderService.findDictFistUnitForDatePlan(jwdCode, datePlan.getRjhmId()));
		request.setAttribute("map", map);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		return "recorderZxPj";
	}

	/**
	 * 查询整车探伤信息
	 */
	public String findJCTS() throws Exception {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.parseInt(rjhmId), 1);
		Long tsBzId = commonService.findDictProTeamByPY(jwdCode, Contains.TSZ_PY).getProteamid();
		// 车体 探伤项目
		List<JCZXFixRec> jcZXFixRecs = recorderService.listJCZXFixRec(jwdCode, Integer.parseInt(rjhmId), tsBzId);
		// 探伤配件
		List<PJStaticInfo> staticInfos = recorderService.findPJStaticInfo(jwdCode, datePlan.getJcType(), tsBzId);
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		List<String> jcPjNums = recorderService.findPjNums(jwdCode, Integer.parseInt(rjhmId));// 查询机车所有填写的编号
		Map<String, Object> staticMap = null;
		for (PJStaticInfo staticInfo : staticInfos) {
			staticMap = new HashMap<String, Object>();
			staticMap.put("pjsid", staticInfo.getPjsid());
			staticMap.put("pjName", staticInfo.getPjName());
			// 需要上车的总动态配件数
			staticMap.put("amount", staticInfo.getAmount());
			// 中修上车的动态配件
			staticMap.put("zxAmount", recorderService.findDynamicInZxFixItem(jwdCode, staticInfo.getPjsid(), jcPjNums));
			staticMap.put("dynamicInfos",
					recorderService.findPJDynamicInfoByPjNums(jwdCode, staticInfo.getPjsid(), jcPjNums));
			Long firstUnitId = staticInfo.getFirstUnit();
			String firstUnitName = commonService.getFirstunitname(jwdCode, firstUnitId);
			if (map.get(firstUnitName) == null) {
				map.put(firstUnitName, new ArrayList<Map<String, Object>>());
			}
			map.get(firstUnitName).add(staticMap);
		}
		request.setAttribute("jcZXFixRecs", jcZXFixRecs);
		request.setAttribute("map", map);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		return "recorderTsPj";
	}

	/**
	 * 根据配件编号查询配件信息
	 * 
	 * @return
	 */
	public String findPjRecordByPjNum() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		Long pjdid = request.getParameter("pjdid") == null ? null : Long.parseLong(request.getParameter("pjdid"));
		String pjNum = request.getParameter("pjNum");
		PJDynamicInfo pjDynamic = null;
		if (pjNum != null && !"".equals(pjNum)) {
			pjDynamic = recorderService.findDynamicInfoByPjNum(jwdCode, pjNum);
		} else {
			pjDynamic = recorderService.findPJDynamicInfoByDID(jwdCode, pjdid);
		}
		if (pjDynamic != null) {
			PJFixRecord record = recorderService.findPJFixRecordByRjhmId(jwdCode, Integer.parseInt(rjhmId),
					pjDynamic.getPjdid());
			List<PJFixRecord> pjFixRecs = recorderService.findPJFixRecordByDid(jwdCode, pjDynamic.getPjdid(),
					record.getPjRecId(), null);
			request.setAttribute("pjFixRecs", pjFixRecs);
		}
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.parseInt(rjhmId), 1);

		units = this.recorderService.findDictFirstUnitByType(jwdCode, datePlan.getJcType());
		request.setAttribute("pjzy", commonService.findDictFistUnitForDatePlan(jwdCode, datePlan.getRjhmId()));
		request.setAttribute("pjDynamic", pjDynamic);
		request.setAttribute("tsbzid", commonService.findDictProTeamByPY(jwdCode, Contains.TSZ_PY).getProteamid());
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		request.setAttribute("datePlan", datePlan);
		return "recorderPjNum";
	}

	public String zxFittingView() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		List<DictProTeam> bzs = recorderService.findAllProTeam(jwdCode, datePlan.getJcType(), 1);
		List<DictFirstUnit> units = recorderService.listFirstUnitsOfJCFixRec(jwdCode, Integer.parseInt(rjhmId), 1);
		String fristUnitStr = request.getParameter("fristUnit");
		Integer fristUnit = 0;
		if (StringUtils.isNotEmpty(fristUnitStr)) {
			fristUnit = Integer.parseInt(fristUnitStr);
		}
		String unitType = "";
		if (fristUnit != null && fristUnit != 0) {// 包含部件
			for (int i = 0; i < units.size(); i++) {
				if (units.get(i).getFirstunitid().intValue() == fristUnit) {
					unitType = units.get(i).getFirstunitname();
					break;
				}
			}
		} else {
			if (units.size() > 0) {
				fristUnit = units.get(0).getFirstunitid().intValue();
				unitType = units.get(0).getFirstunitname();
			}
		}
		request.setAttribute("pjzy", recorderService.findDictFistUnitForDatePlan(jwdCode, datePlan.getRjhmId()));
		List<PJFixRecord> pjfixRecs = null;
		String pjNum = request.getParameter("pjNum");
		if (null != pjNum && !"".equals(pjNum)) {
			PJDynamicInfo dynamicInfo = recorderService.findDynamicInfoByPjNum(jwdCode, pjNum);
			Long staticInfo = dynamicInfo.getPjStaticInfo();
			unitType = recorderService.findFirstUnitName(jwdCode, staticInfo);
			pjfixRecs = recorderService.forNumAndDataPlan(jwdCode, pjNum);
		} else {
			pjfixRecs = recorderService.forFirstUnit(jwdCode, fristUnit, datePlan.getRjhmId());
		}
		request.setAttribute("pjFixRecs", pjfixRecs);
		request.setAttribute("unitType", unitType);
		request.setAttribute("bzs", bzs);
		request.setAttribute("units", units);
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		return "recorderUnitPj";
	}

	// 显示多条数据记录
	public String zxFittingDetailViewNew() {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		Long staticId = Long.parseLong(request.getParameter("staticId"));
		PJStaticInfo pjStatic = recorderService.findPJStaticInfoById(jwdCode, staticId);
		List<String> jcPjNums = recorderService.findPjNums(jwdCode, Integer.parseInt(rjhmId));// 查询机车所有填写的编号
		// 查询所有的上车动态配件信息
		List<PJDynamicInfo> dynamicInfos = recorderService.findPJDynamicInfoByPjNums(jwdCode, staticId, jcPjNums);
		int amount = pjStatic.getAmount() == null ? 0 : pjStatic.getAmount().intValue();
		Map<PJDynamicInfo, List<PJFixRecord>> map = new HashMap<PJDynamicInfo, List<PJFixRecord>>();
		if (dynamicInfos != null && dynamicInfos.size() > 0) {
			for (PJDynamicInfo dynamicInfo : dynamicInfos) {
				PJFixRecord record = recorderService.findPJFixRecordByRjhmId(jwdCode, Integer.parseInt(rjhmId),
						dynamicInfo.getPjdid());
				List<PJFixRecord> pjFixRecs = null;
				if (record == null) {
					pjFixRecs = recorderService.findPJFixRecordByDid(jwdCode, dynamicInfo.getPjdid(), null, null);
				} else {
					pjFixRecs = recorderService.findPJFixRecordByDid(jwdCode, dynamicInfo.getPjdid(),
							record.getPjRecId(), null);
				}
				map.put(dynamicInfo, pjFixRecs);
			}
		}

		Map<String, List<PJFixItem>> map1 = new HashMap<String, List<PJFixItem>>();
		if (dynamicInfos.size() < amount) {
			int value = amount - dynamicInfos.size();
			for (int i = 0; i < value; i++) {
				List<PJFixItem> pjFixItems = recorderService.findPjFixItemByStaticId(jwdCode, staticId);
				map1.put(i + "", pjFixItems);
			}
		}
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("jwdCode", jwdCode);
		request.setAttribute("pjStatic", pjStatic);
		request.setAttribute("map", map);
		request.setAttribute("map1", map1);
		request.setAttribute("tsbzid", commonService.findDictProTeamByPY(jwdCode, Contains.TSZ_PY).getProteamid());
		return "recorderUnitDetail";
	}
	
}

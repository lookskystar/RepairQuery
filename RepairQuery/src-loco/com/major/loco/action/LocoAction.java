package com.major.loco.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.major.admin.service.CommonServiceI;
import com.major.base.action.BaseAction;
import com.major.base.vo.CountPlanModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.DictArea;
import com.major.base.vo.JCFlowRec;
import com.major.loco.service.LocoServiceI;

public class LocoAction extends BaseAction {

	private static final long serialVersionUID = -4556762490063562239L;
	private static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");

	/** request */
	private HttpServletRequest request = ServletActionContext.getRequest();

	private LocoServiceI locoService;

	private CommonServiceI commonService;

	@Resource
	public void setLocoService(LocoServiceI locoService) {
		this.locoService = locoService;
	}

	@Resource
	public void setCommonService(CommonServiceI commonService) {
		this.commonService = commonService;
	}

	/**
	 * 统计在修机车
	 * @return
	 * @throws Exception
	 */
	public String countLocoOnFix() throws Exception {
		Date now = new Date();
		String st = request.getParameter("st");
		String et = request.getParameter("et");
		List<CountPlanModel> countModels = locoService.CountLocoOnFix(st, et);
		request.setAttribute("countModels", countModels);
		request.setAttribute("st", (null == st)? YMD.format(now) : st);
		request.setAttribute("et", (null == et)? YMD.format(now) : et);
		return "countLocoOnFix";
	}

	/**
	 * 查询各机务段股道图信息
	 * @return
	 * @throws Exception
	 */
	public String findGdtInfo() throws Exception {
		String jwdCode = (null == request.getParameter("jwdCode")) ? "0801" : request.getParameter("jwdCode");
		List<DatePlanPri> list = locoService.findDatePlanPri(jwdCode);
		String jcJson = toJSON(list);
		DictArea area = commonService.queryDictAreaByCode(jwdCode);
		request.setAttribute("jcJson", jcJson);
		return area.getGdtUrl();

	}

	private String toJSON(List<DatePlanPri> list) {
		StringBuffer sb = new StringBuffer("[");
		DatePlanPri dp;
		for (int i = 0; i < list.size(); i++) {
			dp = list.get(i);
			sb.append("{'rjhmId':'" + dp.getRjhmId() + "','jcnum':'" + dp.getJcnum() + "','fixFreque':'"
					+ dp.getFixFreque() + "','gdh':'" + dp.getGdh() + "','statue':" + dp.getPlanStatue() + ",'tw':"
					+ dp.getTwh() + ", 'jwdcode':"+ dp.getJwdcode() +"}");
			if (i != (list.size() - 1)) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public String findPlanInfoDetail() throws Exception {
		String rjhmId = request.getParameter("rjhmId");
		String jwdCode = request.getParameter("jwdCode");
		// 查找日计划
		DatePlanPri datePlan = commonService.findDatePlanPriById(jwdCode, rjhmId);
		List<JCFlowRec> flowRecs = locoService.findJCFlowRecByDatePlan(jwdCode, rjhmId);
		if (datePlan.getProjectType() == 0) {// 小辅修
			// 试验情况判断
			String xcxc = datePlan.getFixFreque();
			if (isSuitXcxc(xcxc)) {
				Map<String, Object> map = locoService.findExpSituation(rjhmId);
				request.setAttribute("map", map);
			}
		}
		request.setAttribute("datePlan", datePlan);
		request.setAttribute("flowRecs", flowRecs);
		request.setAttribute("jwdCode", jwdCode);
		return "findPlanInfoDetail";
	}
	
	/**
     * 判断是否满足合适的修程修次
     * @return
     */
	private boolean isSuitXcxc(String xcxc){
		boolean flag=true;
		if(xcxc.startsWith("LX") || xcxc.startsWith("JG")|| xcxc.startsWith("ZZ")||xcxc.startsWith("QZ") || xcxc.startsWith("CJ")){
			flag=false;
		}
		return flag;
	}

}

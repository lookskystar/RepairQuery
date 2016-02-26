package com.major.loco.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.major.base.vo.CountPlanModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.DictArea;
import com.major.base.vo.JCFlowRec;
import com.major.loco.dao.LocoDaoI;

public class LocoDaoImpl implements LocoDaoI {

	/** spring jdbcTemplate 对象 */
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<CountPlanModel> CountLocoOnFix(String st, String et) {
		String areaSql = "select * from DICT_AREA";
		List<DictArea> areaList = jdbcTemplate.query(areaSql, new RowMapper<DictArea>() {
			public DictArea mapRow(ResultSet rs, int row) throws SQLException {
				DictArea area = new DictArea();
				area.setAreaid(rs.getString("areaId"));
				area.setName(rs.getString("name"));
				area.setIp(rs.getString("ip"));
				area.setDbname(rs.getString("dbname"));
				area.setLoginname(rs.getString("loginname"));
				area.setPassword(rs.getString("password"));
				area.setPy(rs.getString("py"));
				area.setJwdcode(rs.getString("jwdcode"));
				area.setDblinkname(rs.getString("dblinkname"));
				return area;
			}
		});
		//int planCount = 0;
		int factCount = 0;
		List<CountPlanModel> countModels = new ArrayList<CountPlanModel>();
		for (DictArea dictArea : areaList) {
			String areaName = dictArea.getName();
			String jwdCode = dictArea.getJwdcode();
			CountPlanModel model = new CountPlanModel();
			model.setAreaName(areaName);
			model.setJwdCode(jwdCode);
			// 大修
			model.setOh_plan(0);
			model.setOh_fact(0);
			model.setOh_unfill(0);
			// 中修计划
//			String fixSql = "select count(*) from MAINPLANDETAIL t ";
//			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
//				fixSql += " where t.PLANTIME between '"+ st +"' and '"+ et +"'" ;
//			}else {
//				fixSql += " where t.PLANTIME = (select to_char(sysdate,'yyyy-mm-dd') from dual)" ;
//			}
//			fixSql += " and t.XCXC like 'Z_' and t.XCXC <> 'ZZ'" + "and t.JWDCODE = '" + jwdCode + "'";
//			planCount = jdbcTemplate.queryForInt(fixSql);
//			model.setMid_plan(planCount);
//			model.setTo_plan(planCount + model.getTo_plan());
			// 中修实际
			String fixSql = "select count(*) from DATEPLAN_PRI t ";
			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
				fixSql += "where t.KCSJ between '"+ (st+" 00:00") +"' and '"+ (et+" 23:59") +"'" ;
			}else {
				fixSql += "where t.KCSJ between (select to_char(TRUNC(SYSDATE), 'yyyy-mm-dd hh24:mi') from dual)  and (select to_char(TRUNC(SYSDATE)+1-1/1440, 'yyyy-mm-dd hh24:mi') from dual)";
			}		
			fixSql += " and t.FIXFREQUE like 'Z_'" + "and t.FIXFREQUE <> 'ZZ'" + "and t.JWDCODE = '" + jwdCode + "' and t.planStatue < 2";
			factCount = jdbcTemplate.queryForInt(fixSql);
			model.setMid_fact(factCount);
			// 中修未兑现
			//model.setMid_unfill(Math.abs(planCount - factCount + model.getTo_unfill()));
			// 合计
			//model.setTo_plan(planCount + model.getTo_plan());
			model.setTo_fact(factCount + model.getTo_fact());
			//model.setTo_unfill(Math.abs(planCount - factCount) + model.getTo_unfill());

			// 小辅修计划
//			fixSql = "select count(*) from MAINPLANDETAIL t ";
//			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
//				fixSql += " where t.PLANTIME between '"+ st +"' and '"+ et +"'" ;
//			}else {
//				fixSql += " where t.PLANTIME = (select to_char(sysdate,'yyyy-mm-dd') from dual)" ;
//			}
//			fixSql += " and (t.XCXC like 'X%' or t.XCXC like 'F%'" + "or t.XCXC = 'CJ'" + "or t.XCXC = 'QZ'" + " or t.XCXC = 'JJ' or t.XCXC = 'YJ' or t.XCXC = 'BNJ' or t.XCXC = 'NJ') and t.JWDCODE = '"+ jwdCode +"'";
//			planCount = jdbcTemplate.queryForInt(fixSql);
//			model.setSm_plan(planCount);
//			model.setTo_plan(planCount + model.getTo_plan());
			// 小辅修实际
			fixSql = "select count(*) from DATEPLAN_PRI t ";
			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
				fixSql += " where t.KCSJ between '"+ (st+" 00:00") +"' and '"+ (et+" 23:59") +"'" ;
			}else {
				fixSql += " where t.KCSJ between (select to_char(TRUNC(SYSDATE), 'yyyy-mm-dd hh24:mi') from dual)  and (select to_char(TRUNC(SYSDATE)+1-1/1440, 'yyyy-mm-dd hh24:mi') from dual)";
			}	
			fixSql += " and (t.FIXFREQUE like 'X%'" + "or t.FIXFREQUE like 'F%'" + "or t.FIXFREQUE = 'CJ'";
			fixSql += " or t.FIXFREQUE = 'QZ' or t.FIXFREQUE = 'JJ' or t.FIXFREQUE = 'YJ' or t.FIXFREQUE = 'BNJ' or t.FIXFREQUE = 'NJ')" + "and t.JWDCODE = '" + jwdCode + "' and t.planStatue < 2";
			factCount = jdbcTemplate.queryForInt(fixSql);
			model.setSm_fact(factCount);
			// 小辅修未兑现
			//model.setSm_unfill(Math.abs(planCount - factCount));
			// 合计
			//model.setTo_plan(planCount + model.getTo_plan());
			model.setTo_fact(factCount + model.getTo_fact());
			//model.setTo_unfill(Math.abs(planCount - factCount) + model.getTo_unfill());

			// 临修计划
//			fixSql = "select count(*) from MAINPLANDETAIL t ";
//			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
//				fixSql += " where t.PLANTIME between '"+ st +"' and '"+ et +"'" ;
//			}else {
//				fixSql += " where t.PLANTIME = (select to_char(sysdate,'yyyy-mm-dd') from dual)" ;
//			}
//			fixSql += " and (t.XCXC = 'LX' or t.XCXC = 'JG'" + " ) and t.JWDCODE = '" + jwdCode + "'";
//			planCount = jdbcTemplate.queryForInt(fixSql);
//			model.setTemp_plan(planCount);
//			model.setTo_plan(planCount + model.getTo_plan());
			// 临修实际
			fixSql = "select count(*) from DATEPLAN_PRI t ";
			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
				fixSql += " where t.KCSJ between '"+ (st+" 00:00") +"' and '"+ (et+" 23:59") +"'" ;
			}else {
				fixSql += " where t.KCSJ between (select to_char(TRUNC(SYSDATE), 'yyyy-mm-dd hh24:mi') from dual)  and (select to_char(TRUNC(SYSDATE)+1-1/1440, 'yyyy-mm-dd hh24:mi') from dual)";
			}
			fixSql += " and (t.FIXFREQUE = 'LX'" + "or t.FIXFREQUE = 'JG'" + " ) and t.JWDCODE = '" + jwdCode + "' and t.planStatue < 2";
			factCount = jdbcTemplate.queryForInt(fixSql);
			model.setTemp_fact(factCount);
			// 临修未兑现
			//model.setTemp_unfill(Math.abs(planCount - factCount));
			// 合计
			//model.setTo_plan(planCount + model.getTo_plan());
			model.setTo_fact(factCount + model.getTo_fact());
			//model.setTo_unfill(Math.abs(planCount - factCount) + model.getTo_unfill());

			// 其它计划
//			fixSql = "select count(*) from MAINPLANDETAIL t ";
//			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
//				fixSql += "where t.PLANTIME between '"+ st +"' and '"+ et +"'" ;
//			}else {
//				fixSql += "where t.PLANTIME = (select to_char(sysdate,'yyyy-mm-dd') from dual)" ;
//			}
//			fixSql +=  " and (t.XCXC = 'ZQZZ' or t.XCXC = 'ZZ'" + " ) and t.JWDCODE = '" + jwdCode + "'";
//			planCount = jdbcTemplate.queryForInt(fixSql);
//			model.setOt_plan(planCount);
//			model.setTo_plan(planCount + model.getTo_plan());
			// 其它实际
			fixSql = "select count(*) from DATEPLAN_PRI t ";
			if(StringUtils.isNotEmpty(st) && StringUtils.isNotBlank(et)){
				fixSql += " where t.KCSJ between '"+ (st+" 00:00") +"' and '"+ (et+" 23:59") +"'" ;
			}else {
				fixSql += " where t.KCSJ between (select to_char(TRUNC(SYSDATE), 'yyyy-mm-dd hh24:mi') from dual)  and (select to_char(TRUNC(SYSDATE)+1-1/1440, 'yyyy-mm-dd hh24:mi') from dual)";
			}
			fixSql += " and (t.FIXFREQUE = 'ZQZZ'" + "or t.FIXFREQUE = 'ZZ'" + ") and t.JWDCODE = '" + jwdCode + "' and t.planStatue < 2";
			factCount = jdbcTemplate.queryForInt(fixSql);
			model.setOt_fact(factCount);
			// 其它未兑现
			//model.setOt_unfill(Math.abs(planCount - factCount));
			// 合计
			//model.setTo_plan(planCount + model.getTo_plan());
			model.setTo_fact(factCount + model.getTo_fact());
			//model.setTo_unfill(Math.abs(planCount - factCount) + model.getTo_unfill());
			countModels.add(model);
		}
		return countModels;
	}

	public List<DatePlanPri> findDatePlanPri(String jwdCode) {
		String sql = "select * from DATEPLAN_PRI where planStatue!=3 and jwdcode = '" + jwdCode + "'";
		List<DatePlanPri> planPriList = jdbcTemplate.query(sql, new RowMapper<DatePlanPri>() {
			public DatePlanPri mapRow(ResultSet rs, int row) throws SQLException {
				DatePlanPri planPri = new DatePlanPri();
				planPri.setRjhmId(rs.getInt("RJHMID"));
				planPri.setJcType(rs.getString("JCTYPE"));
				planPri.setJcnum(rs.getString("JCNUM"));
				planPri.setFixFreque(rs.getString("FIXFREQUE"));
				planPri.setKcsj(rs.getString("KCSJ"));
				planPri.setJhqjsj(rs.getString("JHQJSJ"));
				planPri.setJhjcsj(rs.getString("JHJCSJ"));
				planPri.setZhiJian(rs.getString("ZHIJIAN"));
				planPri.setGongZhang(rs.getString("GONGZHANG"));
				planPri.setPlanStatue(rs.getInt("PLANSTATUE"));
				planPri.setGdh(rs.getString("GDH"));
				planPri.setTwh(rs.getString("TWH"));
				planPri.setZdr(rs.getString("ZDR"));
				planPri.setZdsj(rs.getString("ZDSJ"));
				planPri.setNodeid(rs.getInt("NODEID"));
				planPri.setYanShou(rs.getString("YANSHOU"));
				planPri.setJiShu(rs.getString("JISHU"));
				planPri.setProjectType(rs.getInt("PROJECTTYPE"));
				planPri.setProteamId(rs.getLong("BANZU"));
				planPri.setSjqjsj(rs.getString("SJQJSJ"));
				planPri.setSjjcsj(rs.getString("SJJCSJ"));
				planPri.setComments(rs.getString("COMMENTS"));
				planPri.setWorkTeam(rs.getString("WORKTEAM"));
				planPri.setJwdcode(rs.getString("JWDCODE"));
				return planPri;
			}
		});
		return planPriList;
	}

	@Override
	public List<JCFlowRec> findJCFlowRecByDatePlan(String jwdCode, String rjhmId) {
		String sql = "select jf.*, dp.proteamname  from JC_FLOWREC jf left join dict_proteam dp "
				+ " on jf.BZID = dp.proteamid" + " where jf.DYPRECID = " + Long.parseLong(rjhmId) + " and dp.jwdcode = '"+ jwdCode +"'";
		List<JCFlowRec> flowRecList = jdbcTemplate.query(sql, new RowMapper<JCFlowRec>() {
			public JCFlowRec mapRow(ResultSet rs, int row) throws SQLException {
				JCFlowRec flowRec = new JCFlowRec();
				flowRec.setJcFlowRecId(rs.getLong("JCFLOWRECID"));
				flowRec.setFixflow(rs.getLong("JCFLOWID"));
				flowRec.setProTeam(rs.getLong("BZID"));
				flowRec.setProTeamName(rs.getString("PROTEAMNAME"));
				flowRec.setFinishStatus(rs.getInt("FINISHSTATUS"));
				flowRec.setFinishTime(rs.getString("FINISHTIME"));
				flowRec.setDatePlanPri(rs.getLong("DYPRECID"));
				flowRec.setJwdCode(rs.getString("JWDCODE"));
				return flowRec;
			}
		});
		return flowRecList;
	}

	@Override
	public Object findExpSituation(String rjhmId, int type) throws Exception {
		String sql = "select count(t.recId) from YSJC_REC t where t.RJHMID = " + rjhmId + "";
		if (type == 0) {
			sql += " and t.fixemp is null";
		} else if (type == 1) {
			sql += " and t.fixemp is null";
		} else if (type == 2) {
			sql += " and t.tech is null";
		} else if (type == 3) {
			sql += " and t.tech is null";
		} else if (type == 4) {
			sql += " and t.commitLead is null";
		} else {
			sql += " and t.accept is null";
		}
		return jdbcTemplate.queryForInt(sql);
	}
}

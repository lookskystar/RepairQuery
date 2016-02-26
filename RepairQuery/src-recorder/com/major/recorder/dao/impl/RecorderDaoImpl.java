package com.major.recorder.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import com.major.base.page.PageContext;
import com.major.base.page.PageModel;
import com.major.base.vo.CountPartModel;
import com.major.base.vo.DatePlanPri;
import com.major.base.vo.DictFirstUnit;
import com.major.base.vo.DictProTeam;
import com.major.base.vo.JCFixrec;
import com.major.base.vo.JCQZFixRec;
import com.major.base.vo.JCZXFixItem;
import com.major.base.vo.JCZXFixRec;
import com.major.base.vo.JtPreDict;
import com.major.base.vo.MainPlan;
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

public class RecorderDaoImpl implements RecorderDaoI {

	/** spring jdbcTemplate 对象 */
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<DatePlanPri> queryLocoFixRec(String itemName, String xcxc, String jcNum, String st, String et, String jwdCode) {
		StringBuilder sqlBuilder = new StringBuilder();
		if(StringUtils.isNotEmpty(itemName)) {
			sqlBuilder.append("select jtf.*, u.xm from ");
			sqlBuilder.append("(select jtfs.*, jtfs.itemname xitemname,jzf.itemname zxitemname from " +
					"(select t.*, jf.itemname from DATEPLAN_PRI t left join JC_FIXREC jf on t.RJHMID = jf.DYPRECID) jtfs " +
					"left join JC_ZX_FIXREC jzf on jtfs.RJHMID = jzf.DYPRECID  " +
					"where (jzf.ITEMNAME like '%"+ itemName +"%' or jtfs.ITEMNAME like '%"+ itemName +"%')) jtf");
			sqlBuilder.append(" left join USERS_PRIVS u on jtf.gongzhang = u.userid where 1 = 1");
		}else {
			sqlBuilder.append("select jtf.*, u.xm from DATEPLAN_PRI jtf  left join USERS_PRIVS u on jtf.gongzhang = u.userid where 1 = 1");
		}
		dealXcxc(sqlBuilder, xcxc);
		if (StringUtils.isNotEmpty(jcNum)) {
			sqlBuilder.append(" and jtf.JCNUM = '" + jcNum + "'");
		}
		if (!StringUtils.isNotEmpty(st) && StringUtils.isNotEmpty(et)) {
			sqlBuilder.append(" and jtf.KCSJ <= '" + et + " 23:59'");
		}
		if (StringUtils.isNotEmpty(st) && !StringUtils.isNotEmpty(et)) {
			sqlBuilder.append(" and jtf.KCSJ >= '" + st + " 00:00'");
		}
		if (StringUtils.isNotEmpty(st) && StringUtils.isNotEmpty(et)) {
			sqlBuilder.append(" and jtf.KCSJ >= '" + st + " 00:00' and jtf.KCSJ <= '" + et + " 23:59'");
		}
		if (StringUtils.isNotEmpty(jwdCode)) {
			sqlBuilder.append(" and jtf.JWDCODE = '" + jwdCode + "' and u.JWDCODE = '"+ jwdCode +"'");
		}
		sqlBuilder.append(" and jtf.planStatue = 3");
		sqlBuilder.append(" order by jctype, kcsj");
		List<DatePlanPri> planList = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<DatePlanPri>() {
			public DatePlanPri mapRow(ResultSet rs, int row) throws SQLException {
				DatePlanPri datePlanPri = new DatePlanPri();
				datePlanPri.setRjhmId(rs.getInt("RJHMID"));
				datePlanPri.setJcType(rs.getString("JCTYPE"));
				datePlanPri.setJcnum(rs.getString("JCNUM"));
				datePlanPri.setJwdcode(rs.getString("JWDCODE"));
				datePlanPri.setFixFreque(rs.getString("FIXFREQUE"));
				datePlanPri.setGdh(rs.getString("GDH"));
				datePlanPri.setTwh(rs.getString("TWH"));
				datePlanPri.setKcsj(rs.getString("KCSJ"));
				datePlanPri.setJhjcsj(rs.getString("JHJCSJ"));
				datePlanPri.setSjjcsj(rs.getString("SJJCSJ"));
				datePlanPri.setGongZhang(rs.getString("XM"));
				datePlanPri.setPlanStatue(rs.getInt("PLANSTATUE"));
				return datePlanPri;
			}
		});
		return planList;
	}
	
	@Override
	public List<DatePlanPri> queryLocoFixRecOnNum(String xcxc, String st, String et, String jwdCode, String type) {
		StringBuilder sqlBuilder = new StringBuilder();
		if (StringUtils.isNotEmpty(type) && type.equals("2")) {
			sqlBuilder.append("select dp.*, u.xm from ");
			sqlBuilder.append("(select * from DATEPLAN_PRI t where 1 = 1 ");
			//未兑现
			if(StringUtils.isNotEmpty(xcxc) && xcxc.equals("zx")) {
				sqlBuilder.append("and t.FIXFREQUE like 'Z_' and t.FIXFREQUE <> 'ZZ' ");
				sqlBuilder.append("and t.jwdcode = '"+ jwdCode +"' ");
				sqlBuilder.append("and t.KCSJ >= '"+ st +" 00:00' and t.KCSJ <= '"+ et +" 23:59' ");
				sqlBuilder.append("and t.rjhmId not in(select rjhmId from MAINPLANDETAIL md where MAINPLANID in ");
				sqlBuilder.append("(select id from MAINPLAN where STARTTIME = '" + st + "'and ENDTIME = '" + et + "' and JWDCODE = '"+ jwdCode + "')");
				sqlBuilder.append("and (md.XCXC like 'Z_' and md.XCXC <> 'ZZ'))) dp ");
			} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("xf")) {
				sqlBuilder.append("and (t.FIXFREQUE like 'X%' or t.FIXFREQUE like 'F%'" + "or t.FIXFREQUE = 'CJ'" + "or t.FIXFREQUE = 'QZ'" + " or t.FIXFREQUE = 'JJ' or t.FIXFREQUE = 'YJ' or t.FIXFREQUE = 'BNJ' or t.FIXFREQUE = 'NJ') ");
				sqlBuilder.append("and t.jwdcode = '"+ jwdCode +"' ");
				sqlBuilder.append("and t.KCSJ >= '"+ st +" 00:00' and t.KCSJ <= '"+ et +" 23:59' ");
				sqlBuilder.append("and t.rjhmId not in(select rjhmId from MAINPLANDETAIL md where MAINPLANID in ");
				sqlBuilder.append("(select id from MAINPLAN where STARTTIME = '" + st + "'and ENDTIME = '" + et + "' and JWDCODE = '"+ jwdCode + "') ");
				sqlBuilder.append("and (md.XCXC like 'X%' or md.XCXC like 'F%'" + "or md.XCXC = 'CJ'" + "or md.XCXC = 'QZ'" + " or md.XCXC = 'JJ' or md.XCXC = 'YJ' or md.XCXC = 'BNJ' or md.XCXC = 'NJ'))) dp");
			} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("lg")) {
				sqlBuilder.append("and (t.FIXFREQUE = 'LX' or t.FIXFREQUE = 'JG'" + ") ");
				sqlBuilder.append("and t.jwdcode = '"+ jwdCode +"' ");
				sqlBuilder.append("and t.KCSJ >= '"+ st +" 00:00 ' and t.KCSJ <= '"+ et +" 23:59' ");
				sqlBuilder.append("and t.rjhmId not in(select rjhmId from MAINPLANDETAIL md where MAINPLANID in ");
				sqlBuilder.append("(select id from MAINPLAN where STARTTIME = '" + st + "'and ENDTIME = '" + et + "' and JWDCODE = '"+ jwdCode + "') ");
				sqlBuilder.append("and (md.xcxc = 'LX' or md.xcxc = 'JG'" + "))) dp");
			} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("qt")) {
				sqlBuilder.append("and (t.FIXFREQUE = 'ZQZZ' or t.FIXFREQUE = 'ZZ'" + " ) ") ;
				sqlBuilder.append("and t.jwdcode = '"+ jwdCode +"' ");
				sqlBuilder.append("and t.KCSJ >= '"+ st +" 00:00' and t.KCSJ <= '"+ et +" 23:59' ");
				sqlBuilder.append("and t.rjhmId not in(select rjhmId from MAINPLANDETAIL md where MAINPLANID in ");
				sqlBuilder.append("(select id from MAINPLAN where STARTTIME = '" + st + "'and ENDTIME = '" + et + "' and JWDCODE = '"+ jwdCode + "') ");
				sqlBuilder.append("and (md.xcxc = 'ZQZZ' or md.xcxc = 'ZZ'" + " ))) dp");
			}
			sqlBuilder.append(", USERS_PRIVS u where dp.gongzhang = u.userid and u.jwdcode = '"+ jwdCode +"'");
		} else if (StringUtils.isNotEmpty(type) && type.equals("1")) {
			sqlBuilder.append("select t.*, u.xm from DATEPLAN_PRI t  left join USERS_PRIVS u on t.gongzhang = u.userid where 1 = 1 and t.planStatue < 2");
			//实际
			if(StringUtils.isNotEmpty(xcxc) && xcxc.equals("zx")) {
				sqlBuilder.append("and t.FIXFREQUE like 'Z_' and t.FIXFREQUE <> 'ZZ' ");
			} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("xf")) {
				sqlBuilder.append("and (t.FIXFREQUE like 'X%' or t.FIXFREQUE like 'F%'" + "or t.FIXFREQUE = 'CJ'" + "or t.FIXFREQUE = 'QZ'" + " or t.FIXFREQUE = 'JJ' or t.FIXFREQUE = 'YJ' or t.FIXFREQUE = 'BNJ' or t.FIXFREQUE = 'NJ') ");
			} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("lg")) {
				sqlBuilder.append("and (t.FIXFREQUE = 'LX' or t.FIXFREQUE = 'JG'" + ") ");
			} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("qt")) {
				sqlBuilder.append("and (t.FIXFREQUE = 'ZQZZ' or t.FIXFREQUE = 'ZZ'" + " ) ") ;
			} 
			st += " 00:00";
			et += " 23:59";
			if (StringUtils.isNotEmpty(st) && StringUtils.isNotEmpty(et)) {
				sqlBuilder.append("and t.KCSJ >= '" + st + "' and t.KCSJ <= '" + et + "' ");
			}
			if (StringUtils.isNotEmpty(jwdCode)) {
				sqlBuilder.append("and t.JWDCODE = '" + jwdCode + "' and u.JWDCODE = '"+ jwdCode +"' ");
			}
		}
		sqlBuilder.append(" order by jctype, kcsj");
		List<DatePlanPri> planList = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<DatePlanPri>() {
			public DatePlanPri mapRow(ResultSet rs, int row) throws SQLException {
				DatePlanPri datePlanPri = new DatePlanPri();
				datePlanPri.setRjhmId(rs.getInt("RJHMID"));
				datePlanPri.setJcType(rs.getString("JCTYPE"));
				datePlanPri.setJcnum(rs.getString("JCNUM"));
				datePlanPri.setJwdcode(rs.getString("JWDCODE"));
				datePlanPri.setFixFreque(rs.getString("FIXFREQUE"));
				datePlanPri.setGdh(rs.getString("GDH"));
				datePlanPri.setTwh(rs.getString("TWH"));
				datePlanPri.setKcsj(rs.getString("KCSJ"));
				datePlanPri.setJhqjsj(rs.getString("JHQJSJ"));
				datePlanPri.setJhjcsj(rs.getString("JHJCSJ"));
				datePlanPri.setGongZhang(rs.getString("XM"));
				datePlanPri.setPlanStatue(rs.getInt("PLANSTATUE"));
				return datePlanPri;
			}
		});
		return planList;
	}
	

	@Override
	public List<MainPlanDetail> queryLocoFixRecOnPlan(String xcxc, String st, String et, String jwdCode) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select * from MAINPLANDETAIL t ");
		sqlBuilder.append("where t.PLANTIME between '"+ st +"' and '"+ et +"' ");
		if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("zx")) {
			sqlBuilder.append("and t.XCXC like 'Z_' and t.XCXC <> 'ZZ' ");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("xf")) {
			sqlBuilder.append("and (t.XCXC like 'X%' or t.XCXC like 'F%'" + "or t.XCXC = 'CJ'" + "or t.XCXC = 'QZ'" + " or t.XCXC = 'JJ' or t.XCXC = 'YJ' or t.XCXC = 'BNJ' or t.XCXC = 'NJ') ");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("lg")) {
			sqlBuilder.append("and (t.XCXC = 'LX' or t.XCXC = 'JG'" + ") ");
		} else {
			sqlBuilder.append("and (t.XCXC = 'ZQZZ' or t.XCXC = 'ZZ'" + " ) ") ;
		}
		sqlBuilder.append("and t.JWDCODE = '" + jwdCode + "' order by kcsj");
		List<MainPlanDetail> detailList = jdbcTemplate.query(sqlBuilder.toString(), new RowMapper<MainPlanDetail>() {
			public MainPlanDetail mapRow(ResultSet rs, int row) throws SQLException {
				MainPlanDetail detail = new MainPlanDetail();
				detail.setJcType(rs.getString("JCTYPE"));
				detail.setJcNum(rs.getString("JCNUM"));
				detail.setXcxc(rs.getString("XCXC"));
				detail.setKilometre(rs.getString("KILOMETRE"));
				detail.setRealKilometre(rs.getString("realKilometre"));
				detail.setKcArea(rs.getString("KCAREA"));
				detail.setComments(rs.getString("COMMENTS"));
				detail.setIsCash(rs.getInt("isCash"));
				detail.setCashReason(rs.getString("cashReason"));
				return detail;
			}
		});
		return detailList;
	}

	public Map<CountPartModel, List<CountPartModel>> countPartRate(String jwdCode, String jcType) {
		Map<CountPartModel, List<CountPartModel>> countMap = new HashMap<CountPartModel, List<CountPartModel>>();
		List<DictFirstUnit> unitFirst = queryDictFirstUnit(jwdCode, jcType);
		for (DictFirstUnit dictFirstUnit : unitFirst) {
			final Long firstUnitId = dictFirstUnit.getFirstunitid();
			final String fristUnitName = dictFirstUnit.getFirstunitname();
			String ucCountSql = "select pdpc, pdfc, pdwc,  pdoc, pdpc+pdfc+pdwc total from "
					+ "(select count(pjdid) pdpc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '"
					+ jwdCode
					+ "' and pd.PJSTATUS = 0), "
					+ "(select count(pjdid) pdfc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '"
					+ jwdCode
					+ "' and pd.PJSTATUS = 3), "
					+ "(select count(pjdid) pdwc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '"
					+ jwdCode
					+ "' and pd.PJSTATUS = 1), "
					+ "(select count(pjdid) pdoc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '" + jwdCode + "' and pd.PJSTATUS = 0 and pd.STOREPOSITION = 2)";
			List<CountPartModel> unitCountList = jdbcTemplate.query(ucCountSql, new RowMapper<CountPartModel>() {
				public CountPartModel mapRow(ResultSet rs, int row) throws SQLException {
					CountPartModel model = new CountPartModel();
					model.setId(firstUnitId);
					model.setName(fristUnitName);
					model.setPassCount(rs.getInt("pdpc"));
					model.setFixCount(rs.getInt("pdfc"));
					model.setWaitCount(rs.getInt("pdwc"));
					model.setOnTrain(rs.getInt("pdoc"));
					model.setTotalCount(rs.getInt("total"));
					if (0 != rs.getInt("pdpc")) {
						NumberFormat nt = NumberFormat.getPercentInstance();
						nt.setMaximumFractionDigits(2);
						Double rate = (double) rs.getInt("pdpc")
								/ (rs.getInt("pdpc") + rs.getInt("pdfc") + rs.getInt("pdwc"));
						model.setPassRate(nt.format(rate));
					}
					return model;
				}
			});
			List<PJStaticInfo> staticList = queryPJStaticInfo(jwdCode, dictFirstUnit.getFirstunitid(), jcType);
			List<CountPartModel> pjModelList = new ArrayList<CountPartModel>();
			String pjCounSql = null;
			for (PJStaticInfo pjStaticInfo : staticList) {
				final Long pjSid = pjStaticInfo.getPjsid();
				final String pjName = pjStaticInfo.getPjName();
				pjCounSql = "select pdpc, pdfc, pdwc,  pdoc, pdpc+pdfc+pdwc total from "
						+ "(select count(PJDID) pdpc from pj_dynamicinfo pd where PJSID = " + pjSid
						+ " and pd.PJSTATUS = 0), " + "(select count(PJDID) pdfc from pj_dynamicinfo pd where PJSID = "
						+ pjSid + " and pd.PJSTATUS = 3),"
						+ "(select count(PJDID) pdwc from pj_dynamicinfo pd where PJSID = " + pjSid
						+ " and pd.PJSTATUS = 1)," + "(select count(PJDID) pdoc from pj_dynamicinfo pd where PJSID = "
						+ pjSid + " and pd.PJSTATUS = 0 and pd.STOREPOSITION = 2)";
				List<CountPartModel> pjCountList = jdbcTemplate.query(pjCounSql, new RowMapper<CountPartModel>() {
					public CountPartModel mapRow(ResultSet rs, int row) throws SQLException {
						CountPartModel model = new CountPartModel();
						model.setId(pjSid);
						model.setName(pjName);
						model.setPassCount(rs.getInt("pdpc"));
						model.setFixCount(rs.getInt("pdfc"));
						model.setWaitCount(rs.getInt("pdwc"));
						model.setOnTrain(rs.getInt("pdoc"));
						model.setTotalCount(rs.getInt("total"));
						if (0 != rs.getInt("pdpc")) {
							NumberFormat nt = NumberFormat.getPercentInstance();
							nt.setMaximumFractionDigits(2);
							Double rate = (double) rs.getInt("pdpc")
									/ (rs.getInt("pdpc") + rs.getInt("pdfc") + rs.getInt("pdwc"));
							model.setPassRate(nt.format(rate));
						}
						return model;
					}
				});
				pjModelList.add(pjCountList.get(0));
			}
			countMap.put(unitCountList.get(0), pjModelList);
		}
		return countMap;
	}
	
	public Map<CountPartModel, List<CountPartModel>> countPartRate2(String jwdCode, String jcType) {
		Map<CountPartModel, List<CountPartModel>> countMap = new HashMap<CountPartModel, List<CountPartModel>>();
		List<DictFirstUnit> unitFirst = queryDictFirstUnit(jwdCode, jcType);
		for (DictFirstUnit dictFirstUnit : unitFirst) {
			final Long firstUnitId = dictFirstUnit.getFirstunitid();
			final String fristUnitName = dictFirstUnit.getFirstunitname();
			String ucCountSql = "select pdpc, pdfc, pdwc,  pdoc, pdpc+pdfc+pdwc total from "
					+ "(select count(pjdid) pdpc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '"
					+ jwdCode
					+ "' and pd.PJSTATUS = 0), "
					+ "(select count(pjdid) pdfc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '"
					+ jwdCode
					+ "' and pd.PJSTATUS = 3), "
					+ "(select count(pjdid) pdwc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '"
					+ jwdCode
					+ "' and pd.PJSTATUS = 1), "
					+ "(select count(pjdid) pdoc from pj_dynamicinfo pd where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "
					+ firstUnitId
					+ " and ps.JCTYPE =',"
					+ jcType
					+ ",' and ps.jwdcode = '"
					+ jwdCode
					+ "') and pd.jwdcode = '" + jwdCode + "' and pd.PJSTATUS = 0 and pd.STOREPOSITION = 2)";
			List<CountPartModel> unitCountList = jdbcTemplate.query(ucCountSql, new RowMapper<CountPartModel>() {
				public CountPartModel mapRow(ResultSet rs, int row) throws SQLException {
					CountPartModel model = new CountPartModel();
					model.setId(firstUnitId);
					model.setName(fristUnitName);
					model.setPassCount(rs.getInt("pdpc"));
					model.setFixCount(rs.getInt("pdfc"));
					model.setWaitCount(rs.getInt("pdwc"));
					model.setOnTrain(rs.getInt("pdoc"));
					model.setTotalCount(rs.getInt("total"));
					if (0 != rs.getInt("pdpc")) {
						NumberFormat nt = NumberFormat.getPercentInstance();
						nt.setMaximumFractionDigits(2);
						Double rate = (double) rs.getInt("pdpc")
								/ (rs.getInt("pdpc") + rs.getInt("pdfc") + rs.getInt("pdwc"));
						model.setPassRate(nt.format(rate));
					}
					return model;
				}
			});
			String pjCounSql = "select pdc.pjname, pdc.pdpc, pdc.pdfc, pdc.pdwc, pdc.pdoc, (pdpc+pdfc+pdwc) total, pdpc/(pdpc+pdfc+pdwc) rate" +
					" from " +
					" (select pdo.pjname, " +
					" sum(case pdo.pjstatus when 0 then pdo.counts else 0 end) pdpc, " +
					" sum(case pdo.pjstatus when 1 then pdo.counts else 0 end) pdwc," +
					" sum(case pdo.pjstatus when 3 then pdo.counts else 0 end) pdfc," +
					" sum(case when (pdo.pjstatus = 0 and pdo.storeposition = 2) then pdo.counts else 0 end) pdoc" +
					" from " +
					" (select pd.pjname,pd.pjstatus,pd.storeposition, count(pjdid) counts from pj_dynamicinfo pd " +
					" where pd.PJSID in (select ps.PJSID from pj_staticinfo ps where ps.FIRSTUNITID = "+ firstUnitId +" and ps.JCTYPE =',"+ jcType +",' and ps.jwdcode = '"+ jwdCode +"') and pd.jwdcode = '"+ jwdCode +"'" +
					" group by pjname, pjstatus, storeposition" +
					" order by pjname）pdo" +
					" group by pdo.pjname" +
					" order by pdo.pjname) pdc";
			List<CountPartModel> pjModelList = jdbcTemplate.query(pjCounSql, new RowMapper<CountPartModel>() {
				public CountPartModel mapRow(ResultSet rs, int row) throws SQLException {
					CountPartModel model = new CountPartModel();
					model.setName(rs.getString("pjname"));
					model.setPassCount(rs.getInt("pdpc"));
					model.setFixCount(rs.getInt("pdfc"));
					model.setWaitCount(rs.getInt("pdwc"));
					model.setOnTrain(rs.getInt("pdoc"));
					model.setTotalCount(rs.getInt("total"));
					NumberFormat nt = NumberFormat.getPercentInstance();
					nt.setMaximumFractionDigits(2);
					model.setPassRate(nt.format(rs.getDouble("rate")));
					return model;
				}
			});
			countMap.put(unitCountList.get(0), pjModelList);
		}
		return countMap;
	}

	public List<PJStaticInfo> queryPJStaticInfo(String jwdCode, Long firstUnitId, String jcType) {
		String sql = "select * from PJ_STATICINFO ps " + " where ps.FIRSTUNITID = '" + firstUnitId + "'"
				+ " and ps.JCTYPE = '," + jcType + ",'" + " and ps.JWDCODE = '" + jwdCode + "'";
		List<PJStaticInfo> staticList = jdbcTemplate.query(sql, new RowMapper<PJStaticInfo>() {
			public PJStaticInfo mapRow(ResultSet rs, int row) throws SQLException {
				PJStaticInfo staticInfo = new PJStaticInfo();
				staticInfo.setPjsid(rs.getLong("PJSID"));
				staticInfo.setPjName(rs.getString("PJNAME"));
				return staticInfo;
			}
		});
		return staticList;
	}

	public List<DictFirstUnit> queryDictFirstUnit(String jwdCode, String jcType) {
		String sql = "select * from DICT_FIRSTUNIT t where t.jwdcode = " + jwdCode + " and t.JCSTYPEVALUE like '%"
				+ jcType + "%' order by FIRSTUNITID";
		List<DictFirstUnit> unitList = jdbcTemplate.query(sql, new RowMapper<DictFirstUnit>() {
			public DictFirstUnit mapRow(ResultSet rs, int row) throws SQLException {
				DictFirstUnit unit = new DictFirstUnit();
				unit.setFirstunitid(rs.getLong("FIRSTUNITID"));
				unit.setFirstunitname(rs.getString("FIRSTUNITNAME"));
				unit.setJcstypevalue(rs.getString("JCSTYPEVALUE"));
				unit.setJwdcode(rs.getString("JWDCODE"));
				unit.setPy(rs.getString("PY"));
				unit.setUrl(rs.getString("URL"));
				return unit;
			}
		});
		return unitList;
	}

	public List<Map<String, Object>> countReportInfo(String st, String et) throws ParseException {
		final List<Map<String, Object>> reportList = new ArrayList<Map<String,Object>>();
		String sql = "SELECT SUBSTR(t.reptime, 0, 10) DAY, " +
					 "SUM(CASE WHEN jwdcode = '0801' THEN 1 ELSE 0 END) \"gz\", " +
					 "SUM(CASE WHEN jwdcode = '0805' THEN 1 ELSE 0 END) \"zz\", " +
					 "SUM(CASE WHEN jwdcode = '08052' THEN 1 ELSE 0 END) \"cs\", " +
					 "SUM(CASE WHEN jwdcode = '0809' THEN 1 ELSE 0 END) \"hh\", " +
					 "SUM(CASE WHEN jwdcode = '0818' THEN 1 ELSE 0 END) \"lc\", " +
					 "SUM(CASE WHEN jwdcode = '0819' THEN 1 ELSE 0 END) \"ss\" " +
					 "FROM jt_predict t " +
					 "WHERE t.type IN(0, 1, 2, 6) AND SUBSTR(t.reptime, 0, 10) BETWEEN ? AND ? " +
					 "GROUP BY SUBSTR(t.reptime, 0, 10) " +
					 "ORDER BY SUBSTR(t.reptime, 0, 10)";
		jdbcTemplate.query(sql, new Object[]{st, et}, new RowMapper<Object>(){
	
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Integer total = 0;
				Map<String, Object> reportMap = new HashMap<String, Object>();
				String date = rs.getString("day");
				reportMap.put("date", date);
				String gz = rs.getString("gz");
				reportMap.put("gz", Integer.parseInt(gz));
				total += Integer.parseInt(gz);
				String lc = rs.getString("lc");
				reportMap.put("lc", Integer.parseInt(lc));
				total += Integer.parseInt(lc);
				String ss = rs.getString("ss");
				reportMap.put("ss", Integer.parseInt(ss));
				total += Integer.parseInt(ss);
				String zz = rs.getString("zz");
				reportMap.put("zz", Integer.parseInt(zz));
				total += Integer.parseInt(zz);
				String cs = rs.getString("cs");
				reportMap.put("cs", Integer.parseInt(cs));
				total += Integer.parseInt(cs);
				String hh = rs.getString("hh");
				reportMap.put("hh", Integer.parseInt(hh));
				total += Integer.parseInt(hh);
				reportMap.put("total", total);
				reportList.add(reportMap);
				return null;
			}
		});
		return reportList;
	}
	
	private void dealXcxc(StringBuilder sqlBuilder, String xcxc) {
		if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("zx")) {
			sqlBuilder.append(" and jtf.FIXFREQUE like 'Z_'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("xx")) {
			sqlBuilder.append(" and jtf.FIXFREQUE like 'X%'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("fx")) {
			sqlBuilder.append(" and jtf.FIXFREQUE like 'F%'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("lx")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'LX'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("jg")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'JG'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("cj")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'CJ'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("qz")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'QZ'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("zz")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'ZZ'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("zqzz")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'ZQZZ'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("yj")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'YJ'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("jj")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'JJ'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("bnj")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'BNJ'");
		} else if (StringUtils.isNotEmpty(xcxc) && xcxc.equals("nj")) {
			sqlBuilder.append(" and jtf.FIXFREQUE  = 'NJ'");
		}
	}

	@Override
	public PlanModel countPlan(String st, String et, String jwdCode) throws Exception {
		String infoSql = "select * from MAINPLAN where STARTTIME = '" + st + "'and ENDTIME = '"+ et +"' and JWDCODE ='" + jwdCode + "'";
		List<MainPlan> mainlList = jdbcTemplate.query(infoSql, new RowMapper<MainPlan>() {
			public MainPlan mapRow(ResultSet rs, int row) throws SQLException {
				MainPlan main = new MainPlan();
				main.setMakeTime(rs.getString("MAKETIME"));
				main.setMakePeople(rs.getString("MAKEPEOPLE"));
				return main;
			}
		});
		PlanModel planModel = new PlanModel();
		if (0 != mainlList.size()) {
			MainPlan mainPlan = mainlList.get(0);
			planModel.setMakePlanTime(mainPlan.getMakeTime());
			planModel.setMakePlanPerson(mainPlan.getMakePeople());
		}
		String countSql = "select * from MAINPLANDETAIL where MAINPLANID in "
				+ " (select id from MAINPLAN where STARTTIME = '" + st + "'and ENDTIME = '" + et + "' and JWDCODE = '"+ jwdCode + "')";
		List<MainPlanDetail> detailList = jdbcTemplate.query(countSql, new RowMapper<MainPlanDetail>() {
			public MainPlanDetail mapRow(ResultSet rs, int row) throws SQLException {
				MainPlanDetail detail = new MainPlanDetail();
				detail.setJcType(rs.getString("JCTYPE"));
				detail.setJcNum(rs.getString("JCNUM"));
				detail.setXcxc(rs.getString("XCXC"));
				detail.setKilometre(rs.getString("KILOMETRE"));
				detail.setKcArea(rs.getString("KCAREA"));
				detail.setComments(rs.getString("COMMENTS"));
				return detail;
			}
		});
		planModel.setDetailList(detailList);
		return planModel;
	}
	
	public List<Map<String, Object>> findJgItemCount(String jwdCode) {
		String sql = "select t.jg_content,t.jc_type,t.plan_num," +
				"(select count(b.predictid) from dateplan_pri a,jt_predict b where a.rjhmid=b.dateplanpri and b.repsituation=t.jg_content and a.jctype=t.jc_type and a.fixfreque='JG' and b.type=3 and a.jwdcode = '"+ jwdCode +"' and b.jwdcode = '"+ jwdCode +"') as sjcount," +
				"t.id as jgid from jg_plan_content t order by t.jg_content,t.jc_type";
		List<Map<String, Object>> objMapList = jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {
			public Map<String, Object> mapRow(ResultSet rs, int row) throws SQLException {
				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("jg_content", rs.getString("jg_content"));
				objMap.put("jc_type", rs.getString("jc_type"));
				objMap.put("plan_num", rs.getString("plan_num"));
				objMap.put("sjcount", rs.getString("sjcount"));
				objMap.put("jgid", rs.getString("jgid"));
				return objMap;
			}
		});
		return objMapList;
	}
	
	public List<Map<String, Object>> findJgAcounts(String jwdCode) {
		String sql = "select j.jcType,count(j.jcType) jcTypeCount from jt_runkilorec j where jwdcode = '"+ jwdCode +"' group by j.jcType order by j.jcType";
		List<Map<String, Object>> objMapList = jdbcTemplate.query(sql, new RowMapper<Map<String, Object>>() {
			public Map<String, Object> mapRow(ResultSet rs, int row) throws SQLException {
				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("jcType", rs.getString("jcType"));
				objMap.put("jcTypeCount", rs.getString("jcTypeCount"));
				return objMap;
			}
		});
		return objMapList;
	}

	@Override
	public List<DictProTeam> findAllProTeam(String jwdcode, String jctype, Integer workflag) {
		String jctypes = jctype.substring(0,2);
		String sql = "select * from DICT_PROTEAM proTeam where proTeam.jctype like '%"+ jctypes +"%' and proTeam.workflag="+ workflag +" and jwdcode = '"+ jwdcode +"'";
		List<DictProTeam> proTeams = jdbcTemplate.query(sql, new RowMapper<DictProTeam>() {
			public DictProTeam mapRow(ResultSet rs, int row) throws SQLException {
				DictProTeam proTeam = new DictProTeam();
				proTeam.setProteamid(rs.getLong("PROTEAMID"));
				proTeam.setProteamname(rs.getString("PROTEAMNAME"));
				proTeam.setJctype(rs.getString("JCTYPE"));
				proTeam.setJwdcode(rs.getString("JWDCODE"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setWorkflag(rs.getInt("WORKFLAG"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setZxFlag(rs.getInt("ZXFLAG"));
				return proTeam;
			}
		});
		return proTeams;
	}

	@Override
	public List<JtPreDict> findJtPreDict(String jwdCode, Integer datePlanPri) {
		String sql = "select * from JT_PREDICT jf where jf.DATEPLANPRI= "+ datePlanPri +" and jf.type=3 and jwdcode = '"+ jwdCode +"'";
		List<JtPreDict> jtPreDicts = jdbcTemplate.query(sql, new RowMapper<JtPreDict>() {
			public JtPreDict mapRow(ResultSet rs, int row) throws SQLException {
				JtPreDict jtPreDict = new JtPreDict();
				jtPreDict.setAccepter(rs.getString("ACCEPTER"));
				jtPreDict.setAcceTime(rs.getString("ACCETIME"));
				jtPreDict.setAffirmGrade(rs.getShort("AFFIRMGRADE"));
				jtPreDict.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jtPreDict.setCommitLd(rs.getString("COMMITLDID"));
				jtPreDict.setDatePlanPri(rs.getInt("DATEPLANPRI"));
				jtPreDict.setDealFixEmp(rs.getString("DEALFIXEMP"));
				jtPreDict.setDealSituation(rs.getString("DEALSITUATION"));
				jtPreDict.setDivisionId(rs.getInt("DIVISIONID"));
				jtPreDict.setFailNote(rs.getString("FAILNOTE"));
				jtPreDict.setFailNum(rs.getString("FAILNUM"));
				jtPreDict.setFixEmp(rs.getString("FIXEMP"));
				jtPreDict.setFixEmpId(rs.getString("FIXEMPID"));
				jtPreDict.setFixEndTime(rs.getString("FIXENDTIME"));
				jtPreDict.setImgUrl(rs.getString("IMGURL"));
				jtPreDict.setItemCtrlAcce(rs.getInt("ITEMCTRLACCE"));
				jtPreDict.setItemCtrlComLd(rs.getInt("ITEMCTRLCOMLD"));
				jtPreDict.setItemCtrlQI(rs.getInt("ITEMCTRQI"));
				jtPreDict.setItemCtrlTech(rs.getInt("ITEMCTRLTECH"));
				jtPreDict.setJcNum(rs.getString("JCNUM"));
				jtPreDict.setJcType(rs.getString("JCTYPE"));
				jtPreDict.setJwdCode(rs.getString("JWDCODE"));
				jtPreDict.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jtPreDict.setLead(rs.getString("LEAD"));
				jtPreDict.setPreDictId(rs.getInt("PREDICTID"));
				jtPreDict.setProTeamId(rs.getLong("PROTEAMID"));
				jtPreDict.setQi(rs.getString("QI"));
				jtPreDict.setQiAffiTime(rs.getString("QIAFFITIME"));
				jtPreDict.setReceiptPeo(rs.getString("RECEIPTPEO"));
				jtPreDict.setReceTime(rs.getString("RECETIME"));
				jtPreDict.setRecStas(rs.getShort("RECSTAS"));
				jtPreDict.setRepDate(rs.getString("REPDATE"));
				jtPreDict.setRepemp(rs.getString("REPEMP"));
				jtPreDict.setRepempNo(rs.getString("REPEMPNO"));
				jtPreDict.setRepPosi(rs.getString("REPPOSI"));
				jtPreDict.setRepsituation(rs.getString("REPSITUATION"));
				jtPreDict.setRepTime(rs.getString("REPTIME"));
				jtPreDict.setScore(rs.getInt("SCORE"));
				jtPreDict.setSmBzNames(rs.getString(""));
				jtPreDict.setSmpPreDictId(rs.getInt("SMPREDICTID"));
				jtPreDict.setTechAffiTime(rs.getString("TECHAFFITIME"));
				jtPreDict.setTechnician(rs.getString("TECHNICIAN"));
				jtPreDict.setThirdUnitId(rs.getLong("THIRDUNITID"));
				jtPreDict.setType(rs.getShort("TYPE"));
				jtPreDict.setUpPjNum(rs.getString("UPPJNUM"));
				jtPreDict.setVerifier(rs.getString("VERYFIER"));
				jtPreDict.setVerifyTime(rs.getString("VERIFYTIME"));
				jtPreDict.setZeroKiloRecId(rs.getInt("ZEROKILORECID"));
				return jtPreDict;
			}
		});
		return jtPreDicts;
	}

	@Override
	public List<JCQZFixRec> findJCQZFixRec(String jwdCode, Integer datePlanPri) {
		String sql = "select jf.*, ji.UNITNAME from JC_QZ_ITEMS ji left join JC_QZ_FIXREC jf on jf.THIRDUNITID = ji.id where jf.JCRECMID="+ datePlanPri +" and jwdCode = '"+ jwdCode +"'";
		List<JCQZFixRec> jcqzFixRecs = jdbcTemplate.query(sql, new RowMapper<JCQZFixRec>() {
			public JCQZFixRec mapRow(ResultSet rs, int row) throws SQLException {
				JCQZFixRec jcqzFixRec = new JCQZFixRec();
				jcqzFixRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jcqzFixRec.setAccepter(rs.getString("ACCEPTER"));
				jcqzFixRec.setBbrw(rs.getString("BBRW"));
				jcqzFixRec.setBbrwAffiTime(rs.getString("BBRWAFFITIME"));
				jcqzFixRec.setBzId(rs.getLong("BZID"));
				jcqzFixRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jcqzFixRec.setCommitLead(rs.getString("COMMITLEAD"));
				jcqzFixRec.setDld(rs.getString("DLD"));
				jcqzFixRec.setDldAffiTime(rs.getString("DLDAFFITIME"));
				jcqzFixRec.setDoPjBarcode(rs.getString("DOPJBARCODE"));
				jcqzFixRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jcqzFixRec.setEmpAfformTime(rs.getString("EMPAFFIRMTIME"));
				jcqzFixRec.setFixEmp(rs.getString("FIXEMP"));
				jcqzFixRec.setFixSituation(rs.getString("FIXSITUATION"));
				jcqzFixRec.setItemName(rs.getString("ITEMNAME"));
				jcqzFixRec.setItems(rs.getLong("THIRDUNITID"));
				jcqzFixRec.setItemType(rs.getShort("ITEMTYPE"));
				jcqzFixRec.setJcid(rs.getInt("JCID"));
				jcqzFixRec.setJcRecId(rs.getLong("JCRECID"));
				jcqzFixRec.setJcRecmId(rs.getInt("JCRECMID"));
				jcqzFixRec.setJskz(rs.getString("JSKZ"));
				jcqzFixRec.setJskzAffiTime(rs.getString("JSKZAFFITIME"));
				jcqzFixRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jcqzFixRec.setJwdCode(rs.getString("jwdCode"));
				jcqzFixRec.setJxzr(rs.getString("JXZR"));
				jcqzFixRec.setJxzrAffiTime(rs.getString("JXZRAFFITIME"));
				jcqzFixRec.setLead(rs.getString("LEAD"));
				jcqzFixRec.setLeadName(rs.getString("LEADNAME"));
				jcqzFixRec.setLeftMargin(rs.getString("LEFTMARGIN"));
				jcqzFixRec.setMoren(rs.getString("MOREN"));
				jcqzFixRec.setPjName(rs.getString("PJNAME"));
				jcqzFixRec.setPjStaticInfo(rs.getLong("PJSID"));
				jcqzFixRec.setQi(rs.getString("QI"));
				jcqzFixRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jcqzFixRec.setRecStas(rs.getShort("RECSTAS"));
				jcqzFixRec.setRightMargin(rs.getString("RIGHTMARGIN"));
				jcqzFixRec.setTech(rs.getString("TECH"));
				jcqzFixRec.setTechAffiTime(rs.getString("TECHAFFITIME"));
				jcqzFixRec.setUpPjBarcode(rs.getString("UPPJBARCODE"));
				jcqzFixRec.setUpPjNum(rs.getString("UPPJNUM"));
				jcqzFixRec.setWekprecid(rs.getInt("WEKPRECID"));
				jcqzFixRec.setWorkerId(rs.getString("WORKERID"));
				jcqzFixRec.setWorkerName(rs.getString("WORKERNAME"));
				jcqzFixRec.setZjkz(rs.getString("ZJKZ"));
				jcqzFixRec.setZjkzAffiTime(rs.getString("ZJKZAFFITIME"));
				jcqzFixRec.setUnitName(rs.getString("UNITNAME"));
				return jcqzFixRec;
			}
		});
		return jcqzFixRecs;
	}

	@Override
	public List<DictFirstUnit> listFirstUnitsOfJCFixRec(String jwdCode, Integer rjhmId, int type) {
		String sql=null;
		if(type==0){//小辅修
			sql="select distinct t.firstunitid as firstUnitId,t.unitname as firstUnitName from jc_fixrec t where t.dyprecid=? and jwdcode = '"+ jwdCode +"'";
		}else{//中修
			sql="select distinct t.firstunitid as firstUnitId,t.unitname as firstUnitName from jc_zx_fixrec t where t.dyprecid=? and jwdCode = '"+ jwdCode +"'";
		}
		List<DictFirstUnit> unitList = jdbcTemplate.query(sql, new Object[]{rjhmId}, new RowMapper<DictFirstUnit>() {
			public DictFirstUnit mapRow(ResultSet rs, int row) throws SQLException {
				DictFirstUnit unit = new DictFirstUnit();
				unit.setFirstunitid(rs.getLong("FIRSTUNITID"));
				unit.setFirstunitname(rs.getString("FIRSTUNITNAME"));
				return unit;
			}
		});
		return unitList;
	}

	@Override
	public List<JCFixrec> findJCFixrec(String jwdCode, Integer datePlanPri, String unit) {
		String sql = "select * from JC_FIXREC jf where  jf.DYPRECID=? and jf.unitName=? and jwdcode = ? order by jf.secUnitName,jf.jcRecId";
		List<JCFixrec> jcFixrecs = jdbcTemplate.query(sql, new Object[]{datePlanPri, unit, jwdCode}, new RowMapper<JCFixrec>() {
			public JCFixrec mapRow(ResultSet rs, int row) throws SQLException {
				JCFixrec fixrec = new JCFixrec();
				fixrec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				fixrec.setAccepter(rs.getString("ACCEPTER"));
				fixrec.setBanzuId(rs.getLong("BZID"));
				fixrec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				fixrec.setCommitLead(rs.getString("COMMITLEAD"));
				fixrec.setDatePlanPri(rs.getInt("DYPRECID"));
				fixrec.setDoPjBarcode(rs.getString("DOPJBARCODE"));
				fixrec.setDownPjNum(rs.getString("DOWNPJNUM"));
				fixrec.setDuration(rs.getInt("DURATION"));
				fixrec.setEmpAfformTime(rs.getString("EMPAFFIRMTIME"));
				fixrec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				fixrec.setFixEmp(rs.getString("FIXEMP"));
				fixrec.setFixEmpId(rs.getString("FIXEMPID"));
				fixrec.setFixitem(rs.getString("ITEMNAME"));
				fixrec.setFixSituation(rs.getString("FIXSITUATION"));
				fixrec.setItemCtrlAcce(rs.getInt("ITEMCTRLACCE"));
				fixrec.setItemCtrlComLd(rs.getInt("ITEMCTRLCOMLD"));
				fixrec.setItemCtrlLead(rs.getInt("ITEMCTRLLEAD"));
				fixrec.setItemCtrlQI(rs.getInt("ITEMCTRLQI"));
				fixrec.setItemCtrlTech(rs.getInt("ITEMCTRLTECH"));
				fixrec.setItemName(rs.getString("ITEMNAME"));
				fixrec.setItemType(rs.getShort("ITEMTYPE"));
				fixrec.setJcNum(rs.getString("JCNUM"));
				fixrec.setJcRecId(rs.getLong("JCRECID"));
				fixrec.setJcType(rs.getString("JCTYPE"));
				fixrec.setJhTime(rs.getString("JHTIME"));
				fixrec.setJwdCode(rs.getString("JWDCODE"));
				fixrec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				fixrec.setLead(rs.getString("LEAD"));
				fixrec.setLeadMargin(rs.getString("LEFTMARGIN"));
				fixrec.setPjName(rs.getString("PJNAME"));
				fixrec.setPjStaticInfo(rs.getLong("PJSID"));
				fixrec.setPosiName(rs.getString("POSINAME"));
				fixrec.setQi(rs.getString("QI"));
				fixrec.setQiAffiTime(rs.getString("QIAFFITIME"));
				fixrec.setRecStas(rs.getShort("RECSTAS"));
				fixrec.setRightMargin(rs.getString("RIGHTMARGIN"));
				fixrec.setSecUnitName(rs.getString("SECUNITNAME"));
				fixrec.setTech(rs.getString("TECH"));
				fixrec.setTechAffiTime(rs.getString("TECHAFFITIME"));
				fixrec.setUnit(rs.getString("UNIT"));
				fixrec.setUnitName(rs.getString("UNITNAME"));
				fixrec.setUpPjBarcode(rs.getString("UPPJBARCODE"));
				fixrec.setUpPjNum(rs.getString("UPPJNUM"));
				return fixrec;
			}
		});
		return jcFixrecs;
	}

	@Override
	public List<JCQZFixRec> findJCQZFixrecByTeam(String jwdCode, Integer rjhmId, Long teamId) {
		String sql = "select jf.*, ji.UNITNAME, ji.xh from JC_QZ_ITEMS ji left join JC_QZ_FIXREC jf on jf.THIRDUNITID = ji.id where jf.JCRECMID=? and jf.bzId=? order by ji.UNITNAME, ji.xh";
		List<JCQZFixRec> jcqzFixRecs = jdbcTemplate.query(sql, new RowMapper<JCQZFixRec>() {
			public JCQZFixRec mapRow(ResultSet rs, int row) throws SQLException {
				JCQZFixRec jcqzFixRec = new JCQZFixRec();
				jcqzFixRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jcqzFixRec.setAccepter(rs.getString("ACCEPTER"));
				jcqzFixRec.setBbrw(rs.getString("BBRW"));
				jcqzFixRec.setBbrwAffiTime(rs.getString("BBRWAFFITIME"));
				jcqzFixRec.setBzId(rs.getLong("BZID"));
				jcqzFixRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jcqzFixRec.setCommitLead(rs.getString("COMMITLEAD"));
				jcqzFixRec.setDld(rs.getString("DLD"));
				jcqzFixRec.setDldAffiTime(rs.getString("DLDAFFITIME"));
				jcqzFixRec.setDoPjBarcode(rs.getString("DOPJBARCODE"));
				jcqzFixRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jcqzFixRec.setEmpAfformTime(rs.getString("EMPAFFIRMTIME"));
				jcqzFixRec.setFixEmp(rs.getString("FIXEMP"));
				jcqzFixRec.setFixSituation(rs.getString("FIXSITUATION"));
				jcqzFixRec.setItemName(rs.getString("ITEMNAME"));
				jcqzFixRec.setItems(rs.getLong("THIRDUNITID"));
				jcqzFixRec.setItemType(rs.getShort("ITEMTYPE"));
				jcqzFixRec.setJcid(rs.getInt("JCID"));
				jcqzFixRec.setJcRecId(rs.getLong("JCRECID"));
				jcqzFixRec.setJcRecmId(rs.getInt("JCRECMID"));
				jcqzFixRec.setJskz(rs.getString("JSKZ"));
				jcqzFixRec.setJskzAffiTime(rs.getString("JSKZAFFITIME"));
				jcqzFixRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jcqzFixRec.setJwdCode(rs.getString("jwdCode"));
				jcqzFixRec.setJxzr(rs.getString("JXZR"));
				jcqzFixRec.setJxzrAffiTime(rs.getString("JXZRAFFITIME"));
				jcqzFixRec.setLead(rs.getString("LEAD"));
				jcqzFixRec.setLeadName(rs.getString("LEADNAME"));
				jcqzFixRec.setLeftMargin(rs.getString("LEFTMARGIN"));
				jcqzFixRec.setMoren(rs.getString("MOREN"));
				jcqzFixRec.setPjName(rs.getString("PJNAME"));
				jcqzFixRec.setPjStaticInfo(rs.getLong("PJSID"));
				jcqzFixRec.setQi(rs.getString("QI"));
				jcqzFixRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jcqzFixRec.setRecStas(rs.getShort("RECSTAS"));
				jcqzFixRec.setRightMargin(rs.getString("RIGHTMARGIN"));
				jcqzFixRec.setTech(rs.getString("TECH"));
				jcqzFixRec.setTechAffiTime(rs.getString("TECHAFFITIME"));
				jcqzFixRec.setUpPjBarcode(rs.getString("UPPJBARCODE"));
				jcqzFixRec.setUpPjNum(rs.getString("UPPJNUM"));
				jcqzFixRec.setWekprecid(rs.getInt("WEKPRECID"));
				jcqzFixRec.setWorkerId(rs.getString("WORKERID"));
				jcqzFixRec.setWorkerName(rs.getString("WORKERNAME"));
				jcqzFixRec.setZjkz(rs.getString("ZJKZ"));
				jcqzFixRec.setZjkzAffiTime(rs.getString("ZJKZAFFITIME"));
				jcqzFixRec.setUnitName(rs.getString("UNITNAME"));
				return jcqzFixRec;
			}
		});
		return jcqzFixRecs;
	}

	@Override
	public List<JtPreDict> findJtPreDictByFlag(String jwdCode, Integer datePlanPri, Object flag, int type) {
		String sql = "";
		List<JtPreDict> JtPreDictList = null;
		//String 类型为专业
		if(flag instanceof String){
			if(type==3){
				sql = "select * from JT_PREDICT jf where jf.DATEPLANPRI=? and jf.type=3 and jf.recStas>=2 and jwdcode = '"+ jwdCode +"'";
			}else{
				sql = "select * from JT_PREDICT jf where jf.DATEPLANPRI=? and jf.type!=3 and jf.recStas>=2 and jwdcode = '"+ jwdCode +"'";
			}
			JtPreDictList = jdbcTemplate.query(sql, new Object[]{datePlanPri}, new RowMapper<JtPreDict>() {
				public JtPreDict mapRow(ResultSet rs, int row) throws SQLException {
					JtPreDict jtPreDict = new JtPreDict();
					jtPreDict.setAccepter(rs.getString("ACCEPTER"));
					jtPreDict.setAcceTime(rs.getString("ACCETIME"));
					jtPreDict.setAffirmGrade(rs.getShort("AFFIRMGRADE"));
					jtPreDict.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
					jtPreDict.setCommitLd(rs.getString("COMMITLDID"));
					jtPreDict.setDatePlanPri(rs.getInt("DATEPLANPRI"));
					jtPreDict.setDealFixEmp(rs.getString("DEALFIXEMP"));
					jtPreDict.setDealSituation(rs.getString("DEALSITUATION"));
					jtPreDict.setDivisionId(rs.getInt("DIVISIONID"));
					jtPreDict.setFailNote(rs.getString("FAILNOTE"));
					jtPreDict.setFailNum(rs.getString("FAILNUM"));
					jtPreDict.setFixEmp(rs.getString("FIXEMP"));
					jtPreDict.setFixEmpId(rs.getString("FIXEMPID"));
					jtPreDict.setFixEndTime(rs.getString("FIXENDTIME"));
					jtPreDict.setImgUrl(rs.getString("IMGURL"));
					jtPreDict.setItemCtrlAcce(rs.getInt("ITEMCTRLACCE"));
					jtPreDict.setItemCtrlComLd(rs.getInt("ITEMCTRLCOMLD"));
					jtPreDict.setItemCtrlQI(rs.getInt("ITEMCTRQI"));
					jtPreDict.setItemCtrlTech(rs.getInt("ITEMCTRLTECH"));
					jtPreDict.setJcNum(rs.getString("JCNUM"));
					jtPreDict.setJcType(rs.getString("JCTYPE"));
					jtPreDict.setJwdCode(rs.getString("JWDCODE"));
					jtPreDict.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
					jtPreDict.setLead(rs.getString("LEAD"));
					jtPreDict.setPreDictId(rs.getInt("PREDICTID"));
					jtPreDict.setProTeamId(rs.getLong("PROTEAMID"));
					jtPreDict.setQi(rs.getString("QI"));
					jtPreDict.setQiAffiTime(rs.getString("QIAFFITIME"));
					jtPreDict.setReceiptPeo(rs.getString("RECEIPTPEO"));
					jtPreDict.setReceTime(rs.getString("RECETIME"));
					jtPreDict.setRecStas(rs.getShort("RECSTAS"));
					jtPreDict.setRepDate(rs.getString("REPDATE"));
					jtPreDict.setRepemp(rs.getString("REPEMP"));
					jtPreDict.setRepempNo(rs.getString("REPEMPNO"));
					jtPreDict.setRepPosi(rs.getString("REPPOSI"));
					jtPreDict.setRepsituation(rs.getString("REPSITUATION"));
					jtPreDict.setRepTime(rs.getString("REPTIME"));
					jtPreDict.setScore(rs.getInt("SCORE"));
					jtPreDict.setSmBzNames(rs.getString(""));
					jtPreDict.setSmpPreDictId(rs.getInt("SMPREDICTID"));
					jtPreDict.setTechAffiTime(rs.getString("TECHAFFITIME"));
					jtPreDict.setTechnician(rs.getString("TECHNICIAN"));
					jtPreDict.setThirdUnitId(rs.getLong("THIRDUNITID"));
					jtPreDict.setType(rs.getShort("TYPE"));
					jtPreDict.setUpPjNum(rs.getString("UPPJNUM"));
					jtPreDict.setVerifier(rs.getString("VERYFIER"));
					jtPreDict.setVerifyTime(rs.getString("VERIFYTIME"));
					jtPreDict.setZeroKiloRecId(rs.getInt("ZEROKILORECID"));
					return jtPreDict;
				}
			});
		}else if(flag instanceof Long){
			if(type==3){
				sql = "select * from JT_PREDICT jf where jf.DATEPLANPRI= '"+ datePlanPri +"' and jf.type=3 and jf.PROTEAMID= "+ flag +" and jf.recStas>=2 and jwdcode = '"+ jwdCode +"'";
			}else{
				sql = "select * from JT_PREDICT jf where jf.DATEPLANPRI= '"+ datePlanPri +"' and jf.type!=3 and jf.PROTEAMID= "+ flag +" and jf.recStas>=2 and jwdcode = '"+ jwdCode +"'";
			}
			JtPreDictList = jdbcTemplate.query(sql, new RowMapper<JtPreDict>() {
				public JtPreDict mapRow(ResultSet rs, int row) throws SQLException {
					JtPreDict jtPreDict = new JtPreDict();
					jtPreDict.setAccepter(rs.getString("ACCEPTER"));
					jtPreDict.setAcceTime(rs.getString("ACCETIME"));
					jtPreDict.setAffirmGrade(rs.getShort("AFFIRMGRADE"));
					jtPreDict.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
					jtPreDict.setCommitLd(rs.getString("COMMITLDID"));
					jtPreDict.setDatePlanPri(rs.getInt("DATEPLANPRI"));
					jtPreDict.setDealFixEmp(rs.getString("DEALFIXEMP"));
					jtPreDict.setDealSituation(rs.getString("DEALSITUATION"));
					jtPreDict.setDivisionId(rs.getInt("DIVISIONID"));
					jtPreDict.setFailNote(rs.getString("FAILNOTE"));
					jtPreDict.setFailNum(rs.getString("FAILNUM"));
					jtPreDict.setFixEmp(rs.getString("FIXEMP"));
					jtPreDict.setFixEmpId(rs.getString("FIXEMPID"));
					jtPreDict.setFixEndTime(rs.getString("FIXENDTIME"));
					jtPreDict.setImgUrl(rs.getString("IMGURL"));
					jtPreDict.setItemCtrlAcce(rs.getInt("ITEMCTRLACCE"));
					jtPreDict.setItemCtrlComLd(rs.getInt("ITEMCTRLCOMLD"));
					jtPreDict.setItemCtrlQI(rs.getInt("ITEMCTRQI"));
					jtPreDict.setItemCtrlTech(rs.getInt("ITEMCTRLTECH"));
					jtPreDict.setJcNum(rs.getString("JCNUM"));
					jtPreDict.setJcType(rs.getString("JCTYPE"));
					jtPreDict.setJwdCode(rs.getString("JWDCODE"));
					jtPreDict.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
					jtPreDict.setLead(rs.getString("LEAD"));
					jtPreDict.setPreDictId(rs.getInt("PREDICTID"));
					jtPreDict.setProTeamId(rs.getLong("PROTEAMID"));
					jtPreDict.setQi(rs.getString("QI"));
					jtPreDict.setQiAffiTime(rs.getString("QIAFFITIME"));
					jtPreDict.setReceiptPeo(rs.getString("RECEIPTPEO"));
					jtPreDict.setReceTime(rs.getString("RECETIME"));
					jtPreDict.setRecStas(rs.getShort("RECSTAS"));
					jtPreDict.setRepDate(rs.getString("REPDATE"));
					jtPreDict.setRepemp(rs.getString("REPEMP"));
					jtPreDict.setRepempNo(rs.getString("REPEMPNO"));
					jtPreDict.setRepPosi(rs.getString("REPPOSI"));
					jtPreDict.setRepsituation(rs.getString("REPSITUATION"));
					jtPreDict.setRepTime(rs.getString("REPTIME"));
					jtPreDict.setScore(rs.getInt("SCORE"));
					jtPreDict.setSmpPreDictId(rs.getInt("SMPREDICTID"));
					jtPreDict.setTechAffiTime(rs.getString("TECHAFFITIME"));
					jtPreDict.setTechnician(rs.getString("TECHNICIAN"));
					jtPreDict.setThirdUnitId(rs.getLong("THIRDUNITID"));
					jtPreDict.setType(rs.getShort("TYPE"));
					jtPreDict.setUpPjNum(rs.getString("UPPJNUM"));
					jtPreDict.setVerifier(rs.getString("VERYFIER"));
					jtPreDict.setVerifyTime(rs.getString("VERIFYTIME"));
					jtPreDict.setZeroKiloRecId(rs.getInt("ZEROKILORECID"));
					return jtPreDict;
				}
			});
		}
		return JtPreDictList;
	}

	@Override
	public List<JCFixrec> findRecByProTeam(String jwdCode, Integer rjhmId, Long teamId) {
		String sql = "select * from JC_FIXREC jf where jf.DYPRECID=? and jf.PROTEAM=? and jwdcode = ? order by jf.unitName, jf.secUnitName,jf.jcRecId";
		List<JCFixrec> jcFixrecs = jdbcTemplate.query(sql, new Object[]{rjhmId, teamId, jwdCode}, new RowMapper<JCFixrec>() {
			public JCFixrec mapRow(ResultSet rs, int row) throws SQLException {
				JCFixrec fixrec = new JCFixrec();
				fixrec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				fixrec.setAccepter(rs.getString("ACCEPTER"));
				fixrec.setBanzuId(rs.getLong("BZID"));
				fixrec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				fixrec.setCommitLead(rs.getString("COMMITLEAD"));
				fixrec.setDatePlanPri(rs.getInt("DYPRECID"));
				fixrec.setDoPjBarcode(rs.getString("DOPJBARCODE"));
				fixrec.setDownPjNum(rs.getString("DOWNPJNUM"));
				fixrec.setDuration(rs.getInt("DURATION"));
				fixrec.setEmpAfformTime(rs.getString("EMPAFFIRMTIME"));
				fixrec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				fixrec.setFixEmp(rs.getString("FIXEMP"));
				fixrec.setFixEmpId(rs.getString("FIXEMPID"));
				fixrec.setFixitem(rs.getString("ITEMNAME"));
				fixrec.setFixSituation(rs.getString("FIXSITUATION"));
				fixrec.setItemCtrlAcce(rs.getInt("ITEMCTRLACCE"));
				fixrec.setItemCtrlComLd(rs.getInt("ITEMCTRLCOMLD"));
				fixrec.setItemCtrlLead(rs.getInt("ITEMCTRLLEAD"));
				fixrec.setItemCtrlQI(rs.getInt("ITEMCTRLQI"));
				fixrec.setItemCtrlTech(rs.getInt("ITEMCTRLTECH"));
				fixrec.setItemName(rs.getString("ITEMNAME"));
				fixrec.setItemType(rs.getShort("ITEMTYPE"));
				fixrec.setJcNum(rs.getString("JCNUM"));
				fixrec.setJcRecId(rs.getLong("JCRECID"));
				fixrec.setJcType(rs.getString("JCTYPE"));
				fixrec.setJhTime(rs.getString("JHTIME"));
				fixrec.setJwdCode(rs.getString("JWDCODE"));
				fixrec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				fixrec.setLead(rs.getString("LEAD"));
				fixrec.setLeadMargin(rs.getString("LEFTMARGIN"));
				fixrec.setPjName(rs.getString("PJNAME"));
				fixrec.setPjStaticInfo(rs.getLong("PJSID"));
				fixrec.setPosiName(rs.getString("POSINAME"));
				fixrec.setQi(rs.getString("QI"));
				fixrec.setQiAffiTime(rs.getString("QIAFFITIME"));
				fixrec.setRecStas(rs.getShort("RECSTAS"));
				fixrec.setRightMargin(rs.getString("RIGHTMARGIN"));
				fixrec.setSecUnitName(rs.getString("SECUNITNAME"));
				fixrec.setTech(rs.getString("TECH"));
				fixrec.setTechAffiTime(rs.getString("TECHAFFITIME"));
				fixrec.setUnit(rs.getString("UNIT"));
				fixrec.setUnitName(rs.getString("UNITNAME"));
				fixrec.setUpPjBarcode(rs.getString("UPPJBARCODE"));
				fixrec.setUpPjNum(rs.getString("UPPJNUM"));
				return fixrec;
			}
		});
		return jcFixrecs;
	}

	@Override
	public List<DictProTeam> findAllZxProTeam(String jwdCode, String jcType, int workFlag) {
		String sql = "select * from DICT_PROTEAM proTeam where proTeam.jctype like '%"+ jcType +"%' and proTeam.zxFlag="+ workFlag +" and jwdcode = '"+ jwdCode +"'";
		List<DictProTeam> proTeams = jdbcTemplate.query(sql, new RowMapper<DictProTeam>() {
			public DictProTeam mapRow(ResultSet rs, int row) throws SQLException {
				DictProTeam proTeam = new DictProTeam();
				proTeam.setProteamid(rs.getLong("PROTEAMID"));
				proTeam.setProteamname(rs.getString("PROTEAMNAME"));
				proTeam.setJctype(rs.getString("JCTYPE"));
				proTeam.setJwdcode(rs.getString("JWDCODE"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setWorkflag(rs.getInt("WORKFLAG"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setZxFlag(rs.getInt("ZXFLAG"));
				return proTeam;
			}
		});
		return proTeams;
	}

	@Override
	public List<JCZXFixItem> findZXItem(String jwdCode, Integer nodeid, String xcxc, String jcsType, Long bzid,
			Integer firstUnitId) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("select * from JC_ZX_FIXITEM t where 1=1");
		if(nodeid != null){
			buffer.append(" and t.NODEID= "+ nodeid +"");
		}
		if(!"".equals(xcxc) && null != xcxc){
			buffer.append(" and t.xcxc like '%"+ xcxc +"%'");
		}
		if(!"".equals(jcsType) && null != jcsType){
			buffer.append(" and t.jcsType like '%"+ jcsType +"%'");
		}
		if(bzid != null){
			buffer.append(" and t.BZID = "+ bzid +"");
		}
		if(firstUnitId != null){
			buffer.append(" and t.FIRSTUNITID = "+ firstUnitId +"");
		}
		buffer.append(" order by t.FIRSTUNITID");
		List<JCZXFixItem> jczxFixItems = jdbcTemplate.query(buffer.toString(), new RowMapper<JCZXFixItem>() {
			public JCZXFixItem mapRow(ResultSet rs, int row) throws SQLException {
				JCZXFixItem jczxFixItem = new JCZXFixItem();
				jczxFixItem.setAmount(rs.getInt("AMOUNT"));
				jczxFixItem.setBzId(rs.getLong("BZID"));
				jczxFixItem.setDuration(rs.getInt("DURATION"));
				jczxFixItem.setFillDefaval(rs.getString("FILLDEFAVAL"));
				jczxFixItem.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				jczxFixItem.setId(rs.getInt("ID"));
				jczxFixItem.setItemCtrlAcce(rs.getShort("ITEMCTRLACCE"));
				jczxFixItem.setItemCtrlComld(rs.getShort("ITEMCTRLCOMLD"));
				jczxFixItem.setItemCtrlLead(rs.getShort("ITEMCTRLLEAD"));
				jczxFixItem.setItemCtrlQi(rs.getShort("ITEMCTRLQI"));
				jczxFixItem.setItemCtrlRept(rs.getShort("ITEMCTRLREPT"));
				jczxFixItem.setItemCtrlTech(rs.getShort("ITEMCTRLTECH"));
				jczxFixItem.setItemName(rs.getString("ITEMNAME"));
				jczxFixItem.setItemPy(rs.getString("ITEMPY"));
				jczxFixItem.setItemRelation(rs.getString("ITEMRELATION"));
				jczxFixItem.setItemSn(rs.getInt("ITEMSN"));
				jczxFixItem.setJc(rs.getString("JC"));
				jczxFixItem.setJcsType(rs.getString("JCSTYPE"));
				jczxFixItem.setJwdCode(rs.getString("JWDCODE"));
				jczxFixItem.setMax(rs.getFloat("MAX"));
				jczxFixItem.setMin(rs.getFloat("MIN"));
				jczxFixItem.setNodeId(rs.getInt("NODEID"));
				jczxFixItem.setParentId(rs.getInt("PARENTID")); 
				jczxFixItem.setPosiName(rs.getString("POSINAME"));
				jczxFixItem.setProTeam(rs.getString("PROTEAM"));
				jczxFixItem.setStdPJId(rs.getLong("STDPJID"));
				jczxFixItem.setTechStandard(rs.getString("TECHSTANDARD"));
				jczxFixItem.setTimeNum(rs.getString("TIMENUM"));
				jczxFixItem.setUnit(rs.getString("UNIT"));
				jczxFixItem.setXcxc(rs.getString("XCXC"));
				jczxFixItem.setYsId(rs.getInt("YSID"));
				jczxFixItem.setUnitName(rs.getString("UNITNAME"));
				return jczxFixItem;
			}
		});
		return jczxFixItems;
	}

	@Override
	public List<JCZXFixRec> findZxRecByProTeam(String jwdCode, Integer rjhmId, Long teamId, Integer nodeId) {
		String sql = "";
		if(nodeId==null){
			sql = "select * from JC_ZX_FIXREC jf where jf.DYPRECID= "+ rjhmId +" and jf.bzId= "+ teamId +" and jwdcode = '"+ jwdCode +"'order by jf.nodeId,jf.unitName";
		}else{
			sql = "select * from JC_ZX_FIXREC jf where jf.DYPRECID= "+ rjhmId +" and jf.bzId= "+ teamId +" and jf.nodeId= "+ nodeId +" and jwdCode = '"+ jwdCode +"' order by jf.unitName";
		}
		List<JCZXFixRec> jczxFixRecs = jdbcTemplate.query(sql, new RowMapper<JCZXFixRec>() {
			public JCZXFixRec mapRow(ResultSet rs, int row) throws SQLException {
				JCZXFixRec jczxFixRec = new JCZXFixRec();
				jczxFixRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jczxFixRec.setAcceptEr(rs.getString("ACCEPTER"));
				jczxFixRec.setAcceptErId(rs.getLong("ACCEPTERID"));
				jczxFixRec.setBzId(rs.getLong("BZID"));
				jczxFixRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jczxFixRec.setCommitLead(rs.getString("COMMITLEAD"));
				jczxFixRec.setCommitLeadId(rs.getLong("COMMITLEADID"));
				jczxFixRec.setDealSituation(rs.getString("DEALSITUATION"));
				jczxFixRec.setDownPjBarCode(rs.getString("DOWNPJBARCODE"));
				jczxFixRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jczxFixRec.setDuration(rs.getInt("DURATION"));
				jczxFixRec.setDyPrecId(rs.getInt("DYPRECID"));
				jczxFixRec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				jczxFixRec.setFixEmp(rs.getString("FIXEMP"));
				jczxFixRec.setFixEmpId(rs.getString("FIXEMPID"));
				jczxFixRec.setFixSituation(rs.getString("FIXSITUATION"));
				jczxFixRec.setId(rs.getLong("ID"));
				jczxFixRec.setItemCtrlAcce(rs.getShort("ITEMCTRLACCE"));
				jczxFixRec.setItemCtrlComld(rs.getShort("ITEMCTRLCOMLD"));
				jczxFixRec.setItemCtrlLead(rs.getShort("ITEMCTRLLEAD"));
				jczxFixRec.setItemCtrlQi(rs.getShort("ITEMCTRLQI"));
				jczxFixRec.setItemCtrlRept(rs.getShort("ITEMCTRLREPT"));
				jczxFixRec.setItemCtrlTech(rs.getShort("ITEMCTRLTECH"));
				jczxFixRec.setItemId(rs.getLong("ITEMID"));
				jczxFixRec.setItemName(rs.getString("ITEMNAME"));
				jczxFixRec.setItemType(rs.getShort("ITEMTYPE"));
				jczxFixRec.setJcNum(rs.getString("JCNUM"));
				jczxFixRec.setJcType(rs.getString("JCTYPE"));
				jczxFixRec.setJhTime(rs.getString("JHTIME"));
				jczxFixRec.setJwdCode(rs.getString("JWDCODE"));
				jczxFixRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jczxFixRec.setLead(rs.getString("LEAD"));
				jczxFixRec.setLeadId(rs.getLong("LEADID"));
				jczxFixRec.setNodeId(rs.getInt("NODEID"));
				jczxFixRec.setPosiName(rs.getString("POSINAME"));
				jczxFixRec.setQi(rs.getString("QI"));
				jczxFixRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jczxFixRec.setQiId(rs.getLong("QIID"));
				jczxFixRec.setRecStas(rs.getShort("RECSTAS"));
				jczxFixRec.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				jczxFixRec.setRept(rs.getString("REPT"));
				jczxFixRec.setReptId(rs.getLong("REPTID"));
				jczxFixRec.setTeachAffiTime(rs.getString("TEALDAFFITIME"));
				jczxFixRec.setTeachId(rs.getLong("TEACHID"));
				jczxFixRec.setTeachName(rs.getString("TEACHNAME"));
				jczxFixRec.setUnit(rs.getString("UNIT"));
				jczxFixRec.setUnitName(rs.getString("UNITNAME"));
				jczxFixRec.setUpPjBarCode(rs.getString("UPPJBARCODE"));
				jczxFixRec.setUpPjNum(rs.getString("UPPJNUM"));
				return jczxFixRec;
			}
		});
		return jczxFixRecs;
	}

	@Override
	public PageModel findJCFixrecLimited(String jwdCode, Integer datePlanPri, String unit) {
		int pageSize = PageContext.getPageSize();
		int min = PageContext.getOffSet();
		int max = min + pageSize;
		String countSql = "select count(*) from JC_FIXREC jf where jf.DYPRECID=? and jwdcode = ? order by jf.secUnitName,jf.jcRecId";
		int count = jdbcTemplate.queryForInt(countSql, new Object[]{datePlanPri, jwdCode});
		String sql = "select * from (select t.*, rownum rn from (select * from JC_FIXREC jf where jf.DYPRECID=? and jwdcode = ? order by jf.secUnitName,jf.jcRecId) t where rownum <= "+ max +") where rn >= "+ min +"";
		List<JCFixrec> jcFixrecs = jdbcTemplate.query(sql, new Object[]{datePlanPri, jwdCode}, new RowMapper<JCFixrec>() {
			public JCFixrec mapRow(ResultSet rs, int row) throws SQLException {
				JCFixrec fixrec = new JCFixrec();
				fixrec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				fixrec.setAccepter(rs.getString("ACCEPTER"));
				fixrec.setBanzuId(rs.getLong("BZID"));
				fixrec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				fixrec.setCommitLead(rs.getString("COMMITLEAD"));
				fixrec.setDatePlanPri(rs.getInt("DYPRECID"));
				fixrec.setDoPjBarcode(rs.getString("DOPJBARCODE"));
				fixrec.setDownPjNum(rs.getString("DOWNPJNUM"));
				fixrec.setDuration(rs.getInt("DURATION"));
				fixrec.setEmpAfformTime(rs.getString("EMPAFFIRMTIME"));
				fixrec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				fixrec.setFixEmp(rs.getString("FIXEMP"));
				fixrec.setFixEmpId(rs.getString("FIXEMPID"));
				fixrec.setFixitem(rs.getString("ITEMNAME"));
				fixrec.setFixSituation(rs.getString("FIXSITUATION"));
				fixrec.setItemCtrlAcce(rs.getInt("ITEMCTRLACCE"));
				fixrec.setItemCtrlComLd(rs.getInt("ITEMCTRLCOMLD"));
				fixrec.setItemCtrlLead(rs.getInt("ITEMCTRLLEAD"));
				fixrec.setItemCtrlQI(rs.getInt("ITEMCTRLQI"));
				fixrec.setItemCtrlTech(rs.getInt("ITEMCTRLTECH"));
				fixrec.setItemName(rs.getString("ITEMNAME"));
				fixrec.setItemType(rs.getShort("ITEMTYPE"));
				fixrec.setJcNum(rs.getString("JCNUM"));
				fixrec.setJcRecId(rs.getLong("JCRECID"));
				fixrec.setJcType(rs.getString("JCTYPE"));
				fixrec.setJhTime(rs.getString("JHTIME"));
				fixrec.setJwdCode(rs.getString("JWDCODE"));
				fixrec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				fixrec.setLead(rs.getString("LEAD"));
				fixrec.setLeadMargin(rs.getString("LEFTMARGIN"));
				fixrec.setPjName(rs.getString("PJNAME"));
				fixrec.setPjStaticInfo(rs.getLong("PJSID"));
				fixrec.setPosiName(rs.getString("POSINAME"));
				fixrec.setQi(rs.getString("QI"));
				fixrec.setQiAffiTime(rs.getString("QIAFFITIME"));
				fixrec.setRecStas(rs.getShort("RECSTAS"));
				fixrec.setRightMargin(rs.getString("RIGHTMARGIN"));
				fixrec.setSecUnitName(rs.getString("SECUNITNAME"));
				fixrec.setTech(rs.getString("TECH"));
				fixrec.setTechAffiTime(rs.getString("TECHAFFITIME"));
				fixrec.setUnit(rs.getString("UNIT"));
				fixrec.setUnitName(rs.getString("UNITNAME"));
				fixrec.setUpPjBarcode(rs.getString("UPPJBARCODE"));
				fixrec.setUpPjNum(rs.getString("UPPJNUM"));
				return fixrec;
			}
		});
		PageModel pageModel = new PageModel();
		pageModel.setDatas(jcFixrecs);
		pageModel.setCount(count);
		pageModel.setOffset(min);
		pageModel.setPageSize(pageSize);
		return pageModel;
	}

	@Override
	public PageModel findJCZXFixRecLimited(String jwdCode, Integer datePlanPri) {
		int pageSize = PageContext.getPageSize();
		int min = PageContext.getOffSet();
		int max = min + pageSize;
		String countSql = "select count(*) from JC_ZX_FIXREC jfr where jfr.DYPRECID= "+ datePlanPri +" and jwdcode = '"+ jwdCode+"' order by jfr.nodeId,jfr.unitName,jfr.id";
		int count = jdbcTemplate.queryForInt(countSql);
		String sql = "select * from (select t.*, rownum rn from (select * from JC_ZX_FIXREC jfr where jfr.DYPRECID= "+ datePlanPri +" and jwdcode = '"+ jwdCode+"' order by jfr.nodeId,jfr.unitName,jfr.id) t where rownum <= "+ max +") where rn >= "+ min +"";
		List<JCZXFixRec> jczxFixRecs = jdbcTemplate.query(sql, new RowMapper<JCZXFixRec>() {
			public JCZXFixRec mapRow(ResultSet rs, int row) throws SQLException {
				JCZXFixRec jczxFixRec = new JCZXFixRec();
				jczxFixRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jczxFixRec.setAcceptEr(rs.getString("ACCEPTER"));
				jczxFixRec.setAcceptErId(rs.getLong("ACCEPTERID"));
				jczxFixRec.setBzId(rs.getLong("BZID"));
				jczxFixRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jczxFixRec.setCommitLead(rs.getString("COMMITLEAD"));
				jczxFixRec.setCommitLeadId(rs.getLong("COMMITLEADID"));
				jczxFixRec.setDealSituation(rs.getString("DEALSITUATION"));
				jczxFixRec.setDownPjBarCode(rs.getString("DOWNPJBARCODE"));
				jczxFixRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jczxFixRec.setDuration(rs.getInt("DURATION"));
				jczxFixRec.setDyPrecId(rs.getInt("DYPRECID"));
				jczxFixRec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				jczxFixRec.setFixEmp(rs.getString("FIXEMP"));
				jczxFixRec.setFixEmpId(rs.getString("FIXEMPID"));
				jczxFixRec.setFixSituation(rs.getString("FIXSITUATION"));
				jczxFixRec.setId(rs.getLong("ID"));
				jczxFixRec.setItemCtrlAcce(rs.getShort("ITEMCTRLACCE"));
				jczxFixRec.setItemCtrlComld(rs.getShort("ITEMCTRLCOMLD"));
				jczxFixRec.setItemCtrlLead(rs.getShort("ITEMCTRLLEAD"));
				jczxFixRec.setItemCtrlQi(rs.getShort("ITEMCTRLQI"));
				jczxFixRec.setItemCtrlRept(rs.getShort("ITEMCTRLREPT"));
				jczxFixRec.setItemCtrlTech(rs.getShort("ITEMCTRLTECH"));
				jczxFixRec.setItemId(rs.getLong("ITEMID"));
				jczxFixRec.setItemName(rs.getString("ITEMNAME"));
				jczxFixRec.setItemType(rs.getShort("ITEMTYPE"));
				jczxFixRec.setJcNum(rs.getString("JCNUM"));
				jczxFixRec.setJcType(rs.getString("JCTYPE"));
				jczxFixRec.setJhTime(rs.getString("JHTIME"));
				jczxFixRec.setJwdCode(rs.getString("JWDCODE"));
				jczxFixRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jczxFixRec.setLead(rs.getString("LEAD"));
				jczxFixRec.setLeadId(rs.getLong("LEADID"));
				jczxFixRec.setNodeId(rs.getInt("NODEID"));
				jczxFixRec.setPosiName(rs.getString("POSINAME"));
				jczxFixRec.setQi(rs.getString("QI"));
				jczxFixRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jczxFixRec.setQiId(rs.getLong("QIID"));
				jczxFixRec.setRecStas(rs.getShort("RECSTAS"));
				jczxFixRec.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				jczxFixRec.setRept(rs.getString("REPT"));
				jczxFixRec.setReptId(rs.getLong("REPTID"));
				jczxFixRec.setTeachAffiTime(rs.getString("TEALDAFFITIME"));
				jczxFixRec.setTeachId(rs.getLong("TEACHID"));
				jczxFixRec.setTeachName(rs.getString("TEACHNAME"));
				jczxFixRec.setUnit(rs.getString("UNIT"));
				jczxFixRec.setUnitName(rs.getString("UNITNAME"));
				jczxFixRec.setUpPjBarCode(rs.getString("UPPJBARCODE"));
				jczxFixRec.setUpPjNum(rs.getString("UPPJNUM"));
				return jczxFixRec;
			}
		});
		PageModel pageModel = new PageModel();
		pageModel.setDatas(jczxFixRecs);
		pageModel.setCount(count);
		pageModel.setOffset(min);
		pageModel.setPageSize(pageSize);
		return pageModel;
	}

	@Override
	public List<DatePlanPri> findJCHistory(String jwdCode, String jcNum, String jcStype) {
		String sql = "select d.*, u.xm from users_privs u right join (select dpp.*, jf.nodename from DATEPLAN_PRI dpp left join JC_FIXFLOW jf on dpp.nodeid = jf.jcflowid where dpp.jctype = '"+ jcStype +"' and dpp.jcnum='"+ jcNum +"' and dpp.jwdcode = '"+ jwdCode +"' and jf.jwdcode = '"+ jwdCode +"' order by dpp.kcsj desc) d on u.userid = d.gongzhang where u.jwdcode = '"+ jwdCode +"'";
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
				planPri.setGongZhang(rs.getString("XM"));
				planPri.setPlanStatue(rs.getInt("PLANSTATUE"));
				planPri.setGdh(rs.getString("GDH"));
				planPri.setTwh(rs.getString("TWH"));
				planPri.setZdr(rs.getString("ZDR"));
				planPri.setZdsj(rs.getString("ZDSJ"));
				planPri.setNodeid(rs.getInt("NODEID"));
				planPri.setNodeName(rs.getString("NODENAME"));
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
	public SignedForFinish getJCjungong(String jwdCode, int rjhmId) {
		String sql = "select * from SIGNEDFORFINISH t where t.dpId=? and jwdcode = ?";
		final SignedForFinish signedForFinish = new SignedForFinish(); 
		jdbcTemplate.query(sql, new Object[]{rjhmId, jwdCode}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{ 
	        	signedForFinish.setBlongArea(rs.getString("BLONGAREA"));
				signedForFinish.setDpId(rs.getInt("DPID"));
				signedForFinish.setDzid(rs.getString("DZID"));
				signedForFinish.setDzxm(rs.getString("DZXM"));
				signedForFinish.setFixArea(rs.getString("FIXAREA"));
				signedForFinish.setJcgzid(rs.getString("JCGZID"));
				signedForFinish.setJcgzxm(rs.getString("JCGZXM"));
				signedForFinish.setJch(rs.getString("JCH"));
				signedForFinish.setJclx(rs.getString("JCLX"));
				signedForFinish.setJgId(rs.getInt("JGID"));
				signedForFinish.setJssj(rs.getString("JSSJ"));
				signedForFinish.setJxzrid(rs.getString("JXZRID"));
				signedForFinish.setJxzrxm(rs.getString("JXZRXM"));
				signedForFinish.setKssj(rs.getString("KSSJ"));
				signedForFinish.setReason(rs.getString("REASON"));
				signedForFinish.setType(rs.getShort("TYPE"));
				signedForFinish.setXcxc(rs.getString("XCXC"));
				signedForFinish.setYsyid(rs.getString("YSYID"));
				signedForFinish.setYsyxm(rs.getString("YSYXM"));
				signedForFinish.setJwdCode(rs.getString("jwdcode"));
			} 
		});  
		return signedForFinish;
	}

	@Override
	public List<YSJCRec> listYSJCRec(String jwdCode, int rjhmId) {
		String sql = "select * from YSJC_REC t where t.RJHMID=?  and jwdcode = ? order by t.orderNo";
		List<YSJCRec> ysjcRecs = jdbcTemplate.query(sql, new Object[]{rjhmId, jwdCode}, new RowMapper<YSJCRec>() {
			public YSJCRec mapRow(ResultSet rs, int row) throws SQLException {
				YSJCRec ysjcRec = new YSJCRec();
				ysjcRec.setAccept(rs.getString("ACCEPT"));
				ysjcRec.setAcceptAffiTime(rs.getString("ACCEPTAFFITIME"));
				ysjcRec.setClassify(rs.getString("CLASSIFY"));
				ysjcRec.setCommAffiTime(rs.getString("COMMAFFITIME"));
				ysjcRec.setCommitLead(rs.getString("COMMITLEAD"));
				ysjcRec.setDatePlanPri(rs.getInt("RJHMID"));
				ysjcRec.setFixemp(rs.getString("FIXEMP"));
				ysjcRec.setFixEmpTime(rs.getString("FIXEMPTIME"));
				ysjcRec.setFixSituation(rs.getString("FIXSITUATION"));
				ysjcRec.setItem(rs.getInt("ITEMID"));
				ysjcRec.setItemName(rs.getString("ITEMNAME"));
				ysjcRec.setOrderNo(rs.getInt("ORDERNO"));
				ysjcRec.setRecId(rs.getInt("RECID"));
				ysjcRec.setTech(rs.getString("TECH"));
				ysjcRec.setTechAffiTime(rs.getString("TECHAFFITIME"));
				ysjcRec.setUnit(rs.getString("UNIT"));
				ysjcRec.setJwdCode(rs.getString("JWDCODE"));
				return ysjcRec;
			}
		});
		return ysjcRecs;
	}

	@Override
	public OilAssayPriRecorder findOilAssayRecByDailyId(String jwdCode, Integer rjhmId) {
		String sql = "select * from OIL_ASSAY_PRIRECORDER where WEKPRECID="+ rjhmId +" and jwdcode = '"+ jwdCode +"'";
		final OilAssayPriRecorder oilAssayPriRecorder = new OilAssayPriRecorder();
		jdbcTemplate.query(sql, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{ 
				oilAssayPriRecorder.setAreaId(rs.getString("AREAID"));
				oilAssayPriRecorder.setDealAdvice(rs.getShort("DEALADVICE"));
				oilAssayPriRecorder.setDetecteStatus(rs.getShort("DETECTESTATUS"));
				oilAssayPriRecorder.setDpId(rs.getInt("WEKPRECID"));
				oilAssayPriRecorder.setFinTime(rs.getString("FINTIME"));
				oilAssayPriRecorder.setJcNum(rs.getString("JCNUM"));
				oilAssayPriRecorder.setJcsTypeVal(rs.getString("JCSTYPEVAL"));
				oilAssayPriRecorder.setJwdCode(rs.getString("JWDCODE"));
				oilAssayPriRecorder.setQuasituation(rs.getShort("QUASITUATION"));
				oilAssayPriRecorder.setReceiptPeo(rs.getString("RECEIPTPEO"));
				oilAssayPriRecorder.setRecesamTime(rs.getString("RECESAMTIME"));
				oilAssayPriRecorder.setRecPriId(rs.getLong("RECPRIID"));
				oilAssayPriRecorder.setSentsamPeo(rs.getString("SENTSAMPEO"));
			} 
		});  
		return oilAssayPriRecorder;
	}

	@Override
	public List<OilAssayItem> findOilAssayItemByJcType(String jwdCode, String jcType) {
		String sql = "select * from OIL_ASSAY_ITEM item where ISUSED=1 and JCVALUE like '%"+jcType+"、%' and jwdcode = '"+ jwdCode +"' order by SERES";
		List<OilAssayItem> oilAssayItems = jdbcTemplate.query(sql, new RowMapper<OilAssayItem>() {
			public OilAssayItem mapRow(ResultSet rs, int row) throws SQLException {
				OilAssayItem oilAssayItem = new OilAssayItem();
				oilAssayItem.setIsused(rs.getShort("ISUSED"));
				oilAssayItem.setJcValue(rs.getString("JCVALUE"));
				oilAssayItem.setReportItemDefin(rs.getString("REPORTITEMDEFIN"));
				oilAssayItem.setReportItemId(rs.getInt("REPORTITEMID"));
				oilAssayItem.setReportItemPy(rs.getString("REPORTITEMPY"));
				oilAssayItem.setSeries(rs.getInt("SERES"));
				oilAssayItem.setJwdCode(rs.getString("JWDCODE"));
				return oilAssayItem;
			}
		});
		return oilAssayItems;
	}

	@Override
	public int findOilDetailRecorderCount(String jwdCode, int itemId, long recId) {
		String sql="select detail.* from oil_assay_detailrecorer detail,oil_assay_subitem sub where detail.subitemid=sub.subitemid and detail.recpriid=? and sub.reportitemid=? and jwdcode = ?";
		return jdbcTemplate.queryForInt(sql, new Object[]{recId, itemId, jwdCode});
	}

	@Override
	public List<Object[]> findOilDetailRecorder(String jwdCode, int itemId, long recId) {
		String sql = "select detail.recdetailid,detail.subitemtitle,sub.minval,sub.maxval,detail.realdeteval,detail.quagrade,detail.receiptpeo,detail.fintime from oil_assay_detailrecorer detail,oil_assay_subitem sub where detail.subitemid=sub.subitemid and detail.recpriid=? and sub.reportitemid=? and jwdcode = ?";
		List<Object[]> list = jdbcTemplate.queryForList(sql, Object[].class, new Object[]{recId, itemId, jwdCode});
		return list;
	}

	@Override
	public List<JCFixrec> listLeftWorkRecord(String jwdCode, Integer rjhmId) {
		String sql = "select * from JC_FIXREC po where po.DYPRECID=? and jwdcode = ?";
		List<JCFixrec> jcFixrecs = jdbcTemplate.query(sql, new Object[]{rjhmId, jwdCode}, new RowMapper<JCFixrec>() {
			public JCFixrec mapRow(ResultSet rs, int row) throws SQLException {
				JCFixrec fixrec = new JCFixrec();
				fixrec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				fixrec.setAccepter(rs.getString("ACCEPTER"));
				fixrec.setBanzuId(rs.getLong("BZID"));
				fixrec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				fixrec.setCommitLead(rs.getString("COMMITLEAD"));
				fixrec.setDatePlanPri(rs.getInt("DYPRECID"));
				fixrec.setDoPjBarcode(rs.getString("DOPJBARCODE"));
				fixrec.setDownPjNum(rs.getString("DOWNPJNUM"));
				fixrec.setDuration(rs.getInt("DURATION"));
				fixrec.setEmpAfformTime(rs.getString("EMPAFFIRMTIME"));
				fixrec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				fixrec.setFixEmp(rs.getString("FIXEMP"));
				fixrec.setFixEmpId(rs.getString("FIXEMPID"));
				fixrec.setFixitem(rs.getString("ITEMNAME"));
				fixrec.setFixSituation(rs.getString("FIXSITUATION"));
				fixrec.setItemCtrlAcce(rs.getInt("ITEMCTRLACCE"));
				fixrec.setItemCtrlComLd(rs.getInt("ITEMCTRLCOMLD"));
				fixrec.setItemCtrlLead(rs.getInt("ITEMCTRLLEAD"));
				fixrec.setItemCtrlQI(rs.getInt("ITEMCTRLQI"));
				fixrec.setItemCtrlTech(rs.getInt("ITEMCTRLTECH"));
				fixrec.setItemName(rs.getString("ITEMNAME"));
				fixrec.setItemType(rs.getShort("ITEMTYPE"));
				fixrec.setJcNum(rs.getString("JCNUM"));
				fixrec.setJcRecId(rs.getLong("JCRECID"));
				fixrec.setJcType(rs.getString("JCTYPE"));
				fixrec.setJhTime(rs.getString("JHTIME"));
				fixrec.setJwdCode(rs.getString("JWDCODE"));
				fixrec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				fixrec.setLead(rs.getString("LEAD"));
				fixrec.setLeadMargin(rs.getString("LEFTMARGIN"));
				fixrec.setPjName(rs.getString("PJNAME"));
				fixrec.setPjStaticInfo(rs.getLong("PJSID"));
				fixrec.setPosiName(rs.getString("POSINAME"));
				fixrec.setQi(rs.getString("QI"));
				fixrec.setQiAffiTime(rs.getString("QIAFFITIME"));
				fixrec.setRecStas(rs.getShort("RECSTAS"));
				fixrec.setRightMargin(rs.getString("RIGHTMARGIN"));
				fixrec.setSecUnitName(rs.getString("SECUNITNAME"));
				fixrec.setTech(rs.getString("TECH"));
				fixrec.setTechAffiTime(rs.getString("TECHAFFITIME"));
				fixrec.setUnit(rs.getString("UNIT"));
				fixrec.setUnitName(rs.getString("UNITNAME"));
				fixrec.setUpPjBarcode(rs.getString("UPPJBARCODE"));
				fixrec.setUpPjNum(rs.getString("UPPJNUM"));
				return fixrec;
			}
		});
		return jcFixrecs;
	}

	@Override
	public List<JCZXFixRec> listZXLeftWorkRecord(String jwdCode, Integer rjhmId) {
		String sql = "select * from JC_ZX_FIXREC po where po.DYPRECID=? and jwdcode = ?";
		List<JCZXFixRec> jczxFixRecs = jdbcTemplate.query(sql, new Object[]{rjhmId, jwdCode}, new RowMapper<JCZXFixRec>() {
			public JCZXFixRec mapRow(ResultSet rs, int row) throws SQLException {
				JCZXFixRec jczxFixRec = new JCZXFixRec();
				jczxFixRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jczxFixRec.setAcceptEr(rs.getString("ACCEPTER"));
				jczxFixRec.setAcceptErId(rs.getLong("ACCEPTERID"));
				jczxFixRec.setBzId(rs.getLong("BZID"));
				jczxFixRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jczxFixRec.setCommitLead(rs.getString("COMMITLEAD"));
				jczxFixRec.setCommitLeadId(rs.getLong("COMMITLEADID"));
				jczxFixRec.setDealSituation(rs.getString("DEALSITUATION"));
				jczxFixRec.setDownPjBarCode(rs.getString("DOWNPJBARCODE"));
				jczxFixRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jczxFixRec.setDuration(rs.getInt("DURATION"));
				jczxFixRec.setDyPrecId(rs.getInt("DYPRECID"));
				jczxFixRec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				jczxFixRec.setFixEmp(rs.getString("FIXEMP"));
				jczxFixRec.setFixEmpId(rs.getString("FIXEMPID"));
				jczxFixRec.setFixSituation(rs.getString("FIXSITUATION"));
				jczxFixRec.setId(rs.getLong("ID"));
				jczxFixRec.setItemCtrlAcce(rs.getShort("ITEMCTRLACCE"));
				jczxFixRec.setItemCtrlComld(rs.getShort("ITEMCTRLCOMLD"));
				jczxFixRec.setItemCtrlLead(rs.getShort("ITEMCTRLLEAD"));
				jczxFixRec.setItemCtrlQi(rs.getShort("ITEMCTRLQI"));
				jczxFixRec.setItemCtrlRept(rs.getShort("ITEMCTRLREPT"));
				jczxFixRec.setItemCtrlTech(rs.getShort("ITEMCTRLTECH"));
				jczxFixRec.setItemId(rs.getLong("ITEMID"));
				jczxFixRec.setItemName(rs.getString("ITEMNAME"));
				jczxFixRec.setItemType(rs.getShort("ITEMTYPE"));
				jczxFixRec.setJcNum(rs.getString("JCNUM"));
				jczxFixRec.setJcType(rs.getString("JCTYPE"));
				jczxFixRec.setJhTime(rs.getString("JHTIME"));
				jczxFixRec.setJwdCode(rs.getString("JWDCODE"));
				jczxFixRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jczxFixRec.setLead(rs.getString("LEAD"));
				jczxFixRec.setLeadId(rs.getLong("LEADID"));
				jczxFixRec.setNodeId(rs.getInt("NODEID"));
				jczxFixRec.setPosiName(rs.getString("POSINAME"));
				jczxFixRec.setQi(rs.getString("QI"));
				jczxFixRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jczxFixRec.setQiId(rs.getLong("QIID"));
				jczxFixRec.setRecStas(rs.getShort("RECSTAS"));
				jczxFixRec.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				jczxFixRec.setRept(rs.getString("REPT"));
				jczxFixRec.setReptId(rs.getLong("REPTID"));
				jczxFixRec.setTeachAffiTime(rs.getString("TEALDAFFITIME"));
				jczxFixRec.setTeachId(rs.getLong("TEACHID"));
				jczxFixRec.setTeachName(rs.getString("TEACHNAME"));
				jczxFixRec.setUnit(rs.getString("UNIT"));
				jczxFixRec.setUnitName(rs.getString("UNITNAME"));
				jczxFixRec.setUpPjBarCode(rs.getString("UPPJBARCODE"));
				jczxFixRec.setUpPjNum(rs.getString("UPPJNUM"));
				return jczxFixRec;
			}
		});
		return jczxFixRecs;
	}

	@Override
	public PageModel findPJDynamicInfo(String jwdCode, String jcsType, String firstUnitId, String pjName, String pjNum, String pjStatus,
			String storePosition, String getOnNum, String bzId) {
		int min = PageContext.getOffSet();
		int max = PageContext.getOffSet() + PageContext.getPageSize();
		StringBuilder builder = new StringBuilder();
		StringBuilder countBuilder = new StringBuilder();
		builder.append("select * from ");
		builder.append("(select t.*, rownum rn from ");
		builder.append("(select pjd.*, pjs.PJNAME pjSname, pjs.JCTYPE, pjs.FIRSTUNITID, pjs.BZIDS from PJ_DYNAMICINFO pjd left join PJ_STATICINFO pjs on pjd.PJSID = pjs.PJSID where 1=1");
		countBuilder.append("select count(*) from ");
		countBuilder.append("(select pjd.*, pjs.PJNAME pjSname, pjs.JCTYPE, pjs.FIRSTUNITID, pjs.BZIDS from PJ_DYNAMICINFO pjd left join PJ_STATICINFO pjs on pjd.PJSID = pjs.PJSID where 1=1");
		if (jcsType != null && !jcsType.equals("")) {
			builder.append(" and JCTYPE like '%" + jcsType + ",%'");
			countBuilder.append(" and JCTYPE like '%" + jcsType + ",%'");
		}
		if (firstUnitId != null && !firstUnitId.equals("")) {
			builder.append(" and FIRSTUNITID= "+ Long.parseLong(firstUnitId) +"");
			countBuilder.append(" and FIRSTUNITID= "+ Long.parseLong(firstUnitId) +"");
		}
		if (pjName != null && !pjName.equals("")) {
			builder.append(" and pjs.pjName = '" + pjName + "' and pjd.pjName = '" + pjName + "'");
			countBuilder.append(" and pjs.pjName = '" + pjName + "' and pjd.pjName = '" + pjName + "'");
		}
		if (pjNum != null && !pjNum.equals("")) {
			builder.append(" and pjd.pjNum like '%" + pjNum + "%'");
			countBuilder.append(" and pjd.pjNum like '%" + pjNum + "%'");
		}
		if (pjStatus != null && !pjStatus.equals("")) {
			builder.append(" and pjd.pjStatus="+ Integer.parseInt(pjStatus) +"");
			countBuilder.append(" and pjd.pjStatus="+ Integer.parseInt(pjStatus) +"");
		}
		if (storePosition != null && !storePosition.equals("")) {
			builder.append(" and pjd.storePosition= "+ Integer.parseInt(storePosition) +"");
			countBuilder.append(" and pjd.storePosition= "+ Integer.parseInt(storePosition) +"");
		}
		if (getOnNum != null && !"".equals(getOnNum)) {
			builder.append(" and pjd.getOnNum like '%" + getOnNum + "%'");
			countBuilder.append(" and pjd.getOnNum like '%" + getOnNum + "%'");
		}
		if (bzId != null && !"".equals(bzId)) {
			builder.append(" and pjs.BZIDS like '%" + bzId + "%'");
			countBuilder.append(" and pjs.BZIDS like '%" + bzId + "%'");
		}
		if (jwdCode != null && !"".equals(jwdCode)) {
			builder.append(" and pjd.jwdcode =  '"+ jwdCode +"'");
			countBuilder.append(" and pjd.jwdcode =  '"+ jwdCode +"'");
		}
		builder.append(" order by pjd.pjStatus,pjd.storePosition asc,pjd.pjdid asc) t");
		builder.append(" where rownum <= "+ max +")");
		builder.append(" where rn >= "+ min +"");
		countBuilder.append(" )");
		List<PJDynamicInfo> dynamicInfos = jdbcTemplate.query(builder.toString(), new RowMapper<PJDynamicInfo>() {
			public PJDynamicInfo mapRow(ResultSet rs, int row) throws SQLException {
				PJDynamicInfo dynamicInfo = new PJDynamicInfo();
				dynamicInfo.setComments(rs.getString("COMMENTS"));
				dynamicInfo.setFactory(rs.getString("FACTORY"));
				dynamicInfo.setFactoryNum(rs.getString("FACTORYNUM"));
				dynamicInfo.setFixFlowName(rs.getString("FIXFLOWNAME"));
				dynamicInfo.setGetOnNum(rs.getString("GETONNUM"));
				dynamicInfo.setJwdCode(rs.getString("JWDCODE"));
				dynamicInfo.setOutFacDate(rs.getDate("OUTFACDATE"));
				dynamicInfo.setPjBarCode(rs.getString("PJBARCODE"));
				dynamicInfo.setPjdid(rs.getLong("PJDID"));
				dynamicInfo.setPjName(rs.getString("PJNAME"));
				dynamicInfo.setPjNum(rs.getString("PJNUM"));
				dynamicInfo.setPjStaticInfo(rs.getLong("PJSID"));
				dynamicInfo.setPjStatus(rs.getInt("PJSTATUS"));
				dynamicInfo.setPy(rs.getString("PY"));
				dynamicInfo.setStorePosition(rs.getInt("STOREPOSITION"));
				dynamicInfo.setPjSname(rs.getString("pjSname"));
				dynamicInfo.setJcType(rs.getString("JCTYPE"));
				return dynamicInfo;
			}
		});
		int count = jdbcTemplate.queryForInt(countBuilder.toString());
		PageModel model = new PageModel();
		model.setCount(count);
		model.setDatas(dynamicInfos);
		model.setOffset(PageContext.getOffSet());
		model.setPageSize(PageContext.getPageSize());
		return model;
	}

	@Override
	public PJDynamicInfo findPJDynamicInfoByPJNum(String jwdCode, String pjNum) {
		String sql = "select pjd.*, pjs.PJNAME pjSname, pjs.JCTYPE, pjs.FIRSTUNITID, pjs.BZIDS from PJ_DYNAMICINFO pjd left join PJ_STATICINFO pjs on pjd.PJSID = pjs.PJSID where pjd.pjNum= '"+ pjNum +"' and pjd.jwdcode = '"+ jwdCode +"'";
		final PJDynamicInfo dynamicInfo = new PJDynamicInfo();
		jdbcTemplate.query(sql,new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				dynamicInfo.setComments(rs.getString("COMMENTS"));
				dynamicInfo.setFactory(rs.getString("FACTORY"));
				dynamicInfo.setFactoryNum(rs.getString("FACTORYNUM"));
				dynamicInfo.setFixFlowName(rs.getString("FIXFLOWNAME"));
				dynamicInfo.setGetOnNum(rs.getString("GETONNUM"));
				dynamicInfo.setJwdCode(rs.getString("JWDCODE"));
				dynamicInfo.setOutFacDate(rs.getDate("OUTFACDATE"));
				dynamicInfo.setPjBarCode(rs.getString("PJBARCODE"));
				dynamicInfo.setPjdid(rs.getLong("PJDID"));
				dynamicInfo.setPjName(rs.getString("PJNAME"));
				dynamicInfo.setPjNum(rs.getString("PJNUM"));
				dynamicInfo.setPjStaticInfo(rs.getLong("PJSID"));
				dynamicInfo.setPjStatus(rs.getInt("PJSTATUS"));
				dynamicInfo.setPy(rs.getString("PY"));
				dynamicInfo.setStorePosition(rs.getInt("STOREPOSITION"));
				dynamicInfo.setPjSname(rs.getString("pjSname"));
				dynamicInfo.setJcType(rs.getString("JCTYPE"));
			}
		});
		return dynamicInfo;
	}

	@Override
	public PJDynamicInfo findPJDynamicInfoById(String jwdCode, Long pjdId) {
		String sql = "select pjd.*, pjs.PJNAME pjSname, pjs.JCTYPE, pjs.FIRSTUNITID, pjs.BZIDS from PJ_DYNAMICINFO pjd left join PJ_STATICINFO pjs on pjd.PJSID = pjs.PJSID where pjd.PJDID= "+ pjdId+" and pjd.jwdcode = '"+ jwdCode +"'";
		final PJDynamicInfo dynamicInfo = new PJDynamicInfo();
		jdbcTemplate.query(sql,new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				dynamicInfo.setComments(rs.getString("COMMENTS"));
				dynamicInfo.setFactory(rs.getString("FACTORY"));
				dynamicInfo.setFactoryNum(rs.getString("FACTORYNUM"));
				dynamicInfo.setFixFlowName(rs.getString("FIXFLOWNAME"));
				dynamicInfo.setGetOnNum(rs.getString("GETONNUM"));
				dynamicInfo.setJwdCode(rs.getString("JWDCODE"));
				dynamicInfo.setOutFacDate(rs.getDate("OUTFACDATE"));
				dynamicInfo.setPjBarCode(rs.getString("PJBARCODE"));
				dynamicInfo.setPjdid(rs.getLong("PJDID"));
				dynamicInfo.setPjName(rs.getString("PJNAME"));
				dynamicInfo.setPjNum(rs.getString("PJNUM"));
				dynamicInfo.setPjStaticInfo(rs.getLong("PJSID"));
				dynamicInfo.setPjStatus(rs.getInt("PJSTATUS"));
				dynamicInfo.setPy(rs.getString("PY"));
				dynamicInfo.setStorePosition(rs.getInt("STOREPOSITION"));
				dynamicInfo.setPjSname(rs.getString("pjSname"));
				dynamicInfo.setJcType(rs.getString("JCTYPE"));
			}
		});
		return dynamicInfo;
	}

	@Override
	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid) {
		String sql = "select * from PJ_FIXRECORD pjfr where pjfr.pjdid=? and jwdcode = ? and (pjfr.type=0 or pjfr.type=2) order by pjfr.pjRecId";
		List<PJFixRecord> pjFixRecords = jdbcTemplate.query(sql, new Object[]{pjdid, jwdCode}, new RowMapper<PJFixRecord>() {
			public PJFixRecord mapRow(ResultSet rs, int row) throws SQLException {
				PJFixRecord fixRecord = new PJFixRecord();
				fixRecord.setAcceaffitime(rs.getDate("ACCEAFFITIME"));
				fixRecord.setAccepter(rs.getString("ACCEPTER"));
				fixRecord.setAccepterid(rs.getLong("ACCEPTERID"));
				fixRecord.setAccepttime(rs.getDate("ACCEPTTIME"));
				fixRecord.setChildPJId(rs.getLong("CHILDPJID"));
				fixRecord.setComldaffitime(rs.getDate("COMLDAFFITIME"));
				fixRecord.setCommitlead(rs.getString("COMMITLEAD"));
				fixRecord.setCommitleadid(rs.getLong("COMMITLEADID"));
				fixRecord.setDealSituaton(rs.getString("DEALSITUATION"));
				fixRecord.setDownpjnum(rs.getString("DOWNPJNUM"));
				fixRecord.setEmpaffirmtime(rs.getDate("EMPAFFIRMTIME"));
				fixRecord.setFixemp(rs.getString("FIXEMP"));
				fixRecord.setFixempid(rs.getLong("FIXEMPID"));
				fixRecord.setFixItem(rs.getString("FIXITEM"));
				fixRecord.setFixsituation(rs.getString("FIXSITUATION"));
				fixRecord.setItemctrlacce(rs.getInt("ITEMCTRLACCE"));
				fixRecord.setItemctrlcomld(rs.getInt("ITEMCTRLCOMLD"));
				fixRecord.setItemctrllead(rs.getInt("ITEMCTRLLEAD"));
				fixRecord.setItemctrlqi(rs.getInt("ITEMCTRLQI"));
				fixRecord.setItemctrlrept(rs.getInt("ITEMCTRLREPT"));
				fixRecord.setItemctrltech(rs.getInt("ITEMCTRLTECH"));
				fixRecord.setJwdCode(rs.getString("JWDCODE"));
				fixRecord.setLdaffirmtime(rs.getDate("LDAFFIRMTIME"));
				fixRecord.setLead(rs.getString("LEAD"));
				fixRecord.setLeadid(rs.getLong("LEADID"));
				fixRecord.setLeftmargin(rs.getString("LEFTMARGIN"));
				fixRecord.setParentId(rs.getLong("PARENTID"));
				fixRecord.setPjDynamicInfo(rs.getLong("PJDID"));
				fixRecord.setPjFixFlowRecord(rs.getLong("PJFIXRECSID"));
				fixRecord.setPjFixItem(rs.getString("PJITEMID"));
				fixRecord.setPjFixRecSid(rs.getLong("PJPREDICTID"));
				fixRecord.setPjname(rs.getString("PJNAME"));
				fixRecord.setPjNum(rs.getString("PJNUM"));
				fixRecord.setPjPredictId(rs.getLong("PJPREDICTID"));
				fixRecord.setPjRecId(rs.getLong("PJRECID"));
				fixRecord.setPosiName(rs.getString("POSINAME"));
				fixRecord.setPreStatus(rs.getInt("PRESTATUS"));
				fixRecord.setQi(rs.getString("QI"));
				fixRecord.setQiaffitime(rs.getDate("QIAFFITIME"));
				fixRecord.setQiid(rs.getLong("QIID"));
				fixRecord.setRecstas(rs.getInt("RECSTAS"));
				fixRecord.setRept(rs.getString("REPT"));
				fixRecord.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				fixRecord.setReptId(rs.getLong("REPTID"));
				fixRecord.setRightmargin(rs.getString("RIGHTMARGIN"));
				fixRecord.setRjhmId(rs.getInt("RJHMID"));
				fixRecord.setTeams(rs.getString("TEAMS"));
				fixRecord.setTechId(rs.getLong("TECHID"));
				fixRecord.setTechName(rs.getString("TECHNAME"));
				fixRecord.setTechTime(rs.getDate("TECHTIME"));
				fixRecord.setType(rs.getInt("TYPE"));
				fixRecord.setUnit(rs.getString("UNIT"));
				fixRecord.setUppjnum(rs.getString("UPPJNUM"));
				return fixRecord;
			}
		});
		return pjFixRecords;
	}

	@Override
	public List<PJFixRecord> findPJFixRecord(String jwdCode, Long pjdid, Long recId) {
		String sql = "select * from PJ_FIXRECORD pjfr where pjfr.pjdid=? and pjfr.type=1 and pjfr.parentId=? and jwdCode = ? order by pjfr.teams desc,pjfr.pjRecId asc";
		List<PJFixRecord> pjFixRecords = jdbcTemplate.query(sql, new Object[]{pjdid, recId, jwdCode}, new RowMapper<PJFixRecord>() {
			public PJFixRecord mapRow(ResultSet rs, int row) throws SQLException {
				PJFixRecord fixRecord = new PJFixRecord();
				fixRecord.setAcceaffitime(rs.getDate("ACCEAFFITIME"));
				fixRecord.setAccepter(rs.getString("ACCEPTER"));
				fixRecord.setAccepterid(rs.getLong("ACCEPTERID"));
				fixRecord.setAccepttime(rs.getDate("ACCEPTTIME"));
				fixRecord.setChildPJId(rs.getLong("CHILDPJID"));
				fixRecord.setComldaffitime(rs.getDate("COMLDAFFITIME"));
				fixRecord.setCommitlead(rs.getString("COMMITLEAD"));
				fixRecord.setCommitleadid(rs.getLong("COMMITLEADID"));
				fixRecord.setDealSituaton(rs.getString("DEALSITUATION"));
				fixRecord.setDownpjnum(rs.getString("DOWNPJNUM"));
				fixRecord.setEmpaffirmtime(rs.getDate("EMPAFFIRMTIME"));
				fixRecord.setFixemp(rs.getString("FIXEMP"));
				fixRecord.setFixempid(rs.getLong("FIXEMPID"));
				fixRecord.setFixItem(rs.getString("FIXITEM"));
				fixRecord.setFixsituation(rs.getString("FIXSITUATION"));
				fixRecord.setItemctrlacce(rs.getInt("ITEMCTRLACCE"));
				fixRecord.setItemctrlcomld(rs.getInt("ITEMCTRLCOMLD"));
				fixRecord.setItemctrllead(rs.getInt("ITEMCTRLLEAD"));
				fixRecord.setItemctrlqi(rs.getInt("ITEMCTRLQI"));
				fixRecord.setItemctrlrept(rs.getInt("ITEMCTRLREPT"));
				fixRecord.setItemctrltech(rs.getInt("ITEMCTRLTECH"));
				fixRecord.setJwdCode(rs.getString("JWDCODE"));
				fixRecord.setLdaffirmtime(rs.getDate("LDAFFIRMTIME"));
				fixRecord.setLead(rs.getString("LEAD"));
				fixRecord.setLeadid(rs.getLong("LEADID"));
				fixRecord.setLeftmargin(rs.getString("LEFTMARGIN"));
				fixRecord.setParentId(rs.getLong("PARENTID"));
				fixRecord.setPjDynamicInfo(rs.getLong("PJDID"));
				fixRecord.setPjFixFlowRecord(rs.getLong("PJFIXRECSID"));
				fixRecord.setPjFixItem(rs.getString("PJITEMID"));
				fixRecord.setPjFixRecSid(rs.getLong("PJPREDICTID"));
				fixRecord.setPjname(rs.getString("PJNAME"));
				fixRecord.setPjNum(rs.getString("PJNUM"));
				fixRecord.setPjPredictId(rs.getLong("PJPREDICTID"));
				fixRecord.setPjRecId(rs.getLong("PJRECID"));
				fixRecord.setPosiName(rs.getString("POSINAME"));
				fixRecord.setPreStatus(rs.getInt("PRESTATUS"));
				fixRecord.setQi(rs.getString("QI"));
				fixRecord.setQiaffitime(rs.getDate("QIAFFITIME"));
				fixRecord.setQiid(rs.getLong("QIID"));
				fixRecord.setRecstas(rs.getInt("RECSTAS"));
				fixRecord.setRept(rs.getString("REPT"));
				fixRecord.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				fixRecord.setReptId(rs.getLong("REPTID"));
				fixRecord.setRightmargin(rs.getString("RIGHTMARGIN"));
				fixRecord.setRjhmId(rs.getInt("RJHMID"));
				fixRecord.setTeams(rs.getString("TEAMS"));
				fixRecord.setTechId(rs.getLong("TECHID"));
				fixRecord.setTechName(rs.getString("TECHNAME"));
				fixRecord.setTechTime(rs.getDate("TECHTIME"));
				fixRecord.setType(rs.getInt("TYPE"));
				fixRecord.setUnit(rs.getString("UNIT"));
				fixRecord.setUppjnum(rs.getString("UPPJNUM"));
				return fixRecord;
			}
		});
		return pjFixRecords;
	}

	@Override
	public List<String> findXXPJNums(String jwdCode, int rjhmId, int type) {
		String sql=null;
		if(type==0){//小修
			sql="select t.UPPJNUM from JC_FIXREC t where t.UPPJNUM is not null and t.DYPRECID=? and jwdcode= ?";
		}else if(type==1){//临修
			sql="select t.UPPJNUM from JT_PREDICT t where t.UPPJNUM is not null and t.DATEPLANPRI=? and jwdcode= ?";
		}else{//春整
			sql="select t.UPPJNUM from JC_QZ_FIXREC t where t.UPPJNUM is not null and t.JCRECMID=? and jwdcode= ?";
		}
		List<String> list = jdbcTemplate.queryForList(sql, new Object[]{rjhmId, jwdCode}, String.class);
		if (null != list && !list.isEmpty()) {
			List<String> numList = new ArrayList<String>();
			for (String string : list) {
				if (null!=string && !"".equals(string)) {
					if (string.indexOf(",") != -1) {
						String[] strs = string.split(",");
						List<String> subNumList = Arrays.asList(strs);
						numList.addAll(subNumList);
					} else {
						numList.add(string);
					}
				}
			}
			return numList;
		}
		return null;
	}

	@Override
	public List<PJStaticInfo> findPJStaticInfoByXXPJNums(String jwdCode, List<String> pjNums) {
		String sql="select * from pj_staticinfo pjs where pjs.pjsid in(select distinct pjd.pjsid from pj_dynamicinfo pjd where pjd.PJNUM in (";
		for(int i = 0; i < pjNums.size(); i++ ){
			if(i != (pjNums.size() - 1)){
				sql += "'"+ pjNums.get(i)+"',";
			}else {
				sql += "'"+ pjNums.get(i)+"'";
			}
		}
		sql += ")) and jwdcode = '"+ jwdCode +"'";
		List<PJStaticInfo> staticList = jdbcTemplate.query(sql,new RowMapper<PJStaticInfo>() {
			public PJStaticInfo mapRow(ResultSet rs, int row) throws SQLException {
				PJStaticInfo staticInfo = new PJStaticInfo();
				staticInfo.setPjsid(rs.getLong("PJSID"));
				staticInfo.setPjName(rs.getString("PJNAME"));
				staticInfo.setFirstUnit(rs.getLong("FIRSTUNITID"));
				return staticInfo;
			}
		});
		return staticList;
	}

	@Override
	public List<PJDynamicInfo> findPJDynamicInfoByPjNums(String jwdCode, Long pjsid, List<String> pjNums) {
		if(pjNums!=null&&pjNums.size()>0){
			String sql = "select pjd.*, pjs.PJNAME pjSname, pjs.JCTYPE, pjs.FIRSTUNITID, pjs.BZIDS from PJ_DYNAMICINFO pjd left join PJ_STATICINFO pjs on pjd.PJSID = pjs.PJSID where pjd.pjsid= "+ pjsid +" and pjd.pjnum in (";
			for(int i = 0; i < pjNums.size(); i++ ){
				if(i != (pjNums.size() - 1)){
					sql += "'"+ pjNums.get(i)+"',";
				}else {
					sql += "'"+ pjNums.get(i)+"'";
				}
			}
			sql += " ) and pjd.jwdcode = '"+ jwdCode +"'";
			List<PJDynamicInfo> dynamicInfos = jdbcTemplate.query(sql,new RowMapper<PJDynamicInfo>() {
				public PJDynamicInfo mapRow(ResultSet rs, int row) throws SQLException {
					PJDynamicInfo dynamicInfo = new PJDynamicInfo();
					dynamicInfo.setComments(rs.getString("COMMENTS"));
					dynamicInfo.setFactory(rs.getString("FACTORY"));
					dynamicInfo.setFactoryNum(rs.getString("FACTORYNUM"));
					dynamicInfo.setFixFlowName(rs.getString("FIXFLOWNAME"));
					dynamicInfo.setGetOnNum(rs.getString("GETONNUM"));
					dynamicInfo.setJwdCode(rs.getString("JWDCODE"));
					dynamicInfo.setOutFacDate(rs.getDate("OUTFACDATE"));
					dynamicInfo.setPjBarCode(rs.getString("PJBARCODE"));
					dynamicInfo.setPjdid(rs.getLong("PJDID"));
					dynamicInfo.setPjName(rs.getString("PJNAME"));
					dynamicInfo.setPjNum(rs.getString("PJNUM"));
					dynamicInfo.setPjStaticInfo(rs.getLong("PJSID"));
					dynamicInfo.setPjStatus(rs.getInt("PJSTATUS"));
					dynamicInfo.setPy(rs.getString("PY"));
					dynamicInfo.setStorePosition(rs.getInt("STOREPOSITION"));
					dynamicInfo.setPjSname(rs.getString("pjSname"));
					dynamicInfo.setJcType(rs.getString("JCTYPE"));
					return dynamicInfo;
				}
			});
			return dynamicInfos;
		}else{
			return new ArrayList<PJDynamicInfo>();
		}
	}

	@Override
	public List<PJStaticInfo> findPJStaticInfo(String jwdCode, String jcType, Long bzId) {
		String sql="select * from pj_staticinfo t where t.jcType like '%,"+ jcType +",%' and t.type=0 and jwdcode = '"+ jwdCode +"'";
		if(null != bzId){
			sql += " and t.bzIds like ?";
		}
		List<PJStaticInfo> staticList = jdbcTemplate.query(sql,new RowMapper<PJStaticInfo>() {
			public PJStaticInfo mapRow(ResultSet rs, int row) throws SQLException {
				PJStaticInfo staticInfo = new PJStaticInfo();
				staticInfo.setPjsid(rs.getLong("PJSID"));
				staticInfo.setPjName(rs.getString("PJNAME"));
				staticInfo.setFirstUnit(rs.getLong("FIRSTUNITID"));
				staticInfo.setAmount(rs.getInt("AMOUNT"));;
				return staticInfo;
			}
		});
		return staticList;
	}

	@Override
	public List<String> findPjNums(String jwdCode, int rjhmId) {
		String sql = "select jzf.uppjnum from jc_zx_fixrec jzf  where jzf.uppjnum is not null and jzf.dyprecid= "+ rjhmId +" and jwdcode = '"+ jwdCode +"'";
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		if (null != list && !list.isEmpty()) {
			List<String> numList = new ArrayList<String>();
			for (String string : list) {
				if (null!=string && !"".equals(string)) {
					if (string.indexOf(",") != -1) {
						String[] strs = string.split(",");
						List<String> subNumList = Arrays.asList(strs);
						numList.addAll(subNumList);
					} else {
						numList.add(string);
					}
				}
			}
			return numList;
		}
		return null;
	}

	@Override
	public int findDynamicInZxFixItem(String jwdCode, Long pjsid, List<String> pjNums) {
		if(pjNums!=null&&pjNums.size()>0){
			String sql = "select count(t.pjdid) from pj_dynamicinfo t where t.pjsid= "+ pjsid +" and t.pjnum in (";
			for(int i = 0; i < pjNums.size(); i++ ){
				if(i != (pjNums.size() - 1)){
					sql += "'"+ pjNums.get(i)+"',";
				}else {
					sql += "'"+ pjNums.get(i)+"'";
				}
			}
			sql += " ) and t.jwdcode = '"+ jwdCode +"'";
			return jdbcTemplate.queryForInt(sql);
		}else{
			return 0;
		}
	}

	@Override
	public List<String> findFittingNumberForFixRec(String jwdCode, int rjhmId) {
		String sql = "select jzf.uppjnum from jc_zx_fixrec jzf  where jzf.uppjnum is not null and jzf.dyprecid= "+ rjhmId +" and jwdcode = '"+ jwdCode +"'";
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		if (!CollectionUtils.isEmpty(list)) {
			List<String> numList = new ArrayList<String>();
			for (String string : list) {
				if (null!=string && !"".equals(string)) {
					if (string.indexOf(",") != -1) {
						String[] strs = string.split(",");
						List<String> subNumList = Arrays.asList(strs);
						numList.addAll(subNumList);
					} else {
						numList.add(string);
					}
				}
			}
			return numList;
		}
		return null;
	}

	@Override
	public List<DictFirstUnit> forFittingNumbers(String jwdCode, List<String> fittingNums) {
		String sql = "select distinct dfu.* from DICT_FIRSTUNIT dfu join PJ_STATICINFO psi on dfu.FIRSTUNITID=psi.FIRSTUNITID join PJ_DYNAMICINFO pdi on psi.PJSID = pdi.PJSID where pdi.PJNUM in (";
		for(int i = 0; i < fittingNums.size(); i++ ){
			if(i != (fittingNums.size() - 1)){
				sql += "'"+ fittingNums.get(i)+"',";
			}else {
				sql += "'"+ fittingNums.get(i)+"'";
			}
		}
		sql += " ) and dfu.jwdcode = '"+ jwdCode +"'";
		List<DictFirstUnit> unitList = jdbcTemplate.query(sql, new RowMapper<DictFirstUnit>() {
			public DictFirstUnit mapRow(ResultSet rs, int row) throws SQLException {
				DictFirstUnit unit = new DictFirstUnit();
				unit.setFirstunitid(rs.getLong("FIRSTUNITID"));
				unit.setFirstunitname(rs.getString("FIRSTUNITNAME"));
				unit.setJcstypevalue(rs.getString("JCSTYPEVALUE"));
				unit.setJwdcode(rs.getString("JWDCODE"));
				unit.setPy(rs.getString("PY"));
				unit.setUrl(rs.getString("URL"));
				return unit;
			}
		});
		return unitList;
	}

	@Override
	public List<JCZXFixRec> listJCZXFixRec(String jwdCode, int rjhmId, Long bzId) {
		String sql = "select * from JC_ZX_FIXREC r where r.DYPRECID= "+ rjhmId +" and r.bzId= "+ bzId +" and jwdcode = '"+ jwdCode +"' order by r.unitName,r.id";
		List<JCZXFixRec> jczxFixRecs = jdbcTemplate.query(sql, new RowMapper<JCZXFixRec>() {
			public JCZXFixRec mapRow(ResultSet rs, int row) throws SQLException {
				JCZXFixRec jczxFixRec = new JCZXFixRec();
				jczxFixRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jczxFixRec.setAcceptEr(rs.getString("ACCEPTER"));
				jczxFixRec.setAcceptErId(rs.getLong("ACCEPTERID"));
				jczxFixRec.setBzId(rs.getLong("BZID"));
				jczxFixRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jczxFixRec.setCommitLead(rs.getString("COMMITLEAD"));
				jczxFixRec.setCommitLeadId(rs.getLong("COMMITLEADID"));
				jczxFixRec.setDealSituation(rs.getString("DEALSITUATION"));
				jczxFixRec.setDownPjBarCode(rs.getString("DOWNPJBARCODE"));
				jczxFixRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jczxFixRec.setDuration(rs.getInt("DURATION"));
				jczxFixRec.setDyPrecId(rs.getInt("DYPRECID"));
				jczxFixRec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				jczxFixRec.setFixEmp(rs.getString("FIXEMP"));
				jczxFixRec.setFixEmpId(rs.getString("FIXEMPID"));
				jczxFixRec.setFixSituation(rs.getString("FIXSITUATION"));
				jczxFixRec.setId(rs.getLong("ID"));
				jczxFixRec.setItemCtrlAcce(rs.getShort("ITEMCTRLACCE"));
				jczxFixRec.setItemCtrlComld(rs.getShort("ITEMCTRLCOMLD"));
				jczxFixRec.setItemCtrlLead(rs.getShort("ITEMCTRLLEAD"));
				jczxFixRec.setItemCtrlQi(rs.getShort("ITEMCTRLQI"));
				jczxFixRec.setItemCtrlRept(rs.getShort("ITEMCTRLREPT"));
				jczxFixRec.setItemCtrlTech(rs.getShort("ITEMCTRLTECH"));
				jczxFixRec.setItemId(rs.getLong("ITEMID"));
				jczxFixRec.setItemName(rs.getString("ITEMNAME"));
				jczxFixRec.setItemType(rs.getShort("ITEMTYPE"));
				jczxFixRec.setJcNum(rs.getString("JCNUM"));
				jczxFixRec.setJcType(rs.getString("JCTYPE"));
				jczxFixRec.setJhTime(rs.getString("JHTIME"));
				jczxFixRec.setJwdCode(rs.getString("JWDCODE"));
				jczxFixRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jczxFixRec.setLead(rs.getString("LEAD"));
				jczxFixRec.setLeadId(rs.getLong("LEADID"));
				jczxFixRec.setNodeId(rs.getInt("NODEID"));
				jczxFixRec.setPosiName(rs.getString("POSINAME"));
				jczxFixRec.setQi(rs.getString("QI"));
				jczxFixRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jczxFixRec.setQiId(rs.getLong("QIID"));
				jczxFixRec.setRecStas(rs.getShort("RECSTAS"));
				jczxFixRec.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				jczxFixRec.setRept(rs.getString("REPT"));
				jczxFixRec.setReptId(rs.getLong("REPTID"));
				jczxFixRec.setTeachAffiTime(rs.getString("TEALDAFFITIME"));
				jczxFixRec.setTeachId(rs.getLong("TEACHID"));
				jczxFixRec.setTeachName(rs.getString("TEACHNAME"));
				jczxFixRec.setUnit(rs.getString("UNIT"));
				jczxFixRec.setUnitName(rs.getString("UNITNAME"));
				jczxFixRec.setUpPjBarCode(rs.getString("UPPJBARCODE"));
				jczxFixRec.setUpPjNum(rs.getString("UPPJNUM"));
				return jczxFixRec;
			}
		});
		return jczxFixRecs;
	}

	@Override
	public PJDynamicInfo findDynamicInfoByPjNum(String jwdCode, String pjNum) {
		String sql = "select pjd.*, pjs.PJNAME pjSname, pjs.JCTYPE, pjs.FIRSTUNITID, pjs.BZIDS from PJ_DYNAMICINFO pjd left join PJ_STATICINFO pjs on pjd.PJSID = pjs.PJSID where pdi.pjNum='"+ pjNum +"' and pjd.jwdcode = '"+ jwdCode +"' order by pjd.pjdid desc";
		final PJDynamicInfo dynamicInfo = new PJDynamicInfo();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				dynamicInfo.setComments(rs.getString("COMMENTS"));
				dynamicInfo.setFactory(rs.getString("FACTORY"));
				dynamicInfo.setFactoryNum(rs.getString("FACTORYNUM"));
				dynamicInfo.setFixFlowName(rs.getString("FIXFLOWNAME"));
				dynamicInfo.setGetOnNum(rs.getString("GETONNUM"));
				dynamicInfo.setJwdCode(rs.getString("JWDCODE"));
				dynamicInfo.setOutFacDate(rs.getDate("OUTFACDATE"));
				dynamicInfo.setPjBarCode(rs.getString("PJBARCODE"));
				dynamicInfo.setPjdid(rs.getLong("PJDID"));
				dynamicInfo.setPjName(rs.getString("PJNAME"));
				dynamicInfo.setPjNum(rs.getString("PJNUM"));
				dynamicInfo.setPjStaticInfo(rs.getLong("PJSID"));
				dynamicInfo.setPjStatus(rs.getInt("PJSTATUS"));
				dynamicInfo.setPy(rs.getString("PY"));
				dynamicInfo.setStorePosition(rs.getInt("STOREPOSITION"));
				dynamicInfo.setPjSname(rs.getString("pjSname"));
				dynamicInfo.setJcType(rs.getString("JCTYPE"));
			}
		});
		return dynamicInfo;
	}

	@Override
	public PJDynamicInfo findPJDynamicInfoByDID(String jwdCode, Long pjdid) {
		String sql = "select pjd.*, pjs.PJNAME pjSname, pjs.JCTYPE, pjs.FIRSTUNITID, pjs.BZIDS from PJ_DYNAMICINFO pjd left join PJ_STATICINFO pjs on pjd.PJSID = pjs.PJSID where pjd.PJDID="+ pjdid +" and pjd.jwdcode = '"+ jwdCode +"'";
		final PJDynamicInfo dynamicInfo = new PJDynamicInfo();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				dynamicInfo.setComments(rs.getString("COMMENTS"));
				dynamicInfo.setFactory(rs.getString("FACTORY"));
				dynamicInfo.setFactoryNum(rs.getString("FACTORYNUM"));
				dynamicInfo.setFixFlowName(rs.getString("FIXFLOWNAME"));
				dynamicInfo.setGetOnNum(rs.getString("GETONNUM"));
				dynamicInfo.setJwdCode(rs.getString("JWDCODE"));
				dynamicInfo.setOutFacDate(rs.getDate("OUTFACDATE"));
				dynamicInfo.setPjBarCode(rs.getString("PJBARCODE"));
				dynamicInfo.setPjdid(rs.getLong("PJDID"));
				dynamicInfo.setPjName(rs.getString("PJNAME"));
				dynamicInfo.setPjNum(rs.getString("PJNUM"));
				dynamicInfo.setPjStaticInfo(rs.getLong("PJSID"));
				dynamicInfo.setPjStatus(rs.getInt("PJSTATUS"));
				dynamicInfo.setPy(rs.getString("PY"));
				dynamicInfo.setStorePosition(rs.getInt("STOREPOSITION"));
				dynamicInfo.setPjSname(rs.getString("pjSname"));
				dynamicInfo.setJcType(rs.getString("JCTYPE"));
			}
		});
		return dynamicInfo;
	}

	@Override
	public PJFixRecord findPJFixRecordByRjhmId(String jwdCode, Integer rjhmId, Long pjdid) {
		String sql = "select * from PJ_FIXRECORD t where t.RJHMID="+ rjhmId +" and (t.type=0 or t.type=2) and t.PJDID="+ pjdid +" and jwdcode = "+ jwdCode +" order by t.pjRecId desc";
		final PJFixRecord fixRecord = new PJFixRecord();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				fixRecord.setAcceaffitime(rs.getDate("ACCEAFFITIME"));
				fixRecord.setAccepter(rs.getString("ACCEPTER"));
				fixRecord.setAccepterid(rs.getLong("ACCEPTERID"));
				fixRecord.setAccepttime(rs.getDate("ACCEPTTIME"));
				fixRecord.setChildPJId(rs.getLong("CHILDPJID"));
				fixRecord.setComldaffitime(rs.getDate("COMLDAFFITIME"));
				fixRecord.setCommitlead(rs.getString("COMMITLEAD"));
				fixRecord.setCommitleadid(rs.getLong("COMMITLEADID"));
				fixRecord.setDealSituaton(rs.getString("DEALSITUATION"));
				fixRecord.setDownpjnum(rs.getString("DOWNPJNUM"));
				fixRecord.setEmpaffirmtime(rs.getDate("EMPAFFIRMTIME"));
				fixRecord.setFixemp(rs.getString("FIXEMP"));
				fixRecord.setFixempid(rs.getLong("FIXEMPID"));
				fixRecord.setFixItem(rs.getString("FIXITEM"));
				fixRecord.setFixsituation(rs.getString("FIXSITUATION"));
				fixRecord.setItemctrlacce(rs.getInt("ITEMCTRLACCE"));
				fixRecord.setItemctrlcomld(rs.getInt("ITEMCTRLCOMLD"));
				fixRecord.setItemctrllead(rs.getInt("ITEMCTRLLEAD"));
				fixRecord.setItemctrlqi(rs.getInt("ITEMCTRLQI"));
				fixRecord.setItemctrlrept(rs.getInt("ITEMCTRLREPT"));
				fixRecord.setItemctrltech(rs.getInt("ITEMCTRLTECH"));
				fixRecord.setJwdCode(rs.getString("JWDCODE"));
				fixRecord.setLdaffirmtime(rs.getDate("LDAFFIRMTIME"));
				fixRecord.setLead(rs.getString("LEAD"));
				fixRecord.setLeadid(rs.getLong("LEADID"));
				fixRecord.setLeftmargin(rs.getString("LEFTMARGIN"));
				fixRecord.setParentId(rs.getLong("PARENTID"));
				fixRecord.setPjDynamicInfo(rs.getLong("PJDID"));
				fixRecord.setPjFixFlowRecord(rs.getLong("PJFIXRECSID"));
				fixRecord.setPjFixItem(rs.getString("PJITEMID"));
				fixRecord.setPjFixRecSid(rs.getLong("PJPREDICTID"));
				fixRecord.setPjname(rs.getString("PJNAME"));
				fixRecord.setPjNum(rs.getString("PJNUM"));
				fixRecord.setPjPredictId(rs.getLong("PJPREDICTID"));
				fixRecord.setPjRecId(rs.getLong("PJRECID"));
				fixRecord.setPosiName(rs.getString("POSINAME"));
				fixRecord.setPreStatus(rs.getInt("PRESTATUS"));
				fixRecord.setQi(rs.getString("QI"));
				fixRecord.setQiaffitime(rs.getDate("QIAFFITIME"));
				fixRecord.setQiid(rs.getLong("QIID"));
				fixRecord.setRecstas(rs.getInt("RECSTAS"));
				fixRecord.setRept(rs.getString("REPT"));
				fixRecord.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				fixRecord.setReptId(rs.getLong("REPTID"));
				fixRecord.setRightmargin(rs.getString("RIGHTMARGIN"));
				fixRecord.setRjhmId(rs.getInt("RJHMID"));
				fixRecord.setTeams(rs.getString("TEAMS"));
				fixRecord.setTechId(rs.getLong("TECHID"));
				fixRecord.setTechName(rs.getString("TECHNAME"));
				fixRecord.setTechTime(rs.getDate("TECHTIME"));
				fixRecord.setType(rs.getInt("TYPE"));
				fixRecord.setUnit(rs.getString("UNIT"));
				fixRecord.setUppjnum(rs.getString("UPPJNUM"));
				
			}
		});
		return fixRecord;
	}

	@Override
	public List<PJFixRecord> findPJFixRecordByDid(String jwdCode, Long pjdid, Long pjRecId, Long bzId) {
		String sql = "select * from PJ_FIXRECORD pjfr where pjfr.pjdid= "+ pjdid +" and pjfr.type=1 and pjfr.parentId= "+ pjRecId +" and jwdcode = '"+ jwdCode +"'";
		if(bzId!=null){
			sql += " and pjfr.teams="+ bzId +"";
		}
		List<PJFixRecord> jczxFixRecs = jdbcTemplate.query(sql, new RowMapper<PJFixRecord>() {
			public PJFixRecord mapRow(ResultSet rs, int row) throws SQLException {
				PJFixRecord fixRecord = new PJFixRecord();
				fixRecord.setAcceaffitime(rs.getDate("ACCEAFFITIME"));
				fixRecord.setAccepter(rs.getString("ACCEPTER"));
				fixRecord.setAccepterid(rs.getLong("ACCEPTERID"));
				fixRecord.setAccepttime(rs.getDate("ACCEPTTIME"));
				fixRecord.setChildPJId(rs.getLong("CHILDPJID"));
				fixRecord.setComldaffitime(rs.getDate("COMLDAFFITIME"));
				fixRecord.setCommitlead(rs.getString("COMMITLEAD"));
				fixRecord.setCommitleadid(rs.getLong("COMMITLEADID"));
				fixRecord.setDealSituaton(rs.getString("DEALSITUATION"));
				fixRecord.setDownpjnum(rs.getString("DOWNPJNUM"));
				fixRecord.setEmpaffirmtime(rs.getDate("EMPAFFIRMTIME"));
				fixRecord.setFixemp(rs.getString("FIXEMP"));
				fixRecord.setFixempid(rs.getLong("FIXEMPID"));
				fixRecord.setFixItem(rs.getString("FIXITEM"));
				fixRecord.setFixsituation(rs.getString("FIXSITUATION"));
				fixRecord.setItemctrlacce(rs.getInt("ITEMCTRLACCE"));
				fixRecord.setItemctrlcomld(rs.getInt("ITEMCTRLCOMLD"));
				fixRecord.setItemctrllead(rs.getInt("ITEMCTRLLEAD"));
				fixRecord.setItemctrlqi(rs.getInt("ITEMCTRLQI"));
				fixRecord.setItemctrlrept(rs.getInt("ITEMCTRLREPT"));
				fixRecord.setItemctrltech(rs.getInt("ITEMCTRLTECH"));
				fixRecord.setJwdCode(rs.getString("JWDCODE"));
				fixRecord.setLdaffirmtime(rs.getDate("LDAFFIRMTIME"));
				fixRecord.setLead(rs.getString("LEAD"));
				fixRecord.setLeadid(rs.getLong("LEADID"));
				fixRecord.setLeftmargin(rs.getString("LEFTMARGIN"));
				fixRecord.setParentId(rs.getLong("PARENTID"));
				fixRecord.setPjDynamicInfo(rs.getLong("PJDID"));
				fixRecord.setPjFixFlowRecord(rs.getLong("PJFIXRECSID"));
				fixRecord.setPjFixItem(rs.getString("PJITEMID"));
				fixRecord.setPjFixRecSid(rs.getLong("PJPREDICTID"));
				fixRecord.setPjname(rs.getString("PJNAME"));
				fixRecord.setPjNum(rs.getString("PJNUM"));
				fixRecord.setPjPredictId(rs.getLong("PJPREDICTID"));
				fixRecord.setPjRecId(rs.getLong("PJRECID"));
				fixRecord.setPosiName(rs.getString("POSINAME"));
				fixRecord.setPreStatus(rs.getInt("PRESTATUS"));
				fixRecord.setQi(rs.getString("QI"));
				fixRecord.setQiaffitime(rs.getDate("QIAFFITIME"));
				fixRecord.setQiid(rs.getLong("QIID"));
				fixRecord.setRecstas(rs.getInt("RECSTAS"));
				fixRecord.setRept(rs.getString("REPT"));
				fixRecord.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				fixRecord.setReptId(rs.getLong("REPTID"));
				fixRecord.setRightmargin(rs.getString("RIGHTMARGIN"));
				fixRecord.setRjhmId(rs.getInt("RJHMID"));
				fixRecord.setTeams(rs.getString("TEAMS"));
				fixRecord.setTechId(rs.getLong("TECHID"));
				fixRecord.setTechName(rs.getString("TECHNAME"));
				fixRecord.setTechTime(rs.getDate("TECHTIME"));
				fixRecord.setType(rs.getInt("TYPE"));
				fixRecord.setUnit(rs.getString("UNIT"));
				fixRecord.setUppjnum(rs.getString("UPPJNUM"));
				return fixRecord;
			}
		});
		return jczxFixRecs;
	}

	@Override
	public List<DictFirstUnit> findDictFirstUnitByType(String jwdCode, String jcStype) {
		String sql = "select * from DICT_FIRSTUNIT jf where jf.jcstypevalue like '%"+ jcStype +"%' and jwdcode = '"+ jwdCode +"'";
		List<DictFirstUnit> unitList = jdbcTemplate.query(sql, new RowMapper<DictFirstUnit>() {
			public DictFirstUnit mapRow(ResultSet rs, int row) throws SQLException {
				DictFirstUnit unit = new DictFirstUnit();
				unit.setFirstunitid(rs.getLong("FIRSTUNITID"));
				unit.setFirstunitname(rs.getString("FIRSTUNITNAME"));
				unit.setJcstypevalue(rs.getString("JCSTYPEVALUE"));
				unit.setJwdcode(rs.getString("JWDCODE"));
				unit.setPy(rs.getString("PY"));
				unit.setUrl(rs.getString("URL"));
				return unit;
			}
		});
		return unitList;
	}

	@Override
	public Long countJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit) {
		String sql = "select count(*) from JC_ZX_FIXREC jfr where jfr.dyPrecId= "+ datePlanPri +" and jfr.firstUnitId= "+ fristUnit +" and jwdcode = '"+ jwdCode +"'";
		return jdbcTemplate.queryForLong(sql);
	}

	@Override
	public List<JCZXFixRec> findJCZXFixRec(String jwdCode, Integer datePlanPri, Integer fristUnit) {
		String sql = "select * from JC_ZX_FIXREC jfr where jfr.dyPrecId="+ datePlanPri +" and jfr.firstUnitId="+ fristUnit +" and jwdcode = "+ jwdCode +" order by jfr.nodeId,jfr.bzId,jfr.unitName";
		List<JCZXFixRec> jczxFixRecs = jdbcTemplate.query(sql, new RowMapper<JCZXFixRec>() {
			public JCZXFixRec mapRow(ResultSet rs, int row) throws SQLException {
				JCZXFixRec jczxFixRec = new JCZXFixRec();
				jczxFixRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jczxFixRec.setAcceptEr(rs.getString("ACCEPTER"));
				jczxFixRec.setAcceptErId(rs.getLong("ACCEPTERID"));
				jczxFixRec.setBzId(rs.getLong("BZID"));
				jczxFixRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jczxFixRec.setCommitLead(rs.getString("COMMITLEAD"));
				jczxFixRec.setCommitLeadId(rs.getLong("COMMITLEADID"));
				jczxFixRec.setDealSituation(rs.getString("DEALSITUATION"));
				jczxFixRec.setDownPjBarCode(rs.getString("DOWNPJBARCODE"));
				jczxFixRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jczxFixRec.setDuration(rs.getInt("DURATION"));
				jczxFixRec.setDyPrecId(rs.getInt("DYPRECID"));
				jczxFixRec.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				jczxFixRec.setFixEmp(rs.getString("FIXEMP"));
				jczxFixRec.setFixEmpId(rs.getString("FIXEMPID"));
				jczxFixRec.setFixSituation(rs.getString("FIXSITUATION"));
				jczxFixRec.setId(rs.getLong("ID"));
				jczxFixRec.setItemCtrlAcce(rs.getShort("ITEMCTRLACCE"));
				jczxFixRec.setItemCtrlComld(rs.getShort("ITEMCTRLCOMLD"));
				jczxFixRec.setItemCtrlLead(rs.getShort("ITEMCTRLLEAD"));
				jczxFixRec.setItemCtrlQi(rs.getShort("ITEMCTRLQI"));
				jczxFixRec.setItemCtrlRept(rs.getShort("ITEMCTRLREPT"));
				jczxFixRec.setItemCtrlTech(rs.getShort("ITEMCTRLTECH"));
				jczxFixRec.setItemId(rs.getLong("ITEMID"));
				jczxFixRec.setItemName(rs.getString("ITEMNAME"));
				jczxFixRec.setItemType(rs.getShort("ITEMTYPE"));
				jczxFixRec.setJcNum(rs.getString("JCNUM"));
				jczxFixRec.setJcType(rs.getString("JCTYPE"));
				jczxFixRec.setJhTime(rs.getString("JHTIME"));
				jczxFixRec.setJwdCode(rs.getString("JWDCODE"));
				jczxFixRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jczxFixRec.setLead(rs.getString("LEAD"));
				jczxFixRec.setLeadId(rs.getLong("LEADID"));
				jczxFixRec.setNodeId(rs.getInt("NODEID"));
				jczxFixRec.setPosiName(rs.getString("POSINAME"));
				jczxFixRec.setQi(rs.getString("QI"));
				jczxFixRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jczxFixRec.setQiId(rs.getLong("QIID"));
				jczxFixRec.setRecStas(rs.getShort("RECSTAS"));
				jczxFixRec.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				jczxFixRec.setRept(rs.getString("REPT"));
				jczxFixRec.setReptId(rs.getLong("REPTID"));
				jczxFixRec.setTeachAffiTime(rs.getString("TEALDAFFITIME"));
				jczxFixRec.setTeachId(rs.getLong("TEACHID"));
				jczxFixRec.setTeachName(rs.getString("TEACHNAME"));
				jczxFixRec.setUnit(rs.getString("UNIT"));
				jczxFixRec.setUnitName(rs.getString("UNITNAME"));
				jczxFixRec.setUpPjBarCode(rs.getString("UPPJBARCODE"));
				jczxFixRec.setUpPjNum(rs.getString("UPPJNUM"));
				return jczxFixRec;
			}
		});
		return jczxFixRecs;
	}

	@Override
	public void saveOilDetailRecorder(String jwdCode, int itemId, long recId, String jcType) {
		String countSql = "select max(RECDETAILID) from oil_assay_detailrecorer";
		int maxId = jdbcTemplate.queryForInt(countSql);
		String sql="insert into oil_assay_detailrecorer(recdetailid,recpriid,subitemid,subitemtitle,jwdcode) " +
				"select "+ maxId +","+ recId +",subitemid,subitemtitle, "+ jwdCode +" from oil_assay_subitem where reportitemid="+ itemId +" and JCSTYPEVAL like '%"+jcType+"、%'";
		jdbcTemplate.execute(sql);
	}

	@Override
	public String findFirstUnitName(String jwdCode, Long staticInfo) {
		String sql = "select df.firstunitname from pj_staticinfo ps left join dict_firstunit df on ps.FIRSTUNITID = df.firstunitid where ps.pjsid = "+ staticInfo +" and ps.jwdcode = '"+ jwdCode +"'";
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	@Override
	public List<PJFixRecord> forNumAndDataPlan(String jwdCode, String pjNum) {
		String sql = "select distinct pfc.*,psi.FIRSTUNITID from PJ_FIXRECORD pfc join PJ_DYNAMICINFO pdi on pfc.PJDID=pdi.PJDID join PJ_STATICINFO psi on pdi.PJSID=psi.PJSID where pdi.PJNUM = '"+ pjNum +"' and pdi.jwdcode = '"+ jwdCode +"' and pfc.TYPE=1 order by pfc.pjname, psi.FIRSTUNITID";
		List<PJFixRecord> pjFixRecords = jdbcTemplate.query(sql, new RowMapper<PJFixRecord>() {
			public PJFixRecord mapRow(ResultSet rs, int row) throws SQLException {
				PJFixRecord fixRecord = new PJFixRecord();
				fixRecord.setAcceaffitime(rs.getDate("ACCEAFFITIME"));
				fixRecord.setAccepter(rs.getString("ACCEPTER"));
				fixRecord.setAccepterid(rs.getLong("ACCEPTERID"));
				fixRecord.setAccepttime(rs.getDate("ACCEPTTIME"));
				fixRecord.setChildPJId(rs.getLong("CHILDPJID"));
				fixRecord.setComldaffitime(rs.getDate("COMLDAFFITIME"));
				fixRecord.setCommitlead(rs.getString("COMMITLEAD"));
				fixRecord.setCommitleadid(rs.getLong("COMMITLEADID"));
				fixRecord.setDealSituaton(rs.getString("DEALSITUATION"));
				fixRecord.setDownpjnum(rs.getString("DOWNPJNUM"));
				fixRecord.setEmpaffirmtime(rs.getDate("EMPAFFIRMTIME"));
				fixRecord.setFixemp(rs.getString("FIXEMP"));
				fixRecord.setFixempid(rs.getLong("FIXEMPID"));
				fixRecord.setFixItem(rs.getString("FIXITEM"));
				fixRecord.setFixsituation(rs.getString("FIXSITUATION"));
				fixRecord.setItemctrlacce(rs.getInt("ITEMCTRLACCE"));
				fixRecord.setItemctrlcomld(rs.getInt("ITEMCTRLCOMLD"));
				fixRecord.setItemctrllead(rs.getInt("ITEMCTRLLEAD"));
				fixRecord.setItemctrlqi(rs.getInt("ITEMCTRLQI"));
				fixRecord.setItemctrlrept(rs.getInt("ITEMCTRLREPT"));
				fixRecord.setItemctrltech(rs.getInt("ITEMCTRLTECH"));
				fixRecord.setJwdCode(rs.getString("JWDCODE"));
				fixRecord.setLdaffirmtime(rs.getDate("LDAFFIRMTIME"));
				fixRecord.setLead(rs.getString("LEAD"));
				fixRecord.setLeadid(rs.getLong("LEADID"));
				fixRecord.setLeftmargin(rs.getString("LEFTMARGIN"));
				fixRecord.setParentId(rs.getLong("PARENTID"));
				fixRecord.setPjDynamicInfo(rs.getLong("PJDID"));
				fixRecord.setPjFixFlowRecord(rs.getLong("PJFIXRECSID"));
				fixRecord.setPjFixItem(rs.getString("PJITEMID"));
				fixRecord.setPjFixRecSid(rs.getLong("PJPREDICTID"));
				fixRecord.setPjname(rs.getString("PJNAME"));
				fixRecord.setPjNum(rs.getString("PJNUM"));
				fixRecord.setPjPredictId(rs.getLong("PJPREDICTID"));
				fixRecord.setPjRecId(rs.getLong("PJRECID"));
				fixRecord.setPosiName(rs.getString("POSINAME"));
				fixRecord.setPreStatus(rs.getInt("PRESTATUS"));
				fixRecord.setQi(rs.getString("QI"));
				fixRecord.setQiaffitime(rs.getDate("QIAFFITIME"));
				fixRecord.setQiid(rs.getLong("QIID"));
				fixRecord.setRecstas(rs.getInt("RECSTAS"));
				fixRecord.setRept(rs.getString("REPT"));
				fixRecord.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				fixRecord.setReptId(rs.getLong("REPTID"));
				fixRecord.setRightmargin(rs.getString("RIGHTMARGIN"));
				fixRecord.setRjhmId(rs.getInt("RJHMID"));
				fixRecord.setTeams(rs.getString("TEAMS"));
				fixRecord.setTechId(rs.getLong("TECHID"));
				fixRecord.setTechName(rs.getString("TECHNAME"));
				fixRecord.setTechTime(rs.getDate("TECHTIME"));
				fixRecord.setType(rs.getInt("TYPE"));
				fixRecord.setUnit(rs.getString("UNIT"));
				fixRecord.setUppjnum(rs.getString("UPPJNUM"));
				return fixRecord;
			}
		});
		return pjFixRecords;
	}

	@Override
	public List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid, List<String> fittingNums) {
		String sql = "select distinct pfc.*, psi.FIRSTUNITID from PJ_FIXRECORD pfc join PJ_DYNAMICINFO pdi on pfc.PJDID=pdi.PJDID join PJ_STATICINFO psi on pdi.PJSID=psi.PJSID where pdi.PJNUM in (";
		for(int i = 0; i < fittingNums.size(); i++ ){
			if(i != (fittingNums.size() - 1)){
				sql += "'"+ fittingNums.get(i)+"',";
			}else {
				sql += "'"+ fittingNums.get(i)+"'";
			}
		}
		sql += " and psi.FIRSTUNITID= "+ firstunitid +" and pfc.TYPE=1 order by pfc.pjname, psi.FIRSTUNITID";
		List<PJFixRecord> pjFixRecords = jdbcTemplate.query(sql, new RowMapper<PJFixRecord>() {
			public PJFixRecord mapRow(ResultSet rs, int row) throws SQLException {
				PJFixRecord fixRecord = new PJFixRecord();
				fixRecord.setAcceaffitime(rs.getDate("ACCEAFFITIME"));
				fixRecord.setAccepter(rs.getString("ACCEPTER"));
				fixRecord.setAccepterid(rs.getLong("ACCEPTERID"));
				fixRecord.setAccepttime(rs.getDate("ACCEPTTIME"));
				fixRecord.setChildPJId(rs.getLong("CHILDPJID"));
				fixRecord.setComldaffitime(rs.getDate("COMLDAFFITIME"));
				fixRecord.setCommitlead(rs.getString("COMMITLEAD"));
				fixRecord.setCommitleadid(rs.getLong("COMMITLEADID"));
				fixRecord.setDealSituaton(rs.getString("DEALSITUATION"));
				fixRecord.setDownpjnum(rs.getString("DOWNPJNUM"));
				fixRecord.setEmpaffirmtime(rs.getDate("EMPAFFIRMTIME"));
				fixRecord.setFixemp(rs.getString("FIXEMP"));
				fixRecord.setFixempid(rs.getLong("FIXEMPID"));
				fixRecord.setFixItem(rs.getString("FIXITEM"));
				fixRecord.setFixsituation(rs.getString("FIXSITUATION"));
				fixRecord.setItemctrlacce(rs.getInt("ITEMCTRLACCE"));
				fixRecord.setItemctrlcomld(rs.getInt("ITEMCTRLCOMLD"));
				fixRecord.setItemctrllead(rs.getInt("ITEMCTRLLEAD"));
				fixRecord.setItemctrlqi(rs.getInt("ITEMCTRLQI"));
				fixRecord.setItemctrlrept(rs.getInt("ITEMCTRLREPT"));
				fixRecord.setItemctrltech(rs.getInt("ITEMCTRLTECH"));
				fixRecord.setJwdCode(rs.getString("JWDCODE"));
				fixRecord.setLdaffirmtime(rs.getDate("LDAFFIRMTIME"));
				fixRecord.setLead(rs.getString("LEAD"));
				fixRecord.setLeadid(rs.getLong("LEADID"));
				fixRecord.setLeftmargin(rs.getString("LEFTMARGIN"));
				fixRecord.setParentId(rs.getLong("PARENTID"));
				fixRecord.setPjDynamicInfo(rs.getLong("PJDID"));
				fixRecord.setPjFixFlowRecord(rs.getLong("PJFIXRECSID"));
				fixRecord.setPjFixItem(rs.getString("PJITEMID"));
				fixRecord.setPjFixRecSid(rs.getLong("PJPREDICTID"));
				fixRecord.setPjname(rs.getString("PJNAME"));
				fixRecord.setPjNum(rs.getString("PJNUM"));
				fixRecord.setPjPredictId(rs.getLong("PJPREDICTID"));
				fixRecord.setPjRecId(rs.getLong("PJRECID"));
				fixRecord.setPosiName(rs.getString("POSINAME"));
				fixRecord.setPreStatus(rs.getInt("PRESTATUS"));
				fixRecord.setQi(rs.getString("QI"));
				fixRecord.setQiaffitime(rs.getDate("QIAFFITIME"));
				fixRecord.setQiid(rs.getLong("QIID"));
				fixRecord.setRecstas(rs.getInt("RECSTAS"));
				fixRecord.setRept(rs.getString("REPT"));
				fixRecord.setReptAffirmTime(rs.getString("REPTAFFIRMTIME"));
				fixRecord.setReptId(rs.getLong("REPTID"));
				fixRecord.setRightmargin(rs.getString("RIGHTMARGIN"));
				fixRecord.setRjhmId(rs.getInt("RJHMID"));
				fixRecord.setTeams(rs.getString("TEAMS"));
				fixRecord.setTechId(rs.getLong("TECHID"));
				fixRecord.setTechName(rs.getString("TECHNAME"));
				fixRecord.setTechTime(rs.getDate("TECHTIME"));
				fixRecord.setType(rs.getInt("TYPE"));
				fixRecord.setUnit(rs.getString("UNIT"));
				fixRecord.setUppjnum(rs.getString("UPPJNUM"));
				return fixRecord;
			}
		});
		return pjFixRecords;
	}

	@Override
	public PJStaticInfo findPJStaticInfoById(String jwdCode, Long pjsid) {
		String sql = "select * from pj_staticinfo t where t.pjsid = "+ pjsid +" and jwdcode = '"+ jwdCode +"'";
		final PJStaticInfo staticInfo = new PJStaticInfo();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				staticInfo.setPjsid(rs.getLong("PJSID"));
				staticInfo.setParent(rs.getLong("PARENTID"));
				staticInfo.setJwdCode(rs.getString("JWDCODE"));
				staticInfo.setPjName(rs.getString("PJNAME"));
				staticInfo.setLowestStore(rs.getInt("LOWESTSTORE"));
				staticInfo.setMostStore(rs.getInt("MOSTSTORE"));
				staticInfo.setFirstUnit(rs.getLong("FIRSTUNITID"));
				staticInfo.setPy(rs.getString("PY"));
				staticInfo.setJcType(rs.getString("JCTYPE"));
				staticInfo.setVisitRecord(rs.getInt("VISITRECORD"));
				staticInfo.setPjFixFlowType(rs.getLong("FLOWTYPEID"));
				staticInfo.setType(rs.getInt("TYPE"));
				staticInfo.setAmount(rs.getInt("AMOUNT"));;
				staticInfo.setBzIds(rs.getString("BZIDS"));
			}
		});
		return staticInfo;
	}

	@Override
	public List<PJFixItem> findPjFixItemByStaticId(String jwdCode, long staticId) {
		String sql="select * from PJ_FIXITEM t where t.PJSID= "+ staticId +" and jwdcode = '"+ jwdCode +"' and type <> 3 order by reptime";
		List<PJFixItem> pjFixItems = jdbcTemplate.query(sql, new RowMapper<PJFixItem>() {
			public PJFixItem mapRow(ResultSet rs, int row) throws SQLException {
				PJFixItem fixItem = new PJFixItem();
				fixItem.setAmount(rs.getInt("AMOUNT"));
				fixItem.setChildPJId(rs.getLong("CHILDPJID"));
				fixItem.setChildPJName(rs.getString("CHILDPJNAME"));
				fixItem.setFixItem(rs.getString("FIXITEM"));
				fixItem.setItemctrlacce(rs.getInt("ITEMCTRLACCE"));
				fixItem.setItemctrlcomld(rs.getInt("ITEMCTRLCOMLD"));
				fixItem.setItemctrllead(rs.getInt("ITEMCTRLLEAD"));
				fixItem.setItemctrlqi(rs.getInt("ITEMCTRLQI"));
				fixItem.setItemctrlrept(rs.getInt("ITEMCTRLREPT"));
				fixItem.setItemctrltech(rs.getInt("ITEMCTRLTECH"));
				fixItem.setItemFillDefault(rs.getString("ITEMFILLDEFA"));
				fixItem.setItemOrder(rs.getInt("ITEMORDER"));
				fixItem.setItempy(rs.getString("ITEMPY"));
				fixItem.setJwdCode(rs.getString("JWDCODE"));
				fixItem.setMaxVal(rs.getDouble("MAXVAL"));
				fixItem.setMinVal(rs.getDouble("MINVAL"));
				fixItem.setPjFixFlow(rs.getLong("NODEID"));
				fixItem.setPjItemId(rs.getLong("PJITEMID"));
				fixItem.setPjName(rs.getString("PJNAME"));
				fixItem.setPjStaticInfo(rs.getLong("PJSID"));
				fixItem.setPosiName(rs.getString("POSINAME"));
				fixItem.setTeam(rs.getLong("PROTEAMID"));
				fixItem.setQueryshwstas(rs.getInt("QUERYSHWSTAS"));
				fixItem.setTechStandard(rs.getString("TECHSTANDARD"));
				fixItem.setUnit(rs.getString("UNIT"));
				return fixItem;
			}
		});
		return pjFixItems;
	}

	@Override
	public List<JtPreDict> countReportDetailInfo(String jwdCode, String st, String et, String type, String jcType, String jcNum, String emp, String fixEmp) {
		String sql = "SELECT * FROM " +
				"(SELECT jt.jwdcode, jt.type, dp.jctype, dp.jcnum, jt.repposi, jt.repsituation, jt.repemp, jt.reptime, " + 
				"jt.dealFixEmp, jt.dealsituation, jt.fixendtime, jt.lead, jt.ldaffirmtime, " +
				"jt.qi, jt.qiaffitime, jt.technician, jt.techaffitime, jt.accepter, jt.accetime, jt.commitld, jt.comldaffitime " +
				"FROM jt_predict jt, dateplan_pri dp " +
				"WHERE jt.dateplanpri = dp.rjhmid) t WHERE 1 = 1";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(jwdCode)) {
			sql += "AND t.jwdcode = ? ";
			params.add(jwdCode);
		}
		if(StringUtils.isNotEmpty(st)) {
			sql += "AND SUBSTR(reptime, 0, 10) >= ? ";
			params.add(st);
		}
		if(StringUtils.isNotEmpty(et)) {
			sql += "AND SUBSTR(reptime, 0, 10) <= ? ";
			params.add(et);
		}
		if(StringUtils.isNotEmpty(type)) {
			sql += "AND t.type = ?";
			params.add(Integer.parseInt(type));
		}
		if(StringUtils.isNotEmpty(jcType)) {
			sql += "AND t.jctype = ?";
			params.add(jcType);
		}
		if(StringUtils.isNotEmpty(jcNum)) {
			sql += "AND t.jcNum = ?";
			params.add(jcNum);
		}
		if(StringUtils.isNotEmpty(emp)) {
			sql += "AND t.repemp like ?";
			params.add(emp);
		}
		if(StringUtils.isNotEmpty(fixEmp)) {
			sql += "AND t.dealfixEmp like ?";
			params.add(fixEmp);
		}
		sql += "ORDER BY t.reptime";
		List<JtPreDict> jtPreDicts = jdbcTemplate.query(sql, params.toArray(),new RowMapper<JtPreDict>() {
			public JtPreDict mapRow(ResultSet rs, int row) throws SQLException {
				JtPreDict jtPreDict = new JtPreDict();
				jtPreDict.setAccepter(rs.getString("ACCEPTER"));
				jtPreDict.setAcceTime(rs.getString("ACCETIME"));
				jtPreDict.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jtPreDict.setDealFixEmp(rs.getString("DEALFIXEMP"));
				jtPreDict.setDealSituation(rs.getString("DEALSITUATION"));
				jtPreDict.setFixEndTime(rs.getString("FIXENDTIME"));
				jtPreDict.setJcNum(rs.getString("JCNUM"));
				jtPreDict.setJcType(rs.getString("JCTYPE"));
				jtPreDict.setJwdCode(rs.getString("JWDCODE"));
				jtPreDict.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jtPreDict.setLead(rs.getString("LEAD"));
				jtPreDict.setQi(rs.getString("QI"));
				jtPreDict.setQiAffiTime(rs.getString("QIAFFITIME"));
				jtPreDict.setRepemp(rs.getString("REPEMP"));
				jtPreDict.setRepPosi(rs.getString("REPPOSI"));
				jtPreDict.setRepsituation(rs.getString("REPSITUATION"));
				jtPreDict.setRepTime(rs.getString("REPTIME"));
				jtPreDict.setTechAffiTime(rs.getString("TECHAFFITIME"));
				jtPreDict.setTechnician(rs.getString("TECHNICIAN"));
				jtPreDict.setType(rs.getShort("TYPE"));
				return jtPreDict;
			}
		});
		return jtPreDicts;
	}

	@Override
	public List<Map<String, Object>> countReportCategoryType(String st, String et, String jwdCode) {
		final List<Map<String, Object>> reportList = new ArrayList<Map<String,Object>>();
		String sql = "SELECT SUBSTR(t.reptime, 0, 10) DAY, " +
					 "SUM(CASE WHEN TYPE = 0 THEN 1 ELSE 0 END) \"JT28\", " +
					 "SUM(CASE WHEN TYPE = 1 THEN 1 ELSE 0 END) \"FJ\", " +
					 "SUM(CASE WHEN TYPE = 2 THEN 1 ELSE 0 END) \"GC\", " +
					 "SUM(CASE WHEN TYPE = 6 THEN 1 ELSE 0 END) \"LGL\" " +
					 "FROM jt_predict t " +
					 "WHERE t.type IN(0, 1, 2, 6) AND t.jwdcode = ? AND SUBSTR(t.reptime, 0, 10) BETWEEN ? AND ? " +
					 "GROUP BY SUBSTR(t.reptime, 0, 10) " +
					 "ORDER BY SUBSTR(t.reptime, 0, 10)";
		jdbcTemplate.query(sql, new Object[]{jwdCode, st, et}, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Integer total = 0;
				Map<String, Object> reportMap = new HashMap<String, Object>();
				String date = rs.getString("day");
				reportMap.put("date", date);
				String jt28 = rs.getString("JT28");
				reportMap.put("JT28", Integer.parseInt(jt28));
				total += Integer.parseInt(jt28);
				String fj = rs.getString("FJ");
				reportMap.put("FJ", Integer.parseInt(fj));
				total += Integer.parseInt(fj);
				String gc = rs.getString("GC");
				reportMap.put("GC", Integer.parseInt(gc));
				total += Integer.parseInt(gc);
				String lgl = rs.getString("LGL");
				reportMap.put("LGL", Integer.parseInt(lgl));
				total += Integer.parseInt(lgl);
				reportMap.put("total", total);
				reportList.add(reportMap);
				return null;
			}
		});
		return reportList;
	}

	@Override
	public List<Map<String, Object>> countReportCategoryJctype(String st, String et, String jwdCode) {
		final List<Map<String, Object>> reportList = new ArrayList<Map<String,Object>>();
		String sql = "SELECT counts.day," +
					 "SUM(CASE WHEN counts.jctype = 'DF4' THEN counts.count ELSE 0 END) \"DF4\", " +
					 "SUM(CASE WHEN counts.jctype = 'DF5' THEN counts.count ELSE 0 END) \"DF5\", " +
					 "SUM(CASE WHEN counts.jctype = 'DF7' THEN counts.count ELSE 0 END) \"DF7\", " +
					 "SUM(CASE WHEN counts.jctype = 'DF7C' THEN counts.count ELSE 0 END) \"DF7C\", " +
					 "SUM(CASE WHEN counts.jctype = 'DF11G' THEN counts.count ELSE 0 END) \"DF11G\", " +
					 "SUM(CASE WHEN counts.jctype = 'DF12' THEN counts.count ELSE 0 END) \"DF12\", " +
					 "SUM(CASE WHEN counts.jctype = 'SS3' THEN counts.count ELSE 0 END) \"SS3\", " +
					 "SUM(CASE WHEN counts.jctype = 'SS3B' THEN counts.count ELSE 0 END) \"SS3B\", " +
					 "SUM(CASE WHEN counts.jctype = 'SS6' THEN counts.count ELSE 0 END) \"SS6\", " +
					 "SUM(CASE WHEN counts.jctype = 'SS6B' THEN counts.count ELSE 0 END) \"SS6B\", " +
					 "SUM(CASE WHEN counts.jctype = 'SS8' THEN counts.count ELSE 0 END) \"SS8\", " +
					 "SUM(CASE WHEN counts.jctype = 'SS9' THEN counts.count ELSE 0 END) \"SS9\", " +
					 "SUM(CASE WHEN counts.jctype = 'HXD1C' THEN counts.count ELSE 0 END) \"HXD1C\", " +
					 "SUM(CASE WHEN counts.jctype = 'HXD3C' THEN counts.count ELSE 0 END) \"HXD3C\" " +
					 "FROM " +
					 "(SELECT SUBSTR(unions.reptime, 0, 10) DAY,  " +
					 "unions.jctype, " +
					 "COUNT(*) count " +
					 "FROM " +
					 "(" +
					 "SELECT dp.rjhmid, dp.jctype, jt.reptime, jt.jwdcode, jt.type " +
					 "FROM jt_predict jt, dateplan_pri dp " +
					 "WHERE jt.dateplanpri = dp.rjhmid " +
					 ") unions " +
					 "WHERE unions.type IN(0, 1, 2, 6) AND unions.jwdcode = ? AND SUBSTR(unions.reptime, 0, 10) BETWEEN ? AND ? " +
					 "GROUP BY SUBSTR(unions.reptime, 0, 10), unions.jctype " +
					 "ORDER BY SUBSTR(unions.reptime, 0, 10) ) counts " +
					 "GROUP BY counts.day " +
					 "ORDER BY counts.day"; 
		jdbcTemplate.query(sql, new Object[]{jwdCode, st, et}, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Integer total = 0;
				Map<String, Object> reportMap = new HashMap<String, Object>();
				String date = rs.getString("day");
				reportMap.put("date", date);
				String df4 = rs.getString("DF4");
				reportMap.put("DF4", Integer.parseInt(df4));
				total += Integer.parseInt(df4);
				String df5 = rs.getString("DF5");
				reportMap.put("DF5", Integer.parseInt(df5));
				total += Integer.parseInt(df5);
				String df7 = rs.getString("DF7");
				reportMap.put("DF7", Integer.parseInt(df7));
				total += Integer.parseInt(df7);
				String df7c = rs.getString("DF7C");
				reportMap.put("DF7C", Integer.parseInt(df7c));
				total += Integer.parseInt(df7c);
				String df11g = rs.getString("DF11G");
				reportMap.put("DF11G", Integer.parseInt(df11g));
				total += Integer.parseInt(df11g);
				String df12 = rs.getString("DF12");
				reportMap.put("DF12", Integer.parseInt(df12));
				total += Integer.parseInt(df12);
				String ss3 = rs.getString("SS3");
				reportMap.put("SS3", Integer.parseInt(ss3));
				total += Integer.parseInt(ss3);
				String ss3b = rs.getString("SS3B");
				reportMap.put("SS3B", Integer.parseInt(ss3b));
				total += Integer.parseInt(ss3b);
				String ss6 = rs.getString("SS6");
				reportMap.put("SS6", Integer.parseInt(ss6));
				total += Integer.parseInt(ss6);
				String ss6b = rs.getString("SS6B");
				reportMap.put("SS6B", Integer.parseInt(ss6b));
				total += Integer.parseInt(ss6b);
				String ss8 = rs.getString("SS8");
				reportMap.put("SS8", Integer.parseInt(ss8));
				total += Integer.parseInt(ss8);
				String ss9 = rs.getString("SS9");
				reportMap.put("SS9", Integer.parseInt(ss9));
				total += Integer.parseInt(ss9);
				String hxd1c = rs.getString("HXD1C");
				reportMap.put("HXD1C", Integer.parseInt(hxd1c));
				total += Integer.parseInt(hxd1c);
				String hxd3c = rs.getString("HXD3C");
				reportMap.put("HXD3C", Integer.parseInt(hxd3c));
				total += Integer.parseInt(hxd3c);
				reportMap.put("total", total);
				reportList.add(reportMap);
				return null;
			}
		});
		return reportList;
	}

	@Override
	public List<Map<String, Object>> countPlans(String st, String et) {
		final List<Map<String, Object>> planList = new ArrayList<Map<String,Object>>();
		String sql = "SELECT times.day, " +
					 "SUM(CASE WHEN mainPlanId IS NOT NULL AND jwdcode = '0801' THEN 1 ELSE 0 END) \"GZ_PLAN\", " +
					 "SUM(CASE WHEN isCash != 0 AND jwdcode = '0801' THEN 1 ELSE 0 END) \"GZ_ACTUAL\", " +
					 "SUM(CASE WHEN isCash = 0 AND jwdcode = '0801' THEN 1 ELSE 0 END) \"GZ_UNFILL\", " +
					 "SUM(CASE WHEN mainPlanId IS NOT NULL AND jwdcode = '0818' THEN 1 ELSE 0 END) \"LC_PLAN\", " +
					 "SUM(CASE WHEN isCash != 0 AND jwdcode = '0818' THEN 1 ELSE 0 END) \"LC_ACTUAL\", " +
					 "SUM(CASE WHEN isCash = 0 AND jwdcode = '0818' THEN 1 ELSE 0 END) \"LC_UNFILL\", " +
					 "SUM(CASE WHEN mainPlanId IS NOT NULL AND jwdcode = '0819' THEN 1 ELSE 0 END) \"SS_PLAN\", " +
					 "SUM(CASE WHEN isCash != 0 AND jwdcode = '0819' THEN 1 ELSE 0 END) \"SS_ACTUAL\", " +
					 "SUM(CASE WHEN isCash = 0 AND jwdcode = '0819' THEN 1 ELSE 0 END) \"SS_UNFILL\", " +
					 "SUM(CASE WHEN mainPlanId IS NOT NULL AND jwdcode = '0805' THEN 1 ELSE 0 END) \"ZZ_PLAN\", " +
					 "SUM(CASE WHEN isCash != 0 AND jwdcode = '0805' THEN 1 ELSE 0 END) \"ZZ_ACTUAL\", " +
					 "SUM(CASE WHEN isCash = 0 AND jwdcode = '0805' THEN 1 ELSE 0 END) \"ZZ_UNFILL\", " +
					 "SUM(CASE WHEN mainPlanId IS NOT NULL AND jwdcode = '08052' THEN 1 ELSE 0 END) \"CS_PLAN\", " +
					 "SUM(CASE WHEN isCash != 0 AND jwdcode = '08052' THEN 1 ELSE 0 END) \"CS_ACTUAL\", " +
					 "SUM(CASE WHEN isCash = 0 AND jwdcode = '08052' THEN 1 ELSE 0 END) \"CS_UNFILL\", " +
					 "SUM(CASE WHEN mainPlanId IS NOT NULL AND jwdcode = '0809' THEN 1 ELSE 0 END) \"HH_PLAN\", " +
					 "SUM(CASE WHEN isCash != 0 AND jwdcode = '0809' THEN 1 ELSE 0 END) \"HH_ACTUAL\", " +
					 "SUM(CASE WHEN isCash = 0 AND jwdcode = '0809' THEN 1 ELSE 0 END) \"HH_UNFILL\" " +
					 "FROM " +
					 "(SELECT TO_CHAR(TO_DATE(?, 'YYYY-MM-DD') + (ROWNUM - 1 ), 'YYYY-MM-DD') day " +
					 "FROM dual " +
					 "CONNECT BY ROWNUM <= (TO_DATE(?, 'YYYY-MM-DD') - TO_DATE(?, 'YYYY-MM-DD') + 1) ) times " +
					 "LEFT OUTER JOIN  " +
					 "(SELECT * " +
					 "FROM mainplandetail t " +
					 "WHERE t.plantime BETWEEN ? AND ?) plans " +
					 "ON plans.plantime = times.day " +
					 "GROUP BY times.day " +
					 "ORDER BY times.day";
		jdbcTemplate.query(sql, new Object[]{st, et, st, st, et}, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Object> reportMap = new HashMap<String, Object>();
				String date = rs.getString("day");
				reportMap.put("date", date);
				String gz_plan = rs.getString("GZ_PLAN");
				reportMap.put("gz_plan", Integer.parseInt(gz_plan));
				String gz_actual = rs.getString("GZ_ACTUAL");
				reportMap.put("gz_actual", Integer.parseInt(gz_actual));
				String gz_unfill = rs.getString("GZ_UNFILL");
				reportMap.put("gz_unfill", Integer.parseInt(gz_unfill));
				String lc_plan = rs.getString("LC_PLAN");
				reportMap.put("lc_plan", Integer.parseInt(lc_plan));
				String lc_actual = rs.getString("LC_ACTUAL");
				reportMap.put("lc_actual", Integer.parseInt(lc_actual));
				String lc_unfill = rs.getString("LC_UNFILL");
				reportMap.put("lc_unfill", Integer.parseInt(lc_unfill));
				String ss_plan = rs.getString("SS_PLAN");
				reportMap.put("ss_plan", Integer.parseInt(ss_plan));
				String ss_actual = rs.getString("SS_ACTUAL");
				reportMap.put("ss_actual", Integer.parseInt(ss_actual));
				String ss_unfill = rs.getString("SS_UNFILL");
				reportMap.put("ss_unfill", Integer.parseInt(ss_unfill));
				String zz_plan = rs.getString("ZZ_PLAN");
				reportMap.put("zz_plan", Integer.parseInt(zz_plan));
				String zz_actual = rs.getString("ZZ_ACTUAL");
				reportMap.put("zz_actual", Integer.parseInt(zz_actual));
				String zz_unfill = rs.getString("ZZ_UNFILL");
				reportMap.put("zz_unfill", Integer.parseInt(zz_unfill));
				String cs_plan = rs.getString("CS_PLAN");
				reportMap.put("cs_plan", Integer.parseInt(cs_plan));
				String cs_actual = rs.getString("CS_ACTUAL");
				reportMap.put("cs_actual", Integer.parseInt(cs_actual));
				String cs_unfill = rs.getString("CS_UNFILL");
				reportMap.put("cs_unfill", Integer.parseInt(cs_unfill));
				String hh_plan = rs.getString("HH_PLAN");
				reportMap.put("hh_plan", Integer.parseInt(hh_plan));
				String hh_actual = rs.getString("HH_ACTUAL");
				reportMap.put("hh_actual", Integer.parseInt(hh_actual));
				String hh_unfill = rs.getString("HH_UNFILL");
				reportMap.put("hh_unfill", Integer.parseInt(hh_unfill));
				planList.add(reportMap);
				return null;
			}
		});
		return planList;
	}

	@Override
	public List<MainPlanDetail> countPlansDetail(String st, String et, String jwdCode, String isCash, String jcType, String jcNum) {
		String sql = "SELECT * FROM mainplandetail WHERE 1 = 1 ";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(st)) {
			sql += "AND planTime >= ? ";
			params.add(st);
		}
		if (StringUtils.isNotEmpty(et)) {
			sql += "AND planTime <= ? ";
			params.add(et);
		}
		if (StringUtils.isNotEmpty(jwdCode)) {
			sql += "AND jwdcode = ? ";
			params.add(jwdCode);
		}
		if (StringUtils.isNotEmpty(isCash) && Integer.parseInt(isCash) == 1) {
			sql += "AND isCash = 1 OR  isCash = 2 ";
		}
		if(StringUtils.isNotEmpty(isCash) && Integer.parseInt(isCash) == 0) {
			sql += "AND isCash = 0 ";
		}
		if (StringUtils.isNotEmpty(jcType)) {
			sql += "AND jcType like ? ";
			params.add(jcType);
		}
		if (StringUtils.isNotEmpty(jcNum)) {
			sql += "AND jcNum = ? ";
			params.add(jcNum);
		}
		sql += "ORDER BY planTime";
		List<MainPlanDetail> detailList = jdbcTemplate.query(sql, params.toArray(),new RowMapper<MainPlanDetail>() {
			public MainPlanDetail mapRow(ResultSet rs, int row) throws SQLException {
				MainPlanDetail detail = new MainPlanDetail();
				detail.setJcType(rs.getString("JCTYPE"));
				detail.setJcNum(rs.getString("JCNUM"));
				detail.setXcxc(rs.getString("XCXC"));
				detail.setKilometre(rs.getString("KILOMETRE"));
				detail.setRealKilometre(rs.getString("realKilometre"));
				detail.setKcArea(rs.getString("KCAREA"));
				detail.setComments(rs.getString("COMMENTS"));
				detail.setIsCash(rs.getInt("isCash"));
				detail.setCashReason(rs.getString("cashReason"));
				return detail;
			}
		});
		return detailList;
	}

	@Override
	public List<Map<String, Object>> countMonthRateData(String st, String et) {
		String sql = "SELECT month, " +
				"MAX(CASE WHEN jwdcode = '0801' THEN rate ELSE  '0.00%' END) gz, " +
				"MAX(CASE WHEN jwdcode = '0818' THEN rate ELSE  '0.00%' END) lc, " +
				"MAX(CASE WHEN jwdcode = '0819' THEN rate ELSE  '0.00%' END) ss, " +
				"MAX(CASE WHEN jwdcode = '0805' THEN rate ELSE  '0.00%' END) zz, " +
				"MAX(CASE WHEN jwdcode = '08052' THEN rate ELSE '0.00%' END) cs, " +
				"MAX(CASE WHEN jwdcode = '0809' THEN rate ELSE  '0.00%' END) hh " +
				"FROM " +
				"(SELECT months.month, " +
				"counts.jwdcode, " +
				"DECODE(plans, null, 0, plans) plans, " +
				"DECODE(actual, null, 0, actual) actual, " +
				"DECODE(unfill, null, 0, unfill) unfill, " +
				"DECODE(actual/NVL(DECODE(actual,0, '2 ',actual), 1) ,null,'0.00',0,'0.00',TO_CHAR(ROUND(actual/plans*100,2),'FM90.00'))||'%' rate " +
				"FROM " +
				"(SELECT TO_CHAR(ADD_MONTHS(TO_DATE(?, 'YYYY-MM'), ROWNUM - 1), 'YYYY-MM') month " +
				"FROM dual " +
				"CONNECT BY ROWNUM <= MONTHS_BETWEEN(TO_DATE(?, 'YYYY-MM'), TO_DATE(?,'YYYY-MM')) + 1) months " +
				"LEFT OUTER JOIN " +
				"(SELECT SUBSTR(t.plantime,0, 7) month,  " +
				"jwdcode, " +
				"COUNT(*) plans, " +
				"SUM(CASE WHEN isCash != 0 THEN 1 ELSE 0 END) actual, " +
				"SUM(CASE WHEN isCash = 0 THEN 1 ELSE 0 END) unfill " +
				"FROM mainplandetail t " +
				"GROUP BY jwdcode, SUBSTR(t.plantime,0, 7) ) counts " +
				"ON months.month = counts.month " +
				"ORDER BY months.month) mj " +
				"GROUP BY month " +
				"ORDER BY month";
		final List<Map<String, Object>> countRateList = new ArrayList<Map<String,Object>>();
		jdbcTemplate.query(sql, new Object[]{st, et, st}, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Object> rateMap = new HashMap<String, Object>();
				String month = rs.getString("month");
				rateMap.put("month", month);
				String gz = rs.getString("gz");
				rateMap.put("gz", gz);
				String lc = rs.getString("lc");
				rateMap.put("lc", lc);
				String ss = rs.getString("ss");
				rateMap.put("ss", ss);
				String zz = rs.getString("zz");
				rateMap.put("zz", zz);
				String cs = rs.getString("cs");
				rateMap.put("cs", cs);
				String hh = rs.getString("hh");
				rateMap.put("hh", hh);
				countRateList.add(rateMap);
				return null;
			}
		});
		return countRateList;
	}
}

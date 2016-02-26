package com.major.admin.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.major.admin.dao.CommonDaoI;
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
import com.major.base.vo.Value;

public class CommonDaoImpl implements CommonDaoI {
	
	/** spring jdbcTemplate 对象 */
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<DictArea> queryDictAreaList() {
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
		return areaList;
	}

	@Override
	public List<JcType> queryDictJcTypeList() {
		String sql = "select * from DICT_JCSTYPE";
		List<JcType> typeList = jdbcTemplate.query(sql, new RowMapper<JcType>() {
			public JcType mapRow(ResultSet rs, int row) throws SQLException {
				JcType jcType = new JcType();
				jcType.setId(rs.getLong("JCTYPEID"));
				jcType.setJcType(rs.getString("JCTYPE"));
				jcType.setType(rs.getString("JCSTYPEVALUE"));
				return jcType;
			}
		});
		return typeList;
	}

	@Override
	public DictArea queryDictAreaByCode(String jwdCode) {
		String areaSql = "select * from DICT_AREA where jwdcode = '"+ jwdCode +"'";
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
				area.setGdtUrl(rs.getString("gdturl"));
				return area;
			}
		});
		return areaList.get(0);
	}
	
	@Override
	public DatePlanPri findDatePlanPriById(String jwdCode, String rjhmId) {
		String sql = "select * from DATEPLAN_PRI where RJHMID = " + Long.parseLong(rjhmId) + " and jwdcode = '"+ jwdCode +"'";
		final DatePlanPri planPri = new DatePlanPri();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
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
			}
		});
		return planPri;
	}

	@Override
	public String findXcxc(String xcxc, String jwdCode) {
		String sql = "select * from dict_jcfixseq dj where dj.fixvalue='"+ xcxc +"' and jwdcode = '"+ jwdCode +"'";
		final Value flowval = new Value();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				flowval.setStringValue(rs.getString("FLOWVAL"));
			}
		});
		return flowval.getStringValue();
	} 

	@Override
	public List<JtPreDict> findJtPreDictPre(String jwdCode, Integer datePlanPri) {
		String sql = "select * from JT_PREDICT jf where jf.DATEPLANPRI= "+ datePlanPri +" and jf.type!=3 and jf.recStas>=2 and jwdcode = '"+ jwdCode +"'";
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
	public SignedForFinish findSignedForFinishByPlan(String jwdCode, Integer datePlanPri) {
		String sql = "select * from SIGNEDFORFINISH jf where jf.dpId= "+ datePlanPri +" and jwdCode = '"+ jwdCode +"'";
		List<SignedForFinish> signedForFinishs = jdbcTemplate.query(sql, new RowMapper<SignedForFinish>() {
			public SignedForFinish mapRow(ResultSet rs, int row) throws SQLException {
				SignedForFinish signedForFinish = new SignedForFinish();
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
				return signedForFinish;
			}
		});
		return signedForFinishs.get(0);
	}

	@Override
	public String getParameterValueById(int id) {
		String sql = "select PARAMETER_VALUE from SYSTEM_PARAMETER sp where sp.id = ?";
		return jdbcTemplate.queryForObject(sql, new Integer[]{id}, String.class);
	}

	@Override
	public Long countItemRelation(String jctype) {
		String sql = "select count(*) from ITEM_RELATION t where t.jcType like ? and t.itemIds is not null";
		return jdbcTemplate.queryForObject(sql, new String[]{jctype}, Long.class);
	}

	@Override
	public List<DictFirstUnit> listFirstUnitsOfTemplate(String jctype) {
		String sql = "select distinct t.FIRSTUNITID as firstUnitId,t.FIRSTUNITNAME as firstUnitName from item_relation t where t.JCTYPE like ?";
		List<DictFirstUnit> unitList = jdbcTemplate.query(sql, new String[]{jctype}, new RowMapper<DictFirstUnit>() {
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
	public List<ItemRelation> listItemRelation(String jctype, String xcxc, Integer firstUnitId) {
		String sql = "select * from ITEM_RELATION t where t.jcType=? and t.firstUnitId=? and t.xcxc like ?";
		List<ItemRelation> itemRelations = jdbcTemplate.query(sql, new Object[]{jctype, firstUnitId, xcxc}, new RowMapper<ItemRelation>() {
			public ItemRelation mapRow(ResultSet rs, int row) throws SQLException {
				ItemRelation itemRelation = new ItemRelation();
				itemRelation.setId(rs.getInt("ID"));
				itemRelation.setJcType(rs.getString("JCTYPE"));
				itemRelation.setFirstUnitId(rs.getInt("FIRSTUNITID"));
				itemRelation.setFirstUnitName(rs.getString("FIRSTUNITNAME"));
				itemRelation.setFixItem(rs.getString("FIXITEM"));
				itemRelation.setFixContent(rs.getString("FIXCONTENT"));
				itemRelation.setTechStanderd(rs.getString("TECHSTANDERD"));
				itemRelation.setXcxc(rs.getString("XCXC"));
				itemRelation.setPosition(rs.getString("POSITION"));
				itemRelation.setUnit(rs.getString("UNIT"));
				itemRelation.setItemIds(rs.getString("ITEMIDS"));
				return itemRelation;
			}
		});
		return itemRelations;
	}

	@Override
	public List<JCFixrec> listJCFixrec(String jwdCode, Integer rjhmId, String itemIds) {
		String sql = "select * from JC_FIXREC t where t.DYPRECID= "+ rjhmId +" and t.THIRDUNITID in ("+itemIds+") and jwdCode = '"+ jwdCode +"'";
		List<JCFixrec> jcFixrecs = jdbcTemplate.query(sql, new RowMapper<JCFixrec>() {
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
	public DictProTeam findDictProTeamByPY(String jwdCode, String tSZ_PY) {
		String sql = "select * from DICT_PROTEAM d where d.py='"+ tSZ_PY +"' and jwdcode = '"+ jwdCode +"'";
		final DictProTeam proTeam = new DictProTeam();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				proTeam.setProteamid(rs.getLong("PROTEAMID"));
				proTeam.setProteamname(rs.getString("PROTEAMNAME"));
				proTeam.setJctype(rs.getString("JCTYPE"));
				proTeam.setJwdcode(rs.getString("JWDCODE"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setWorkflag(rs.getInt("WORKFLAG"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setZxFlag(rs.getInt("ZXFLAG"));
			}
		});
		return proTeam;
	}

	@Override
	public List<JcExpRec> findJcExpRecs(String jwdCode, int rjhmId, long jceiId) {
		String sql = "select jer.* from jc_exp_rec jer join jc_experiment_item jei on jer.itemid = jei.jceiid where jei.parentid=? and jer.dyprecid=? and jer.jwdcode = ?";
		List<JcExpRec> jcExpRecs = jdbcTemplate.query(sql, new Object[]{jceiId, rjhmId, jwdCode}, new RowMapper<JcExpRec>() {
			public JcExpRec mapRow(ResultSet rs, int row) throws SQLException {
				JcExpRec jcExpRec = new JcExpRec();
				jcExpRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jcExpRec.setAccepter(rs.getString("ACCEPTER"));
				jcExpRec.setAccepterId(rs.getInt("ACCEPTERID"));
				jcExpRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jcExpRec.setCommitLead(rs.getString("COMMITLEAD"));
				jcExpRec.setCommitLeadId(rs.getInt("COMMITLEADID"));
				jcExpRec.setDoPjBarCode(rs.getString("COMMITLEADID"));
				jcExpRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jcExpRec.setDypRecId(rs.getInt("DYPRECID"));
				jcExpRec.setEmpAffirmTime(rs.getString("EMPAFFIRMTIME"));
				jcExpRec.setExpStatus(rs.getString("EXPSTATUS"));
				jcExpRec.setExpType(rs.getInt("EXPTYPE"));
				jcExpRec.setFixEmp(rs.getString("FIXEMP"));
				jcExpRec.setFixEmpId(rs.getString("FIXEMPID"));
				jcExpRec.setFixSignee(rs.getString("FIXSIGNEE"));
				jcExpRec.setFixSigneeId(rs.getInt("FIXSIGNEEID"));
				jcExpRec.setItemId(rs.getLong("ITEMID"));
				jcExpRec.setItemName(rs.getString("ITEMNAME"));
				jcExpRec.setJceRId(rs.getLong("JCERID"));
				jcExpRec.setJcnum(rs.getString("JCNUM"));
				jcExpRec.setJwdCode(rs.getString("JWDCODE"));
				jcExpRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jcExpRec.setLeader(rs.getString("LEADER"));
				jcExpRec.setLeadId(rs.getInt("LEADID"));
				jcExpRec.setQi(rs.getString("QI"));
				jcExpRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jcExpRec.setQiId(rs.getInt("QIID"));
				jcExpRec.setRecStas(rs.getShort("RECSTAS"));
				jcExpRec.setTeachFiTime(rs.getString("TEACHFITIME"));
				jcExpRec.setUnit(rs.getString("UNIT"));
				jcExpRec.setTeachId(rs.getInt("TEACHID"));
				jcExpRec.setUpPjBarCode(rs.getString("UPPJBARCODE"));
				jcExpRec.setUpPjNum(rs.getString("UPPJNUM"));
				jcExpRec.setXc(rs.getString("XC"));
				return jcExpRec;
			}
		});
		return jcExpRecs;
	}

	@Override
	public JcExpRec findJcExperimentByDatePlanAndExpId(String jwdCode, int rjhmId, String expId) {
		String sql = "select * from jc_exp_rec rec where rec.dyprecid=? and rec.itemId = ? and jwdcode = ? and rec.expType=1";
		final JcExpRec jcExpRec = new JcExpRec();
		jdbcTemplate.query(sql, new Object[]{rjhmId, expId, jwdCode}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{ 
				jcExpRec.setAcceAffiTime(rs.getString("ACCEAFFITIME"));
				jcExpRec.setAccepter(rs.getString("ACCEPTER"));
				jcExpRec.setAccepterId(rs.getInt("ACCEPTERID"));
				jcExpRec.setComLdAffiTime(rs.getString("COMLDAFFITIME"));
				jcExpRec.setCommitLead(rs.getString("COMMITLEAD"));
				jcExpRec.setCommitLeadId(rs.getInt("COMMITLEADID"));
				jcExpRec.setDoPjBarCode(rs.getString("COMMITLEADID"));
				jcExpRec.setDownPjNum(rs.getString("DOWNPJNUM"));
				jcExpRec.setDypRecId(rs.getInt("DYPRECID"));
				jcExpRec.setEmpAffirmTime(rs.getString("EMPAFFIRMTIME"));
				jcExpRec.setExpStatus(rs.getString("EXPSTATUS"));
				jcExpRec.setExpType(rs.getInt("EXPTYPE"));
				jcExpRec.setFixEmp(rs.getString("FIXEMP"));
				jcExpRec.setFixEmpId(rs.getString("FIXEMPID"));
				jcExpRec.setFixSignee(rs.getString("FIXSIGNEE"));
				jcExpRec.setFixSigneeId(rs.getInt("FIXSIGNEEID"));
				jcExpRec.setItemId(rs.getLong("ITEMID"));
				jcExpRec.setItemName(rs.getString("ITEMNAME"));
				jcExpRec.setJceRId(rs.getLong("JCERID"));
				jcExpRec.setJcnum(rs.getString("JCNUM"));
				jcExpRec.setJwdCode(rs.getString("JWDCODE"));
				jcExpRec.setLdAffirmTime(rs.getString("LDAFFIRMTIME"));
				jcExpRec.setLeader(rs.getString("LEADER"));
				jcExpRec.setLeadId(rs.getInt("LEADID"));
				jcExpRec.setQi(rs.getString("QI"));
				jcExpRec.setQiAffiTime(rs.getString("QIAFFITIME"));
				jcExpRec.setQiId(rs.getInt("QIID"));
				jcExpRec.setRecStas(rs.getShort("RECSTAS"));
				jcExpRec.setTeachFiTime(rs.getString("TEACHFITIME"));
				jcExpRec.setUnit(rs.getString("UNIT"));
				jcExpRec.setTeachId(rs.getInt("TEACHID"));
				jcExpRec.setUpPjBarCode(rs.getString("UPPJBARCODE"));
				jcExpRec.setUpPjNum(rs.getString("UPPJNUM"));
				jcExpRec.setXc(rs.getString("XC"));
			} 
		});  
		return jcExpRec;
	}
	
	@Override
	public List<String> findFittingNumberForFixRec(String jwdCode, int rjhmId) {
		String sql = "select jzf.uppjnum from jc_zx_fixrec jzf where jzf.uppjnum is not null and jzf.dyprecid= "+ rjhmId +" and jwdcode = '"+ jwdCode +"'";
		List<String> list =  jdbcTemplate.queryForList(sql, String.class);
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
	public List<PJFixRecord> forFirstUnit(String jwdCode, long firstunitid, List<String> fittingNums) {
		String sql = "select distinct pfc.*, psi.FIRSTUNITID from PJ_FIXRECORD pfc join PJ_DYNAMICINFO pdi on pfc.PJDID=pdi.PJDID join PJ_STATICINFO psi on pdi.PJSID=psi.PJSID where pdi.PJNUM in (:PJNUMS) and psi.FIRSTUNITID=:FIRSTUNITID and pfc.TYPE=1  and pfc.jwdcode = ? order by pfc.pjname, psi.FIRSTUNITID";
		List<PJFixRecord> pjFixRecords = jdbcTemplate.query(sql, new Object[]{fittingNums, jwdCode}, new RowMapper<PJFixRecord>() {
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
	public DictProTeam findDictProTeamById(String jwdCode, Long bzId) {
		String sql = "select * from DICT_PROTEAM d where d.PROTEAMID="+ bzId +" and jwdcode = '"+ jwdCode +"'";
		final DictProTeam proTeam = new DictProTeam();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				proTeam.setProteamid(rs.getLong("PROTEAMID"));
				proTeam.setProteamname(rs.getString("PROTEAMNAME"));
				proTeam.setJctype(rs.getString("JCTYPE"));
				proTeam.setJwdcode(rs.getString("JWDCODE"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setWorkflag(rs.getInt("WORKFLAG"));
				proTeam.setPy(rs.getString("PY"));
				proTeam.setZxFlag(rs.getInt("ZXFLAG"));
			}
		});
		return proTeam;
	}

	@Override
	public List<DictJcStype> findDictJcStype() {
		String sql = "select * from DICT_JCSTYPE";
		List<DictJcStype> dictJcStypes = jdbcTemplate.query(sql, new RowMapper<DictJcStype>() {
			public DictJcStype mapRow(ResultSet rs, int row) throws SQLException {
				DictJcStype dictJcStype = new DictJcStype();
				dictJcStype.setJcTypeId(rs.getInt("JCTYPEID"));
				dictJcStype.setJcStypeValue(rs.getString("JCTYPE"));
				return dictJcStype;
			}
		});
		return dictJcStypes;
	}

	@Override
	public String getFirstunitname(String jwdCode, Long firstUnitId) {
		String sql = "select FIRSTUNITNAME from DICT_FIRSTUNIT where FIRSTUNITID = ? and jwdcode = ?";
		String firstUnitName = jdbcTemplate.queryForObject(sql, String.class, new Object[]{firstUnitId, jwdCode});
		return firstUnitName;
	}
}

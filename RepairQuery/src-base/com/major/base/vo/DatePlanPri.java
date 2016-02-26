package com.major.base.vo;

import java.io.Serializable;

/**
 * 检修日计划主表
 * 
 * @author eleven
 * 
 */
public class DatePlanPri implements Serializable {

	private static final long serialVersionUID = -8854154275158320603L;

	// 日计划记录ID
	private Integer rjhmId;
	// 机务段代码
	private String jwdcode;
	// 车型
	private String jcType;
	// 车编号
	private String jcnum;
	// 修程修次
	private String fixFreque;
	// 扣车时间
	private String kcsj;
	// 计划起机时间
	private String jhqjsj;
	// 实际起机时间
	private String sjqjsj;
	// 计划交车时间
	private String jhjcsj;
	// 实际交车时间
	private String sjjcsj;
	// 交车工长ID
	private String gongZhang;
	// 质检ID
	private String zhiJian;
	// 技术ID
	private String jiShu;
	// 验收员ID
	private String yanShou;
	// 计划状态：-1-新建 , 0-在修 1-待验 2-已交 3-转出
	private Integer planStatue;
	// 股道号
	private String gdh;
	// 台位号
	private String twh;
	// 制定计划人
	private String zdr;
	// 制定计划时间
	private String zdsj;
	// 机车检修流程节点ID
	private Integer nodeid;
	// 机车检修流程名称
	private String nodeName;
	// 项目类型 0：小辅修 1:中修
	private Integer projectType;

	private Integer otherId;
	// 备注
	private String comments;
	// 负责包修班组ID
	private Long proteamId;
	// 不进行包修的班组 例如3,5
	private String workTeam;

	public Integer getRjhmId() {
		return rjhmId;
	}

	public void setRjhmId(Integer rjhmId) {
		this.rjhmId = rjhmId;
	}

	public String getJcType() {
		return jcType;
	}

	public void setJcType(String jcType) {
		this.jcType = jcType;
	}

	public String getJcnum() {
		return jcnum;
	}

	public void setJcnum(String jcnum) {
		this.jcnum = jcnum;
	}

	public String getFixFreque() {
		return fixFreque;
	}

	public void setFixFreque(String fixFreque) {
		this.fixFreque = fixFreque;
	}

	public String getKcsj() {
		return kcsj;
	}

	public void setKcsj(String kcsj) {
		this.kcsj = kcsj;
	}

	public String getJhqjsj() {
		return jhqjsj;
	}

	public void setJhqjsj(String jhqjsj) {
		this.jhqjsj = jhqjsj;
	}

	public String getSjqjsj() {
		return sjqjsj;
	}

	public void setSjqjsj(String sjqjsj) {
		this.sjqjsj = sjqjsj;
	}

	public String getJhjcsj() {
		return jhjcsj;
	}

	public void setJhjcsj(String jhjcsj) {
		this.jhjcsj = jhjcsj;
	}

	public String getSjjcsj() {
		return sjjcsj;
	}

	public void setSjjcsj(String sjjcsj) {
		this.sjjcsj = sjjcsj;
	}

	public String getGongZhang() {
		return gongZhang;
	}

	public void setGongZhang(String gongZhang) {
		this.gongZhang = gongZhang;
	}

	public String getZhiJian() {
		return zhiJian;
	}

	public void setZhiJian(String zhiJian) {
		this.zhiJian = zhiJian;
	}

	public String getJiShu() {
		return jiShu;
	}

	public void setJiShu(String jiShu) {
		this.jiShu = jiShu;
	}

	public String getYanShou() {
		return yanShou;
	}

	public void setYanShou(String yanShou) {
		this.yanShou = yanShou;
	}

	public Integer getPlanStatue() {
		return planStatue;
	}

	public void setPlanStatue(Integer planStatue) {
		this.planStatue = planStatue;
	}

	public String getGdh() {
		return gdh;
	}

	public void setGdh(String gdh) {
		this.gdh = gdh;
	}

	public String getTwh() {
		return twh;
	}

	public void setTwh(String twh) {
		this.twh = twh;
	}

	public String getZdr() {
		return zdr;
	}

	public void setZdr(String zdr) {
		this.zdr = zdr;
	}

	public String getZdsj() {
		return zdsj;
	}

	public void setZdsj(String zdsj) {
		this.zdsj = zdsj;
	}

	public Integer getNodeid() {
		return nodeid;
	}

	public void setNodeid(Integer nodeid) {
		this.nodeid = nodeid;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getOtherId() {
		return otherId;
	}

	public void setOtherId(Integer otherId) {
		this.otherId = otherId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getProteamId() {
		return proteamId;
	}

	public void setProteamId(Long proteamId) {
		this.proteamId = proteamId;
	}

	public String getWorkTeam() {
		return workTeam;
	}

	public void setWorkTeam(String workTeam) {
		this.workTeam = workTeam;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getJwdcode() {
		return jwdcode;
	}

	public void setJwdcode(String jwdcode) {
		this.jwdcode = jwdcode;
	}

	@Override
	public String toString() {
		return "DatePlanPri [rjhmId=" + rjhmId + ", jcType=" + jcType + ", jcnum=" + jcnum + ", fixFreque=" + fixFreque
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rjhmId == null) ? 0 : rjhmId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatePlanPri other = (DatePlanPri) obj;
		if (rjhmId == null) {
			if (other.rjhmId != null)
				return false;
		} else if (!rjhmId.equals(other.rjhmId))
			return false;
		return true;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}

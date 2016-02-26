package com.major.base.vo;

import java.io.Serializable;

public class DictProTeam implements Serializable {

	private static final long serialVersionUID = -695991163509954145L;

	/** 主键 */
	private Long proteamid;
	/** 专业班组名 */
	private String proteamname;
	/** 班组拼音 */
	private String py;
	/** 机车类型 */
	private String jctype;
	/** 对应状态 */
	private Integer workflag;
	/** 中修判别字段 */
	private Integer zxFlag;
	/** 机务段编码 */
	private String jwdcode;

	public Long getProteamid() {
		return proteamid;
	}

	public void setProteamid(Long proteamid) {
		this.proteamid = proteamid;
	}

	public String getProteamname() {
		return proteamname;
	}

	public void setProteamname(String proteamname) {
		this.proteamname = proteamname;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getJctype() {
		return jctype;
	}

	public void setJctype(String jctype) {
		this.jctype = jctype;
	}

	public Integer getWorkflag() {
		return workflag;
	}

	public void setWorkflag(Integer workflag) {
		this.workflag = workflag;
	}

	public Integer getZxFlag() {
		return zxFlag;
	}

	public void setZxFlag(Integer zxFlag) {
		this.zxFlag = zxFlag;
	}

	public String getJwdcode() {
		return jwdcode;
	}

	public void setJwdcode(String jwdcode) {
		this.jwdcode = jwdcode;
	}

}

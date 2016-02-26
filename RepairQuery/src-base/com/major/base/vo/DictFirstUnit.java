package com.major.base.vo;

import java.io.Serializable;

public class DictFirstUnit implements Serializable {

	private static final long serialVersionUID = 1165954724451874276L;

	/** 专业ID */
	private Long firstunitid;
	/** 一级部件名 */
	private String firstunitname;
	/** 部件拼音 */
	private String py;
	/** 机车型号值 */
	private String jcstypevalue;
	/** 访问报告的URL */
	private String url;
	/** 机务段编号 */
	private String jwdcode;

	public DictFirstUnit() {
	}

	public DictFirstUnit(Long firstunitid, String firstunitname, String py, String jcstypevalue, String url,
			String jwdcode) {
		this.firstunitid = firstunitid;
		this.firstunitname = firstunitname;
		this.py = py;
		this.jcstypevalue = jcstypevalue;
		this.url = url;
		this.jwdcode = jwdcode;
	}

	public Long getFirstunitid() {
		return firstunitid;
	}

	public void setFirstunitid(Long firstunitid) {
		this.firstunitid = firstunitid;
	}

	public String getFirstunitname() {
		return firstunitname;
	}

	public void setFirstunitname(String firstunitname) {
		this.firstunitname = firstunitname;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getJcstypevalue() {
		return jcstypevalue;
	}

	public void setJcstypevalue(String jcstypevalue) {
		this.jcstypevalue = jcstypevalue;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJwdcode() {
		return jwdcode;
	}

	public void setJwdcode(String jwdcode) {
		this.jwdcode = jwdcode;
	}

	@Override
	public String toString() {
		return "DictFirstUnit [firstunitid=" + firstunitid + ", firstunitname=" + firstunitname + ", py=" + py
				+ ", jcstypevalue=" + jcstypevalue + ", url=" + url + ", jwdcode=" + jwdcode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstunitid == null) ? 0 : firstunitid.hashCode());
		result = prime * result + ((jwdcode == null) ? 0 : jwdcode.hashCode());
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
		DictFirstUnit other = (DictFirstUnit) obj;
		if (firstunitid == null) {
			if (other.firstunitid != null)
				return false;
		} else if (!firstunitid.equals(other.firstunitid))
			return false;
		if (jwdcode == null) {
			if (other.jwdcode != null)
				return false;
		} else if (!jwdcode.equals(other.jwdcode))
			return false;
		return true;
	}
}

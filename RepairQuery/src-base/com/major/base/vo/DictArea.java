package com.major.base.vo;

import java.io.Serializable;

public class DictArea implements Serializable {

	private static final long serialVersionUID = 558253231238996504L;

	/**
	 * 区域代码
	 */
	private String areaid;
	/**
	 * 区域名称
	 */
	private String name;
	/**
	 * 服务器IP
	 */
	private String ip;
	/**
	 * 数据库名
	 */
	private String dbname;
	/**
	 * 登录名
	 */
	private String loginname;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 拼音
	 */
	private String py;

	/**
	 * 机务段代码
	 */
	private String jwdcode;

	/**
	 * dblink名称
	 */
	private String dblinkname;

	/**
	 * 是否启用
	 */
	private int isused;

	/**
	 * 股道图Url
	 */
	private String gdtUrl;

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getJwdcode() {
		return jwdcode;
	}

	public void setJwdcode(String jwdcode) {
		this.jwdcode = jwdcode;
	}

	public String getDblinkname() {
		return dblinkname;
	}

	public void setDblinkname(String dblinkname) {
		this.dblinkname = dblinkname;
	}

	public int getIsused() {
		return isused;
	}

	public void setIsused(int isused) {
		this.isused = isused;
	}

	public String getGdtUrl() {
		return gdtUrl;
	}

	public void setGdtUrl(String gdtUrl) {
		this.gdtUrl = gdtUrl;
	}

	@Override
	public String toString() {
		return "DictArea [areaid=" + areaid + ", name=" + name + ", ip=" + ip + ", dbname=" + dbname + ", loginname="
				+ loginname + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaid == null) ? 0 : areaid.hashCode());
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
		DictArea other = (DictArea) obj;
		if (areaid == null) {
			if (other.areaid != null)
				return false;
		} else if (!areaid.equals(other.areaid))
			return false;
		return true;
	}

}

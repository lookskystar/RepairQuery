package com.major.base.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 */
public class UsersPrivs implements	Serializable {

	private static final long serialVersionUID = 1462494541946842527L;
	/**
	 * 主键
	 */
	private Long userid;
	/**
	 * 机务段代码（编号）
	 */
	private String jwdcode;
	/**
	 * 区域代码
	 */
	private String areaid;
	/**
	 * 是否启用（0表示禁用、1表示启用）
	 */
	private Integer isuse;
	/**
	 * 部门编号
	 */
	private Long depatid;
	/**
	 * 班组id
	 */
	private Long bzid;
	/**
	 * 职务id
	 */
	private Long zwid;
	/**
	 * 用户姓名
	 */
	private String xm;
	/**
	 * 用户登录名
	 */
	private String name;
	/**
	 * 用户工号
	 */
	private String gonghao;
	/**
	 * IDK卡号
	 */
	private String idkid;
	/**
	 * 登录密码
	 */
	private String pwd;
	/**
	 * 登录时间
	 */
	private Date logintime;
	/**
	 * 专业id
	 */
	private Long proteamid;
	/**
	 * 登记日期
	 */
	private Date uptime;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 密码提问
	 */
	private String que;
	/**
	 * 密码答案
	 */
	private String ans;
	/**
	 * 办公电话
	 */
	private String tel;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 手机号码1
	 */
	private String mobile;
	/**
	 * 手机号码2
	 */
	private String mobile2;
	/**
	 * 家庭电话
	 */
	private String homephone;
	/**
	 * 通讯地址
	 */
	private String address;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 姓名拼音
	 */
	private String py;
	/**
	 * 备注
	 */
	private String qita;
	/**
	 * 用户角色字符串--用于显示，不映射数据库
	 */
	private String roles;
	
	// Property accessors
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getJwdcode() {
		return jwdcode;
	}
	public void setJwdcode(String jwdcode) {
		this.jwdcode = jwdcode;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public Integer getIsuse() {
		return isuse;
	}
	public void setIsuse(Integer isuse) {
		this.isuse = isuse;
	}
	public Long getDepatid() {
		return depatid;
	}
	public void setDepatid(Long depatid) {
		this.depatid = depatid;
	}
	public Long getBzid() {
		return bzid;
	}
	public void setBzid(Long bzid) {
		this.bzid = bzid;
	}
	public Long getZwid() {
		return zwid;
	}
	public void setZwid(Long zwid) {
		this.zwid = zwid;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGonghao() {
		return gonghao;
	}
	public void setGonghao(String gonghao) {
		this.gonghao = gonghao;
	}
	public String getIdkid() {
		return idkid;
	}
	public void setIdkid(String idkid) {
		this.idkid = idkid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getLogintime() {
		return logintime;
	}
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
	public Long getProteamid() {
		return proteamid;
	}
	public void setProteamid(Long proteamid) {
		this.proteamid = proteamid;
	}
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getQue() {
		return que;
	}
	public void setQue(String que) {
		this.que = que;
	}
	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getHomephone() {
		return homephone;
	}
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public String getQita() {
		return qita;
	}
	public void setQita(String qita) {
		this.qita = qita;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
}
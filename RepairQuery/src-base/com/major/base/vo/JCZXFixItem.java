package com.major.base.vo;

import java.io.Serializable;

public class JCZXFixItem implements Serializable {

	private static final long serialVersionUID = 8199104856244418195L;

	// 机车分解（组装）项目ID
	private Integer id;
	// 段代码
	private String jwdCode;
	// 节点ID
	private Integer nodeId;
	// 项目名
	private String itemName;
	// 项目顺序号
	private Integer itemSn;
	// 部位名称
	private String posiName;
	// 父级项目ID
	private Integer parentId;
	// 分解时长
	private String timeNum;
	// 技术要求及标准
	private String techStandard;
	// 项目填报默认值
	private String fillDefaval;
	// 最小值
	private Float min;
	// 最大值
	private Float max;
	// 单位
	private String unit;
	// 检修项目拼音简写
	private String itemPy;
	// 项目卡控人员：0-工长不控；1-工长卡控
	private Short itemCtrlLead;
	// 项目卡控人员：0-交车工长不控；1-交车工长卡控
	private Short itemCtrlComld;
	// 项目卡控人员：0-质检员不控；1-质检员卡控
	private Short itemCtrlQi;
	// 项目卡控人员：0-技术员不控；1-技术员卡控
	private Short itemCtrlTech;
	// 项目卡控人员：0-验收员不控；1-验收员卡控
	private Short itemCtrlAcce;
	// 适用车型：填写说明：DF4,SS3,SS3B
	private String jcsType;
	// 修程修次：填写说明: F1,F2,F3,F4,X1
	private String xcxc;
	// 检次：每次、双辅等
	private String jc;
	// 班组
	private String proTeam;
	// 班组ID
	private Long bzId;
	// 预设分工大类ID
	private Integer ysId;
	// 项目关联 如分解1-A1-关联--组装1-B1
	private String itemRelation;
	// 大部件ID
	private Integer firstUnitId;
	// 部件名称
	private String unitName;
	// 项目检修时长
	private Integer duration;
	// 需填配件数量
	private Integer amount;
	// 组装配件对应的静态配件ID
	private Long stdPJId;
	// 复探卡控
	private Short itemCtrlRept;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemSn() {
		return itemSn;
	}

	public void setItemSn(Integer itemSn) {
		this.itemSn = itemSn;
	}

	public String getPosiName() {
		return posiName;
	}

	public void setPosiName(String posiName) {
		this.posiName = posiName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(String timeNum) {
		this.timeNum = timeNum;
	}

	public String getTechStandard() {
		return techStandard;
	}

	public void setTechStandard(String techStandard) {
		this.techStandard = techStandard;
	}

	public String getFillDefaval() {
		return fillDefaval;
	}

	public void setFillDefaval(String fillDefaval) {
		this.fillDefaval = fillDefaval;
	}

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getItemPy() {
		return itemPy;
	}

	public void setItemPy(String itemPy) {
		this.itemPy = itemPy;
	}

	public Short getItemCtrlLead() {
		return itemCtrlLead;
	}

	public void setItemCtrlLead(Short itemCtrlLead) {
		this.itemCtrlLead = itemCtrlLead;
	}

	public Short getItemCtrlComld() {
		return itemCtrlComld;
	}

	public void setItemCtrlComld(Short itemCtrlComld) {
		this.itemCtrlComld = itemCtrlComld;
	}

	public Short getItemCtrlQi() {
		return itemCtrlQi;
	}

	public void setItemCtrlQi(Short itemCtrlQi) {
		this.itemCtrlQi = itemCtrlQi;
	}

	public Short getItemCtrlTech() {
		return itemCtrlTech;
	}

	public void setItemCtrlTech(Short itemCtrlTech) {
		this.itemCtrlTech = itemCtrlTech;
	}

	public Short getItemCtrlAcce() {
		return itemCtrlAcce;
	}

	public void setItemCtrlAcce(Short itemCtrlAcce) {
		this.itemCtrlAcce = itemCtrlAcce;
	}

	public String getJcsType() {
		return jcsType;
	}

	public void setJcsType(String jcsType) {
		this.jcsType = jcsType;
	}

	public String getXcxc() {
		return xcxc;
	}

	public void setXcxc(String xcxc) {
		this.xcxc = xcxc;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public String getProTeam() {
		return proTeam;
	}

	public void setProTeam(String proTeam) {
		this.proTeam = proTeam;
	}

	public Integer getYsId() {
		return ysId;
	}

	public void setYsId(Integer ysId) {
		this.ysId = ysId;
	}

	public String getItemRelation() {
		return itemRelation;
	}

	public void setItemRelation(String itemRelation) {
		this.itemRelation = itemRelation;
	}

	public Integer getFirstUnitId() {
		return firstUnitId;
	}

	public void setFirstUnitId(Integer firstUnitId) {
		this.firstUnitId = firstUnitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Long getStdPJId() {
		return stdPJId;
	}

	public void setStdPJId(Long stdPJId) {
		this.stdPJId = stdPJId;
	}

	public Short getItemCtrlRept() {
		return itemCtrlRept;
	}

	public void setItemCtrlRept(Short itemCtrlRept) {
		this.itemCtrlRept = itemCtrlRept;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Long getBzId() {
		return bzId;
	}

	public void setBzId(Long bzId) {
		this.bzId = bzId;
	}

}

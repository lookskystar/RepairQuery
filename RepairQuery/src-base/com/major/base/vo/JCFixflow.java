package com.major.base.vo;

import java.io.Serializable;

/**
 * 机车检修流程表
 * 
 * @author eleven
 * 
 */
public class JCFixflow implements Serializable {

	private static final long serialVersionUID = 7829320530932886129L;

	/**
	 * 表主键：机车检修流程ID
	 */
	private int jcFlowId;
	/**
	 * 机车类型：内燃、电力
	 */
	private String jcType;
	/**
	 * 段代码
	 */
	private String jwdCode;
	/**
	 * 修程修次
	 */
	private String repairSeq;
	/**
	 * 机车型号值
	 */
	private String jcsTypeValue;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 节点顺序号
	 */
	private Integer nodeOrder;
	/**
	 * 规定检修开始时间
	 */
	private String fixBeginTime;
	/**
	 * 规定检修结束时间
	 */
	private String fixEndTime;
	/**
	 * 卡控标志：0-不卡控；1-可控可不控；2-必须卡控
	 */
	private Integer ctrlSign;
	/**
	 * 并行标志：0-可并行；1-顺序
	 */
	private Integer synchroSign;
	/**
	 * 节点卡控人员：0-工长不控；1-工长卡控
	 */
	private Integer nodeCtrlLead;
	/**
	 * 节点卡控人员：0-交车工长不控；1-交车工长卡控
	 */
	private Integer itemCtrlComLD;
	/**
	 * 节点卡控人员：0-质检员不控；1-质检员卡控
	 */
	private Integer nodeCtrlQI;
	/**
	 * 节点卡控人员：0-技术员不控；1-技术员卡控
	 */
	private Integer nodeCtrlTech;
	/**
	 * 节点卡控人员：0-验收员不控；1-验收员卡控
	 */
	private Integer nodeCtrlAcce;
	/**
	 * 提示内容
	 */
	private String promtCon;
	/**
	 * 节点拼音简写
	 */
	private String py;

	public int getJcFlowId() {
		return jcFlowId;
	}

	public void setJcFlowId(int jcFlowId) {
		this.jcFlowId = jcFlowId;
	}

	public String getJcType() {
		return jcType;
	}

	public void setJcType(String jcType) {
		this.jcType = jcType;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getRepairSeq() {
		return repairSeq;
	}

	public void setRepairSeq(String repairSeq) {
		this.repairSeq = repairSeq;
	}

	public String getJcsTypeValue() {
		return jcsTypeValue;
	}

	public void setJcsTypeValue(String jcsTypeValue) {
		this.jcsTypeValue = jcsTypeValue;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getNodeOrder() {
		return nodeOrder;
	}

	public void setNodeOrder(Integer nodeOrder) {
		this.nodeOrder = nodeOrder;
	}

	public String getFixBeginTime() {
		return fixBeginTime;
	}

	public void setFixBeginTime(String fixBeginTime) {
		this.fixBeginTime = fixBeginTime;
	}

	public String getFixEndTime() {
		return fixEndTime;
	}

	public void setFixEndTime(String fixEndTime) {
		this.fixEndTime = fixEndTime;
	}

	public Integer getCtrlSign() {
		return ctrlSign;
	}

	public void setCtrlSign(Integer ctrlSign) {
		this.ctrlSign = ctrlSign;
	}

	public Integer getSynchroSign() {
		return synchroSign;
	}

	public void setSynchroSign(Integer synchroSign) {
		this.synchroSign = synchroSign;
	}

	public Integer getNodeCtrlLead() {
		return nodeCtrlLead;
	}

	public void setNodeCtrlLead(Integer nodeCtrlLead) {
		this.nodeCtrlLead = nodeCtrlLead;
	}

	public Integer getItemCtrlComLD() {
		return itemCtrlComLD;
	}

	public void setItemCtrlComLD(Integer itemCtrlComLD) {
		this.itemCtrlComLD = itemCtrlComLD;
	}

	public Integer getNodeCtrlQI() {
		return nodeCtrlQI;
	}

	public void setNodeCtrlQI(Integer nodeCtrlQI) {
		this.nodeCtrlQI = nodeCtrlQI;
	}

	public Integer getNodeCtrlTech() {
		return nodeCtrlTech;
	}

	public void setNodeCtrlTech(Integer nodeCtrlTech) {
		this.nodeCtrlTech = nodeCtrlTech;
	}

	public Integer getNodeCtrlAcce() {
		return nodeCtrlAcce;
	}

	public void setNodeCtrlAcce(Integer nodeCtrlAcce) {
		this.nodeCtrlAcce = nodeCtrlAcce;
	}

	public String getPromtCon() {
		return promtCon;
	}

	public void setPromtCon(String promtCon) {
		this.promtCon = promtCon;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	@Override
	public String toString() {
		return "JCFixflow [jcFlowId=" + jcFlowId + ", jcType=" + jcType + ", jwdCode=" + jwdCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + jcFlowId;
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
		JCFixflow other = (JCFixflow) obj;
		if (jcFlowId != other.jcFlowId)
			return false;
		return true;
	}

}

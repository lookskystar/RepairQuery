package com.major.base.vo;

import java.io.Serializable;
import java.util.Date;

public class PJDynamicInfo implements Serializable {

	private static final long serialVersionUID = -91752572828219516L;

	/** 配件ID */
	private Long pjdid;
	/** 配件名 */
	private String pjName;
	/** 所属配件静态名 */
	private String pjSname;
	/** 配件静态信息 */
	private Long pjStaticInfo;
	/** 机务段编号 */
	private String jwdCode;
	/** 上车编号 */
	private String getOnNum;
	/** 出厂编号 */
	private String factoryNum;
	/** 配件条形码 */
	private String pjBarCode;
	/** 配件生产厂家 */
	private String factory;
	/** 出厂日期 */
	private Date outFacDate;
	/** 存储位置：0-中心库，1-班组，2-车上，3-外地，4-配件上 */
	private Integer storePosition;
	/** 配件状态：0-合格，1-待修，2-报废,3-检修中 */
	private Integer pjStatus;
	/** 配件名拼音 */
	private String py;
	/** 在检修过程中当前检修流程节点名 */
	private String fixFlowName;
	/** 配件编号 */
	private String pjNum;
	/** 配件交接记录 */
	private Long handoverRecord;
	/** 备注信息 */
	private String comments;
	/** 机车型号 */
	private String jcType;

	public PJDynamicInfo() {

	}

	public PJDynamicInfo(Long pjdid, String pjName, Long pjStaticInfo, String jwdCode, String getOnNum,
			String factoryNum, String pjBarCode, String factory, Date outFacDate, Integer storePosition,
			Integer pjStatus, String py, String fixFlowName, String pjNum, Long handoverRecord, String comments) {
		this.pjdid = pjdid;
		this.pjName = pjName;
		this.pjStaticInfo = pjStaticInfo;
		this.jwdCode = jwdCode;
		this.getOnNum = getOnNum;
		this.factoryNum = factoryNum;
		this.pjBarCode = pjBarCode;
		this.factory = factory;
		this.outFacDate = outFacDate;
		this.storePosition = storePosition;
		this.pjStatus = pjStatus;
		this.py = py;
		this.fixFlowName = fixFlowName;
		this.pjNum = pjNum;
		this.handoverRecord = handoverRecord;
		this.comments = comments;
	}

	public Long getPjdid() {
		return pjdid;
	}

	public void setPjdid(Long pjdid) {
		this.pjdid = pjdid;
	}

	public String getPjName() {
		return pjName;
	}

	public void setPjName(String pjName) {
		this.pjName = pjName;
	}

	public Long getPjStaticInfo() {
		return pjStaticInfo;
	}

	public void setPjStaticInfo(Long pjStaticInfo) {
		this.pjStaticInfo = pjStaticInfo;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getGetOnNum() {
		return getOnNum;
	}

	public void setGetOnNum(String getOnNum) {
		this.getOnNum = getOnNum;
	}

	public String getFactoryNum() {
		return factoryNum;
	}

	public void setFactoryNum(String factoryNum) {
		this.factoryNum = factoryNum;
	}

	public String getPjBarCode() {
		return pjBarCode;
	}

	public void setPjBarCode(String pjBarCode) {
		this.pjBarCode = pjBarCode;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public Date getOutFacDate() {
		return outFacDate;
	}

	public void setOutFacDate(Date outFacDate) {
		this.outFacDate = outFacDate;
	}

	public Integer getStorePosition() {
		return storePosition;
	}

	public void setStorePosition(Integer storePosition) {
		this.storePosition = storePosition;
	}

	public Integer getPjStatus() {
		return pjStatus;
	}

	public void setPjStatus(Integer pjStatus) {
		this.pjStatus = pjStatus;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getFixFlowName() {
		return fixFlowName;
	}

	public void setFixFlowName(String fixFlowName) {
		this.fixFlowName = fixFlowName;
	}

	public String getPjNum() {
		return pjNum;
	}

	public void setPjNum(String pjNum) {
		this.pjNum = pjNum;
	}

	public Long getHandoverRecord() {
		return handoverRecord;
	}

	public void setHandoverRecord(Long handoverRecord) {
		this.handoverRecord = handoverRecord;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPjSname() {
		return pjSname;
	}

	public void setPjSname(String pjSname) {
		this.pjSname = pjSname;
	}

	public String getJcType() {
		return jcType;
	}

	public void setJcType(String jcType) {
		this.jcType = jcType;
	}

}

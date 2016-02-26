package com.major.base.vo;

import java.io.Serializable;

public class JCFlowRec implements Serializable {

	private static final long serialVersionUID = 2030719032843005358L;
	/** 主键ID */
	private Long jcFlowRecId;
	/** 检修流程 */
	private Long fixflow;
	/** 班组 */
	private Long proTeam;
	/** 班组名称 */
	private String proTeamName;
	/** 完成状态 0：未完成 1：已完成 */
	private int finishStatus;
	/** 完成时间 */
	private String finishTime;
	/** 日计划 */
	private Long datePlanPri;
	/** 机务段编码 */
	private String jwdCode;

	public JCFlowRec() {
	}

	public JCFlowRec(Long jcFlowRecId, Long fixflow, Long proTeam, int finishStatus, String finishTime, Long datePlanPri) {
		this.jcFlowRecId = jcFlowRecId;
		this.fixflow = fixflow;
		this.proTeam = proTeam;
		this.finishStatus = finishStatus;
		this.finishTime = finishTime;
		this.datePlanPri = datePlanPri;
	}

	public Long getJcFlowRecId() {
		return jcFlowRecId;
	}

	public void setJcFlowRecId(Long jcFlowRecId) {
		this.jcFlowRecId = jcFlowRecId;
	}

	public Long getFixflow() {
		return fixflow;
	}

	public void setFixflow(Long fixflow) {
		this.fixflow = fixflow;
	}

	public Long getProTeam() {
		return proTeam;
	}

	public void setProTeam(Long proTeam) {
		this.proTeam = proTeam;
	}

	public int getFinishStatus() {
		return finishStatus;
	}

	public void setFinishStatus(int finishStatus) {
		this.finishStatus = finishStatus;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public Long getDatePlanPri() {
		return datePlanPri;
	}

	public void setDatePlanPri(Long datePlanPri) {
		this.datePlanPri = datePlanPri;
	}

	public String getJwdCode() {
		return jwdCode;
	}

	public void setJwdCode(String jwdCode) {
		this.jwdCode = jwdCode;
	}

	public String getProTeamName() {
		return proTeamName;
	}

	public void setProTeamName(String proTeamName) {
		this.proTeamName = proTeamName;
	}

	@Override
	public String toString() {
		return "JCFlowRec [jcFlowRecId=" + jcFlowRecId + ", fixflow=" + fixflow + ", proTeam=" + proTeam
				+ ", finishStatus=" + finishStatus + ", finishTime=" + finishTime + ", datePlanPri=" + datePlanPri
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jcFlowRecId == null) ? 0 : jcFlowRecId.hashCode());
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
		JCFlowRec other = (JCFlowRec) obj;
		if (jcFlowRecId == null) {
			if (other.jcFlowRecId != null)
				return false;
		} else if (!jcFlowRecId.equals(other.jcFlowRecId))
			return false;
		return true;
	}
}

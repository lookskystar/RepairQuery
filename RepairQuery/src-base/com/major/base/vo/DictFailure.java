package com.major.base.vo;

import java.io.Serializable;

public class DictFailure implements Serializable {

	private static final long serialVersionUID = 8164390968769344171L;
	/** ID */
	private Long id;
	/** 专业名称 */
	private String firstUnitname;
	/** 二级部件名称 */
	private String secUnitname;
	/** 三级部件名称 */
	private String thirdUnitname;
	/** 内容 */
	private String content;
	/** 检修内容 */
	private String fixContent;
	/** 分值 */
	private Integer score;

	public DictFailure() {
		// TODO Auto-generated constructor stub
	}

	public DictFailure(Long id, String firstUnitname, String secUnitname, String thirdUnitname, String content,
			String fixContent, Integer score) {
		this.id = id;
		this.firstUnitname = firstUnitname;
		this.secUnitname = secUnitname;
		this.thirdUnitname = thirdUnitname;
		this.content = content;
		this.fixContent = fixContent;
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstUnitname() {
		return firstUnitname;
	}

	public void setFirstUnitname(String firstUnitname) {
		this.firstUnitname = firstUnitname;
	}

	public String getSecUnitname() {
		return secUnitname;
	}

	public void setSecUnitname(String secUnitname) {
		this.secUnitname = secUnitname;
	}

	public String getThirdUnitname() {
		return thirdUnitname;
	}

	public void setThirdUnitname(String thirdUnitname) {
		this.thirdUnitname = thirdUnitname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFixContent() {
		return fixContent;
	}

	public void setFixContent(String fixContent) {
		this.fixContent = fixContent;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "DictFailure [id=" + id + ", firstUnitname=" + firstUnitname + ", secUnitname=" + secUnitname
				+ ", thirdUnitname=" + thirdUnitname + ", content=" + content + ", fixContent=" + fixContent
				+ ", score=" + score + "]";
	}

}

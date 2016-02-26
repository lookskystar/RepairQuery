package com.major.base.vo;

import java.io.Serializable;

public class JcType implements Serializable {

	private static final long serialVersionUID = 5151357523508138254L;

	/** 主键ID */
	private Long id;
	/** 机车类型 */
	private String type;
	/** 修程类型 */
	private String jcType;

	public JcType() {

	}

	public JcType(Long id, String type, String jcType) {
		this.id = id;
		this.type = type;
		this.jcType = jcType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJcType() {
		return jcType;
	}

	public void setJcType(String jcType) {
		this.jcType = jcType;
	}

	@Override
	public String toString() {
		return "JcType [id=" + id + ", type=" + type + ", jcType=" + jcType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		JcType other = (JcType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

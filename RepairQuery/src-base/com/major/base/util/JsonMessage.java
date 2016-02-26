/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-4
 * @ClassName: JsonMessage.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午9:26:36eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-4 eleven v1.0.0 新建
 */
package com.major.base.util;

import java.io.OutputStream;
import java.util.List;

/**
 * @Title: JsonMessage.java
 * @Description: TODO(Json信息工具类)
 * @author eleven
 * @date 2014-3-4 下午9:26:36
 * 
 */
public class JsonMessage {
	private boolean success = false;

	private String message = "";

	private Object object = null;

	private List<?> objectList = null;

	private OutputStream os = null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<?> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<?> objectList) {
		this.objectList = objectList;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}
}

/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-4
 * @ClassName: CurdMessage.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午9:27:40eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-4 eleven v1.0.0 新建
 */
package com.major.base.util;

/**
 * @Title: CurdMessage.java
 * @Description: TODO(CURD操作提示枚举类)
 * @author eleven
 * @date 2014-3-4 下午9:27:40
 *
 */
public enum CurdMessage {
	AddSuccess("添加成功!"), 
	AddFailure("添加失败!"), 
	DeleteSuccess("删除成功!"), 
	DeleteFailure("删除失败!"), 
	EditSuccess("编辑成功！"), 
	EditFailure("编辑失败！"), 
	SaveSucess("保存成功!"), 
	SaveFailure("保存失敗"),
	LoginSuccess("登录成功！"),
	LoginFailure("登录失败!");
	private String message;

	CurdMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

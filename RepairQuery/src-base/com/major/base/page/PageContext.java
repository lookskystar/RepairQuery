/**
 *
 * Copyright: Copyright (c) 2014 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-11
 * @ClassName: PagenationContext.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0上午10:47:39eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-11 eleven v1.0.0 新建
 */
package com.major.base.page;

/**
 * @Title: PagenationContext.java
 * @Description: TODO(自动管理分页参数:offSet、pageSize)
 * @author eleven
 * @date 2014-3-11 上午10:47:39
 * 
 */
public class PageContext {

	/** 分页RowIndex */
	private static ThreadLocal<Integer> offSet = new ThreadLocal<Integer>();
	/** 分页PageSize */
	private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();

	public static Integer getOffSet() {
		Integer os = offSet.get();
		// 默认值0
		if (os == null) {
			return 0;
		}
		return os;
	}

	public static void setOffSet(Integer os) {
		offSet.set(os);
	}

	public static void removeOffSet() {
		offSet.remove();
	}

	public static Integer getPageSize() {
		Integer ps = pageSize.get();
		// 默认值10
		if (ps == null) {
			return 10;
		}
		return ps;
	}

	public static void setPageSize(Integer ps) {
		pageSize.set(ps);
	}

	public static void removePageSize() {
		pageSize.remove();
	}

}

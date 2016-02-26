/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-3
 * @ClassName: NameUtil.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午04:35:00eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.util;

/**
 * @Title: NameUtil.java
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author eleven
 * @date 2014-3-3 下午04:35:00
 *
 */
public final class NameUtil {
	
	/** 私有构造函数 ：请不要实例化工具类*/
	private NameUtil() {
		throw new Error("Please Dont't Instance UtilClass!");
	}

	/**
	 * @Title: getUnderlineName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param entityName
	 * @return
	 * @return: String
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	public static String getUnderlineName(String entityName) {
		return entityName.toLowerCase();
	}

}

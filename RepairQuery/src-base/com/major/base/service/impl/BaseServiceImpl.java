/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.service.impl@date:2014-3-3
 * @ClassName: BaseServiceImpl.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午02:30:56eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.service.impl;


import java.text.SimpleDateFormat;

import com.major.base.service.BaseServiceI;

/**
 * @Title: BaseServiceImpl.java
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author eleven
 * @date 2014-3-3 下午02:30:56
 *
 */
public class BaseServiceImpl<T> implements BaseServiceI<T>{
	
	public static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");
}

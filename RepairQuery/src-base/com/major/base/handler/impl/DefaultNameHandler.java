/**
 *
 * Copyright: Copyright (c) 2014 Asiainfo-Linkage
 *
 * com.major.base.service.impl@date:2014-3-3
 * @ClassName: DefaultNameHandler.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午03:15:31eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.handler.impl;

import com.major.base.handler.NameHandler;
import com.major.base.util.NameUtil;

/**
 * @Title: DefaultNameHandler.java
 * @Description: TODO(ORACLE名称处理接口实现类)
 * @author eleven
 * @date 2014-3-3 下午03:15:31
 *
 */
public class DefaultNameHandler implements NameHandler {
	
	/** 单例模式 */
	private DefaultNameHandler(){
	}
	
	private static class SingletonHolder {
		private static DefaultNameHandler defaultNameHandler = new DefaultNameHandler();
	}
	
	public static DefaultNameHandler getSingltonHandler(){
		return SingletonHolder.defaultNameHandler;
	}
	
	/** 列名前缀 */
    private static final String PREFIX = "_";

    /** 主键后缀 */
    private static final String PRI_SUFFIX = "_id";

	/* (非 Javadoc)
	 * <p>Title: getColumnName</p>
	 * <p>Description: </p>
	 * @param fieldName
	 * @return
	 * @see com.major.base.service.NameHandler#getColumnName(java.lang.String)
	 */
	public String getColumnName(String fieldName) {
		String underlineName = NameUtil.getUnderlineName(fieldName);
        //数据库列名:tableName_ColumnName
        return PREFIX + underlineName;
	}

	/* (非 Javadoc)
	 * <p>Title: getPrimaryName</p>
	 * <p>Description: </p>
	 * @param entityName
	 * @return
	 * @see com.major.base.service.NameHandler#getPrimaryName(java.lang.String)
	 */
	public String getPrimaryName(String entityName) {
		String underlineName = NameUtil.getUnderlineName(entityName);
        //主键以表名加上“_id” 如user表主键即“user_id”
        return underlineName + PRI_SUFFIX;
	}

	/* (非 Javadoc)
	 * <p>Title: getTableName</p>
	 * <p>Description: </p>
	 * @param entityName
	 * @return
	 * @see com.major.base.service.NameHandler#getTableName(java.lang.String)
	 */
	public String getTableName(String entityName) {
		//Java属性的骆驼命名法转换回数据库下划线“_”分隔的格式
        return NameUtil.getUnderlineName(entityName);
	}

}

/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.service@date:2014-3-3
 * @ClassName: NameHandler.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午03:13:55eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.handler;

/**
 * @Title: NameHandler.java
 * @Description: TODO(名称处理接口)
 * @author eleven
 * @date 2014-3-3 下午03:13:55
 *
 */
public interface NameHandler {
	
	/**
	 * 
	 * @Title: getTableName
	 * @Description: TODO(根据实体名获取表名)
	 * @param entityName
	 * @return
	 * @return: String
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
    public String getTableName(String entityName);

    /**
     * 
     * @Title: getPrimaryName
     * @Description: TODO(根据表名获取主键名)
     * @param entityName
     * @return
     * @return: String
     * @author: eleven
     * @date: 2014-3-3
     * @history: Date  Author  Version  Description
     * 			 ---------------------------------------------------------*
     * 			 2014-3-3  eleven v1.0.0   修改原因
     */
    public String getPrimaryName(String entityName);

    /**
     * 
     * @Title: getColumnName
     * @Description: TODO(根据属性名获取列名)
     * @param fieldName
     * @return
     * @return: String
     * @author: eleven
     * @date: 2014-3-3
     * @history: Date  Author  Version  Description
     * 			 ---------------------------------------------------------*
     * 			 2014-3-3  eleven v1.0.0   修改原因
     */
    public String getColumnName(String fieldName);
}

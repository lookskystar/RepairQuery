/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-3
 * @ClassName: SqlContext.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午03:22:41eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.sql;

import java.util.List;

/**
 * @Title: SqlContext.java
 * @Description: TODO(执行SQL的上下文内容)
 * @author eleven
 * @date 2014-3-3 下午03:22:41
 *
 */
public class SqlContext {
	
	/** 执行的sql */
    private StringBuilder sql;

    /** 主键名称 */
    private String primaryKey;

    /** 参数，对应sql中的?号 */
    private List<Object>  params;

    public SqlContext(StringBuilder sql, String primaryKey, List<Object> params) {
        this.sql = sql;
        this.primaryKey = primaryKey;
        this.params = params;
    }

	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
    
}

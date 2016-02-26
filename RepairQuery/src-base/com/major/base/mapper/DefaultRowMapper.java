/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-3
 * @ClassName: DefaultRowMapper.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午04:30:05eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.mapper;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.major.base.handler.NameHandler;
import com.major.base.util.ClassUtil;

/**
 * @Title: DefaultRowMapper.java
 * @Description: TODO(默认通用类型映射转换)
 * @author eleven
 * @date 2014-3-3 下午04:30:05
 *
 */
public class DefaultRowMapper implements RowMapper<Object>{
	
	/** 转换的目标对象 */
    private Class<?> clazz;

    /** 名称处理器 */
    private NameHandler nameHandler;

    public DefaultRowMapper(Class<?> clazz, NameHandler nameHandler) {
        this.clazz = clazz;
        this.nameHandler = nameHandler;
    }

	/* (非 Javadoc)
	* <p>Title: mapRow</p>
	* <p>Description: </p>
	* @param rs
	* @param rowNum
	* @return
	* @throws SQLException
	* @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	*/
	public Object mapRow(ResultSet resultSet, int i) throws SQLException {
		Object entity = ClassUtil.newInstance(this.clazz);
		BeanInfo beanInfo = ClassUtil.getSelfBeanInfo(this.clazz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			String column = nameHandler.getTableName(nameHandler.getColumnName(pd.getName()));
			Method writeMethod = pd.getWriteMethod();
			if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
				writeMethod.setAccessible(true);
			}
			try {
				writeMethod.invoke(entity, resultSet.getObject(column));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
  
}

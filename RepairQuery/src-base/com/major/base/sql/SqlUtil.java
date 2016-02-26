/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-3
 * @ClassName: SqlUtil.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午03:20:01eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.sql;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.major.base.handler.NameHandler;
import com.major.base.util.ClassUtil;

/**
 * @Title: SqlUtil.java
 * @Description: TODO(SQL辅助类)
 * @author eleven
 * @date 2014-3-3 下午03:20:01
 *
 */
public final class SqlUtil {
	
	/** 私有构造函数 ：请不要实例化工具类*/
	private SqlUtil() {
		throw new Error("Please Dont't Instance UtilClass!");
	}
	
	/**
	 * 
	 * @Title: buildInsertSql
	 * @Description: TODO(构建插入SQL)
	 * @param entity
	 * @param nameHandler
	 * @return
	 * @return: SqlContext
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
    public static SqlContext buildInsertSql(Object entity, NameHandler nameHandler) {
        Class<?> clazz = entity.getClass();
        //获取表名
        String tableName = nameHandler.getTableName(clazz.getSimpleName());
        //获取主键名
        String primaryName = nameHandler.getPrimaryName(clazz.getSimpleName());
        //组装SQL语句
        StringBuilder sql = new StringBuilder("insert into ");
        List<Object> params = new ArrayList<Object>();
        sql.append(tableName);
        //获取属性信息
        BeanInfo beanInfo = ClassUtil.getSelfBeanInfo(clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        sql.append("(");
        StringBuilder args = new StringBuilder();
        args.append("(");
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null ||  !StringUtils.isNotEmpty(value.toString())) {
                continue;
            }
            String columnName = nameHandler.getColumnName(pd.getName());
            sql.append(tableName + columnName);
            args.append("?");
            params.add(value);
            sql.append(",");
            args.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        args.deleteCharAt(args.length() - 1);
        args.append(")");
        sql.append(")");
        sql.append(" values ");
        sql.append(args);
        return new SqlContext(sql, primaryName, params);
    }

   /**
    * 
    * @Title: buildUpdateSql
    * @Description: TODO(构建更新SQL)
    * @param entity
    * @param nameHandler
    * @return
    * @return: SqlContext
    * @author: eleven
    * @date: 2014-3-3
    * @history: Date  Author  Version  Description
    * 			 ---------------------------------------------------------*
    * 			 2014-3-3  eleven v1.0.0   修改原因
    */
    public static SqlContext buildUpdateSql(Object entity, NameHandler nameHandler) {
        Class<?> clazz = entity.getClass();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        //获取表名
        String tableName = nameHandler.getTableName(clazz.getSimpleName());
        //获取主键名
        String primaryName = nameHandler.getPrimaryName(clazz.getSimpleName());
        //获取属性信息
        BeanInfo beanInfo = ClassUtil.getSelfBeanInfo(clazz);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        sql.append("update ");
        sql.append(tableName);
        sql.append(" set ");
        Object primaryValue = null;
        for (PropertyDescriptor pd : pds) {
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null ||  !StringUtils.isNotEmpty(value.toString())) {
                continue;
            }
            String columnName = nameHandler.getColumnName(pd.getName());
            if (primaryName.equalsIgnoreCase(tableName + columnName)) {
                primaryValue = value;
            }
            sql.append(tableName + columnName);
            sql.append(" = ");
            sql.append("?");
            params.add(value);
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" where ");
        sql.append(primaryName);
        sql.append(" = ?");
        params.add(primaryValue);
        return new SqlContext(sql, primaryName, params);
    }

   /**
    * 
    * @Title: buildQueryCondition
    * @Description: TODO(构建查询条件)
    * @param entity
    * @param nameHandler
    * @return
    * @return: SqlContext
    * @author: eleven
    * @date: 2014-3-3
    * @history: Date  Author  Version  Description
    * 			 ---------------------------------------------------------*
    * 			 2014-3-3  eleven v1.0.0   修改原因
    */
    public static SqlContext buildQueryCondition(Object entity, NameHandler nameHandler) {
    	Class<?> clazz = entity.getClass();
        //获取属性信息
        BeanInfo beanInfo = ClassUtil.getSelfBeanInfo(entity.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        //获取表名
        String tableName = nameHandler.getTableName(clazz.getSimpleName());
        StringBuilder condition = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        int count = 0;
        for (PropertyDescriptor pd : pds) {
        	String columnName = nameHandler.getColumnName(pd.getName());
            Object value = getReadMethodValue(pd.getReadMethod(), entity);
            if (value == null ||  !StringUtils.isNotEmpty(value.toString())) {
                continue;
            }
            if (count > 0) {
                condition.append(" and ");
            }
        	condition.append(tableName + columnName);
            condition.append(" = ?");
            params.add(value);
            count++;
        }
        return new SqlContext(condition, null, params);
    }

   /**
    * 
    * @Title: getReadMethodValue
    * @Description: TODO(获取属性值)
    * @param readMethod
    * @param entity
    * @return
    * @return: Object
    * @author: eleven
    * @date: 2014-3-3
    * @history: Date  Author  Version  Description
    * 			 ---------------------------------------------------------*
    * 			 2014-3-3  eleven v1.0.0   修改原因
    */
    private static Object getReadMethodValue(Method readMethod, Object entity) {
    	Object instance = null;
        if (readMethod == null) {
            return null;
        }
        try {
            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                readMethod.setAccessible(true);
            }
            instance = readMethod.invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

}

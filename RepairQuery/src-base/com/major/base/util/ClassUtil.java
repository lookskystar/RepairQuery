/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.util@date:2014-3-3
 * @ClassName: ClassUtil.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午03:21:40eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @Title: ClassUtil.java
 * @Description: TODO(类属性辅助类)
 * @author eleven
 * @date 2014-3-3 下午03:21:40
 *
 */
public final class ClassUtil {
	
	/** 私有构造函数 ：请不要实例化工具类*/
	private ClassUtil() {
		throw new Error("Please Dont't Instance UtilClass!");
	}
	
	private static final Map<Class<?>, BeanInfo> classCache = Collections.synchronizedMap(new WeakHashMap<Class<?>, BeanInfo>());

   /**
    * 
    * @Title: getSelfBeanInfo
    * @Description: TODO(获取类本身的BeanInfo，不包含父类属性)
    * @param clazz
    * @return
    * @return: BeanInfo
    * @author: eleven
    * @date: 2014-3-3
    * @history: Date  Author  Version  Description
    * 			 ---------------------------------------------------------*
    * 			 2014-3-3  eleven v1.0.0   修改原因
    */
	public static BeanInfo getSelfBeanInfo(Class<?> clazz) {
    	BeanInfo beanInfo = null;
        try {
            if (classCache.get(clazz) == null) {
                beanInfo = Introspector.getBeanInfo(clazz, clazz.getSuperclass());
                classCache.put(clazz, beanInfo);
                Class<?> classToFlush = clazz;
                do {
                    Introspector.flushFromCaches(classToFlush);
                    classToFlush = classToFlush.getSuperclass();
                } while (classToFlush != null);
            } else {
                beanInfo = classCache.get(clazz);
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
		return beanInfo;
    }

    /**
     * 
     * @Title: newInstance
     * @Description: TODO(初始化实例)
     * @param clazz
     * @return
     * @return: Object
     * @author: eleven
     * @date: 2014-3-3
     * @history: Date  Author  Version  Description
     * 			 ---------------------------------------------------------*
     * 			 2014-3-3  eleven v1.0.0   修改原因
     */
    public static Object newInstance(Class<?> clazz) {
    	Object instance = null;
        try {
        	instance = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return instance;
    }

}

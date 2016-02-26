/**
 *
 * Copyright: Copyright (c) 2014 Asiainfo-Linkage
 *
 * com.major.base.dao@date:2014-3-3
 * @ClassName: BaseDaoI.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午02:22:10eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import com.major.base.page.PageModel;


/**
 * @Title: BaseDaoI.java
 * @Description: TODO(基础Dao类)
 * @author eleven
 * @date 2014-3-3 下午02:22:10
 *
 */
public interface BaseDaoI<T> {

	/**
	 * @Title: insert
	 * @Description: TODO(添加记录)
	 * @param entity
	 * @return
	 * @return: Long
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	void save(T entity);
	
	/**
	 * @Title: delete
	 * @Description: TODO(删除记录)
	 * @param id
	 * @return: void
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	void delete(Serializable id);
	
	/**
	 * 
	 * @Title: deleteOfBatch
	 * @Description: TODO(批量删除记录)
	 * @param ids
	 * @return: void
	 * @author: eleven
	 * @date: 2014-3-5
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-5  eleven v1.0.0   修改原因
	 */
	int[] deleteOfBatch(final String ids[]);
	
	/**
	 * @Title: deleteAll
	 * @Description: TODO(清空表记录)
	 * @return: void
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	void deleteAll();

	/**
	 * @Title: update
	 * @Description: TODO(更新记录)
	 * @param entity
	 * @return: void
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	void update(T entity);

	/**
	 * @Title: getById
	 * @Description: TODO(查询记录)
	 * @param id
	 * @return
	 * @return: T
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	T getById(Serializable id);

	/**
	 * @Title: findAll
	 * @Description: TODO(查询所有表记录)
	 * @return
	 * @return: List<T>
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	List<T> queryList();
	
	/**
	 * 
	 * @Title: queryPageList
	 * @Description: TODO(分页查询记录)
	 * @param entity
	 * @param orderColum
	 * @return
	 * @return: PageModel
	 * @author: eleven
	 * @date: 2014-3-6
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-6  eleven v1.0.0   修改原因
	 */
	PageModel queryPageList(T entity, LinkedHashMap<String, String> orderBy);
	

	/**
	 * @Title: queryCount
	 * @Description: TODO(查询记录数)
	 * @param entity
	 * @return
	 * @return: int
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date  Author  Version  Description
	 * 			 ---------------------------------------------------------*
	 * 			 2014-3-3  eleven v1.0.0   修改原因
	 */
	int queryCount(T entity);
	
}

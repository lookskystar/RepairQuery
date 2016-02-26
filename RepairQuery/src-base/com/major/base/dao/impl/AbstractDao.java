package com.major.base.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.major.base.page.PageContext;
import com.major.base.page.PageModel;

/**
 * 
 * @Title: AbstractDao.java
 * @Description: TODO(分页处理的业务类)
 * @author eleven
 * @date 2014-3-3 上午11:45:47
 * 
 */
public class AbstractDao extends JdbcDaoSupport {

	/**
	 * 
	 * @Title: findPageModel
	 * @Description: TODO(联表分页查询时SQL语句的参数为一个数组)
	 * @param sql
	 * @param args
	 * @return
	 * @return: PageModel
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date Author Version Description
	 *           ---------------------------------------------------------*
	 *           2014-3-3 eleven v1.0.0 修改原因
	 */
	public PageModel findPageModel(String sql, Object... args) {
		PageModel pm = new PageModel();
		int count = getJdbcTemplate().queryForInt(getCountSql(sql), args);
		pm.setCount(count);
		sql = getPageSQL(sql, PageContext.getOffSet(), PageContext.getPageSize());
		List<?> datas = getJdbcTemplate().queryForList(sql, args);
		pm.setDatas(datas);
		return pm;
	}

	/**
	 * 
	 * @Title: getCountSql
	 * @Description: TODO(根据SQL语句得出所有的记录条数)
	 * @param sql
	 * @return
	 * @return: String
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date Author Version Description
	 *           ---------------------------------------------------------*
	 *           2014-3-3 eleven v1.0.0 修改原因
	 */
	protected String getCountSql(String sql) {
		return "select count(*) " + sql.substring(sql.indexOf("from"));
	}

	/**
	 * 
	 * @Title: getPageSQL
	 * @Description: TODO(构造数据分页SQL)
	 * @param queryString
	 * @param startIndex
	 * @param pageSize
	 * @return
	 * @return: String
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date Author Version Description
	 *           ---------------------------------------------------------*
	 *           2014-3-3 eleven v1.0.0 修改原因
	 */
	protected String getPageSQL(String queryString, Integer startIndex, Integer pageSize) {
		String result = "";
		if (null != startIndex && null != pageSize) {
			result = queryString + " limit " + startIndex + "," + pageSize;
		} else if (null != startIndex && null == pageSize) {
			result = queryString + " limit " + startIndex;
		} else {
			result = queryString;
		}
		return result;
	}
}

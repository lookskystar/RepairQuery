/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.dao.impl@date:2014-3-3
 * @ClassName: BaseDaoImpl.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午02:20:30eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.util.CollectionUtils;

import com.major.base.dao.BaseDaoI;
import com.major.base.handler.NameHandler;
import com.major.base.handler.impl.DefaultNameHandler;
import com.major.base.mapper.DefaultRowMapper;
import com.major.base.page.PageContext;
import com.major.base.page.PageModel;
import com.major.base.sql.SqlContext;
import com.major.base.sql.SqlUtil;

/**
 * @Title: BaseDaoImpl.java
 * @Description: TODO(泛型通用dao实现)
 * @author eleven
 * @param <T>
 * @date 2014-3-3 下午02:20:30
 * 
 */
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	/** 实体类对象 */
	private Class<T> entityClass;

	/** 运行期获取 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		Type superclass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superclass;
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
	}

	/** 名称加工处理器 */
	private NameHandler nameHandler = DefaultNameHandler.getSingltonHandler();

	/** spring jdbcTemplate 对象 */
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);

	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/*
	 * (非 Javadoc) <p>Title: insert</p> <p>Description: </p>
	 * 
	 * @param entity
	 * 
	 * @return
	 * 
	 * @see com.major.base.dao.BaseDaoI#insert(java.lang.Object)
	 */
	public void save(T entity) {
		final SqlContext sqlContext = SqlUtil.buildInsertSql(entity, this.nameHandler);
		// 主键自增数据库实现
		/*
		 * KeyHolder keyHolder = new GeneratedKeyHolder();
		 * jdbcTemplate.update(new PreparedStatementCreator() { public
		 * PreparedStatement createPreparedStatement(Connection con) throws
		 * SQLException { PreparedStatement ps =
		 * con.prepareStatement(sqlContext.getSql().toString(), new String[] {
		 * sqlContext.getPrimaryKey() }); int index = 0; for (Object param :
		 * sqlContext.getParams()) { index++; ps.setObject(index, param); }
		 * return ps; } }, keyHolder);
		 */
		// return keyHolder.getKey().longValue();
		// 非主键自增数据库实现
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sqlContext.getSql().toString());
				int index = 0;
				for (Object param : sqlContext.getParams()) {
					index++;
					ps.setObject(index, param);
				}
				return ps;
			}
		});

	}

	/*
	 * (非 Javadoc) <p>Title: delete</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @see com.major.base.dao.BaseDaoI#delete(java.io.Serializable)
	 */
	public void delete(Serializable id) {
		String tableName = this.nameHandler.getTableName(entityClass.getSimpleName());
		String primaryName = this.nameHandler.getPrimaryName(entityClass.getSimpleName());
		String sql = "delete from " + tableName + " where " + primaryName + " = ?";
		jdbcTemplate.update(sql, id);
	}

	/*
	 * (非 Javadoc) <p>Title: deleteOfBatch</p> <p>Description: </p>
	 * 
	 * @param ids
	 * 
	 * @return
	 * 
	 * @see com.major.base.dao.BaseDaoI#deleteOfBatch(java.lang.String[])
	 */
	public int[] deleteOfBatch(final String[] ids) {
		String tableName = this.nameHandler.getTableName(entityClass.getSimpleName());
		String primaryName = this.nameHandler.getPrimaryName(entityClass.getSimpleName());
		String sql = "delete from " + tableName + " where " + primaryName + " = ?";
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ids[i]);
			}

			public int getBatchSize() {
				return ids.length;
			}
		};
		return jdbcTemplate.batchUpdate(sql, setter);
	}

	/*
	 * (非 Javadoc) <p>Title: deleteAll</p> <p>Description: </p>
	 * 
	 * @see com.major.base.dao.BaseDaoI#deleteAll()
	 */
	public void deleteAll() {
		String tableName = this.nameHandler.getTableName(entityClass.getSimpleName());
		String sql = " truncate table " + tableName;
		jdbcTemplate.execute(sql);
	}

	/*
	 * (非 Javadoc) <p>Title: update</p> <p>Description: </p>
	 * 
	 * @param entity
	 * 
	 * @see com.major.base.dao.BaseDaoI#update(java.lang.Object)
	 */
	public void update(T entity) {
		SqlContext sqlContext = SqlUtil.buildUpdateSql(entity, this.nameHandler);
		jdbcTemplate.update(sqlContext.getSql().toString(), sqlContext.getParams().toArray());
	}

	/*
	 * (非 Javadoc) <p>Title: getById</p> <p>Description: </p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see com.major.base.dao.BaseDaoI#getById(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	public T getById(Serializable id) {
		String tableName = this.nameHandler.getTableName(entityClass.getSimpleName());
		String primaryName = this.nameHandler.getPrimaryName(entityClass.getSimpleName());
		String sql = "select * from " + tableName + " where " + primaryName + " = ?";
		return (T) ((List<Object>) jdbcTemplate.query(sql, new DefaultRowMapper(entityClass, this.nameHandler), id))
				.get(0);
	}

	/*
	 * (非 Javadoc) <p>Title: findAll</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.major.base.dao.BaseDaoI#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryList() {
		String sql = "select * from " + this.nameHandler.getTableName(entityClass.getSimpleName());
		return (List<T>) jdbcTemplate.query(sql, new DefaultRowMapper(entityClass, this.nameHandler));
	}

	/*
	 * (非 Javadoc) <p>Title: queryCount</p> <p>Description: </p>
	 * 
	 * @param entity
	 * 
	 * @return
	 * 
	 * @see com.major.base.dao.BaseDaoI#queryCount(java.lang.Object)
	 */
	public int queryCount(T entity) {
		String tableName = this.nameHandler.getTableName(entityClass.getSimpleName());
		StringBuilder countSql = new StringBuilder("select count(*) from ");
		countSql.append(tableName);
		SqlContext sqlContext = SqlUtil.buildQueryCondition(entity, this.nameHandler);
		if (sqlContext.getSql().length() > 0) {
			countSql.append(" where ");
			countSql.append(sqlContext.getSql());
		}
		return jdbcTemplate.queryForInt(countSql.toString(), sqlContext.getParams().toArray());
	}

	/*
	 * (非 Javadoc) <p>Title: queryPageList</p> <p>Description: </p>
	 * 
	 * @param entity
	 * 
	 * @param orderColum
	 * 
	 * @return
	 * 
	 * @see com.major.base.dao.BaseDaoI#queryPageList(java.lang.Object,
	 * java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	public PageModel queryPageList(T entity, LinkedHashMap<String, String> orderBy) {
		PageModel pageModel = new PageModel();
		String tableName = this.nameHandler.getTableName(entityClass.getSimpleName());
		StringBuilder querySql = new StringBuilder("select * from (select t.*, rownum rn from (select * from ");
		StringBuilder countSql = new StringBuilder("select count(*) from ");
		querySql.append(tableName);
		countSql.append(tableName);
		SqlContext sqlContext = SqlUtil.buildQueryCondition(entity, this.nameHandler);
		if (sqlContext.getSql().length() > 0) {
			querySql.append("  where ");
			countSql.append("  where ");
			querySql.append(sqlContext.getSql());
			countSql.append(sqlContext.getSql());
		}
		if (!CollectionUtils.isEmpty(orderBy)) {
			querySql.append(" order by ");
			for (Iterator<String> iterator = orderBy.keySet().iterator(); iterator.hasNext();) {
				String filedName = (String) iterator.next();
				String underlineColumnName = this.nameHandler.getColumnName(filedName);
				String columnName = tableName + underlineColumnName;
				querySql.append(columnName);
				querySql.append(" " + orderBy.get(filedName) + ",");
				querySql.deleteCharAt(querySql.length() - 1);
			}
		}
		querySql.append(" ) t where rownum <= ?)");
		querySql.append(" where rn > ?");
		List<Object> queryParams = new ArrayList<Object>(sqlContext.getParams());
		queryParams.add(PageContext.getOffSet() + PageContext.getPageSize());
		queryParams.add(PageContext.getOffSet());
		List<T> list = (List<T>) jdbcTemplate.query(querySql.toString(), queryParams.toArray(), new DefaultRowMapper(
				entityClass, this.nameHandler));
		int totalCount = jdbcTemplate.queryForInt(countSql.toString(), sqlContext.getParams().toArray());
		pageModel.setDatas(list);
		pageModel.setCount(totalCount);
		pageModel.setOffset(PageContext.getOffSet());
		pageModel.setPageSize(PageContext.getPageSize());
		return pageModel;
	}

}
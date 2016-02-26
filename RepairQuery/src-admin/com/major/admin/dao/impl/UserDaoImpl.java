package com.major.admin.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.major.admin.dao.UserDaoI;
import com.major.base.vo.UsersPrivs;

public class UserDaoImpl implements UserDaoI {
	
	/** spring jdbcTemplate 对象 */
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public UsersPrivs login(String userName, String password) {
		String sql = "select * from USERS_PRIVS t where (t.gonghao = '"+ userName +"' or t.name = '"+ userName +"' or t.xm = '" + userName +"') and t.pwd = '"+ password +"' and islogin = 1";
		final UsersPrivs usersPrivs = new UsersPrivs();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				usersPrivs.setUserid(rs.getLong("USERID"));
				usersPrivs.setPwd(rs.getString("PWD"));
				usersPrivs.setXm(rs.getString("XM"));
			}
		});
		return usersPrivs;
	}

	@Override
	public void updateUser(UsersPrivs user) {
		jdbcTemplate.execute("update USERS_PRIVS u set u.pwd = "+ user.getPwd() +" where u.userid = "+ user.getUserid()+"");
	}

}

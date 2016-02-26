package com.major.admin.service.impl;

import javax.annotation.Resource;

import com.major.admin.dao.UserDaoI;
import com.major.admin.service.UserServiceI;
import com.major.base.vo.UsersPrivs;

public class UserServiceImpl implements UserServiceI {

	private UserDaoI userDao;

	@Resource
	public void setUserDao(UserDaoI userDao) {
		this.userDao = userDao;
	}

	@Override
	public UsersPrivs login(String userName, String password) {
		return userDao.login(userName, password);
	}

	@Override
	public void update(UsersPrivs user) {
		userDao.updateUser(user);
	}

}

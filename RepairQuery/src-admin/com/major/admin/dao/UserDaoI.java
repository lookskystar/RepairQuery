package com.major.admin.dao;

import com.major.base.vo.UsersPrivs;

public interface UserDaoI {

	UsersPrivs login(String userName, String password);

	void updateUser(UsersPrivs user);

}

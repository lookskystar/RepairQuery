package com.major.admin.service;

import com.major.base.vo.UsersPrivs;

public interface UserServiceI {

	UsersPrivs login(String userName, String password);
	
	void update(UsersPrivs user);
}

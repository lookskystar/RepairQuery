package com.major.admin.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.major.admin.service.UserServiceI;
import com.major.base.action.BaseAction;
import com.major.base.vo.UsersPrivs;

public class UserAction extends BaseAction {

	private static final long serialVersionUID = 8815983461294507937L;

	/** request */
	private HttpServletRequest request = ServletActionContext.getRequest();

	private UserServiceI userService;

	@Resource
	public void setUserService(UserServiceI userService) {
		this.userService = userService;
	}

	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		UsersPrivs user = null;
		String towardStr = "";
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
			user = userService.login(userName, password);
			String pwdDb = user.getPwd();
			if (null == pwdDb || !pwdDb.equals(password)){
				request.setAttribute("loginError", "您输入的用户名或密码不对，请重新输入！");
				towardStr = "loginOut";
			} else {
				request.getSession().setAttribute("session_user", user);
				towardStr = "index";
			}
		} else {
			user = (UsersPrivs) request.getSession().getAttribute("session_user");
			if(null == user) {
				towardStr = "loginOut";
			} else {
				towardStr = "index";
			}
		}
		return towardStr;
	}
	
	/**
	 * 用户注销
	 * @return
	 * @throws Exception
	 */
	public String loginOut() throws Exception {
		request.getSession().removeAttribute("session_user");
		request.getSession().setAttribute("session_user", null);
		return "loginOut";
	}
	
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	public String updatePasswdShow() throws Exception {
		UsersPrivs user = (UsersPrivs) request.getSession().getAttribute("session_user");
		request.setAttribute("xm", user.getXm());
		return "updatePasswdShow";
	}
	
	/**
	 * 修改密码
	 * @return
	 * @throws Exception
	 */
	public String updatePasswd() throws Exception {
		UsersPrivs user = (UsersPrivs) request.getSession().getAttribute("session_user");
		String pwd = request.getParameter("pwd");
		user.setPwd(pwd);
		userService.update(user);
		return loginOut();
	}
}

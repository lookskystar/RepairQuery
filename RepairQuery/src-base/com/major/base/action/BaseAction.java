/**
 *
 * Copyright: Copyright (c) 2012 Asiainfo-Linkage
 *
 * com.major.base.action@date:2014-3-3
 * @ClassName: BaseAction.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0下午8:20:34eleven
 * @author: elevenmajor_fix
 *
 * Modification History:
 * Date  Author  Version Description
 * ---------------------------------------------------------*
 * 2014-3-3 eleven v1.0.0 新建
 */
package com.major.base.action;

import java.text.SimpleDateFormat;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.major.base.page.PageModel;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @Title: BaseAction.java
 * @Description: TODO(ACTION基础类)
 * @author eleven
 * @date 2014-3-3 下午8:20:34
 * 
 */
public class BaseAction extends ActionSupport{

	private static final long serialVersionUID = 3359127020170058411L; 
	
	private PageModel pager = new PageModel();
	
	public PageModel getPager() {
		return pager;
	}

	public void setPager(PageModel pager) {
		this.pager = pager;
	}

	public static final SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 * @Title: writeJson
	 * @Description: TODO(响应前台JSON对象)
	 * @param object
	 * @return: void
	 * @author: eleven
	 * @date: 2014-3-4
	 * @history: Date Author Version Description
	 *           ---------------------------------------------------------*
	 *           2014-3-4 eleven v1.0.0 修改原因
	 */
	public void writeJson(Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

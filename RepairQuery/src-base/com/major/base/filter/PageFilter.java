package com.major.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.major.base.page.PageContext;

/**
 * 
 * @Title: PageFilter.java
 * @Description: TODO(分页过滤器)
 * @author eleven
 * @date 2014-3-3 上午11:32:01
 * 
 */
public class PageFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		PageContext.setOffSet(getOffSet(httpRequest));
		PageContext.setPageSize(getPageSize(httpRequest));
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PageContext.removeOffSet();
			PageContext.removePageSize();
		}
	}

	/**
	 * 
	 * @Title: getOffset
	 * @Description: TODO(从请求中获取offset的值,默认值0)
	 * @param request
	 * @return
	 * @return: Integer
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date Author Version Description
	 *           ---------------------------------------------------------*
	 *           2014-3-3 eleven v1.0.0 修改原因
	 */
	private static Integer getOffSet(HttpServletRequest request) {
		Integer offSet = 0;
		try {
			String offSetString = request.getParameter("pager.offset");
			if (StringUtils.isNotEmpty(offSetString)) {
				offSet = Integer.parseInt(offSetString);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return offSet;
	}

	/**
	 * 
	 * @Title: getPageSize
	 * @Description: TODO(从请求中获取pageSize,默认的值10)
	 * @return
	 * @return: Integer
	 * @author: eleven
	 * @date: 2014-3-3
	 * @history: Date Author Version Description
	 *           ---------------------------------------------------------*
	 *           2014-3-3 eleven v1.0.0 修改原因
	 */
	private static Integer getPageSize(HttpServletRequest request) {
		Integer pageSize = 10;
		try {
			String pageSizeString = request.getParameter("pageSize");
			if (StringUtils.isNotEmpty(pageSizeString)) {
				pageSize = Integer.parseInt(pageSizeString);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		request.setAttribute("pageSize", pageSize);
		return pageSize;
	}

}

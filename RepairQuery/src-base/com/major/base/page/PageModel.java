package com.major.base.page;

import java.util.List;

/**
 * 
 * @Title: PageModel.java
 * @Description: TODO(View类)
 * @author eleven
 * @date 2014-3-3 上午11:36:19
 * 
 */
public class PageModel {
	/** 分页RowIndex */
	private int offset;
	/** 分页数据PageSize */
	private int pageSize;
	/** 分页数据总数 */
	private int count;
	/** 分页数据集合 */
	private List<?> datas;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<?> getDatas() {
		return datas;
	}

	public void setDatas(List<?> datas) {
		this.datas = datas;
	}
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}

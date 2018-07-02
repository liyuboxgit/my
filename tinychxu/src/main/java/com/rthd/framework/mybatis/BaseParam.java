package com.rthd.framework.mybatis;

public class BaseParam extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7620043218483174901L;
	private Integer pageNo = 1;
	private Integer pageSize = Integer.MAX_VALUE;
	
	public Integer getPageNo() {
		return (pageNo-1)*pageSize;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

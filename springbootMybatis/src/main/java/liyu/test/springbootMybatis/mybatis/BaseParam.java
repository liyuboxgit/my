package liyu.test.springbootMybatis.mybatis;

import java.io.Serializable;
import java.util.Date;

public class BaseParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7620043218483174901L;
	private Integer pageNo = 1;
	private Integer pageSize = Integer.MAX_VALUE;
	
	private Integer id;
	private Date create_time;
	private Date update_time;
	private Integer version;
	
	public Integer getPageNo() {
		return pageNo;
	}
	public Integer getRowNum() {
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}

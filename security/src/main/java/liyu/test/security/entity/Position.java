package liyu.test.security.entity;

import java.io.Serializable;

import liyu.test.framework.mvc.BaseEntity;

/**
 * @author Administrator
 *
 */
public class Position extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer pid;
	private Integer departMentId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getDepartMentId() {
		return departMentId;
	}

	public void setDepartMentId(Integer departMentId) {
		this.departMentId = departMentId;
	}
}

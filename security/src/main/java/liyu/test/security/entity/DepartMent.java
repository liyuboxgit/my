package liyu.test.security.entity;

import java.io.Serializable;

import liyu.test.framework.mvc.BaseEntity;

/**
 * @author Administrator
 *
 */
public class DepartMent extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private DepartMent parent;

	public DepartMent getParent() {
		return parent;
	}

	public void setParent(DepartMent parent) {
		this.parent = parent;
	}

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
}
package liyu.test.springboot_v2.model;

import java.io.Serializable;

public class Role implements Serializable{
	private static final long serialVersionUID = 7230511303996647622L;
	
	private Integer id;
	private String rolename;
	
	public Role(Integer id, String rolename) {
		this.id = id;
		this.rolename = rolename;
	}
	
	public Role() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}

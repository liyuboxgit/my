package liyu.test.springboot.model;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{
	private static final long serialVersionUID = 7230511303996647622L;
	private Integer id;
	private String rolename;
	private List<User> users;
	
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

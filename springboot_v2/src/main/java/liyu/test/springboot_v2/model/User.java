package liyu.test.springboot_v2.model;

import java.io.Serializable;


public class User implements Serializable{
	private static final long serialVersionUID = 8768982937738871210L;
	
	private Integer id;
	private String username;
	private String password = "123456";
	private Role role;
	
	public User(Integer id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public User() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}

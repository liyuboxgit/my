package liyu.test.anbao.core;

import java.io.Serializable;

public abstract class AuthUser implements Serializable{	
	private static final long serialVersionUID = 1L;
	private String username; 
	private String password;
	private String[] roles;
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
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
}

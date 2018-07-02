package com.rthd.framework.config.shiro;

import java.io.Serializable;

public class ShiroUser implements Serializable{
	private static final long serialVersionUID = 1L;

	public ShiroUser() {}
	public ShiroUser(String username,String password,String roles,Integer version) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.version = version;
	}
	
	private String username;
	private String password;
	private String roles;
	private Integer version;
	
	public String getPassword() {
		return this.password;
	}
	public String getUsername() {
		return username;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
}

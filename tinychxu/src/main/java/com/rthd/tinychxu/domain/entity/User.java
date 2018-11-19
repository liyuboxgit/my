package com.rthd.tinychxu.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.rthd.framework.mybatis.BaseEntity;
@Entity
@Table(name="user")
public class User extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
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
}

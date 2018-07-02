package com.rthd.tinychxu.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.rthd.framework.mybatis.BaseEntity;
@Entity
@Table(name="demo")
public class Demo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

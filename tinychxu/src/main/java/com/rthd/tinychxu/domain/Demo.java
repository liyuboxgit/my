package com.rthd.tinychxu.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.rthd.framework.mybatis.BaseEntity;
@Entity
@Table(name="demo")
public class Demo extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Integer age;
	
	private BigDecimal money;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}

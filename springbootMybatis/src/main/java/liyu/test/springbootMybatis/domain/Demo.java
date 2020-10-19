package liyu.test.springbootMybatis.domain;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import liyu.test.springbootMybatis.mybatis.BaseEntity;
@Entity
@Table(name="t_demo")
public class Demo extends BaseEntity{	
	private static final long serialVersionUID = 1L;
	
	@Column(length=20)
	private String name;
	private Short age;
	@Column(precision=2,scale=0)
	private BigDecimal money;
	private Date birth;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getAge() {
		return age;
	}
	public void setAge(Short age) {
		this.age = age;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
}

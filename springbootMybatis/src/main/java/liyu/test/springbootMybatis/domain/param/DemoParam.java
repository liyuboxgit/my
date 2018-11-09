package liyu.test.springbootMybatis.domain.param;
import java.math.BigDecimal;
import java.util.Date;

import liyu.test.springbootMybatis.mybatis.BaseParam;

public class DemoParam extends BaseParam{	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer age;
	private BigDecimal money;
	private Date birth;
	
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
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
}

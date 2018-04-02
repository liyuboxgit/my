package liyu.test.sb2_0.domain;

import java.math.BigDecimal;
import java.util.Date;

public class SB{
	private Integer id;
	private String name;
	private BigDecimal price;
	private Character sex_type;
	private Date birth;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Character getSex_type() {
		return sex_type;
	}
	public void setSex_type(Character sex_type) {
		this.sex_type = sex_type;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
}


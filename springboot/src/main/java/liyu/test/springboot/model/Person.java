package liyu.test.springboot.model;

import java.util.Date;

//@Entity
//@Table(name = "t_person")
public class Person {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//@Column(name = "name")
	private String name;
	//@Column(name = "email")
	private String email;
	//@Column(name = "telephone")
	private String telephone;
	//@Column(name = "create_time")
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

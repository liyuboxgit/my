package liyu.test.mybatis.model;

public class Person {
	/* id */
	private Integer id;
	/* 创建时间 */
	private java.util.Date createTime;
	/* 邮箱 */
	private String email;
	/* 姓名 */
	private String name;
	/* 手机号 */
	private String telephone;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephone() {
		return this.telephone;
	}
}
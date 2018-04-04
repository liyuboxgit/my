package liyu.test.sb2_0.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import liyu.test.sb2_0.domain.base.BaseEntity;

@Entity
@Table(name="se_user")
public class User extends BaseEntity{
	private String username;
	private String cnname;
	private String mobile;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCnname() {
		return cnname;
	}
	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}

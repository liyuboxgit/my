package liyu.test.sb2_0.configure.shiro;

import java.io.Serializable;

public class ShiroUser implements Serializable{
	private static final long serialVersionUID = 1L;

	public ShiroUser() {}
	public ShiroUser(String username,String password,Integer version) {
		this.username = username;
		this.password = password;
		this.version = version;
	}
	
	private String username;
	private String password;
	private Integer version;
	private Integer uid;
	private Integer oid;
	
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
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
}

package liyu.test.sb2_0.configure.simpleShiro;

import java.io.Serializable;

public class SimpleShiroUser implements Serializable{
	private static final long serialVersionUID = 1L;

	public SimpleShiroUser() {}
	public SimpleShiroUser(String username,String password) {
		this.username = username;
		this.password = password;
	}
	
	private String username;
	private String password;
	
	public String getPassword() {
		return this.password;
	}
	public String getUsername() {
		return username;
	}
}

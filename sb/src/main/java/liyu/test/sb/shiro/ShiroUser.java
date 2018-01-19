package liyu.test.sb.shiro;

import java.io.Serializable;

public class ShiroUser implements Serializable{
	private static final long serialVersionUID = 1L;

	public ShiroUser() {}
	public ShiroUser(String username,String password) {
		this.username = username;
		this.password = password;
	}
	
	private String username;
	private String password;
	
	public String getPassword() {
		return this.password;
	}

}

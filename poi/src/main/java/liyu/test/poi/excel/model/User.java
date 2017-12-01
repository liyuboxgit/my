package liyu.test.poi.excel.model;

import java.util.Date;

import liyu.test.poi.excel.annotation.SingleParser;
import liyu.test.poi.excel.annotation.SinglePosition;

@SingleParser(templateCode = "0002")
public class User {
	
	@SinglePosition(x = "A", y = 1, nullable = false)
	private String loginName;

	@SinglePosition(x = "B", y = 1, nullable = false)
	private String userName;
	
	@SinglePosition(x = "C", y = 1, nullable = false)
	private Integer age;
	
	@SinglePosition(x = "D", y = 1, nullable = false)
	private Date birth;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}

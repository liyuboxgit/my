package liyu.test.poi.excel.model;

import java.util.Date;

import liyu.test.poi.excel.annotation.BatchParser;
import liyu.test.poi.excel.annotation.BatchPosition;

@BatchParser(templateCode = "0002", startRow = 1)
public class SimpleUser {
	@BatchPosition(x = "A", nullable = false)
	private String loginName;

	@BatchPosition(x = "B", nullable = false)
	private String userName;
	
	@BatchPosition(x = "C", nullable = false)
	private Integer age;

	@BatchPosition(x = "D", nullable = false)
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

}

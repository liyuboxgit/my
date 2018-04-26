package liyu.test.shiro.model;

import java.util.List;

public class Role {
	private String name;
	private List<Permission> permissions;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	
}

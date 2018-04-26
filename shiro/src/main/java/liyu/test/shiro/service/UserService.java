package liyu.test.shiro.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import liyu.test.shiro.model.Permission;
import liyu.test.shiro.model.Role;
import liyu.test.shiro.model.User;
@Service
public class UserService {

	public User findByUsername(String username) {
		User user = new User();
		if("admin".equals(username)){
			user.setUsername("admin");
			user.setPassword("123456");
		}			
		return user;
	}

	public Set<Role> findRoles(String username) {
		Role role = new Role();
		role.setName("user");
		HashSet<Role> set = new HashSet<Role>();
		set.add(role);
		for(Role el:set) {
			Permission permission = new Permission();
			permission.setName("query");
			el.setPermissions(new ArrayList<Permission>());
			el.getPermissions().add(permission);
		}
		return set;
	}
	
}

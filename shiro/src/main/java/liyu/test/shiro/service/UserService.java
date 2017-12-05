package liyu.test.shiro.service;

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
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Permission> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}

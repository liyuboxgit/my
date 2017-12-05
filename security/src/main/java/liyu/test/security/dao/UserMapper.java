package liyu.test.security.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.security.entity.User;
import liyu.test.security.entity.po.UserPo;

@Repository
public interface UserMapper {
	public void add(UserPo user);

	public User get(UserPo user);
	
	public List<User> query();
}

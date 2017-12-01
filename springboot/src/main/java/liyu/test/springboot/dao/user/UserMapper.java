package liyu.test.springboot.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.springboot.model.user.User;

@Repository
public interface UserMapper {
	public List<User> findList(User user);
	public int insert(User user);
}

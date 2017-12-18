package liyu.test.springboot_v2.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.springboot_v2.model.User;
@Repository
public interface UserMapper {
	public User get(int id);
	public List<User> findList(User user);
	public void insert(User user);
}

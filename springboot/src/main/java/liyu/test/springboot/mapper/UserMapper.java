package liyu.test.springboot.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import liyu.test.springboot.model.User;
@Repository
public interface UserMapper {
	public User get(int id);
	public List<User> findList(User user);
	public Integer findCount(User user);
	public void insert(User user);
}

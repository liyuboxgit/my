package liyu.test.springboot.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.springboot.dao.user.UserMapper;
import liyu.test.springboot.model.user.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	@Transactional
	public void insert(User user){
		this.userMapper.insert(user);
	}
}

package liyu.test.springboot.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.springboot.mapper.UserMapper;
import liyu.test.springboot.model.User;
import liyu.test.springboot.service.BaseService;

@Service
public class UserService extends BaseService{
	@Autowired
	private UserMapper userMapper;
	@Transactional
	public void insert(User user){
		this.userMapper.insert(user);
	}
}

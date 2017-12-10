package liyu.test.springboot.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import liyu.test.springboot.mapper.user.UserMapper;
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
	
	public User findOne(){
		List<User> list = userMapper.findList(new User());
		return list.isEmpty()?null:list.get(0);
		
		/*User user = new User();
		user.setUsername("user john");*/
		
		//return user;
	}
}

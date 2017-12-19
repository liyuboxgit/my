package liyu.test.springboot_v2.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.springboot_v2.mapper.UserMapper;
import liyu.test.springboot_v2.model.User;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/login")
	@ResponseBody
	public List<User> login(){
		List<User> list = userMapper.findList(new User());
		return list;
	}
}

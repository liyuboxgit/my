package liyu.test.springboot_v2.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {
	
	
	/*@RequestMapping("/login")
	@ResponseBody
	public List<User> login(){
		List<User> list = userMapper.findList(new User());
		return list;
	}*/
}

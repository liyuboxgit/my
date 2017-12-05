package liyu.test.security.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.security.entity.User;
import liyu.test.security.service.ButtonService;
import liyu.test.security.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController{
	@Autowired
	private UserService userService;
	@Autowired
	private ButtonService buttonService;
	@RequestMapping("/queryForList")
	@ResponseBody
	public List<User> queryForList(){
		buttonService.query();
		
		User user = userService.getByLoginName("scott");
		List<User> list = new ArrayList<User>();
		list.add(user);
		return list;
	}
}

package liyu.test.springboot.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import liyu.test.springboot.model.User;
import liyu.test.springboot.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/findList")
	public String findList(Model model){
		User user = userService.findOne();
		model.addAttribute("user", user);
		return "user/user_list";
	}
}

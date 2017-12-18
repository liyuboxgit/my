package liyu.test.springboot_v2.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/login")
	@ResponseBody
	public String login(){
		return "success";
	}
}

package liyu.test.springSecurity.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {
	@RequestMapping("/")
	public String sccess() {
		return "success,返回成功";
	}
}

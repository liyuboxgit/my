package liyu.test.springSecurity.web;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {
	@RequestMapping("/")
	public String sccess() {
		return "success,返回成功";
	}
	@RequestMapping("/util/getDate")
	public Object getDate() {
		return new Date();
	}
}

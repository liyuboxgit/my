package liyu.test.shiro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.shiro.model.User;
import liyu.test.shiro.service.UserService;
import liyu.test.shiro.util.AjaxResult;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String toroot() {
		return "login";
	}
	
	@RequestMapping("/noperm")
	public String noperm() {
		return "noperm";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public AjaxResult login(@RequestParam("username") String username, @RequestParam("password") String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException ice) {
			// 捕获密码错误异常
			return AjaxResult.fail("捕获密码错误异常");
		} catch (UnknownAccountException uae) {
			// 捕获未知用户名异常
			return AjaxResult.fail("捕获未知用户名异常");
		} catch (ExcessiveAttemptsException eae) {
			// 捕获错误登录过多的异常
			return AjaxResult.fail("捕获错误登录过多的异常");
		}
		User user = userService.findByUsername(username);
		subject.getSession().setAttribute("user", user);
		return AjaxResult.success();
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public AjaxResult query(){
		return AjaxResult.success();
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public AjaxResult add(){
		return AjaxResult.success();
	}
}
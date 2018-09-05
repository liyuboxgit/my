package liyu.test.anbao.example;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import liyu.test.anbao.core.Auth;
import liyu.test.anbao.core.util.JsonRet;
import liyu.test.anbao.core.util.StringUtil;
import liyu.test.anbao.core.util.WebUtil;

@RestController
public class LoginController{
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Value("${me.login_page}")
	private String loginPage;
	@Autowired
	private Auth auth;
	@RequestMapping("/")
	public void index() throws IOException {
		String page = WebUtil.getServletRequest().getContextPath() + loginPage;
		WebUtil.getServletResponse().sendRedirect(page);
	}
	
	@RequestMapping("/logout")
	public JsonRet logout() throws IOException {
		auth.logout(WebUtil.getServletRequest());
		return WebUtil.success();
	}
	
	@RequestMapping("/login")
	public JsonRet login(@RequestBody User user) throws IOException {
		if(StringUtil.isBlank(user.getUsername())||StringUtil.isBlank(user.getPassword())) {
			return WebUtil.fail("用户名和密码不能为空！");
		}
		try {
			auth.login(user);
		} catch (Exception ex) {
			return WebUtil.errorAndReture(logger, "登录失败", ex);
		}
		
		return WebUtil.success("登录成功！");
	}	

	
	@RequestMapping("/test")
	public String test() {
		return "ok";
	}
	@RequestMapping("/webs/test")
	public String test_() {
		return "ok";
	}
}

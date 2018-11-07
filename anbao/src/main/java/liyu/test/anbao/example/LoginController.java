package liyu.test.anbao.example;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import liyu.test.anbao.core.Auth;
import liyu.test.anbao.core.AuthInterceptor;
import liyu.test.anbao.core.Protection;
import liyu.test.anbao.core.util.JsonRet;
import liyu.test.anbao.core.util.StringUtil;
import liyu.test.anbao.core.util.WebUtil;

@RestController
public class LoginController{
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private Auth auth;
	/*@RequestMapping("/")
	public void index() throws IOException {
		String page = WebUtil.getServletRequest().getContextPath() + loginPage;
		WebUtil.getServletResponse().sendRedirect(page);
	}*/
	
	@RequestMapping("/logout")
	public JsonRet logout() throws IOException {
		auth.logout(WebUtil.getServletRequest());
		WebUtil.getServletRequest().removeAttribute(AuthInterceptor.ckey);
		return WebUtil.success();
	}
	
	@RequestMapping("/login")
	public JsonRet login(@RequestBody User user) throws IOException {
		if(StringUtil.isBlank(user.getUsername())||StringUtil.isBlank(user.getPassword())) {
			return WebUtil.fail("用户名和密码不能为空！");
		}
		try {
			user.setRoles(new String[] {"ROLE1","ROLE2"});
			auth.login(user);
		} catch (Exception ex) {
			return WebUtil.errorAndReture(logger, "登录失败", ex);
		}
		
		return WebUtil.success("登录成功！");
	}	

	///this url needs logining.
	@RequestMapping("/test")
	public String test() {
		auth.setAttributeInRedis("test", "author", "liyu", 15);
		return "ok";
	}
	///this url needs no logining.
	@RequestMapping("/webs/test")
	public String test_() {
		return auth.getAttributeInRedis("test", "author");
	}
	///this url needs permission for it has Protection annotation.
	@RequestMapping("/role")
	@Protection
	public String role() {
		return "ok,have funny!";
	}
}

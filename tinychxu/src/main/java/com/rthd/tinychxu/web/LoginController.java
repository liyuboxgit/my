package com.rthd.tinychxu.web;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rthd.framework.base.BaseController;
import com.rthd.framework.util.JsonRet;
import com.rthd.framework.util.WebUtil;

@RestController
public class LoginController extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Value("${me.login_page}")
	private String loginPage;
	@RequestMapping({"/","/loginout"})
	@ResponseBody
	public void root() throws IOException {
		if(SecurityUtils.getSubject().isAuthenticated()) {
			SecurityUtils.getSubject().logout();
		}
		String page = WebUtil.getServletRequest().getContextPath()+loginPage;
		WebUtil.getServletResponse().sendRedirect(page);
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public JsonRet login(String username, String password) throws IOException {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.isAuthenticated()) {
				token.setRememberMe(false);
				currentUser.login(token);
			}
		} catch (Exception ex) {
			return WebUtil.errorAndReture(logger, "登录失败", ex);
		}
		return WebUtil.success("登录成功！");
	}
}

package liyu.test.sb2_0.web;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.sb2_0.web.util.WebUtil;

@Controller
@RequestMapping("/")
public class LoginController {
	protected static final Logger logger = LoggerFactory.getLogger(LoginController.class);
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
	public WebUtil.JsonRet login(String username, String password) throws IOException {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.isAuthenticated()) {
				token.setRememberMe(true);
				currentUser.login(token);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			return WebUtil.fail("登录失败!"+ex.getMessage(), ex.getCause());
		}
		return WebUtil.success("登录成功！");
	}
}

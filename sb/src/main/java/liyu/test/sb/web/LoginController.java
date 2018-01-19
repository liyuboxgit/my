package liyu.test.sb.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	@RequestMapping({"/","/loginout"})
	public String toLogin(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
			request.setAttribute("msg", "you are login out successful.");
		}else {
			request.setAttribute("msg", "you are not logging.");
		}
		return "login";
	}

	@RequestMapping("/login")
	@ResponseBody
	public String login(String username, String password, Model model) {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject currentUser = SecurityUtils.getSubject();
			if (!currentUser.isAuthenticated()) {
				token.setRememberMe(true);
				currentUser.login(token);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "error,you are not login,because " + ex.getMessage();
		}
		return "success,you are login,you can use: /loginout to login out ";
	}
}

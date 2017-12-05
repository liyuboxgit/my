package liyu.test.security.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sec")
public class AuthenticationController {
	private static final Logger logger = Logger.getLogger(AuthenticationController.class);
	@RequestMapping("/login") 
	public String login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		logger.debug("execute login...");
		
		Subject subject = SecurityUtils.getSubject();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {			
			subject.login(token);
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute("msg", "用户名或密码错误，请重新登录！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		
		return "/sec/index";
	}
}

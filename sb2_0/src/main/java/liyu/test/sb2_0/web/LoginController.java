package liyu.test.sb2_0.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class LoginController {
	@RequestMapping("/")
	@ResponseBody
	public String root(HttpServletRequest req,HttpServletResponse res) throws IOException {
		///第一种方式，返回到mvc模板下动态文件
		//return "login";
		
		///第二种方式，返回到静态页面
		res.sendRedirect(req.getContextPath()+"/static/pages/login.html");
		//return null;
		
		///第三种方式，返回到静态页面
		return "redirect:"+req.getContextPath()+"/static/pages/login.html";
	}
}

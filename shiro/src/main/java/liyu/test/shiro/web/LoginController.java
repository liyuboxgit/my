package liyu.test.shiro.web;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.shiro.util.AjaxResult;

@Controller
public class LoginController {	
	@RequestMapping("/")
	public String toroot() {
		SavedRequest savedRequest = null;  
		Subject subject = SecurityUtils.getSubject();
	    Session session = subject.getSession(false);  
	    if (session != null) {  
	        savedRequest = (SavedRequest) session.getAttribute("shiroSavedRequest");  
	        if(savedRequest!=null) {
	        	String url = savedRequest.getRequestUrl();
	        	if(!"/favicon.ico".equals(url)) {
	        		session.setAttribute("preUrl",url);
	        	}
	        }
	    }   
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
			return AjaxResult.fail("捕获密码错误异常");
		} catch (UnknownAccountException uae) {
			return AjaxResult.fail("捕获未知用户名异常");
		} catch (ExcessiveAttemptsException eae) {
			return AjaxResult.fail("捕获错误登录过多的异常");
		}
		
		String preUrl = (String) subject.getSession().getAttribute("preUrl");
		
		return AjaxResult.setResult(preUrl);
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("/query")
	@ResponseBody
	public AjaxResult query(){
		if(true)
			throw new RuntimeException("just for test.");
		return AjaxResult.success();
	}
	
	@RequestMapping("/date")
	@ResponseBody
	public AjaxResult date() {
		return AjaxResult.setResult(new Date());
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public AjaxResult add(){
		return AjaxResult.success();
	}
}
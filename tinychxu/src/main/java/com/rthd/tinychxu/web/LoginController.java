package com.rthd.tinychxu.web;

import java.io.IOException;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
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
	/**
	 * 
	 * @Description: 此方法描述的是：跳登录页面，跳之前保存上一个请求地址
	 * @author: you@rthdtax.com
	 * @version: 2018年11月19日 下午4:30:17
	 */
	@RequestMapping("/")
	@ResponseBody
	public void root() throws IOException {
		Subject subject = SecurityUtils.getSubject();
	    Session session = subject.getSession(false);  
	    if (session != null) {  
	    	SavedRequest savedRequest = (SavedRequest) session.getAttribute("shiroSavedRequest");  
	        if(savedRequest!=null) {
	        	String url = savedRequest.getRequestUrl();
	        	if(!"/favicon.ico".equals(url)) {
	        		session.setAttribute("preUrl",url);
	        	}
	        }
	    }   
		
		String page = WebUtil.getServletRequest().getContextPath()+loginPage;
		WebUtil.getServletResponse().sendRedirect(page);
	}
	
	/**
	 * 
	 * @Description: 此方法描述的是：登录，登录成功后返回原请求地址
	 * @author: you@rthdtax.com
	 * @version: 2018年11月19日 下午4:31:11
	 */
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
			
			String preUrl = (String) currentUser.getSession().getAttribute("preUrl");
			return WebUtil.setResult(preUrl);
			
		} catch (Exception ex) {
			return WebUtil.errorAndReture(logger, "登录失败", ex);
		}
	}
	
	/**
	 * 
	 * @Description: 此方法描述的是：登出
	 * @author: you@rthdtax.com
	 * @version: 2018年11月19日 下午4:31:53
	 */
	@RequestMapping("/loginout")
	@ResponseBody
	public void loginout() throws IOException {
		if(SecurityUtils.getSubject().isAuthenticated()) {
			SecurityUtils.getSubject().logout();
		}
		String page = WebUtil.getServletRequest().getContextPath()+loginPage;
		WebUtil.getServletResponse().sendRedirect(page);
	}
	
	/**
	 * 
	 * @Description: 此方法描述的是：模拟主页面
	 * @author: you@rthdtax.com
	 * @version: 2018年11月19日 下午4:32:06
	 */
	@RequestMapping("/index")
	@ResponseBody
	public JsonRet index(){
		return WebUtil.setResult("this is index page (as main page or 主页面)");
	}
	
	/**
	 * 
	 * @Description: 此方法描述的是：获取服务器时间
	 * @author: you@rthdtax.com
	 * @version: 2018年11月19日 下午4:32:17
	 */
	@RequestMapping("/date")
	@ResponseBody
	public JsonRet date(){
		return WebUtil.setResult(new Date());
	}
}

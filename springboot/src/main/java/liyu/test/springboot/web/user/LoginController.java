package liyu.test.springboot.web.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.springboot.util.AjaxResult;

@Controller
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@RequestMapping("/")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("/index")
	public String toIndex(){
		return "index";
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public AjaxResult login(String username,String password,Model model){
		try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
            Subject currentUser = SecurityUtils.getSubject();  
            if (!currentUser.isAuthenticated()){
                token.setRememberMe(true);  
                currentUser.login(token);
            } 
        }catch(Exception ex){
        	logger.error("login error:"+ex.getMessage());
        	return AjaxResult.fail(ex.getMessage());
        }
 		return AjaxResult.success();
	}
	
	
}

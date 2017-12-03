package liyu.test.springboot.web.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@RequestMapping("/login")
	public String login(String username,String password,Model model){
		try{
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
            Subject currentUser = SecurityUtils.getSubject();  
            if (!currentUser.isAuthenticated()){
                token.setRememberMe(true);  
                currentUser.login(token);
            } 
        }catch(Exception ex){
        	logger.error("login error:"+ex.getMessage());
        	return "user/login";
        }
 		return "user/index";
	}
	
	@RequestMapping("/index")
	public String index(){
		return "user/index";
	}
}

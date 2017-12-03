package liyu.test.springboot.web;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import liyu.test.springboot.mapper.user.UserMapper;
import liyu.test.springboot.model.User;
import liyu.test.springboot.service.user.UserService;

@Controller
@RequestMapping("/simple")
public class SimpleController {

	private final Logger logger = LoggerFactory.getLogger(SimpleController.class);  
	
    @RequestMapping("/home")
    @ResponseBody
    String home() {
    	logger.info(new Date().toString());
        return "Hello World";
    }
	
    @RequestMapping("/index")
    String index(){
    	return "index";
    }
    
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserService userService;
    
}
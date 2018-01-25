package liyu.test.sb.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import liyu.test.sb.framework.RestControllerMapping;

@RestControllerMapping(path = "/rest") 
public class RestFulController {
	@Value("${rest.msg}")
	private String msg;
	
	@RequestMapping("/msg")
	public String msg(){
		return "success:" + msg;
	}
}














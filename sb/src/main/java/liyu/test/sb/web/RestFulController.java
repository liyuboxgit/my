package liyu.test.sb.web;

import org.springframework.web.bind.annotation.RequestMapping;

import liyu.test.sb.framework.RestControllerMapping;

@RestControllerMapping(path = "/rest") 
public class RestFulController {
	@RequestMapping("/msg")
	public String msg(){
		return "success";
	}
}














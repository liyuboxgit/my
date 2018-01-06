package liyu.test.sb.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestFulController {
	@RequestMapping("/msg")
	public String msg(){
		return "success";
	}
}

package liyu.test.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order/query")
public class OrderController {
	@RequestMapping("/C")
	@ResponseBody
	public String C(){
		return "C";
	}
	@RequestMapping("/R")
	@ResponseBody
	public String R(){
		return "R";
	}
	@RequestMapping("/T")
	@ResponseBody
	public String T(){
		return "T";
	}
}

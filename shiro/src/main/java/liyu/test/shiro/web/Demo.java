package liyu.test.shiro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class Demo {
	@RequestMapping("/index")
	@ResponseBody
	public String index(){
		return "index";
	}
}

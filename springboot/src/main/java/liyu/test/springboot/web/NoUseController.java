package liyu.test.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/person")
public class NoUseController {
	@RequestMapping("/add")
	@ResponseBody
	public String add(Model model){
		return "success";
	}
}

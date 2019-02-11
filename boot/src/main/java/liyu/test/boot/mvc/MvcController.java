package liyu.test.boot.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MvcController {
	@RequestMapping("/mvc")
	public String index() {
		return "springmvc success";
	}
}

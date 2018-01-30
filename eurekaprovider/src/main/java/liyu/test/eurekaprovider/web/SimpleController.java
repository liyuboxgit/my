package liyu.test.eurekaprovider.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class SimpleController {
	@RequestMapping("/msg")
	public String sccess() {
		return "success from provider.";
	}
}

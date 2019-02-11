package liyu.test.boot.webflux.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FluxController {
	@RequestMapping("/flux")
	public String index() {
		return "flux success";
	}
}

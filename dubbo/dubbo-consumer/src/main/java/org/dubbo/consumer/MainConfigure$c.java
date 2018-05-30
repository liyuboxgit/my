package org.dubbo.consumer;

import org.dubbo.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainConfigure$c {
	@Autowired
	private ConsumerService cs;
	@RequestMapping("/consumer/index")
	public String index() throws Exception {
		return cs.ConService();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure$c.class, args);
	}
}

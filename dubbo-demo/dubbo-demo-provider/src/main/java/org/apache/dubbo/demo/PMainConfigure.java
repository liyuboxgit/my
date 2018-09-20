package org.apache.dubbo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ImportResource(locations= {"spring.xml"})
public class PMainConfigure{
	
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}


	public static void main(String[] args) {
		SpringApplication.run(PMainConfigure.class, args);
	}
}


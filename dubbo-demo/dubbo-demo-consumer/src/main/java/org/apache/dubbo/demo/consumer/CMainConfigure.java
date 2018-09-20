package org.apache.dubbo.demo.consumer;

import org.apache.dubbo.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ImportResource(locations= {"spring.xml"})
public class CMainConfigure{
	@Autowired
	private DemoService demoService;
	
	@RequestMapping("/")
	public String index(String n) {
		String string = demoService.sayHello(n==null?"me":n);
		return "index :"+string;
	}

	public static void main(String[] args) {
		SpringApplication.run(CMainConfigure.class, args);
	}
}


package liyu.test.mvc;

import org.apache.dubbo.demo.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;


@SpringBootApplication
@EnableDubbo
@RestController
public class MainConfigure {
	@Reference
	private DemoService demoService;
	
	@RequestMapping("/")
	public String sccess() {
		System.out.println(demoService.sayHello("2020"));
		return "success";
	}
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

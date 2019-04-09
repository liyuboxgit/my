package liyu.test.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class MainConfigure {
	@RequestMapping("/")
	public String sccess() {
		return "success";
	}
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

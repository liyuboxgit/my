package liyu.test.log4j_logstash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@SpringBootApplication
@RestController
public class MainConfigure {
	private static Logger logger = LoggerFactory.getLogger(MainConfigure.class);
	@RequestMapping("/")
	public String sccess() {
		logger.debug(new Date().toString());
		return "success";
	}
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

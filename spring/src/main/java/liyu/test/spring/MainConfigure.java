package liyu.test.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
@RestController
public class MainConfigure {
	@RequestMapping("/")
	public String sccess() {
		return "success";
	}
	
	// 1.javaconfig in package:./javaconfig
	// 2.quantz test in ./Test0
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		 return new SchedulerFactoryBean();
	}
}

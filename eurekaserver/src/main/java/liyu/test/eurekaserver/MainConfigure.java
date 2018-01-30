package liyu.test.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class MainConfigure {
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

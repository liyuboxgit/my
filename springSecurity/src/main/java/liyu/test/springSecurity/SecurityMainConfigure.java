package liyu.test.springSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import liyu.test.springSecurity.config.SpringMvcConfigrue;


@SpringBootApplication
@Import({SpringMvcConfigrue.class})
public class SecurityMainConfigure {
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityMainConfigure.class, args);
	}
}

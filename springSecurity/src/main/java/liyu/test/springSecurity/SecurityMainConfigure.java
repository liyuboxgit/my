package liyu.test.springSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import liyu.test.springSecurity.config.SpringMvcConfigrue;
import liyu.test.springSecurity.config.SpringSecurityConfig;


@SpringBootApplication
@EnableWebSecurity
@Import({SpringMvcConfigrue.class,SpringSecurityConfig.class})
public class SecurityMainConfigure {
	
	public static void main(String[] args) {
		SpringApplication.run(SecurityMainConfigure.class, args);
	}
}

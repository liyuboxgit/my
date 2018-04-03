package liyu.test.sb2_0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liyu.test.sb2_0.configure.ShiroConfigure;
import liyu.test.sb2_0.configure.SpringMvcConfigrue;


@SpringBootApplication
@EnableTransactionManagement
@Import({ShiroConfigure.class,SpringMvcConfigrue.class})
public class MainConfigure extends SpringBootServletInitializer{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainConfigure.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

package liyu.test.springbootMybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Import({DruidConfiguration.class,SpringMvcConfigrue.class})
@ServletComponentScan(basePackages= {"liyu.test.springbootMybatis.druidFilter"})
public class MainConfigure extends SpringBootServletInitializer{
	@RequestMapping("/")
	public String sccess() {
		return "success";
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainConfigure.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

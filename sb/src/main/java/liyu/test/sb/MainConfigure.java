package liyu.test.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MainConfigure extends WebMvcConfigurerAdapter{
	@Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		 return new SchedulerFactoryBean();
	}
	
	public static void main(String[] args){
		SpringApplication.run(MainConfigure.class, args);
	}
}

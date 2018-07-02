package com.rthd.tinychxu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.rthd.framework.config.DruidConfiguration;
import com.rthd.framework.config.ShiroConfigure;
import com.rthd.framework.config.SpringMvcConfigrue;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@Import({ShiroConfigure.class,SpringMvcConfigrue.class,DruidConfiguration.class})
@ServletComponentScan(basePackages= {"com.rthd.framework.config.datasource"})
public class MainConfigure extends SpringBootServletInitializer{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainConfigure.class);
    }
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

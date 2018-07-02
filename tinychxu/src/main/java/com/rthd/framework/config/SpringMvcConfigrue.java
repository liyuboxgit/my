package com.rthd.framework.config;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rthd.framework.util.LogUtil;
import com.rthd.framework.util.StringUtil;

@Configuration
public class SpringMvcConfigrue extends WebMvcConfigurerAdapter{
	private static Logger logger = LoggerFactory.getLogger(SpringMvcConfigrue.class);
	private static DateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
	
	@Bean
    public Converter<String, Date> convert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                Date date = null;
                try {
                	if(!StringUtil.isEmpty(source)) {                		
                		date = DATA_FORMAT.parse(source);
                	}
                } catch (ParseException e) {
                	LogUtil.error(logger, "parse error", e);
                }
                return date;
            }
        };
    }
}

package liyu.test.anbao;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import liyu.test.anbao.core.SecurityInterceptor;
import liyu.test.anbao.core.util.DateUtil;
import liyu.test.anbao.core.util.StringUtil;

@Configuration
public class SpringMvcConfigrue extends WebMvcConfigurerAdapter{
	private static Logger logger = LoggerFactory.getLogger(SpringMvcConfigrue.class);
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor()).addPathPatterns("/**");
	} 
	
	@Bean
	public SecurityInterceptor securityInterceptor() {
		return new SecurityInterceptor();
	}
	
	@Bean
    public Converter<String, Date> convert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                Date date = null;
                try {
                	if(StringUtil.isNotBlank(source)) {                		
                		if(source.trim().length()==10)
                			date = DateUtil.DATA_FORMAT01.parse(source);
                		else
                			date = DateUtil.DATA_FORMAT02.parse(source);
                	}
                } catch (ParseException e) {
                	logger.error(e.getMessage(), e);
                }
                return date;
            }
        };
    }
	
	@Bean
	public FilterRegistrationBean corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);  
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	    bean.setOrder(0);
	    return bean;
	}
}

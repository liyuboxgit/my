package liyu.test.anbao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import liyu.test.anbao.core.JedisPoolManager;
import liyu.test.anbao.core.util.ApplicationPropertes;
import liyu.test.anbao.example.AuthImpl;

@SpringBootApplication
@Import({SpringMvcConfigrue.class})
public class AnbaoMainConfigure extends SpringBootServletInitializer{
	
	@Bean
	public JedisPoolManager jedisPoolManager() {
		return new JedisPoolManager(ApplicationPropertes.instance().getRedis_ip(),
				ApplicationPropertes.instance().getRedis_port());
	}
	
	@Bean
	public AuthImpl authImpl() {
		return new AuthImpl(jedisPoolManager());
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AnbaoMainConfigure.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(AnbaoMainConfigure.class, args);
	}
}

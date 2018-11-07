package liyu.test.anbao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import liyu.test.anbao.core.JedisPoolManager;
import liyu.test.anbao.core.util.Conf;
import liyu.test.anbao.example.AuthImpl;

@SpringBootApplication
@Import({SpringMvcConfigrue.class})
public class AnbaoMainConfigure extends SpringBootServletInitializer{
	
	@Bean
	public JedisPoolManager jedisPoolManager() {
		return new JedisPoolManager(Conf.get(Conf.REDIS_IP),
				Conf.get(Conf.REDIS_PORT));
	}
	
	@Bean
	public AuthImpl authImpl() {
		AuthImpl impl = new AuthImpl(jedisPoolManager());
		Set<String> roles = new HashSet<String>();
		Map<String,Set<String>> res = new HashMap<String,Set<String>>();
		
		res.put("/anbao/role", roles);
		res.get("/anbao/role").add("ROLE3");
		
		impl.setUrlResources(res);
		return impl;
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AnbaoMainConfigure.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(AnbaoMainConfigure.class, args);
	}
}

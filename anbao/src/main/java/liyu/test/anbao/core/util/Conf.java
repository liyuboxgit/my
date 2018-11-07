package liyu.test.anbao.core.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;


public class Conf {
	public final static Logger logger = LoggerFactory.getLogger(Conf.class);
			
	public static final String ANON="me.anon";
	public static final String SESSION_SECONDS="me.session_seconds";
	public static final String MAX_SESSION="me.max_session";
	public static final String REDIS_IP="me.redis_ip";
	public static final String REDIS_PORT="me.redis_port";

	private static Properties properties;
	static {		
		Properties conf = new Properties();
		String profile = null;
		
		try (InputStream in = Object.class.getResourceAsStream("/application.properties");) {
			conf.load(in);
			profile = conf.getProperty("spring.profiles.active");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
	    yaml.setResources(new ClassPathResource("application-"+profile+".yml"));
	    properties = yaml.getObject();
	}
	
	public static String get(String key,String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}
}

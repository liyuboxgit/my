package liyu.test.springbootMybatis.mybatis;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

public class Conf{
	public final static Logger logger = LoggerFactory.getLogger(Conf.class);   
	private static Conf instance;
	/**
	 * 属性
	 */
	private String url;
	private String username;
	private String password;
	private String driver;
	private Class<?> entity;
	
	static {		
		Properties conf = new Properties();
		String profile = null;
		
		try (InputStream in = Conf.class.getResourceAsStream("/application.properties");) {
			conf.load(in);
			profile = conf.getProperty("spring.profiles.active");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
	    yaml.setResources(new ClassPathResource("application-"+profile+".yml"));
	    conf = yaml.getObject();
		
	    try {
			instance = new Conf();
			instance.url = conf.getProperty("spring.datasource.url").replace("&", "&amp;");;
			instance.username = conf.getProperty("spring.datasource.username");
			instance.password = conf.getProperty("spring.datasource.password");
			instance.driver = conf.getProperty("spring.datasource.driver-class-name");
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static Conf get() {
		return instance;
	}

	public static Conf get(Class<?> type) {
		instance.entity = type;
		return instance;
	}

	public String toHibernateConfigString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<!DOCTYPE hibernate-configuration PUBLIC\r\n" + 
				"		          \"-//Hibernate/Hibernate Configuration DTD 3.0//EN\"\r\n" + 
				"		          \"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd\">");
		
		builder.append("<hibernate-configuration>");
		builder.append("<session-factory>");
		builder.append("<property name=\"connection.url\">"+url+"</property>");
		builder.append("<property name=\"connection.username\">"+username+"</property>");
		builder.append("<property name=\"connection.password\">"+password+"</property>");
		builder.append("<property name=\"connection.driver_class\">"+driver+"</property>");
		builder.append("<property name=\"dialect\">org.hibernate.dialect.MySQLDialect</property>");
		builder.append("<property name=\"show_sql\">false</property>");
		builder.append("<property name=\"format_sql\">false</property>");
		builder.append("<mapping class=\""+entity.getName()+"\" />");
		builder.append("</session-factory>");
		builder.append("</hibernate-configuration>");
		return builder.toString();
	}
	
	public String getUrl() {
		return this.url;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getDriver() {
		return driver;
	}
	public Class<?> getEntity() {
		return entity;
	}
}

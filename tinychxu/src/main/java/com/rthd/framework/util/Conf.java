package com.rthd.framework.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private String dialect;
	private Class<?> entity;
	
	private String shiro_conf;
	private String redis_ip;
	private String redis_port;
	
	private String login_url;
	private String unauthorized_url;
	private String anon;
	private String authc;
	
	private String max_session;
	private String session_timeout;
	
	static {		
		Properties conf = new Properties();
		String profile = null;
		
		try (InputStream in = Conf.class.getResourceAsStream("/application.properties");) {
			conf.load(in);
			profile = conf.getProperty("spring.profiles.active");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try (InputStream in = Conf.class.getResourceAsStream("/application-"+profile+".properties");) {
			conf.load(in);
			instance = new Conf();
			instance.url = conf.getProperty("spring.datasource.url").replace("&", "&amp;");;
			instance.username = conf.getProperty("spring.datasource.username");
			instance.password = conf.getProperty("spring.datasource.password");
			instance.driver = conf.getProperty("spring.datasource.driverClassName");
			instance.dialect = "org.hibernate.dialect.MySQLDialect"; 
			
			instance.redis_ip = conf.getProperty("redis_ip");
			instance.redis_port = conf.getProperty("redis_port");
			instance.login_url = conf.getProperty("login_url");
			instance.unauthorized_url = conf.getProperty("unauthorized_url");
			instance.anon = conf.getProperty("anon");
			instance.authc = conf.getProperty("authc");
			instance.max_session=conf.getProperty("max_session");
			instance.session_timeout=conf.getProperty("session_timeout_millisecond");
		} catch (Exception e) {
			logger.error("全局配置类初始化错误", e);
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
		builder.append("<property name=\"dialect\">"+dialect+"</property>");
		builder.append("<property name=\"show_sql\">false</property>");
		builder.append("<property name=\"format_sql\">false</property>");
		builder.append("<mapping class=\""+entity.getName()+"\" />");
		builder.append("</session-factory>");
		builder.append("</hibernate-configuration>");
		return builder.toString();
	}
	
	private Conf() {}
	
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
	public String getDialect() {
		return dialect;
	}
	public Class<?> getEntity() {
		return entity;
	}
	public String getRedis_ip() {
		return redis_ip;
	}
	public String getRedis_port() {
		return redis_port;
	}
	public String getShiro_conf() {
		return shiro_conf;
	}
	public String getLogin_url() {
		return login_url;
	}
	public String getUnauthorized_url() {
		return unauthorized_url;
	}
	public String getAnon() {
		return anon;
	}
	public String getAuthc() {
		return authc;
	}
	public String getMax_session() {
		return max_session;
	}
	public String getSession_timeout() {
		return session_timeout;
	}
}

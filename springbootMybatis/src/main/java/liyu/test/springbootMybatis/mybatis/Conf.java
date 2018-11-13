package liyu.test.springbootMybatis.mybatis;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Conf{
	public final static Logger logger = LoggerFactory.getLogger(Conf.class);   
	private static Conf instance;
	private Properties conf;
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
		
		try (InputStream in = Conf.class.getResourceAsStream("/application-"+profile+".properties");) {
			conf.clear();
			conf.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    try {
			instance = new Conf();			
			instance.conf = conf;
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
	
	public String getProperty(String confName) {
		return instance.conf.getProperty(confName, "");
	}
	
	public String toHibernateConfigString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append("<!DOCTYPE hibernate-configuration PUBLIC\r\n" + 
				"		          \"-//Hibernate/Hibernate Configuration DTD 3.0//EN\"\r\n" + 
				"		          \"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd\">");
		
		builder.append("<hibernate-configuration>");
		builder.append("<session-factory>");
		builder.append("<property name=\"connection.url\">"+conf.getProperty("spring.datasource.url").replace("&", "&amp;")+"</property>");
		builder.append("<property name=\"connection.username\">"+conf.getProperty("spring.datasource.username")+"</property>");
		builder.append("<property name=\"connection.password\">"+conf.getProperty("spring.datasource.password")+"</property>");
		builder.append("<property name=\"connection.driver_class\">"+conf.getProperty("spring.datasource.driver-class-name")+"</property>");
		builder.append("<property name=\"dialect\">org.hibernate.dialect.MySQLDialect</property>");
		builder.append("<property name=\"show_sql\">false</property>");
		builder.append("<property name=\"format_sql\">false</property>");
		builder.append("<mapping class=\""+entity.getName()+"\" />");
		builder.append("</session-factory>");
		builder.append("</hibernate-configuration>");
		return builder.toString();
	}

	public Class<?> getEntity() {
		return this.entity;
	}
}

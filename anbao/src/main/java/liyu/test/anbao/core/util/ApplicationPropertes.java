package liyu.test.anbao.core.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationPropertes {
	public final static Logger logger = LoggerFactory.getLogger(ApplicationPropertes.class);  
	private static ApplicationPropertes instance;
	static {		
		Properties conf = new Properties();
		String profile = null;
		
		try (InputStream in = ApplicationPropertes.class.getResourceAsStream("/application.properties");) {
			conf.load(in);
			profile = conf.getProperty("spring.profiles.active");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try (InputStream in = ApplicationPropertes.class.getResourceAsStream("/application-"+profile+".properties");) {
			conf.load(in);
			instance = new ApplicationPropertes();
			instance.login_url = conf.getProperty("login_url");
			instance.anon = conf.getProperty("anon");
			instance.session_seconds = conf.getProperty("session_seconds","1800");
			instance.redis_ip = conf.getProperty("redis_ip");
			instance.redis_port = conf.getProperty("redis_port");
			instance.only_one_user_login = conf.getProperty("only_one_user_login","true");
		} catch (Exception ex) {
			logger.error(ApplicationPropertes.class.getName(), ex);
		}
	}
	
	public static ApplicationPropertes instance() {
		return instance;
	}
	
	private String login_url;
	private String anon;
	private String session_seconds;
	private String redis_ip;
	private String redis_port;
	private String only_one_user_login;

	public String getLogin_url() {
		return login_url;
	}

	public void setLogin_url(String login_url) {
		this.login_url = login_url;
	}

	public String getAnon() {
		return anon;
	}

	public void setAnon(String anon) {
		this.anon = anon;
	}

	public String[] getAnonSet() {
		if(StringUtil.isNotBlank(this.anon))
			return this.anon.split(",");
		else
			return new String[] {};
	}

	public String getSession_seconds() {
		return session_seconds;
	}

	public void setSession_seconds(String session_seconds) {
		this.session_seconds = session_seconds;
	}

	public String getRedis_ip() {
		return redis_ip;
	}

	public void setRedis_ip(String redis_ip) {
		this.redis_ip = redis_ip;
	}

	public String getRedis_port() {
		return redis_port;
	}

	public void setRedis_port(String redis_port) {
		this.redis_port = redis_port;
	}

	public String getOnly_one_user_login() {
		return only_one_user_login;
	}

	public void setOnly_one_user_login(String only_one_user_login) {
		this.only_one_user_login = only_one_user_login;
	}
}

package activity523;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
/**
 * 建表、删表
 */
public class CreateTable {
	private static String url;
	private static String driver;
	private static String username;
	private static String password;

	public static void main(String[] args) {
		init();
		dropTable();
		createTable();
	}
	
	private static void init() {
		Resource prop = new ClassPathResource("jdbc.mysql.properties");
		InputStream in = null;
		try {
			in = prop.getInputStream();
			Properties properties = new Properties();
			properties.load(in);
		
			driver=properties.getProperty("driver");
			url=properties.getProperty("url");
			username=properties.getProperty("username");
			password=properties.getProperty("password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void dropTable() {
		try {
			Class.forName(driver).newInstance();
			Connection conn = (Connection) DriverManager.getConnection(url, username, password);
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
			runner.runScript(Resources.getResourceAsReader("liyu/test/activity/init/drop1.sql"));
			runner.runScript(Resources.getResourceAsReader("liyu/test/activity/init/drop2.sql"));
			runner.runScript(Resources.getResourceAsReader("liyu/test/activity/init/drop3.sql"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createTable(){
		
		ProcessEngineConfiguration conf = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration(); 
		conf.setJdbcDriver(driver);
		conf.setJdbcUrl(url);
		conf.setJdbcUsername(username);
		conf.setJdbcPassword(password); 
		conf.setDatabaseSchemaUpdate("drop-create");
		
		@SuppressWarnings("unused")
		ProcessEngine processEngine = conf.buildProcessEngine();
	}
}

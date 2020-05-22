package liyu.test.activity.init;

import java.sql.Connection;
import java.sql.DriverManager;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
/**
 * 
 * @ClassName: CreateTable 
 * @Description: TODO
 * @author: Administrator
 * @date: 2017年9月20日 上午11:08:01
 */
public class CreateTable {
	private static String url = "jdbc:mysql://localhost:3306/activity5";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String username = "root";
	private static String password = "liyuff";

	public static void main(String[] args) {
//		dropTable();
		createTable();
	}
	/**
	 * no use
	 * @Title: dropTable 
	 * @Description: TODO
	 * @return: void
	 */
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

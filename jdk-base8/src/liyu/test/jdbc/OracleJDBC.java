package liyu.test.jdbc;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OracleJDBC {
	static String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
	static String user = "scott";
	static String password = "liyuff";
	public static void main(String[] args) {
		URLClassLoader loader = null;
		Connection conn = null;
		try {
			URL resource = Object.class.getResource("/liyu/test/jdbc/ojdbc6.jar");
			File file = new File(resource.getFile());
			
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> es = jarFile.entries();  
			while (es.hasMoreElements()) { 
				JarEntry jarEntry = (JarEntry) es.nextElement();  
				String name = jarEntry.getName();
				if(name.endsWith("OracleDriver.class"))
					System.out.println("jar包的class: "+name);
			} 
			jarFile.close();
			
			loader = new URLClassLoader(new URL[]{resource});  
			Class<?> loadClass = loader.loadClass("oracle.jdbc.OracleDriver");
			Driver driver = (Driver)loadClass.newInstance();
				
			Properties info = new Properties();  
			info.setProperty("user", user);  
		    info.setProperty("password", password); 
			conn = driver.connect(jdbcUrl, info);
			
			Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("select * from dept");
            
            ResultSetMetaData rsmd = set.getMetaData(); 
            
            String col = rsmd.getColumnName(1);
            
            System.out.println(col);
            
            
            int type = rsmd.getColumnType(1);
            System.out.println(type);//12 java.sql.Types VARCHAR
            
            System.out.println(rsmd.getColumnTypeName(1));
            
            while(set.next()) {
            	Object object = set.getObject(1);
            	System.out.println(object);
            }
            
            set.close();
            statement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				loader.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

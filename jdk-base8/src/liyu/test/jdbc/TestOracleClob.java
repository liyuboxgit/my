package liyu.test.jdbc;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TestOracleClob {
	static String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
	static String user = "scott";
	static String password = "liyuff";
	static Connection getConn() {
		Connection conn = null;
		/* 原始方式获取conn
		 * try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
			
		URLClassLoader loader = null;
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
			
			return conn;
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	static void closeConn(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 建表、插入数据、查询数据、删表
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		long id = new Date().getTime();
		//建表
		conn = getConn();
		try {
			String sql = "create table testlog(id number,cont clob)";
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(conn);
		}
		//插入数据
		conn = getConn();
		try {
			///1.插入空对象
			String sql="insert into testlog(id,cont) values(?,empty_clob())";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
			
			///2.查出来更新
			sql = "select cont from testlog where id = ? for update";
			statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
			Clob clob = null;
			if(rs.next()){
				clob = rs.getClob(1);
			}
			String str  = "这是我要插入的数据";
			clob.setString(1,str);
			rs.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(conn);
		}
		
		//取出数据
		conn = getConn();
		try {
			String sql = "select * from testlog where id="+id;
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			int count=0;
			if(rs.next()){
				System.out.print(++count);
				Clob c = rs.getClob("cont");
				String value = c.getSubString(1,(int)c.length());
				System.out.println(id+":"+value);
			}
			rs.close();
			statement.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(conn);
		}
		//删表
		conn = getConn();
		try {
			String sql = "drop table testlog";
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			statement.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(conn);
		}
	}
}

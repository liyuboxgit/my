package liyu.test.hive_v122;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveCreateDb {
   private static String driverName = "org.apache.hive.jdbc.HiveDriver";
   
   public static void main(String[] args) {
	   try {
		   Class.forName(driverName);			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	    
	   Connection con = null;
	   try {	
	    	con = DriverManager.getConnection("jdbc:hive2://desk:10000/default", "", "");
	    	Statement stmt = con.createStatement();
	    	stmt.executeQuery("CREATE DATABASE userdb");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con!=null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
      
      
   }
}
package liyu.test.boot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJDBC {
	static String jdbcUrl = "jdbc:oracle:thin:@10.0.0.249:1521:orcl";
	static String user = "fpfx";
	static String password = "vD_p_90";
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            System.out.println(conn);
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("select * from FPFX_DM_BIZ");
            
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
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	try {
				conn.close();
			} catch (SQLException e) {}
        }
	}
}

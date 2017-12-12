package liyu.test.mybatis.codec;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @ClassName: Codec 
 * @Description: 生成mayatis的mapper配置文件和实体类
 * @author: liyu
 * @date: 2017年12月12日 下午4:52:18
 */
public abstract class Codec {
	
	public static void main(String[] args) {
		long timestampt = System.currentTimeMillis();
			
		DataSource dataSource = new DataSource(
				"jdbc:mysql://localhost:3306/mybatis",
				"com.mysql.jdbc.Driver",
				"root","root");
		
		try {
			File path = new File(System.getProperty("user.home") + File.separator + "codec_" + timestampt);
			
			createDir(path,Path.DIR_BEAN.getValue());
			createDir(path,Path.DIR_MAPPER.getValue());
			createDir(path,Path.DIR_DAO.getValue());
			
			Connection conn = dataSource.getConn();
			String sql = 
				"select "
					+ "COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY,EXTRA "
					+ "from information_schema.columns "
					+ "where table_schema=? and table_name=?";
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, Path.DB.getValue());
			preparedStatement.setString(2, Path.TABLE.getValue());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			StringBuffer javaBean = new StringBuffer();

			javaBean.append("package "+Path.DIR_BEAN.getValue()+";\n");
			javaBean.append("public class "+Path.BEAN.getValue()+"{"+"\n");
			while(resultSet.next()){
				String field = genFieldStr(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
				if(field == null){
					System.err.println("类型错误！");
					break;
				}
				javaBean.append("    "+field+";\n");
			}

			javaBean.append("}");
			
			byte[] bytes = javaBean.toString().getBytes(Charset.defaultCharset());
			File file = new File(path,Path.DIR_BEAN.getValue().replace(".", File.separator)+File.separator+Path.BEAN.getValue()+".java");
			file.createNewFile();
			Files.write(file.toPath(), bytes);
				
			DataSource.close(resultSet,preparedStatement,conn);
			
			java.awt.Desktop.getDesktop().open(path);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static String genFieldStr(String string, String type, String common) {
		if(string.startsWith("_")){
			throw new RuntimeException("数据库column不能以下划线开头！");
		}else if(string.indexOf("_")>-1){
			string = string.substring(0, string.indexOf("_")) 
					+ string.substring(string.indexOf("_")+1, string.indexOf("_")+2).toUpperCase()
					+ string.substring(string.indexOf("_")+2);
		}
		
		switch (type) {
			case "bigint":
				return "/*"+common+"*/\n"+"    private Integer "+string;
			case "int":
				return "/*"+common+"*/\n"+"    private Integer "+string;	
			case "varchar":
				return "/*"+common+"*/\n"+"    private String "+string;
			case "datetime":
				return "/*"+common+"*/\n"+"    private java.util.Date "+string;
			default:
				break;
		}
		
		return null;
	}

	private static void createDir(File path, String value) {
		mkDir(new File(path,value.replace(".", File.separator)));
	}
	
	private static void mkDir(File file) {  
        if (file.getParentFile().exists()) {  
            file.mkdir();  
        } else {  
            mkDir(file.getParentFile());  
            file.mkdir();    
        }  
    }  
}

enum Path{
	DIR_BEAN("liyu.test.mybatis.model"),DIR_MAPPER("liyu.test.mybatis.mapper"),DIR_DAO("liyu.test.mybatis.mapper"),
	DB("mybatis"),TABLE("t_person"),BEAN("Person");
	
	private String value;
	private Path(String value) {
		this.value = value;
	}
	public String getValue(){
		return this.value;
	}
}

class DataSource{
	private String url,driver,username,password;
	private javax.sql.DataSource dataSource;
	
	public DataSource(String url, String driver, String username, String password) {
		super();
		this.url = url;
		this.driver = driver;
		this.username = username;
		this.password = password;
	}
	public DataSource(javax.sql.DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	public Connection getConn() throws SQLException, ClassNotFoundException{
		if(this.dataSource!=null){
			return dataSource.getConnection();
		}else{
			Class.forName(this.driver);
			return DriverManager.getConnection(this.url, this.username, this.password);
		}
	}
	public static void close(AutoCloseable...closeable){
		if(closeable!=null){
			try {
				for (AutoCloseable autoCloseable : closeable) {
					autoCloseable.close();
				}
			} catch (Exception e) {
				
			}
		}
	}
}

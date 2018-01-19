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
import java.util.ArrayList;

/**
 * 
 * @ClassName: Codec mysql
 * @Description: 生成mayatis的mapper配置文件和实体类
 * 		使用时拷贝注释代码BaseMapper code，生成项目的BaseMapper
 * 		根据项目需要配置enmu Path，有任何问题请联系309147857@qq.com
 * 		mapper配置文件中无分页参数，这个在后续添加，建议不使用分页插件，因为所有的分页插件都是非官方支持，而mybatis的缓存非常好，
 * 		不能保证第三方分页插件和官方mybatis缓存的兼容性。
 * 		除了生成实体bean，没有生成其他po和vo，使用时请根据需要自行添加。
 * 		生成之mapper配置文件中有<resultMap/>,但其内容为空，配置这一项的原因是希望用户能使用mybatis的高级映射之多对一单向关联，
 * 		为什么要使用多对一关联，而不使用其他呢，因为其比较简单，使用最多，能降低sql join的代码。
 * 		那为什么不使用全部高级映射和双向关联呢，因为学习门槛高，大部分colder没用过mybatis之高级映射。
 * 		<resultMap type="liyu.test.springboot.model.User" id="User">
 *	    	<association property="role" column="role_id" 
 *	    		select="liyu.test.springboot.mapper.role.RoleMapper.get">
 *	    	</association>
 *   	</resultMap>
 * @author: liyu
 * @date: 2017年12月12日 下午4:52:18
 */
public class MysqlCodec {
	/**
	 * 首先配置，Path枚举分三部分：
	 * 1，那个库、那个表、那个类；
	 * 2，实体类位置、mapperXML位置、mapper接口位置；
	 * 3，jdbc连接的配置;
	 * 4，BaseMapper，最后将附上BaseMapper的代码。
	 */
	enum Path{
		DB_SCHEMA	("mybatis"),
		DB_TABLE	("t_user"),
		DB_BEAN		("User"),
		
		DIR_BEAN	("liyu.test.mybatis.model"),
		DIR_MAPPER	("liyu.test.mybatis.mapper"),
		DIR_DAO		("liyu.test.mybatis.mapper"),
		
		JDBC_URL	("jdbc:mysql://192.168.0.63:3306/mybatis"),
		JDBC_DRIVER	("com.mysql.jdbc.Driver"),
		JDBC_USER	("root"),
		JDBC_PSWORD	("root"),
		
		BASE_MAPPER ("liyu.test.mybatis.BaseMapper");
		
		private String value;Path(String value) {this.value = value;}
	}
	/**文件分割符*/
	public static final String sp = File.separator;
	
	private void run(){
		//1.当前时间
		long timestampt = System.currentTimeMillis();
		//2.dataSource	
		DataSource dataSource = new DataSource(
				Path.JDBC_URL.value,
				Path.JDBC_DRIVER.value,
				Path.JDBC_USER.value,
				Path.JDBC_PSWORD.value
		);
		
		try {
			//3，指定代码生成的根路径，并逐级创建
			File path = new File(System.getProperty("user.home") + sp + "codec_" + timestampt);
			createDir(path,Path.DIR_BEAN.value);
			createDir(path,Path.DIR_MAPPER.value);
			createDir(path,Path.DIR_DAO.value);
			
			Connection conn = dataSource.getConn();
			String sql = 
				"select "
					+ "COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY,EXTRA "
					+ "from information_schema.columns "
					+ "where table_schema=? and table_name=?";
			
			String sql_ = "select TABLE_NAME,TABLE_COMMENT from information_schema.tables  where table_schema = ? "
					+ "and table_name = ?";
			
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, Path.DB_SCHEMA.value);
			preparedStatement.setString(2, Path.DB_TABLE.value);
			
			PreparedStatement preparedStatement_ = conn.prepareStatement(sql_);
			preparedStatement_.setString(1, Path.DB_SCHEMA.value);
			preparedStatement_.setString(2, Path.DB_TABLE.value);
			ResultSet resultSet_ = preparedStatement_.executeQuery();
			
			String tableCommen = "";
			while(resultSet_.next()){
				tableCommen = resultSet_.getString(2);
			}
			
			
			//4，执行查询返回jdbc之ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			
			//5,开始拼接
			StringBuffer 
				javaBean = new StringBuffer(),
				javaBeanGetsetM  = new StringBuffer(),
				mapperXml  = new StringBuffer(),
				daoBean = new StringBuffer();
			
			String id = null;
			ArrayLists<String> columnsExceptId = new ArrayLists<String>();
				
			
			javaBean.append("package "+Path.DIR_BEAN.value+";\n");
			javaBean.append("/**\n*"+tableCommen+"("+Path.DB_TABLE.value+")\n**/\npublic class "+Path.DB_BEAN.value+" implements java.io.Serializable{"+"\n");
			javaBean.append("\n    private static final long serialVersionUID = 1L;\n");
			
			mapperXml.append(
			"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
				"<!DOCTYPE mapper\n"+
				  "  PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n"+
				  "  \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n"+
				"<mapper namespace=\""+Path.DIR_MAPPER.value+"."+Path.DB_BEAN.value+"Mapper\">\n");
			
			while(resultSet.next()){
				javaBean.append("    /**"+resultSet.getString(3)+"*/\n"+"    private "+typeConvert(resultSet.getString(2))+javaNaming(resultSet.getString(1))+";\n");
				getsetMothod(resultSet.getString(1),resultSet.getString(2),javaBeanGetsetM);
				
				if(!resultSet.getString(4).equals(""))
					id = resultSet.getString(1);
				else{
					columnsExceptId.add(resultSet.getString(1));
				}
			}
			javaBean.append(javaBeanGetsetM);
			javaBean.append("\n}");
			
			mapperXml.append("    <resultMap type=\""+Path.DIR_BEAN.value+"."+Path.DB_BEAN.value+"\" id=\"BaseResultMap\"></resultMap>");
			mapperXml.append("\n    <sql id=\"cols\">\n        "+columnsExceptId.joinc()+id+" as "+javaNaming(id)+"\n    </sql>");
			mapperXml.append("\n    <sql id=\"whereSql\">\n        <where>");
			mapperXml.append("\n            <if test=\""+javaNaming(id)+" != null\">\n                and "+id+"=#{"+javaNaming(id)+"}\n            </if>");
			for(String str:columnsExceptId)
			mapperXml.append("\n            <if test=\""+javaNaming(str)+" != null\">\n                and "+str+"=#{"+javaNaming(str)+"}\n            </if>");
			mapperXml.append("\n        </where>");
			mapperXml.append("\n    </sql>");
			mapperXml.append("\n    <insert id=\"create\">\n        insert into "+Path.DB_TABLE.value+"("+columnsExceptId.join()+") values("+columnsExceptId.joinv()+")\n    </insert>");
			mapperXml.append("\n    <update id=\"merge\">\n        update "+Path.DB_TABLE.value+" set "+columnsExceptId.joinu()+" where "+id+"=#{"+javaNaming(id)+"}\n    </update>");
			mapperXml.append("\n    <update id=\"updateColumn\">\n        update ${tableName} set ${columnName}=#{value} where "+id+" = #{primaryKey}\n    </update>");
			mapperXml.append("\n    <delete id=\"delete\">\n        delete from "+Path.DB_TABLE.value+" where "+id+"=#{"+javaNaming(id)+"}\n    </delete>");
			mapperXml.append("\n    <delete id=\"deleteBatch\" parameterType=\"java.util.List\">\n        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\";end;\" separator=\";\">\n            delete from "+Path.DB_TABLE.value+" where "+id+"=#{item."+javaNaming(id)+"}\n        </foreach>\n    </delete>");
			mapperXml.append("\n    <select id=\"findOne\" resultMap=\"BaseResultMap\">\n        select <include refid=\"cols\"/> from "+Path.DB_TABLE.value+" where "+id+"=#{"+javaNaming(id)+"}\n    </select>");
			mapperXml.append("\n    <select id=\"findList\" resultMap=\"BaseResultMap\">\n        select <include refid=\"cols\"/> from "+Path.DB_TABLE.value+" <include refid=\"whereSql\"/>\n    </select>");
			mapperXml.append("\n    <select id=\"findCount\" resultType=\"java.lang.Integer\">\n        select count(1) from "+Path.DB_TABLE.value+" <include refid=\"whereSql\"/>\n    </select>");
			mapperXml.append("\n</mapper>");
			
			daoBean.append("package "+Path.DIR_DAO.value+";\n");
			daoBean.append("import org.springframework.stereotype.Repository;\n");
			daoBean.append("import "+Path.DIR_BEAN.value+"."+Path.DB_BEAN.value+";\n");
			daoBean.append("@Repository\n");
			daoBean.append("public interface "+Path.DB_BEAN.value+"Mapper extends BaseMapper<"+Path.DB_BEAN.value+">{"+"\n");
			daoBean.append("}");
			
			//6，写入文件
			byte[] bytes = javaBean.toString().getBytes(Charset.defaultCharset());
			File file = new File(path,Path.DIR_BEAN.value.replace(".", sp)+sp+Path.DB_BEAN.value+".java");
			file.createNewFile();
			Files.write(file.toPath(), bytes);

			byte[] xmlbytes = mapperXml.toString().getBytes(Charset.defaultCharset());
			File xmlfile = new File(path,Path.DIR_MAPPER.value.replace(".", sp)+sp+Path.DB_BEAN.value+"Mapper.xml");
			xmlfile.createNewFile();
			Files.write(xmlfile.toPath(), xmlbytes);
			
			byte[] daobytes = daoBean.toString().getBytes(Charset.defaultCharset());
			File daofile = new File(path,Path.DIR_MAPPER.value.replace(".", sp)+sp+Path.DB_BEAN.value+"Mapper.java");
			daofile.createNewFile();
			Files.write(daofile.toPath(), daobytes);
			
			//7,关闭资源	
			dataSource.close(resultSet,preparedStatement,resultSet_,preparedStatement_,conn);
			//8，打开窗口
			java.awt.Desktop.getDesktop().open(path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void main(String[] args) {
		new MysqlCodec().run();
	}

	private static void getsetMothod(String string, String type, StringBuffer getsetM) {
		string = javaNaming(string);
		String str = string.substring(0, 1).toUpperCase() + string.substring(1);
		String javaType = typeConvert(type);
		
		getsetM.append("\n    public void set"+str+"("+javaType+string+"){\n        this."+string+"="+string+";\n    }");
		getsetM.append("\n    public "+javaType+"get"+str+"(){\n        return this."+string+";\n    }");
	}
	
	public static String javaNaming(String column){
		column = column.toLowerCase();
		if(column.startsWith("_")){
			throw new RuntimeException("数据库column不能以下划线开头！");
		}else if(column.indexOf("_")>-1){
			String[] fields = column.split("_");
	        StringBuilder sbuilder = new StringBuilder(fields[0]);
	        for (int i = 1; i < fields.length; i++) {
	            char[] cs=fields[i].toCharArray();
	            cs[0]-=32;
	            sbuilder.append(String.valueOf(cs));
	        }
	        return sbuilder.toString();
		}
		return column;
	}
	
	private static String typeConvert(String dbType){
		switch (dbType) {
			case "bigint":
				return "Integer ";
			case "int":
				return "Integer ";	
			case "varchar":
				return "String ";
			case "datetime":
				return "java.util.Date ";
			case "decimal":
				return "java.math.BigDecimal ";	
			default:
				break;
		}
		throw new RuntimeException("类型缺失或错误！");
	}
	
	private static void createDir(File path, String value) {
		mkDir(new File(path,value.replace(".", sp)));
	}
	
	private static void mkDir(File file) {  
        if (file.getParentFile().exists()) file.mkdir();  
        else{mkDir(file.getParentFile()); file.mkdir();}  
    }  
	
	
	class ArrayLists<E> extends ArrayList<E>{
		private static final long serialVersionUID = 1L;

		public String join(){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<this.size();i++){
				sb.append((i==this.size()-1)?this.get(i):(this.get(i)+","));
			}
			return sb.toString();
		}
		
		public String joinv(){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<this.size();i++){
				sb.append((i==this.size()-1)?val(this.get(i)):val(this.get(i))+",");
			}
			return sb.toString();
		}
		
		public String joinu(){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<this.size();i++){
				if(i==this.size()-1)
					sb.append(this.get(i)+"=#{"+javaNaming((String)this.get(i))+"}");
				else
					sb.append(this.get(i)+"=#{"+javaNaming((String)this.get(i))+"},");
			}
			return sb.toString();
		}
		
		public String joinc(){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<this.size();i++){
				sb.append(this.get(i)+" as "+javaNaming((String)this.get(i))+",");
			}
			return sb.toString();
		}
		
		private String val(E e){
			return "#{"+javaNaming((String)e)+"}";
		}

	}
	
	/**
	 * 
	 * @ClassName: DataSource 
	 * @Description: 
	 * @author: liyu
	 * @date: 2017年12月13日 上午10:33:53
	 */
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
		public void close(AutoCloseable...closeable){
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
}



/*
 * ================================BaseMapper code=====================================
public interface BaseMapper <T>{
	public void create(T t);
	public void merge(T t);
	public boolean updateColumn(UpdateColumnWapper updateColumnWapper);
	public void delete(T t);
	public void deleteBatch(List<T> list);
	public T findOne(T t);	
	public List<T> findList(T t);
	public Integer findCount(T t);
}
public class UpdateColumnWapper {
	private String tableName;
	private String columnName;
	private Object value;
	private Object primaryKey;
	
	public UpdateColumnWapper(String tableName, String columnName, Object value, Object primaryKey) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.primaryKey = primaryKey;
		this.value = value;
	}
	public String getTableName() {
		return tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public Object getPrimaryKey() {
		return primaryKey;
	}
	public Object getValue() {
		return value;
	}
}
=======================================================================================*/

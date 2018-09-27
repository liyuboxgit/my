package com.rthd.tinychxu.util.tools;

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

import javax.persistence.Table;

import com.rthd.framework.util.Conf;
import com.rthd.tinychxu.domain.entity.Demo;

public class MysqlCodec {
	private static String DB_SCHEMAs;
	private static String DB_TABLEs;
	private static String DB_BEANs;
	private static String DIR_BEANs;
	private static String DIR_MAPPERs;
	private static String JDBC_URLs;
	private static String JDBC_DRIVERs;
	private static String JDBC_USERs;
	private static String JDBC_PSWORDs;
	private static String JAVA_NAMEINGs;
	static {
		Conf conf = Conf.get(Demo.class);
		JDBC_URLs = conf.getUrl();
		JDBC_DRIVERs = conf.getDriver();
		JDBC_USERs = conf.getUsername();
		JDBC_PSWORDs = conf.getPassword();
		JAVA_NAMEINGs = "false";
		DB_SCHEMAs = JDBC_URLs.substring(JDBC_URLs.lastIndexOf("/")+1, JDBC_URLs.indexOf("?"));
		DB_TABLEs = conf.getEntity().getAnnotation(Table.class).name();
		DB_BEANs = conf.getEntity().getSimpleName();
		DIR_BEANs = conf.getEntity().getPackage().getName();
		DIR_MAPPERs = DIR_BEANs;
	}
	
	public static void main(String[] args) {
		new MysqlCodec().run();
	}
	
	enum Path {
		
		DB_SCHEMA(DB_SCHEMAs), DB_TABLE(DB_TABLEs), DB_BEAN(DB_BEANs),

		DIR_BEAN(DIR_BEANs), DIR_MAPPER(DIR_MAPPERs),

		JDBC_URL(JDBC_URLs), JDBC_DRIVER(JDBC_DRIVERs), JDBC_USER(JDBC_USERs), JDBC_PSWORD(JDBC_PSWORDs),

		JAVA_NAMEING(JAVA_NAMEINGs);
		
		/*DB_SCHEMA("mybatis"), DB_TABLE("invoice7key"), DB_BEAN("Invoice7key"),

		DIR_BEAN("com.rthd.sb2_0.domain"), DIR_MAPPER("com.rthd.sb2_0.domain"),

		JDBC_URL("jdbc:mysql://127.0.0.1:3306/mybatis"), JDBC_DRIVER("com.mysql.jdbc.Driver"), JDBC_USER("root"), JDBC_PSWORD("root"),

		JAVA_NAMEING("false");*/

		private String value;

		Path(String value) {
			this.value = value;
		}
	}

	public static final String sp = File.separator;

	private void run() {

		long timestampt = System.currentTimeMillis();

		DataSource dataSource = new DataSource(Path.JDBC_URL.value, Path.JDBC_DRIVER.value, Path.JDBC_USER.value, Path.JDBC_PSWORD.value);

		try {

			File path = new File(System.getProperty("user.home") + sp + "codec_" + timestampt);
			createDir(path, Path.DIR_BEAN.value);
			createDir(path, Path.DIR_MAPPER.value);

			Connection conn = dataSource.getConn();
			String sql = "select " + 
					"COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY,EXTRA "
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
			while (resultSet_.next()) {
				tableCommen = resultSet_.getString(2);
			}

			ResultSet resultSet = preparedStatement.executeQuery();

			StringBuffer javaBean = new StringBuffer(), javaBeanGetsetM = new StringBuffer(),
					mapperXml = new StringBuffer();

			String id = null;
			ArrayLists<String> columnsExceptId = new ArrayLists<String>();

			javaBean.append("package " + Path.DIR_BEAN.value + ";\n");
			javaBean.append("/**\n*" + tableCommen + "(" + Path.DB_TABLE.value + ")\n**/\npublic class " + Path.DB_BEAN.value + " implements java.io.Serializable{" + "\n");
			javaBean.append("\n    private static final long serialVersionUID = 1L;\n");

			mapperXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" 
					+ "<!DOCTYPE mapper\n"
					+ "  PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n"
					+ "  \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" 
					+ "<mapper namespace=\"" + Path.DIR_BEAN.value + "." + Path.DB_BEAN.value + "\">\n");

			while (resultSet.next()) {
				javaBean.append("    /**" + resultSet.getString(3) + "*/\n" + "    private " + typeConvert(resultSet.getString(2)) + javaNaming(resultSet.getString(1)) + ";\n");
				getsetMothod(resultSet.getString(1), resultSet.getString(2), javaBeanGetsetM);

				if (resultSet.getString(4).equals("PRI"))
					id = resultSet.getString(1);
				else {
					columnsExceptId.add(resultSet.getString(1));
				}
			}
			javaBean.append(javaBeanGetsetM);
			javaBean.append("\n}");

			mapperXml.append("    <resultMap type=\"" + Path.DIR_BEAN.value + "." + Path.DB_BEAN.value + "\" id=\"BaseResultMap\"></resultMap>");
			mapperXml.append("\n    <sql id=\"cols\">\n        " + columnsExceptId.joinc() + id + "\n    </sql>");
			//mapperXml.append("\n    <sql id=\"cols\">\n        " + columnsExceptId.joinc() + id + " as " + javaNaming(id) + "\n    </sql>");
			mapperXml.append("\n    <sql id=\"whereSql\">\n        <where>");
			mapperXml.append("\n            <if test=\"" + javaNaming(id) + " != null\">\n                and " + id + "=#{" + javaNaming(id) + "}\n            </if>");
			for (String str : columnsExceptId) {
				if(str.equals("version"))
					continue;
				mapperXml.append("\n            <if test=\"" + javaNaming(str) + " != null\">\n                and " + str + "=#{" + javaNaming(str) + "}\n            </if>");
			}
			mapperXml.append("\n        </where>");
			mapperXml.append("\n    </sql>");
			mapperXml.append("\n    <sql id=\"pageSql\">\n        <if test=\"pageNo!=null and pageSize!=null\">\n            " + "limit #{pageNo},#{pageSize}"+"\n        </if>\n    </sql>");
			mapperXml.append("\n    <insert id=\"insert\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n        insert into " + Path.DB_TABLE.value + "(" + columnsExceptId.join() + ") values(" + columnsExceptId.joinv() + ")\n    </insert>");
			//mapperXml.append("\n    <update id=\"merge\">\n        update " + Path.DB_TABLE.value + " set " + columnsExceptId.joinu() + " where " + id + "=#{" + javaNaming(id) + "}\n    </update>");
			mapperXml.append("\n    <update id=\"merge\">\n        update " + Path.DB_TABLE.value + " set " + columnsExceptId.joinu() + ",version=version+1,update_time=now() where " + id + "=#{" + javaNaming(id) + "} and version=#{version}\n    </update>");
			//mapperXml.append("\n    <update id=\"dynamicUpdate\">\n        update ${tableName} set ${columnName}=#{value} where " + id + " = #{primaryKey}\n    </update>");
			mapperXml.append("\n    <update id=\"dynamicUpdate\">\n        update ${tableName} set ${columnName}=#{value},version=version+1,update_time=now() where " + id + " = #{primaryKey} and version=#{version}\n    </update>");
			//mapperXml.append("\n    <delete id=\"delete\">\n        delete from " + Path.DB_TABLE.value + " where " + id + "=#{" + javaNaming(id) + "}\n    </delete>");
			mapperXml.append("\n    <delete id=\"delete\">\n        delete from " + Path.DB_TABLE.value + " where " + id + "=#{" + javaNaming(id) + "} and version=#{version}\n    </delete>");
			//mapperXml.append("\n    <delete id=\"deleteBatch\" parameterType=\"java.util.List\">\n        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\";end;\" separator=\";\">\n            delete from " + Path.DB_TABLE.value + " where " + id + "=#{item." + javaNaming(id) + "}\n        </foreach>\n    </delete>");
			mapperXml.append("\n    <select id=\"findone\" resultMap=\"BaseResultMap\">\n        select <include refid=\"cols\"/> from " + Path.DB_TABLE.value + " where " + id + "=#{" + javaNaming(id) + "} \n    </select>");
			mapperXml.append("\n    <select id=\"findlist\" resultMap=\"BaseResultMap\">\n        select <include refid=\"cols\"/> from " + Path.DB_TABLE.value + " <include refid=\"whereSql\"/> <include refid=\"pageSql\"/>\n    </select>");
			mapperXml.append("\n    <select id=\"findcount\" resultType=\"java.lang.Long\">\n        select count(1) from " + Path.DB_TABLE.value + " <include refid=\"whereSql\"/>\n    </select>");
			mapperXml.append("\n</mapper>");

			byte[] bytes = javaBean.toString().getBytes(Charset.defaultCharset());
			File file = new File(path, Path.DIR_BEAN.value.replace(".", sp) + sp + Path.DB_BEAN.value + ".java");
			file.createNewFile();
			Files.write(file.toPath(), bytes);

			byte[] xmlbytes = mapperXml.toString().getBytes(Charset.defaultCharset());
			File xmlfile = new File(path,
					Path.DIR_MAPPER.value.replace(".", sp) + sp + Path.DB_BEAN.value + "Mapper.xml");
			xmlfile.createNewFile();
			Files.write(xmlfile.toPath(), xmlbytes);

			dataSource.close(resultSet, preparedStatement, resultSet_, preparedStatement_, conn);

			java.awt.Desktop.getDesktop().open(path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void getsetMothod(String string, String type, StringBuffer getsetM) {
		string = javaNaming(string);
		String str = string.substring(0, 1).toUpperCase() + string.substring(1);
		String javaType = typeConvert(type);

		getsetM.append("\n    public void set" + str + "(" + javaType + string + "){\n        this." + string + "=" + string + ";\n    }");
		getsetM.append("\n    public " + javaType + "get" + str + "(){\n        return this." + string + ";\n    }");
	}

	public static String javaNaming(String column) {
		if (Path.JAVA_NAMEING.value.equals("true")) {
			column = column.toLowerCase();
			if (column.startsWith("_")) {
				throw new RuntimeException("数据库column不能以下划线开头！");
			} else if (column.indexOf("_") > -1) {
				String[] fields = column.split("_");
				StringBuilder sbuilder = new StringBuilder(fields[0]);
				for (int i = 1; i < fields.length; i++) {
					char[] cs = fields[i].toCharArray();
					cs[0] -= 32;
					sbuilder.append(String.valueOf(cs));
				}
				return sbuilder.toString();
			}
			return column;
		} else {
			return column;
		}
	}

	private static String typeConvert(String dbType) {
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
		case "char":
			return "Character ";
		case "longtext":
			return "String";
		default:
			break;
		}
		throw new RuntimeException("类型缺失或错误！");
	}

	private static void createDir(File path, String value) {
		mkDir(new File(path, value.replace(".", sp)));
	}

	private static void mkDir(File file) {
		if (file.getParentFile().exists())
			file.mkdir();
		else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}

	class ArrayLists<E> extends ArrayList<E> {
		private static final long serialVersionUID = 1L;

		public String join() {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.size(); i++) {
				sb.append((i == this.size() - 1) ? this.get(i) : (this.get(i) + ","));
			}
			return sb.toString();
		}

		public String joinv() {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.size(); i++) {
				sb.append((i == this.size() - 1) ? val(this.get(i)) : val(this.get(i)) + ",");
			}
			return sb.toString();
		}

		public String joinu() {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.size(); i++) {
				if(this.get(i).equals("update_time")||this.get(i).equals("version"))
					continue;
				if (i == this.size() - 1)
					sb.append(this.get(i) + "=#{" + javaNaming((String) this.get(i)) + "}");
				else
					sb.append(this.get(i) + "=#{" + javaNaming((String) this.get(i)) + "},");
			}
			return sb.toString();
		}

		public String joinc() {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < this.size(); i++) {
				sb.append(this.get(i) + ",");
				//sb.append(this.get(i) + " as " + javaNaming((String) this.get(i)) + ",");
			}
			return sb.toString();
		}

		private String val(E e) {
			String f = (String) e;
			if(f.equals("create_time"))
				return "now()";
			if(f.equals("update_time"))
				return "now()";
			if(f.equals("version"))
				return "0";
			return "#{" + javaNaming((String) e) + "}";
		}
	}

	class DataSource {
		private String url, driver, username, password;
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

		public Connection getConn() throws SQLException, ClassNotFoundException {
			if (this.dataSource != null) {
				return dataSource.getConnection();
			} else {
				Class.forName(this.driver);
				return DriverManager.getConnection(this.url, this.username, this.password);
			}
		}

		public void close(AutoCloseable... closeable) {
			if (closeable != null) {
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

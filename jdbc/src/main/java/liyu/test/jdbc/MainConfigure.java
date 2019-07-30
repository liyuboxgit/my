package liyu.test.jdbc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@Controller
public class MainConfigure extends WebMvcConfigurerAdapter{
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/template/**").addResourceLocations("classpath:/template/");
    }
	
	ObjectMapper mapper = new ObjectMapper();
	private String driverClass = com.mysql.jdbc.Driver.class.getName();
	private String url = "jdbc:mysql://localhost:3306/product?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useAffectedRows=true";
	private String username = "root";
	private String password = "root";
	
	@Bean
	public DataSource dataSource() {
		return new MySimpleDataSource() {
			@Override
			public synchronized Connection getConnection() throws SQLException {
				Connection conn = null;
	            if(super.getPools().size() == 0) {
	            	conn = super.build(driverClass, url, username, password);
	            } else {
	                conn = super.getPools().remove(0);
	            }
	            return conn;	
			}

			@Override
			public void init() {
				for(int i=0;i<super.getMinCount();i++)
					super.getPools().add(super.build(driverClass, url, username, password));
			}

			@Override
			public synchronized void close(Connection conn) {
				if(super.getPools().size() <= super.getMaxCount()) {
					super.getPools().add(conn);
				}else {
					try {
						conn.close();
					} catch (SQLException e) {}
				}
			}
		};
	}
	
	@Bean
	public JdbcTemplate JdbcTemplate(DataSource dataSource) {
		return new DonotCloseJdbcTemplate(dataSource);
	}
	
	@Autowired
	private JdbcTemplate jt;
	
	@RequestMapping("/")
	public String sccess(ModelMap map) {
		 map.addAttribute("title","请输入正确的可执行的sql，=><button>配置</button>&nbsp;<button>查看结果</button>&nbsp;<button>清空</button>&nbsp;<button>格式化</button>&nbsp;<button>逆向</button>&nbsp;<button>《</button>&nbsp;<button>》</button>");
		 return "index";
	}
	
	@RequestMapping(path="/sql",method= {RequestMethod.POST})
	@ResponseBody
	public Ret sccess(String sql) throws JsonProcessingException {
		if(sql.trim().startsWith("drop") || sql.trim().startsWith("DROP")
			||sql.trim().startsWith("create") || sql.trim().startsWith("CREATE")) {
			jt.execute(sql);
			return Ret.success("ddl","ddl ok");
		}else if(sql.trim().startsWith("delete") || sql.trim().startsWith("DELETE")
					||sql.trim().startsWith("update") || sql.trim().startsWith("UPDATE")
					||sql.trim().startsWith("insert") || sql.trim().startsWith("INSERT")) {
			int i = jt.update(sql);
			return Ret.success("dml","dml ok and result is"+i);
		}else {			
			List<Map<String, Object>> ret = jt.queryForList(sql);
			return Ret.success("select",ret);
		}
	}
	
	@RequestMapping(path="/config",method= {RequestMethod.POST})
	@ResponseBody
	public Ret config(String url,String username,String password,int rw) {
		if(rw==1) {
			return Ret.success("",this.url+" "+this.username+" "+this.password);
		}else {			
			try {
				Class.forName(driverClass);
				Connection conn = DriverManager.getConnection(url, username, password);
				this.url = url;
				this.username = username;
				this.password = password;
				conn.close();
				((MySimpleDataSource)jt.getDataSource()).distroy();
				return Ret.success("","config success");
			}catch(Exception e){
				e.printStackTrace();
				return Ret.fail("config faild:"+e.getMessage());
			}
		}
	}
	
	@RequestMapping(path="/export")
	@ResponseBody
	public void export(String name,HttpServletResponse response) throws Exception{
		Connection c = this.jt.getDataSource().getConnection();
		Statement s = null;
		ResultSet r = null;
		try {
			StringBuffer sb = new StringBuffer();
			
			List<Map<String,Object>> list = this.jt.queryForList("show create table "+name);
			String ct = (String) list.get(0).get("Create Table");
			sb.append(ct.replace("\n", ""));
			sb.append(";\n\n\n");

			
			s = c.createStatement();
			r = s.executeQuery("select * from "+name);
			
			ResultSetMetaData data = r.getMetaData();
			int count = data.getColumnCount();
			String[] l = new String[count];
			for(int i=0;i<count;i++) {
				l[i] = data.getColumnLabel(i+1);
			}
			
			while(r.next()) {
				InsertSqlBean bean = new InsertSqlBean();
				bean.setSize(count);
				bean.setLabels(l);
				bean.setName(name);
				
				Object[] values = new Object[bean.getSize()];
				for(int i=0;i<count;i++) {
					values[i] = r.getObject(data.getColumnName(i+1));
				}
				bean.setValues(values);
				sb.append(bean.sql());
				sb.append("\n");
			}
			
			response.reset();
            String resultFileName = name+".sql";
            response.setCharacterEncoding("UTF-8");  
            response.setHeader("Content-disposition", "attachment;filename=" + resultFileName);
            response.setContentType("application/plain");
              
            PrintWriter out = response.getWriter();
            
            out.print(sb.toString());
            out.flush();
            out.close();
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
            out.print(e.toString());
            out.flush();
            out.close();
		} finally {
			r.close();
			s.close();
			((MySimpleDataSource)this.jt.getDataSource()).close(c);
		}
	}
	public static final String sp = File.separator;
	@RequestMapping(path="/codec")
	@ResponseBody
	public void codec(String name,String tableName,HttpServletResponse response) throws Exception{
		Connection c = this.jt.getDataSource().getConnection();
		PreparedStatement s = null;
		PreparedStatement s_ = null;
		
		try {
			long timestampt = System.currentTimeMillis();
			File path = new File(System.getProperty("user.home") + sp + "codec_" + timestampt);
			
		
			String pak = null;
			int i = name.lastIndexOf(".");
			if(i==-1) {
				pak = "temp";
			}else {
				pak = name.substring(0,i);
			}
			
			String clz = null;
			if(i==-1) {
				clz = name;
			}else {
				clz = name.substring(i+1);
			}
			
			createDir(path, pak);
			createDir(path, pak);
			
			String sql = "select " + 
					"COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY,EXTRA "
					+ "from information_schema.columns " 
					+ "where table_schema=? and table_name=?";

			String sql_ = "select TABLE_NAME,TABLE_COMMENT from information_schema.tables  where table_schema = ? "
					+ "and table_name = ?";
			String schema = url.substring(url.lastIndexOf("/")+1, url.indexOf("?"));
			s = c.prepareStatement(sql);
			s.setString(1, schema);
			s.setString(2, tableName);
			
			s_ = c.prepareStatement(sql_);
			s_.setString(1, schema);
			s_.setString(2, tableName);
			ResultSet resultSet_ = s_.executeQuery();
			
			String tableCommen = "";
			while (resultSet_.next()) {
				tableCommen = resultSet_.getString(2);
			}
			
			ResultSet resultSet = s.executeQuery();
			
			StringBuffer javaBean = new StringBuffer(), javaBeanGetsetM = new StringBuffer(),
					mapperXml = new StringBuffer();
			
			String id = null;
			ArrayLists<String> columnsExceptId = new ArrayLists<String>();

			javaBean.append("package " + pak + ";\n");
			javaBean.append("/**\n*" + tableCommen + "(" + tableName + ")\n**/\npublic class " + clz + " implements java.io.Serializable{" + "\n");
			javaBean.append("\n    private static final long serialVersionUID = 1L;\n");

			mapperXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" 
					+ "<!DOCTYPE mapper\n"
					+ "  PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n"
					+ "  \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" 
					+ "<mapper namespace=\"" + pak + "." + clz + "\">\n");
			
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
			
			mapperXml.append("    <resultMap type=\"" + pak + "." + clz + "\" id=\"BaseResultMap\"></resultMap>");
			mapperXml.append("\n    <sql id=\"cols\">\n        " + columnsExceptId.joinc() + id + "\n    </sql>");
			mapperXml.append("\n    <sql id=\"whereSql\">\n        <where>");
			mapperXml.append("\n            <if test=\"" + javaNaming(id) + " != null\">\n                and " + id + "=#{" + javaNaming(id) + "}\n            </if>");
			for (String str : columnsExceptId) {
				if(str.equals("versions"))
					continue;
				mapperXml.append("\n            <if test=\"" + javaNaming(str) + " != null\">\n                and " + str + "=#{" + javaNaming(str) + "}\n            </if>");
			}
			mapperXml.append("\n        </where>");
			mapperXml.append("\n    </sql>");
			mapperXml.append("\n    <sql id=\"pageSql\">\n        <if test=\"rowNum!=null and pageSize!=null\">\n            " + "limit #{rowNum},#{pageSize}"+"\n        </if>\n    </sql>");
			mapperXml.append("\n    <insert id=\"insert\" useGeneratedKeys=\"true\" keyProperty=\""+id+"\">\n        insert into " + tableName + "(" + columnsExceptId.join() + ") values(" + columnsExceptId.joinv() + ")\n    </insert>");
			mapperXml.append("\n    <update id=\"merge\">\n        update " + tableName + " set " + columnsExceptId.joinu() + ",versions=versions+1,update_time=now() where " + id + "=#{" + javaNaming(id) + "} and versions=#{versions}\n    </update>");
			mapperXml.append("\n	<update id=\"mergeSelective\">\n");
			mapperXml.append("        update "+tableName);
			mapperXml.append("\n        <set>");
			for (String str : columnsExceptId) {
				if(str.equals("version")||str.equals("create_time")||str.equals("update_time"))
					continue;
				mapperXml.append("\n            <if test=\"" + javaNaming(str) + " != null\">\n                " + javaNaming(str) + "=#{" + javaNaming(str) + "},\n            </if>");
			}
			mapperXml.append("\n            <if test=\"true\">\n                update_time=now(), version=version+1,\n            </if>");
			mapperXml.append("\n        </set>\n");
			mapperXml.append("        where id=#{" + javaNaming(id) + "} and version=#{version}");
			mapperXml.append("\n    </update>");
			//mapperXml.append("\n    <update id=\"dynamicUpdate\">\n        update ${tableName} set ${columnName}=#{value},versions=versions+1,update_time=now() where " + id + " = #{primaryKey} and versions=#{versions}\n    </update>");
			mapperXml.append("\n    <delete id=\"delete\">\n        delete from " + tableName + " where " + id + "=#{" + javaNaming(id) + "} \n    </delete>");
			mapperXml.append("\n    <select id=\"findone\" resultMap=\"BaseResultMap\">\n        select <include refid=\"cols\"/> from " + tableName + " where " + id + "=#{" + javaNaming(id) + "} \n    </select>");
			mapperXml.append("\n    <select id=\"findlist\" resultMap=\"BaseResultMap\">\n        select <include refid=\"cols\"/> from " + tableName + " <include refid=\"whereSql\"/> <include refid=\"pageSql\"/>\n    </select>");
			mapperXml.append("\n    <select id=\"findcount\" resultType=\"java.lang.Long\">\n        select count(1) from " + tableName + " <include refid=\"whereSql\"/>\n    </select>");
			mapperXml.append("\n</mapper>");

			byte[] bytes = javaBean.toString().getBytes(Charset.defaultCharset());
			File file = new File(path, pak.replace(".", sp) + sp + clz + ".java");
			file.createNewFile();
			Files.write(file.toPath(), bytes);

			byte[] xmlbytes = mapperXml.toString().getBytes(Charset.defaultCharset());
			File xmlfile = new File(path,
					pak.replace(".", sp) + sp + clz + "Mapper.xml");
			xmlfile.createNewFile();
			Files.write(xmlfile.toPath(), xmlbytes);
			
			resultSet.close();
			resultSet_.close();
			
			File zipfile = new File(path,"mybatis.zip");
			zipfile.createNewFile();
			byte[] buf=new byte[1024];
			ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipfile));
			File[] fs = new File[] {file,xmlfile};
			
			for(int ii=0;ii<fs.length;ii++) {
				FileInputStream in=new FileInputStream(fs[ii]);
				zipOutputStream.putNextEntry(new ZipEntry(fs[ii].getName()));
				int len;
				while((len=in.read(buf))>0){
					zipOutputStream.write(buf,0,len);
				}
				zipOutputStream.closeEntry();
				in.close();
			}
			zipOutputStream.close();
			
			response.reset();
            String resultFileName =  "mybatis.zip";
            resultFileName = URLEncoder.encode(resultFileName,"UTF-8");  
            response.setCharacterEncoding("UTF-8");  
            response.setHeader("Content-disposition", "attachment;filename=" + resultFileName);
            response.setContentType("application/zip");
           
            DataInputStream in = new DataInputStream(
                    new FileInputStream(zipfile));  
            
            OutputStream out = response.getOutputStream();
            
            int bytess = 0;
            byte[] bufferOut = new byte[1024];  
            while ((bytess = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytess);  
            }
            out.close();
            in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			response.reset();
            try {
                OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");  
                String data = "<script language='javascript'>alert(\"下载失败\");</script>";
                writer.write(data); 
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
		} finally {
			s.close();
			s_.close();
			((MySimpleDataSource)this.jt.getDataSource()).close(c);
		}
	}
	
	private static void getsetMothod(String string, String type, StringBuffer getsetM) {
		string = javaNaming(string);
		String str = string.substring(0, 1).toUpperCase() + string.substring(1);
		String javaType = typeConvert(type);

		getsetM.append("\n    public void set" + str + "(" + javaType + string + "){\n        this." + string + "=" + string + ";\n    }");
		getsetM.append("\n    public " + javaType + "get" + str + "(){\n        return this." + string + ";\n    }");
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
		case "date":
			return "java.util.Date ";
		case "decimal":
			return "java.math.BigDecimal ";
		case "char":
			return "Character ";
		case "longtext":
			return "String ";
		case "text":
			return "String ";
		case "mediumtext":
			return "String ";
		default:
			break;
		}
		throw new RuntimeException("类型["+dbType+"]缺失或错误！");
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
	
	@RequestMapping(path="/upload")
	@ResponseBody
	public Ret upload(@RequestParam(value = "sql_file",required = true) MultipartFile file) {
		if(file != null && !file.isEmpty()){
			try {
				Connection conn = jt.getDataSource().getConnection();
				ScriptRunner runner = new ScriptRunner(conn);
				runner.setEscapeProcessing(false);
	            runner.setSendFullScript(false); 
	            runner.runScript(new InputStreamReader(file.getInputStream(),"UTF-8"));
	            conn.close();
	            return Ret.successMsg("upload ok");
			} catch (Exception e) {
				return Ret.fail(e.toString());
			}
		}else {
			return Ret.fail("上传文件为空");
		}
	}

	@RequestMapping(path="/camel")
	@ResponseBody
	public Ret camel(String name) {
		String rs = tablename2java(name);
		return Ret.successMsg(rs);
	}
	
	private static String underline2Camel(String line, boolean smallCamel) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
					: Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}
	
	private static String tablename2java(String tn) {
		/*int index = tn.indexOf("_");
		if(index>-1) {
			tn = tn.substring(index+1);
		}*/
		
		String string = underline2Camel(tn,false);
		return string;
	}
	
	@RequestMapping("/jsonformat")
	@ResponseBody
	public String jsonFormat(String str) {
		return JsonTool.formatJson(str, "        ");
	}
	
	public static class Ret{
		private boolean success;
		private String msg = "ok";
		private Object data;
		
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
		public boolean getSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
		public static Ret success() {
			Ret ret = new Ret();
			ret.setSuccess(true);
			return ret;
		}
		
		public static Ret successMsg(String msg) {
			Ret ret = new Ret();
			ret.setSuccess(true);
			ret.setMsg(msg);
			return ret;
		}
		
		public static Ret successData(Object data) {
			Ret ret = new Ret();
			ret.setSuccess(true);
			ret.setData(data);
			return ret;
		}
		
		public static Ret success(String msg, Object data) {
			Ret ret = new Ret();
			ret.setSuccess(true);
			ret.setMsg(msg);
			ret.setData(data);
			return ret;
		}
		
		public static Ret fail(String msg) {
			Ret ret = new Ret();
			ret.setSuccess(false);
			ret.setMsg(msg);
			return ret;
		}
		
		public static Ret fail(String msg, Object data) {
			Ret ret = new Ret();
			ret.setSuccess(false);
			ret.setMsg(msg);
			ret.setData(data);
			return ret;
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
	
	@SuppressWarnings("unused")
	public static String javaNaming(String column) {
		if (/*Path.JAVA_NAMEING.value.equals("true")*/ false) {
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
			if(this.get(i).equals("update_time")||this.get(i).equals("versions"))
				continue;
			if (i == this.size() - 1)
				sb.append(this.get(i) + "=#{" + MainConfigure.javaNaming((String) this.get(i)) + "}");
			else
				sb.append(this.get(i) + "=#{" + MainConfigure.javaNaming((String) this.get(i)) + "},");
		}
		
		String ret = sb.toString();
		if(ret.endsWith(",")) {
			ret = ret.substring(0, ret.length()-1);
		}
		
		return ret;
	}

	public String joinc() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.size(); i++) {
			sb.append(this.get(i) + ",");
		}
		return sb.toString();
	}

	private String val(E e) {
		String f = (String) e;
		if(f.equals("create_time"))
			return "now()";
		if(f.equals("update_time"))
			return "now()";
		if(f.equals("versions"))
			return "0";
		return "#{" + MainConfigure.javaNaming((String) e) + "}";
	}
}
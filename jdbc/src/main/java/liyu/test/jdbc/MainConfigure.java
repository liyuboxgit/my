package liyu.test.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private String password = "liyuff";
	
	@Bean
	public DataSource dataSource() {
		return new DataSource() {
			
			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void setLoginTimeout(int seconds) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLogWriter(PrintWriter out) throws SQLException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Logger getParentLogger() throws SQLFeatureNotSupportedException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLoginTimeout() throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public PrintWriter getLogWriter() throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Connection getConnection(String username, String password) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Connection getConnection() throws SQLException {
				try {
					Class.forName(driverClass);
					return DriverManager.getConnection(url, username, password);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}
	
	@Bean
	public JdbcTemplate JdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Autowired
	private JdbcTemplate jt;
	
	@RequestMapping("/")
	public String sccess(ModelMap map) {
		 map.addAttribute("title","请输入正确的可执行的sql，=><button>配置</button>&nbsp;<button>查看结果</button>&nbsp;<button>清空</button>");
		 return "index";
	}
	
	@RequestMapping(path="/sql",method= {RequestMethod.POST})
	@ResponseBody
	public String sccess(String sql) throws JsonProcessingException {
		 List<Map<String, Object>> ret = jt.queryForList(sql);
		 return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ret);
	}
	
	@RequestMapping(path="/config",method= {RequestMethod.POST})
	@ResponseBody
	public String config(String url,String username,String password,int rw) {
		if(rw==1) {
			return this.url+" "+this.username+" "+this.password;
		}else {			
			try {
				Class.forName(driverClass);
				Connection conn = DriverManager.getConnection(url, username, password);
				this.url = url;
				this.username = username;
				this.password = password;
				return "config success:"+conn.getMetaData().getDatabaseProductName();
			}catch(Exception e){
				e.printStackTrace();
				return "config faild:"+e.getMessage();
			}
		}
	}
	
	@RequestMapping(path="/export")
	public ResponseEntity<byte[]> export(String name) {
		try {
			StringBuffer sb = new StringBuffer();
			
			List<Map<String,Object>> list = this.jt.queryForList("show create table "+name);
			String ct = (String) list.get(0).get("Create Table");
			sb.append(ct);
			sb.append(";\n\n\n");
			Connection c = this.jt.getDataSource().getConnection();
			
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery("select * from "+name);
			
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
			
			HttpHeaders headers=new HttpHeaders();
			headers.add("Content-Disposition", "attachment;filename="+name+".sql");
			HttpStatus statusCode = HttpStatus.OK;
			ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(sb.toString().getBytes(), headers, statusCode);
			return response;
		} catch (Exception e) {
			HttpHeaders headers=new HttpHeaders();
			headers.add("Content-Disposition", "attachment;filename="+name+".sql");
			HttpStatus statusCode = HttpStatus.BAD_REQUEST;
			ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(e.toString().getBytes(), headers, statusCode);
			return response;
		}
	}
	
	@RequestMapping(path="/upload")
	@ResponseBody
	public Map<String,String> upload() {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("succss", "true");
		return map;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

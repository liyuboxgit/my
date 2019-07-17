package liyu.test.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
		 map.addAttribute("title","请输入正确的可执行的sql，点击提交，查看结果");
		 return "index";
	}
	
	@RequestMapping(path="/sql",method= {RequestMethod.POST})
	@ResponseBody
	public String sccess(String sql) throws JsonProcessingException {
		 List<Map<String, Object>> ret = jt.queryForList(sql);
		 return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ret);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

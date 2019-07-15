package liyu.test.jdbc;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class T {

	public static void main(String[] args) {
		
		DruidDataSource ds = new com.alibaba.druid.pool.DruidDataSource();
		
		ds.setDriverClassName("");
		ds.setUrl("jdbc:mysql://localhost:3306/product?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useAffectedRows=true");
		ds.setUsername("root");
		ds.setPassword("liyuff");
		
		DruidPooledConnection connection = null;
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		Integer count = jdbcTemplate.queryForObject("select count(*) from station", Integer.class);
		
		System.out.println(count);
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ds.close();
	}

}

package liyu.test.springbootMybatis;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration  
public class DruidConfiguration {  
	private static Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);
    @Value("${spring.datasource.url}")  
    private String dbUrl;  
    @Value("${spring.datasource.username}")  
    private String username;  
    @Value("${spring.datasource.password}")  
    private String password;  
    @Value("${spring.datasource.driver-class-name}")  
    private String driverClassName;  
    
    @Value("5")  
    private int initialSize;  
    @Value("5")  
    private int minIdle;  
    @Value("20")  
    private int maxActive;  
    @Value("60000")  
    private int maxWait;  
    @Value("60000")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("300000")  
    private int minEvictableIdleTimeMillis;  
    @Value("SELECT 1")  
    private String validationQuery;  
    @Value("true")  
    private boolean testWhileIdle;  
    @Value("false")  
    private boolean testOnBorrow;  
    @Value("false")  
    private boolean testOnReturn;  
    @Value("true")  
    private boolean poolPreparedStatements;  
    @Value("20")  
    private int maxPoolPreparedStatementPerConnectionSize;  
    @Value("stat,wall,log4j")  
    private String filters;  
    @Value("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500")  
    private String connectionProperties;  
    @Value("true")  
    private boolean useGlobalDataSourceStat;  
  
    @Bean     
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
    
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);  
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
        	logger.error("数据源配置错误", e);
        }  
        datasource.setConnectionProperties(connectionProperties);  
        return datasource;  
    }  
}  
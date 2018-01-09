package liyu.test.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.JdbcType;

import junit.framework.TestCase;
import liyu.test.mybatis.ext.ExtObjectFactory;
import liyu.test.mybatis.mapper.BaoGuanDanMapper;
import liyu.test.mybatis.model.BaoGuanDan;

public class SimpleTest extends TestCase{
	public void testDataSource() throws SQLException{
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setUrl("jdbc:oracle:thin:@192.168.100.252:1521:zssd");
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUsername("cktsfk");
		dataSource.setPassword("cktsfk");
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			assertNotNull(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		
	}
	
	public void testProgramMapper(){
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setUrl("jdbc:oracle:thin:@192.168.100.252:1521:zssd");
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUsername("cktsfk");
		dataSource.setPassword("cktsfk");
		
		
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);
		
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(BaoGuanDanMapper.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		configuration.setMapUnderscoreToCamelCase(Boolean.TRUE);
		configuration.setObjectFactory(new ExtObjectFactory());
		
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		
		SqlSession session = sqlSessionFactory.openSession();
		Object one = session.selectOne("liyu.test.mybatis.mapper.BaoGuanDanMapper.findCount", new BaoGuanDan());
		if(one instanceof Integer){
			System.out.println(one);
		}
		
		BaoGuanDanMapper baoGuanDanMapper = session.getMapper(BaoGuanDanMapper.class);
		Date now = baoGuanDanMapper.now();
		if(now !=null){
			System.out.println(now);
		}
	}
	
	public void testXmlConfMapper() throws IOException, ParseException {
        InputStream is = Resources.getResourceAsStream("conf/mybatis-config.xml");
        
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sessionFactory.openSession();
        BaoGuanDanMapper mapper = session.getMapper(BaoGuanDanMapper.class);
        mapper.findList(new BaoGuanDan());
        session.commit();	
	}
	
	
}

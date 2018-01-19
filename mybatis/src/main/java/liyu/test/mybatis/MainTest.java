package liyu.test.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import liyu.test.mybatis.mapper.RoleMapper;
import liyu.test.mybatis.mapper.UserMapper;
import liyu.test.mybatis.model.Role;
import liyu.test.mybatis.model.User;

public class MainTest {
	public static void main(String[] args) throws IOException, ParseException {
		InputStream is = Resources.getResourceAsStream("conf/mybatis-config.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession session = sessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		Integer count = userMapper.findCount(new User());
		System.out.println(count);
		
		RoleMapper roleMapper = session.getMapper(RoleMapper.class);
		count = roleMapper.findCount(new Role());
		System.out.println(count);
		session.commit();
		
	}
}

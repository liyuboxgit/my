package liyu.test.springboot_v2.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import liyu.test.springboot_v2.Start;
import liyu.test.springboot_v2.mapper.UserMapper;
import liyu.test.springboot_v2.model.User;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Start.class)
@MapperScan(basePackages="liyu.test.springboot_v2.mapper")
public class TestMepper {
	@Autowired
	private UserMapper userMapper;
	@Test
	public void test(){
		System.out.println(userMapper.findList(new User()).size());
		System.out.println(new Date().toString());
	}
}

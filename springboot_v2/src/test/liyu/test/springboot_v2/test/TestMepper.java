package liyu.test.springboot_v2.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import liyu.test.springboot_v2.Start;
import liyu.test.springboot_v2.mapper.EnhanceMapper;
import liyu.test.springboot_v2.model.User;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Start.class)
/*@MapperScan(basePackages="liyu.test.springboot_v2.mapper")*/
public class TestMepper {
	@Autowired
	private EnhanceMapper enhanceMapper;
	@Test
	public void test(){
		
	}
	@Test
	public void testEnhanceMapper(){
		User user = new User();
		List<User> list = enhanceMapper.findList(EnhanceMapper.findlist, user, User.class);
		System.out.println(list.size());

		Long findCount = enhanceMapper.findCount(EnhanceMapper.findcount, user, User.class);
		Assert.assertEquals(list.size(),findCount.intValue());
	}
}

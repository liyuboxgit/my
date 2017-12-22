package liyu.test.springboot_v2.test;

import java.util.Date;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import liyu.test.springboot_v2.Start;
import liyu.test.springboot_v2.mapper.EnhanceMapper;
import liyu.test.springboot_v2.mapper.EnhanceMapper.Page;
import liyu.test.springboot_v2.mapper.UpdateColumnWapper;
import liyu.test.springboot_v2.mapper.UserMapper;
import liyu.test.springboot_v2.model.Role;
import liyu.test.springboot_v2.model.User;
import liyu.test.springboot_v2.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Start.class)
/*@MapperScan(basePackages="liyu.test.springboot_v2.mapper")*/
public class TestMepper {
	@Autowired
	private EnhanceMapper enhanceMapper;
	@Autowired
	private RoleService roleService;
	@Test
	public void test(){
		
	}
	@Test
	public void testEnhanceMapper(){
		Role role = new Role();
		role.setRolename("hello");
		enhanceMapper.exccute(Role.class, EnhanceMapper.insert, role);
	}
}

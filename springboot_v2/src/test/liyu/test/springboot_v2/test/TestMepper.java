package liyu.test.springboot_v2.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import liyu.test.springboot_v2.Start;
import liyu.test.springboot_v2.mapper.EnhanceMapper;
import liyu.test.springboot_v2.mapper.UserMapper;
import liyu.test.springboot_v2.model.Role;
import liyu.test.springboot_v2.model.User;
import liyu.test.springboot_v2.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Start.class)
@MapperScan(basePackages="liyu.test.springboot_v2.mapper")
public class TestMepper {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private EnhanceMapper enhanceMapper;
	@Autowired
	private RoleService roleService;
	@Test
	public void test(){
		System.out.println(userMapper.findList(new User()).size());
		System.out.println(new Date().toString());
	}
	@Test
	public void testEnhanceMapper(){
		List<Object> list = enhanceMapper.findList("liyu.test.springboot_v2.mapper.UserMapper.findList", new User());
		System.out.println(list.size());
	}
	@Test
	public void testRoleService(){
		Role role = new Role();
		role.setId(1);
		role.setRolename("超级管理员");
		int ret = this.roleService.update(role);
		System.out.println(ret);
	}
}

package security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import liyu.test.security.entity.User;
import liyu.test.security.service.ButtonService;
import liyu.test.security.service.ModuleService;
import liyu.test.security.service.PermissionService;
import liyu.test.security.service.PositionServie;
import liyu.test.security.service.UserService;

public class JunitTest {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
	UserService userService = applicationContext.getBean(UserService.class);
	ModuleService moduleService = applicationContext.getBean(ModuleService.class);
	ButtonService buttonService = applicationContext.getBean(ButtonService.class);
	PositionServie positionServie = applicationContext.getBean(PositionServie.class);
	PermissionService permissionService = applicationContext.getBean(PermissionService.class);
	public static final String SPACE="";
	@Before
	public void init(){
		User u = userService.getByLoginName("scott");		
		System.out.println(u.getUserName());
		//初始化数据，执行前把springxml中的mvn和shiro注释掉
		/*PositionPo position = new PositionPo();
		position.setName("经理");
		position.setPid(1);
		position.setDepartMentId(1);
		positionServie.add(position);
		
		UserPo user = new UserPo();
		user.setLoginName("scott");
		user.setPassword("123456");
		user.setUserName("scott");
		user.setDepartMentId(1);
		user.setPositionId(position.getId());
		userService.add(user);
		
		ModulePo module = new ModulePo();
		module.setEnable(1);
		module.setName("订单");
		module.setUrl(SPACE);
		module.setPid(0);
		moduleService.add(module);
		
		ModulePo module2 = new ModulePo();
		module.setEnable(1);
		module2.setName("订单查询");
		module2.setUrl("/order/query");
		module2.setPid(module.getId());
		moduleService.add(module2);
		
		ButtonPo button = new ButtonPo();
		button.setCode("C");
		button.setMark("增加");
		button.setEnable(1);
		buttonService.add(button);
		
		ButtonPo button2 = new ButtonPo();
		button2.setCode("R");
		button2.setMark("查询");
		button2.setEnable(1);
		buttonService.add(button2);
		
		ButtonPo button3 = new ButtonPo();
		button3.setCode("U");
		button3.setMark("更新");
		button3.setEnable(1);
		buttonService.add(button3);
		
		ButtonPo button4 = new ButtonPo();
		button4.setCode("D");
		button4.setMark("删除");
		button4.setEnable(1);
		buttonService.add(button4);
		
		PermissionPo permission = new PermissionPo();
		permission.setModuleId(module2.getId());
		permission.setPositionId(position.getId());
		permission.setButtons("C,R,U,D");
		permissionService.add(permission);*/
	}
	@Test
	public void test(){
		/*org.apache.shiro.mgt.SecurityManager securityManager = (org.apache.shiro.mgt.SecurityManager) applicationContext.getBean("securityManager");
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("scott", "123456");
		subject.login(token);
		System.out.println("login success..");
		
		boolean hasRole = subject.hasRole("orderQuery");
		Assert.assertTrue(hasRole);
		subject.checkPermission("orderQuery:C");
		subject.checkPermission("orderQuery:R");
		subject.checkPermission("orderQuery:U");
		subject.checkPermission("orderQuery:D");*/
		
	}
	@After
	public void after(){
		System.out.println("test success...");
	}
}

package security;


import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.json.JSON;

import liyu.test.framework.mybatis.page.Pager;
import liyu.test.security.entity.User;
import liyu.test.security.service.UserService;

/**
 * 
 * @ClassName: UserServiceTest 
 * @Description: 执行前注释mvc和shiro配置文件
 * @author: liyu
 * @date: 2017年10月17日 上午10:05:40
 */
public class UserServiceTest {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
	UserService userService = applicationContext.getBean(UserService.class);
	public static void main(String[] args) {
		UserServiceTest t = new UserServiceTest();
		
		
		/*test cache
		System.out.println(t.userService);
		List<User> list = t.userService.qeuryForList();
		System.out.println(list.size());
		System.out.println(t.userService.qeuryForList().size());
		UserPo userPo = new UserPo();
		userPo.setLoginName("test1");
		t.userService.add(userPo);
		System.out.println(t.userService.qeuryForList().size());*/
		
		Pager<Object> page = t.userService.queryForPage();
		try {
			String json = JSON.json(page);
			System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 
		
		System.out.println(page.getTotal());
		for(int i=0;i<page.getResult().size();i++){
			User user = (User) page.getResult().get(i);
			System.out.println(user.getLoginName());
		}
		
		
		/*Output output = new Output(1, 4096); 
		Kryo kryo = new Kryo(); 
		Registration register = kryo.register(Pager.class); 
		kryo.writeObject(output, page); 
		byte[] bb = output.toBytes(); 
		System.out.println(bb.length);
		output.close();
		
		Input input = new Input(bb); 
		@SuppressWarnings("unchecked")
		Pager<Object> ret = kryo.readObject(input, Pager.class); 
		System.out.println(ret.getTotal());*/
	}
}

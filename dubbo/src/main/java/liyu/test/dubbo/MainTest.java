package liyu.test.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import liyu.test.dubbo.provider.DemoService;

public class MainTest {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"liyu/test/dubbo/consumer/consumer.xml"});
        context.start();
		DemoService demoService = (DemoService) context.getBean("demoService");
		System.out.println(demoService.sayHello("world"));
	}
}

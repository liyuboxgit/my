package liyu.test.sb.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboConsumerTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("liyu/test/sb/dubbo/provider/dubboConsumer.xml");
		SimpleService service = (SimpleService) context.getBean("simService");
		System.out.println("=============================================>"+service.sayHello());
	}
}

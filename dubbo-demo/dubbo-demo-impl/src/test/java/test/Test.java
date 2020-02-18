package test;

import org.apache.dubbo.demo.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import liyu.test.dubbo.demo.consumer.ConsumerConfiguer;
import liyu.test.dubbo.demo.consumer.ConsumerService;

public class Test {
	/**
	 * dubbo生产端和消费端是独立，xml和annotation组合起来可以有四种调用方式
	 * 如果生产端返回结果非常慢，可以在消费端针对某个service设置timeout大一些。
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		/*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test/consumer.xml");
		DemoService demoService = applicationContext.getBean(DemoService.class);
		String sayHello = demoService.sayHello("Jhor");
		System.out.println("======>"+sayHello);*/
		
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(ConsumerConfiguer.class);
		ConsumerService service = configApplicationContext.getBean(ConsumerService.class);
		System.err.println("======>"+service.consum("Tom"));
	}
}

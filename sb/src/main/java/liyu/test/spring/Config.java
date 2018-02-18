package liyu.test.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("liyu.test.spring")
public class Config {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		ComponentBean bean = context.getBean(ComponentBean.class);
		bean.getCount();
		
		System.out.println(bean.getCount());
		
		System.out.println(context.getBean(I.class));//Exception will rise,because has two primary interface I.
		context.close();
	}
	
	@Bean
	@Primary
	public A a(){
		return new A();
	}
	
	@Bean
	@Primary
	public B b(){
		return new B();
	}

}

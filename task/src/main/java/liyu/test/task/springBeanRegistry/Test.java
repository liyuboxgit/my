package liyu.test.task.springBeanRegistry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test implements ApplicationContextAware{
	public void test(){
		System.out.println(this.getClass().getName()+":"+
				this.applicationContext.getBean(this.getClass()).hashCode()+","+
				this.hashCode());
	}
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MyBeanDefinitionRegistryPostProcessor.class);
		Test bean = ctx.getBean(Test.class);
		bean.test();
	}

	public ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}

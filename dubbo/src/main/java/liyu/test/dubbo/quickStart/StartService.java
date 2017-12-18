package liyu.test.dubbo.quickStart;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartService {
	public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"liyu/test/dubbo/quickStart/service.xml"});
        context.start();
        System.in.read(); // press any key to exit
    }
}

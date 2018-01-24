package liyu.test.dubbo;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Setup {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "liyu/test/dubbo/provider/service.xml" });
		context.start();
		// press any key to exit
		System.in.read();
	}
}

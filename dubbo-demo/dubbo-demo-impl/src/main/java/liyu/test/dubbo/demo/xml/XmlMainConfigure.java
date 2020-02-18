package liyu.test.dubbo.demo.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations= {"spring.xml"})
public class XmlMainConfigure{
	public static void main(String[] args) {
		SpringApplication.run(XmlMainConfigure.class, args);
	}
}


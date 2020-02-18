package liyu.test.dubbo.demo.consumer;

import org.apache.dubbo.demo.DemoService;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

@Component
public class ConsumerService {
	@Reference(timeout=12000)
	private DemoService demoService;
	
	public String consum(String name) {
		return demoService.sayHello(name);
	}
}

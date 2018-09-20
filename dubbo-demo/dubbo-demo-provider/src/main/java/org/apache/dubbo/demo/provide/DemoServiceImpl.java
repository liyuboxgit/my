package org.apache.dubbo.demo.provide;
import org.apache.dubbo.demo.DemoService;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
		}
        return "Hello " + name;
    }
}

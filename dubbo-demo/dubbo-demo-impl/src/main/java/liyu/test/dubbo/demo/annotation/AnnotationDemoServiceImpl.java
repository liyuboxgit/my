package liyu.test.dubbo.demo.annotation;

import org.apache.dubbo.demo.DemoService;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class AnnotationDemoServiceImpl implements DemoService{
	public String sayHello(String name) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "name from "+AnnotationDemoServiceImpl.class.getName();
	}
}

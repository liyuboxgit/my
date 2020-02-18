package liyu.test.dubbo.demo.xml;
import org.apache.dubbo.demo.DemoService;

public class XmlDemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "Hello " + name;
    }
}

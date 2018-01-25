package liyu.test.sb.dubbo.provider;

import org.springframework.stereotype.Service;

@Service("SimpleDubboService")
public class SimpleServiceImpl implements SimpleService{

	@Override
	public String sayHello() {
		return "hello msg from dubbo provider";
	}

}

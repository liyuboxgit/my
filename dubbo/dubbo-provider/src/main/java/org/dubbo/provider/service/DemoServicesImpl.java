package org.dubbo.provider.service;
import com.alibaba.dubbo.config.annotation.Service; 
@Service(version = "1.0.0",timeout=3000) 
public class DemoServicesImpl implements DemoServices {
	@Override
	public String toProvider(String name) throws Exception {
	    Thread.sleep(3000);
		return name+",恭喜你连接成功";
	}
} 
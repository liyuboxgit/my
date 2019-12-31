package liyu.test.sbdp.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.dubbo.demo.DemoService;
import org.springframework.stereotype.Component;
@Service
@Component
public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "springboot-dobbo:"+name;
    }
}

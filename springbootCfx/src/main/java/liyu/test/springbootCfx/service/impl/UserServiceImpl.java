package liyu.test.springbootCfx.service.impl;

import org.springframework.stereotype.Component;

import liyu.test.springbootCfx.entity.User;
import liyu.test.springbootCfx.service.UserService;

import javax.jws.WebService;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/01/30
 */
@WebService(serviceName = "UserService",
        targetNamespace = "http://service.simple.cfx.com",
        endpointInterface = "liyu.test.springbootCfx.service.UserService")
@Component
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getUserList(String userName) {
        System.out.println("输入参数：" + userName);
        User user1 = new User("张三", "男");
        User user2 = new User("李四", "男");
        return Arrays.asList(user1,user2);
    }
}

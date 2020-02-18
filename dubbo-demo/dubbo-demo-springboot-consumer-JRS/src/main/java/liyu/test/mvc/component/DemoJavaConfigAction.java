package liyu.test.mvc.component;

import javax.annotation.PostConstruct;

import org.apache.dubbo.demo.User;
import org.apache.dubbo.demo.UserService;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @author Jinkai.Ma
 */
@Component
public class DemoJavaConfigAction {
	
    @Reference
    private UserService userService;

    @PostConstruct
    public void start() throws Exception {
        User user = new User(1L);
        System.out.println("SUCESS: registered user with id " + userService.registUser(user).getId());
        System.out.println("SUCESS: got user " + userService.getUser(1L));
    }
}

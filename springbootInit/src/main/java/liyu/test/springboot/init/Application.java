package liyu.test.springboot.init;

import liyu.test.springboot.init.config.SpringSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableWebSecurity
@Controller
@Import(SpringSecurityConfig.class)
public class Application {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/userLogin")
    public String login(){
        return "loginPage";
    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

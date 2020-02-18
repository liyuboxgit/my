package liyu.test.sbdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring.xml")
public class SpringBootDubboProviderConf {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboProviderConf.class, args);
    }
}

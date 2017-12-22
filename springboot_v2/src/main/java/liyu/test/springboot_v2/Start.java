package liyu.test.springboot_v2;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
/*@MapperScan(basePackages="liyu.test.springboot_v2.mapper")*/
public class Start {
	private static Logger logger = LoggerFactory.getLogger(Start.class);
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
        logger.debug("springboot_v2 start at {}", new Date().toString());
    }
}

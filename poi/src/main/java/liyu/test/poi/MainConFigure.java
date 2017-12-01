package liyu.test.poi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class MainConFigure {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainConFigure.class, args);
    }
}

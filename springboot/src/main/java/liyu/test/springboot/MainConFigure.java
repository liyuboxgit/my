package liyu.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;

import liyu.test.springboot.web.NoUseController;

@SpringBootApplication(scanBasePackages="liyu.test.springboot.config")
@ComponentScan(
	excludeFilters= {@Filter(type=FilterType.ASSIGNABLE_TYPE,classes= {NoUseController.class})}
)
public class MainConFigure {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(MainConFigure.class, args);
    }
}

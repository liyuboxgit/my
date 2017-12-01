package liyu.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import liyu.test.springboot.util.DataSourceConfiguration;
import liyu.test.springboot.util.MyBatisConfig;
import liyu.test.springboot.util.MyBatisMapperScannerConfig;
import liyu.test.springboot.util.MyWebMvcConfig;

@EnableAutoConfiguration
@ComponentScan
@Import({
	MyWebMvcConfig.class,
	DataSourceConfiguration.class,
	MyBatisConfig.class,
	MyBatisMapperScannerConfig.class
})

public class MainConFigure {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(MainConFigure.class, args);
    }
   
}

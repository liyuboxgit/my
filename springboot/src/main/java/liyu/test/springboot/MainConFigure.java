package liyu.test.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import liyu.test.springboot.config.DataSourceConfiguration;
import liyu.test.springboot.config.MyBatisConfig;
import liyu.test.springboot.config.MyBatisMapperScannerConfig;
import liyu.test.springboot.config.MyWebMvcConfig;
import liyu.test.springboot.config.SecurityConfigure;

@EnableAutoConfiguration
@ComponentScan
@Import({
	MyWebMvcConfig.class,
	SecurityConfigure.class,
	DataSourceConfiguration.class,
	MyBatisConfig.class,
	MyBatisMapperScannerConfig.class
})

public class MainConFigure {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(MainConFigure.class, args);
    }
}

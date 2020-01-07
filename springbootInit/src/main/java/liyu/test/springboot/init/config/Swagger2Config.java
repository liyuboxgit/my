package liyu.test.springboot.init.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApiModule1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("module1")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("liyu.test.spring.web.module1"))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public Docket createRestApiModule2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("module2")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("liyu.test.spring.web.module2"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("springboot利用swagger构建api文档")
            .description("简单优雅的restfun风格，http://blog.csdn.net/saytime")
            .termsOfServiceUrl("http://blog.csdn.net/saytime")
            .version("1.0")
            .build();
    }
}
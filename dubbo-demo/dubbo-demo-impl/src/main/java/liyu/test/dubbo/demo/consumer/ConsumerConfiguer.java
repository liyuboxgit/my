package liyu.test.dubbo.demo.consumer;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@Configurable
@EnableDubbo(scanBasePackages="liyu.test.dubbo.demo.consumer")
@ComponentScan("liyu.test.dubbo.demo.consumer")
public class ConsumerConfiguer {
	@Bean
	public ConsumerConfig consumerConfit() {
		return new ConsumerConfig();
	}
	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("demo-annotationConsumer");
		return applicationConfig;
	}
	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setProtocol("zookeeper");
		registryConfig.setAddress("localhost");
		registryConfig.setPort(2181);
		registryConfig.setTimeout(12000);
		return registryConfig;
	}
}

package liyu.test.dubbo.demo.annotation;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@SpringBootApplication
public class AnnotationMainConfigure {
	public static void main(String[] args) {
		SpringApplication.run(AnnotationMainConfigure.class, args);
	}
	@Configurable
	@EnableDubbo(scanBasePackages="liyu.test.dubbo.demo.annotation")
	static class ProviderConfigure{
		@Bean
		public ProviderConfig providerConfig() {
			return new ProviderConfig();
		}
		@Bean
		public ApplicationConfig applicationConfig() {
			ApplicationConfig applicationConfig = new ApplicationConfig();
			applicationConfig.setName("demo-annotationProvider");
			return applicationConfig;
		}
		@Bean
		public RegistryConfig registryConfig() {
			RegistryConfig registryConfig = new RegistryConfig();
			registryConfig.setProtocol("zookeeper");
			registryConfig.setAddress("localhost");
			registryConfig.setPort(2181);
			return registryConfig;
		}
		@Bean
		public ProtocolConfig protocolConfig() {
			ProtocolConfig protocolConfig = new ProtocolConfig();
			protocolConfig.setName("dubbo");
			protocolConfig.setPort(20880);
			return protocolConfig;
		}
	}
}

package liyu.test.springWebflux;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.ipc.netty.resources.LoopResources;


@SpringBootApplication
@PropertySource(value="classpath:application.yaml")
public class MainConfigure {
	@Value("${performance.loopThreads}")
    private int loopThreads;

    @Value("${performance.workThreads}")
    private int workThreads;
	
    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
        factory.addServerCustomizers(builder -> {
            builder.loopResources(LoopResources.create("research-http", loopThreads, true));
        });
        return factory;
    }

    @Bean
    public Scheduler scheduler() {
        ExecutorService threadPool = Executors.newFixedThreadPool(workThreads);
        return Schedulers.fromExecutor(threadPool);
    }
    
	
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

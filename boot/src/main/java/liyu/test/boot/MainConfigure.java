package liyu.test.boot;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import liyu.test.boot.webflux.WebFluxConfig;

@SpringBootApplication
@Import({WebFluxConfig.class})
public class MainConfigure {
	public static void main(String[] args) throws IOException {
		//SpringApplication.run(MainConfigure.class, args);
		
		/* use netty startup
		 * AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				WebFluxConfig.class);
		HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(applicationContext).build();
		ReactorHttpHandlerAdapter httpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer.create("localhost", 8090).newHandler(httpHandlerAdapter).block();
		System.in.read();*/
	}
}

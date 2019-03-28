package liyu.test.boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
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
		
		new Timer().schedule(new R(), 1000, 1000); 
	}
	
	private static class R extends TimerTask{

		@Override
		public void run() {
			try {
				URL url = new java.net.URL("https://..rthdtax.com/rtaxhelp/webs");
				URLConnection connection = url.openConnection();
				InputStream inputStream = connection.getInputStream();
				StringBuilder sb = new StringBuilder();
				String line;

				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = br.readLine()) != null) {
				    sb.append(line);
				}
				
				System.out.println(sb);
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

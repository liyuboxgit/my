package liyu.test.eurekaconsumer;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class MainConfigure {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping("/")
	public String sccess() {
		//String ret = restTemplate.getForObject("http://localhost:8762/provider/msg", String.class);
		List<ServiceInstance> list = this.discoveryClient.getInstances("eurekaprovider");
		if(list.size()!=0 && list.get(0)!=null){
			URI uri = list.get(0).getUri();
			String ret = restTemplate.getForObject(uri+"/provider/msg", String.class);
			return "success,"+ret;
		}else{
			return "success,"+"no provider finded";
		}
		
	}
	
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(MainConfigure.class, args);
	}
}

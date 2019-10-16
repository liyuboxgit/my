package springmvc.test;

import org.springframework.web.client.RestTemplate;

public class Cli {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println(restTemplate.getForEntity("http://localhost:8080/", String.class).getBody());
	}

}

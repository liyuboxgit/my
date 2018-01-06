package liyu.test.sb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
public class RestFulClientTest {
	 
	 private TestRestTemplate restTemplate = new TestRestTemplate();
	 
	 @Test
	 public void test(){
		 String string = restTemplate.getForObject("http://localhost:8080/rest/msg", String.class);
		 System.out.println(string);
	 }
}

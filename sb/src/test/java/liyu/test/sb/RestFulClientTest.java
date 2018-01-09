package liyu.test.sb;

import java.lang.annotation.Annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import liyu.test.sb.framework.RestControllerMapping;
import liyu.test.sb.web.RestFulController;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
public class RestFulClientTest {

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void test() {
		String string = restTemplate.getForObject("http://localhost:8080/rest/msg", String.class);
		System.out.println(string);
	}

	public static void main(String[] args) {
		Annotation[] annotations = RestFulController.class.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			if(annotation.annotationType().isAnnotationPresent(RequestMapping.class)){
				RestControllerMapping mapping = RestFulController.class.getAnnotation(RestControllerMapping.class);
				for (String path : mapping.path()) {
					System.out.println(path);
				}
			}
		}
	}
}

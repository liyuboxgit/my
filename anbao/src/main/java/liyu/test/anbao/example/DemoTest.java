package liyu.test.anbao.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class DemoTest {
	public static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
	public static void main(String[] args) throws URISyntaxException, IOException {
		/*String url = "http://localhost:8080/anbao/login";
		StringMap stringMap = new StringMap();
		stringMap.put("username", "1");
		stringMap.put("password", "2");*/
		
		/*Map<String,Object> stringMap = new HashMap<String,Object>();
		stringMap.put("username", "1");
		stringMap.put("password", "2");
		String string = restTemplate().getForObject(url, String.class, stringMap);
		System.out.println(string);
		
		ResponseEntity<String> entity = restTemplate().getForEntity(url, String.class, stringMap);
		String body = entity.getBody();
		System.out.println(body);*/
		
		URL uri = new URL("http://localhost:8080/anbao/test");
		
		URLConnection connection = uri.openConnection();
		InputStream inputStream = connection.getInputStream();
		List<String> list = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
		list.forEach(el ->{
			System.out.println(el);
		});
		inputStream.close();
		
	}
}

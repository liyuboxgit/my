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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import liyu.test.anbao.core.util.JsonRet;
import liyu.test.anbao.core.util.StringMap;

public class DemoTest {
	public static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		StringMap user = new StringMap();
		user.put("username", "1");
		user.put("password", "1");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
		HttpEntity<String> strEntity = new HttpEntity<String>(JSON.toJSONString(user),headers);
		JsonRet resultData = restTemplate().postForObject("http://localhost:8080/anbao/login",strEntity,JsonRet.class);
		System.out.println(resultData);
		
		URL uri = new URL("http://localhost:8080/anbao/test?ckey="+resultData.getCkey());
		
		URLConnection connection = uri.openConnection();
		InputStream inputStream = connection.getInputStream();
		List<String> list = IOUtils.readLines(inputStream, Charset.forName("UTF-8"));
		list.forEach(el ->{
			System.out.println(el);
		});
		inputStream.close();
		
		
		

		
	}
}

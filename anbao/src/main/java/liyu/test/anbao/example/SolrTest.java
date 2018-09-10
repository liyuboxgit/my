package liyu.test.anbao.example;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import liyu.test.anbao.core.util.StringMap;

public class SolrTest {
	public static void main(String[] args) {
		String url = "http://192.168.126.128:8983/solr/liyu/update?_=1536563699949&commitWithin=1000&overwrite=true&wt=json";
		
		StringMap map = new StringMap(new String[] {"id","visit","comp"},new Object[] {"1","130","test"});
		Object[] param = new Object[] {map};
		System.out.println(JSON.toJSONString(param));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
		HttpEntity<String> strEntity = new HttpEntity<String>(JSON.toJSONString(param),headers);
		String resultData = restTemplate().postForObject(url,strEntity,String.class);
		System.out.println(resultData);
		
	}
	
	public static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}

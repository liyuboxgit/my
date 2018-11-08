package anbao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import liyu.test.anbao.AnbaoMainConfigure;
import liyu.test.anbao.core.JedisPoolManager;
import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=AnbaoMainConfigure.class)
public class BaseTest {
	@Autowired
	private JedisPoolManager jpm;
	@Test
	public void test() {
		Jedis jedis = jpm.getJedis(0);
		String string = jedis.get("test");
		System.out.println(string);
	}
	
	public static void main(String[] args) throws IOException {
		/*{
			"total": 17,
			"data": {
				"openid": ["oMJtq01BpeZlwh5J3xbmbmc_MWao", "oMJtq0zzTI8qQB6aXe9f2jxf4Uws", "oMJtq0_LCZxpTOYonGwGoa0DvPmM", "oMJtq0y9p2u2pVGWtiej2484X75M", "oMJtq0_8D5mJaNJaqIZ-agG6DIvk", "oMJtq01NX2Y1EOrWZwQ-YJuGNKts", "oMJtq09IAziBHuj5a6DKROoZeKyQ", "oMJtq07RDHZZ_gAOZlxCL8JL7bnU", "oMJtq0-nfBhoap4IBtTWf8KcQMkw", "oMJtq0-5jxJmB1bw56ZQcEVKUQmg", "oMJtq06Hut5VHlkDnK-xH20y6CT8", "oMJtq0ySSRH85o3IszQq4zr_8xEA", "oMJtq0yE9oEMqCkUJt2iWET9tcoQ", "oMJtq06XZH2ll617BbwQPHzA7nTM", "oMJtq01ZV6usnVmcnKm9PlMaA-5k", "oMJtq08n9EgckkAAlv7S3m-TOnp8", "oMJtq069Q8Jh9wzsE092cfhtXjcQ"]
			},
			"count": 17,
			"next_openid": "oMJtq069Q8Jh9wzsE092cfhtXjcQ"
		}*/
		StringBuffer stringBuffer = new StringBuffer();
		Path path = Paths.get("test.json");
		List<String> allLines = Files.readAllLines(path);
		allLines.forEach(e->stringBuffer.append(e));
		System.out.println(stringBuffer);
		
		String s = stringBuffer.toString();
		JSONObject json = JSON.parseObject(s);
		System.out.println(json.getJSONObject("data").getJSONArray("openid").size());
		
	}
}

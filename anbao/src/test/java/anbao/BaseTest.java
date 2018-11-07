package anbao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}

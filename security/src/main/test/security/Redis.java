package security;

import java.util.HashMap;
import java.util.Map;

import liyu.test.framework.shiro.JedisPoolManager;
import redis.clients.jedis.Jedis;

public class Redis {
	public static void main(String[] args) {
		JedisPoolManager manager = new JedisPoolManager();
		Jedis jedis = manager.getJedis(0);
		jedis.hset("m1", "a", "b");//m1:{a:b} 
		Map<String, String> map = jedis.hgetAll("m1");
		System.out.println(map.get("a"));//echo b
		jedis.del("m1");//delete m1
		Map<String, String> map2 = new HashMap<String,String>();
		map2.put("a", "1");
		jedis.hmset("m1", map2);//rebuild m1
		System.out.println(jedis.hgetAll("m1"));//echo {a:1}
		jedis.del("m1");
	}
}

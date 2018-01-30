package liyu.test.framework.shiro;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolManager {
	private JedisPool jedisPool = RedisFactory.getInstence().getJedisPool();
    
    public Jedis getJedis(int index) {
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(index);
            return jedis;
        } catch (Exception e) {
            return retry(index);
        	/*return null;*/
        }
		
    }
    
	private Jedis retry(int index) {
		ProcessBuilder proc = new ProcessBuilder("D:/redis/redis-server.exe");
		try {
			proc.start();
			
			Thread.sleep(2000);
			Jedis jedis = jedisPool.getResource();
			if(jedis!=null){
				jedis.select(index);
				return jedis;
			}else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void releaseJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
	
	
}
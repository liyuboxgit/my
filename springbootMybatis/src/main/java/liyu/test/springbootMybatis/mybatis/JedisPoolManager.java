package liyu.test.springbootMybatis.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolManager {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private JedisPool jedisPool;
	
	public JedisPoolManager(String ip,String port) {
		this.init(ip, port);
	}
	
	public void init(String ip,String port) {	
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(false);
        this.jedisPool = new JedisPool(config, ip, Integer.parseInt(port));
	}
	
    
    public Jedis getJedis(int index) {
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(index);
            return jedis;
        } catch (Exception e) {
           logger.error("can not get redis connection", e);
        }
		return null;
    }
    
	public void releaseJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
	
	public void clearCache() {
		Jedis jedis = getJedis(0);
		jedis.flushDB();
	}
}
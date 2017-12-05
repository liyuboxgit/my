package liyu.test.framework.shiro;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisFactory {
	private JedisPool jedisPool;
    public JedisPool getJedisPool() {
    	return jedisPool;
    }

    private RedisFactory(){
    	Properties properties = new Properties();    	
    	InputStream in = null; 
    	try {
    		in = this.getClass().getResourceAsStream("/redis.properties");
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(in!=null)
			try {
				in.close();
			} catch (IOException e) {}
		}
    	
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(false);
        this.jedisPool = new JedisPool(config, properties.getProperty("ip"), Integer.parseInt(properties.getProperty("port")));
	}
	
	
    private static class Holder{
    	private static RedisFactory instence = new RedisFactory();
    }
    
  
	public static RedisFactory getInstence(){
		return Holder.instence;
	}
	
	
}

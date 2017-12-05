package liyu.test.framework.mybatis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.apache.log4j.Logger;

import liyu.test.framework.shiro.JedisPoolManager;
import liyu.test.framework.shiro.SerializeUtil;
import redis.clients.jedis.Jedis;
/**
 * 
 * @ClassName: All cache set in one redisdb using hash.
 * @Description: TODO
 * @author: liyu
 * @date: 2017年10月17日 上午10:33:14
 */
public class MybatisRedisCache extends JedisPoolManager implements Cache{
	private static Logger logger = Logger.getLogger(MybatisRedisCache.class);
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();   
    
    private String id;
    private byte[]  key; 
      
    public MybatisRedisCache(final String id) {    
        if (id == null) {  
            throw new IllegalArgumentException("Cache instances require an ID");  
        }  
        logger.debug("MybatisRedisCache:id="+id);  
        this.id = id; 
        this.key = id.getBytes();
        this.rebuild();
    }  
    /**
     * 
     * @Title: rebuild 
     * @Description: 删除hash，后建一个空hash
     * @return: void
     */
    private void rebuild(){
    	Jedis redisClient = this.getJedis(0);
		try {
			redisClient.del(this.key);
			redisClient.hset(this.key, "".getBytes(), "".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseJedis(redisClient);
		}
    }
    
	public String getId() {
		return this.id;
	}
	
	public void putObject(Object key, Object value) {
		Jedis redisClient = this.getJedis(0);
		try {
			redisClient.hset(this.key, SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseJedis(redisClient);
		}
	}

	public Object getObject(Object key) {
		Jedis redisClient = this.getJedis(0);
		try {
			return SerializeUtil.deserialize(redisClient.hget(this.key,SerializeUtil.serialize(key.toString())), Object.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			this.releaseJedis(redisClient);
		}
	}

	public Object removeObject(Object key) {
		Jedis redisClient = this.getJedis(0);
		try {
			return redisClient.hdel(this.key, SerializeUtil.serialize(key.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			this.releaseJedis(redisClient);
		}
	}

	public void clear() {
		this.rebuild();
	}

	public int getSize() {
		return 0;
		/*Jedis redisClient = this.getJedis(0);
		try {			
			return Integer.valueOf(redisClient.dbSize().toString()); 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			this.releaseJedis(redisClient);
		}*/
	}

	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
}

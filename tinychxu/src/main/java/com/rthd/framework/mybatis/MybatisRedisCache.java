package com.rthd.framework.mybatis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rthd.framework.config.shiro.JedisPoolManager;
import com.rthd.framework.config.shiro.SerializeUtil;
import com.rthd.framework.util.Conf;

import redis.clients.jedis.Jedis;
/**
 * 
 * @ClassName: All cache set in one redisdb using hash.
 * @Description: TODO
 * @author: liyu
 * @date: 2017年10月17日 上午10:33:14
 */
public class MybatisRedisCache implements Cache{
	static Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();   
    
	private JedisPoolManager pm = new JedisPoolManager(Conf.get().getRedis_ip(), Conf.get(null).getRedis_port());;
	
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
    	Jedis redisClient = pm.getJedis(0);
		try {
			redisClient.del(this.key);
			redisClient.hset(this.key, "".getBytes(), "".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(redisClient);
		}
    }
    
	public String getId() {
		return this.id;
	}
	
	public void putObject(Object key, Object value) {
		Jedis redisClient = pm.getJedis(0);
		try {
			redisClient.hset(this.key, SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(redisClient);
		}
	}

	public Object getObject(Object key) {
		Jedis redisClient = pm.getJedis(0);
		try {
			return SerializeUtil.deserialize(redisClient.hget(this.key,SerializeUtil.serialize(key.toString())), Object.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.releaseJedis(redisClient);
		}
	}

	public Object removeObject(Object key) {
		Jedis redisClient = pm.getJedis(0);
		try {
			return redisClient.hdel(this.key, SerializeUtil.serialize(key.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			pm.releaseJedis(redisClient);
		}
	}

	public void clear() {
		this.rebuild();
	}

	public int getSize() {
		return 0;
	}

	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}
	
	public void setJedisPoolManager(JedisPoolManager jedisPoolManager) {
		this.pm = jedisPoolManager;
	}
}

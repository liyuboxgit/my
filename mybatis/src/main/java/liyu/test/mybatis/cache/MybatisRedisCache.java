package liyu.test.mybatis.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
/**
 * 
 * @ClassName: All cache set in one redisdb using hash.
 * @Description: TODO
 * @author: liyu
 * @date: 2017年10月17日 上午10:33:14
 */
public class MybatisRedisCache extends JedisPoolManager implements Cache{
	private static final Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();   
    
    private String id;
    private byte[]  key; 
    private String relatedCacheIds = null; 
      
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
		logger.debug("putObject.............");  
		
		Jedis redisClient = this.getJedis(0);
		try {
			if(value!=null)
				redisClient.hset(this.key, SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseJedis(redisClient);
		}
	}

	public Object getObject(Object key) {
		logger.debug("getObject.............");  
		
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
		logger.debug("clear.............");  
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
		return /*readWriteLock*/null;
	}
	
	public String[] getRelatedCacheIds() {
		if(this.relatedCacheIds != null && this.relatedCacheIds.trim().length()>0)
			return relatedCacheIds.split(",");
		else
			return new String[]{};
	}
	
	public void setRelatedCacheIds(String relatedCacheIds) {
		this.relatedCacheIds = relatedCacheIds;
	}

}

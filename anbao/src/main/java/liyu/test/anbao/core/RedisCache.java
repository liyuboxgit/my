package liyu.test.anbao.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liyu.test.anbao.core.JedisPoolManager;
import liyu.test.anbao.core.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class RedisCache<K, V>{
	
	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);
	
	private JedisPoolManager pm;
	
	public RedisCache(JedisPoolManager pm) {
		this.pm = pm;
	}
	
	@SuppressWarnings("unchecked")
	public V get(K key) {
		if (key == null) {
			return null;
		}
		logger.debug(key.toString());
		V v = null;
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] value = jedis.get(SerializeUtil.serialize(key));
			if(value!=null) {				
				v = (V)SerializeUtil.deserialize(value).readObject();
			}
		} catch (JedisException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
		return v;
	}

	public V put(K key, V value, int expt) {
		if (key == null) {
			return null;
		}
		logger.debug(key.toString()+"("+ value==null? "true":"false" +")");
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] keys = SerializeUtil.serialize(key);
			byte[] values = SerializeUtil.serialize(value);
			jedis.setex(keys, expt, values);
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
		return null;
	}

	public void reset(K key, int expt) {
		if(key!=null) {
			Jedis jedis = pm.getJedis(0);
			try {
				byte[] keys = SerializeUtil.serialize(key);
				jedis.expire(keys, expt);
			} catch (JedisException e) {
				e.printStackTrace();
			} finally {
				pm.releaseJedis(jedis);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		if (key == null) {
			return null;
		}
		V v = null;
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] value = jedis.get(SerializeUtil.serialize(key));
			v = (V)SerializeUtil.deserialize(value).readObject();
			if(v != null) {
				jedis.del(SerializeUtil.serialize(key));
			}
		} catch (JedisException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
		return v;
	}

	public void clear() {
		Jedis jedis = pm.getJedis(0);
		try {
			jedis.flushDB();
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
	}
}


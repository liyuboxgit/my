package liyu.test.anbao.core;

import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	@SuppressWarnings("unchecked")
	public static <T> T gets(Serializable key,Class<T> type,JedisPoolManager jpm) {
		if (key == null) {
			return null;
		}
		T v = null;
		Jedis jedis = jpm.getJedis(0);
		try {
			byte[] value = jedis.get(SerializeUtil.serialize(key));
			v = (T)SerializeUtil.deserialize(value).readObject();
			if(v != null) {
				jedis.del(SerializeUtil.serialize(key));
			}
		} catch (JedisException | ClassNotFoundException | IOException | ClassCastException e) {
			e.printStackTrace();
		} finally {
			jpm.releaseJedis(jedis);
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
	
	public static void puts(Serializable key,Serializable value,int expt, JedisPoolManager jpm) {
		Jedis jedis = jpm.getJedis(0);
		try {
			byte[] keys = SerializeUtil.serialize(key);
			byte[] values = SerializeUtil.serialize(value);
			jedis.setex(keys, expt, values);
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			jpm.releaseJedis(jedis);
		}
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
	
	public static void resets(Serializable key, JedisPoolManager jpm, int expt) {
		if(key!=null) {
			Jedis jedis = jpm.getJedis(0);
			try {
				byte[] keys = SerializeUtil.serialize(key);
				jedis.expire(keys, expt);
			} catch (JedisException e) {
				e.printStackTrace();
			} finally {
				jpm.releaseJedis(jedis);
			}
		}
	}
	
	public void remove(K key) {
		Jedis jedis = pm.getJedis(0);
		try {
			byte[] value = jedis.get(SerializeUtil.serialize(key));
			Object v = SerializeUtil.deserialize(value).readObject();
			if(v != null) {
				jedis.del(SerializeUtil.serialize(key));
			}
		} catch (JedisException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			pm.releaseJedis(jedis);
		}
	}

	public static void removes(Serializable key, JedisPoolManager jpm) {
		Jedis jedis = jpm.getJedis(0);
		try {
			byte[] value = jedis.get(SerializeUtil.serialize(key));
			Object v = SerializeUtil.deserialize(value).readObject();
			if(v != null) {
				jedis.del(SerializeUtil.serialize(key));
			}
		} catch (JedisException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			jpm.releaseJedis(jedis);
		}
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
	
	public static void clears(JedisPoolManager jpm) {
		Jedis jedis = jpm.getJedis(0);
		try {
			jedis.flushDB();
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {
			jpm.releaseJedis(jedis);
		}
	}
}

